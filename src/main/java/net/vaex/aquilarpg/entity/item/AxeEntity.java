package net.vaex.aquilarpg.entity.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.entity.RPGModEntities;
import net.vaex.aquilarpg.item.custom.weapon.RPGAxeWeapon;
import net.vaex.aquilarpg.util.RPGSoundEvents;
import org.jetbrains.annotations.NotNull;
import org.jline.utils.Log;

import javax.annotation.Nullable;
import java.util.Random;


public class AxeEntity extends AbstractArrow implements IEntityAdditionalSpawnData
{
    public ItemStack axeItem;
    AxeEntity axeEntity;
    private static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.defineId(AxeEntity.class, EntityDataSerializers.BOOLEAN);
    private boolean hasImpacted;

    public AxeEntity(EntityType<? extends AxeEntity> type, Level world)
    {
        super(type, world);
    }

    public AxeEntity(Level world, LivingEntity owner, ItemStack item)
    {
        super(RPGModEntities.AXE.get(), owner, world);
        this.axeItem = item.copy();
        this.entityData.set(ID_FOIL, item.hasFoil());
    }

    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(ID_FOIL, false);
    }

    int ticksSpinning = 0;

    public int GetSpinTicks() {return ticksSpinning;}

    public void tick()
    {
        if (this.inGroundTime > 1 && !hasImpacted)
        {
            this.hasImpacted = true;
        }

        if(!hasImpacted)
        {
            ticksSpinning++;
        }

        super.tick();
    }

    protected @NotNull ItemStack getPickupItem()
    {

        return this.axeItem.copy();
    }


    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unused")
    public boolean isFoil()
    {
        return this.entityData.get(ID_FOIL);
    }

    @Nullable
    protected EntityHitResult findHitEntity(@NotNull Vec3 position, @NotNull Vec3 projection)
    {
        return this.hasImpacted ? null : super.findHitEntity(position, projection);
    }

    public boolean GetHasImpacted()
    {
        return hasImpacted;
    }

    protected void onHitEntity(EntityHitResult hitResult){
        Entity entity = hitResult.getEntity();
        LivingEntity living = entity instanceof LivingEntity ? (LivingEntity)entity : null;
        float damage = ((RPGAxeWeapon)axeItem.getItem()).getActualAttackDamage();
        int armorPenetration = (int) ((RPGAxeWeapon)axeItem.getItem()).getArmorPenetration();
        Entity owner = this.getOwner();
        DamageSource src = owner == null ? new IndirectEntityDamageSource("axe", this, this.getOwner()).setProjectile(): new IndirectEntityDamageSource("axe.player", this, this.getOwner()).setProjectile();
        this.hasImpacted = true;
        if (entity.hurt(src, (damage * 2))){
            if (living !=null)
            {
                if (owner instanceof LivingEntity livingEntity) {
                    if (livingEntity.isBlocking()) {
                        ItemStack blockingStack = living.getOffhandItem();
                        blockingStack.hurtAndBreak(armorPenetration, livingEntity, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                    } else {
                        ItemStack head = livingEntity.getItemBySlot(EquipmentSlot.HEAD);
                        ItemStack chest = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
                        ItemStack legs = livingEntity.getItemBySlot(EquipmentSlot.LEGS);
                        ItemStack feet = livingEntity.getItemBySlot(EquipmentSlot.FEET);
                        head.hurtAndBreak(armorPenetration, livingEntity, (var) -> var.broadcastBreakEvent(EquipmentSlot.HEAD));
                        chest.hurtAndBreak(armorPenetration, livingEntity, (var) -> var.broadcastBreakEvent(EquipmentSlot.CHEST));
                        legs.hurtAndBreak(armorPenetration, livingEntity, (var) -> var.broadcastBreakEvent(EquipmentSlot.LEGS));
                        feet.hurtAndBreak(armorPenetration, livingEntity, (var) -> var.broadcastBreakEvent(EquipmentSlot.FEET));
                    }
                    {
                        if (!(living.getMobType().equals(MobType.UNDEAD))) {
                            if (new Random().nextInt(3) == 1) {
                                living.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 120, 0));
                                living.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                            }
                        }
                        //TODO Abrage ob target Rüstung trägt oder blockt!!!!! NUR KNOCKBACK
                        EnchantmentHelper.doPostHurtEffects(living, owner);
                        EnchantmentHelper.doPostDamageEffects((LivingEntity) owner, living);
                    }
                    Log.info(owner + " hit " + livingEntity);
                    Log.info(" armorPenetration " + armorPenetration);
                }

                this.doPostHurtEffects(living);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));

        this.playSound(SoundEvents.TRIDENT_HIT, 1.0f, 1.0F);
    }

    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent()
    {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    public void readAdditionalSaveData(@NotNull CompoundTag tag)
    {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Axe", 10))
        {
            this.axeItem = ItemStack.of(tag.getCompound("Axe"));
        }

        this.hasImpacted = tag.getBoolean("DealtDamage");
    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag)
    {
        super.addAdditionalSaveData(tag);
        tag.put("Axe", this.axeItem.save(new CompoundTag()));
        tag.putBoolean("DealtDamage", this.hasImpacted);
    }

    public void tickDespawn()
    {
        if (this.pickup != Pickup.ALLOWED)
        {
            super.tickDespawn();
        }
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldRender(double x, double y, double z)
    {
        return true;
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer)
    {
        buffer.writeItem(this.axeItem);
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData)
    {
        this.axeItem = additionalData.readItem();
    }
}
