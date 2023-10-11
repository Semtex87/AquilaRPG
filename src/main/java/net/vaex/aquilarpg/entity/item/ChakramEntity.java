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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.entity.RPGModEntities;
import net.vaex.aquilarpg.item.custom.weapon.RPGDaggerWeapon;
import net.vaex.aquilarpg.util.RPGSoundEvents;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;
                                                                                                                        //TODO
public class ChakramEntity extends AbstractArrow implements IEntityAdditionalSpawnData {
    public ItemStack daggerItem;
    private BlockState lastState;
    private static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.defineId(ChakramEntity.class, EntityDataSerializers.BOOLEAN);
    private boolean hasImpacted;

    public ChakramEntity(EntityType<? extends ChakramEntity> type, Level world) {
        super(type, world);
    }

    public ChakramEntity(Level world, LivingEntity owner, ItemStack item) {
        super(RPGModEntities.DAGGER.get(), owner, world);
        this.daggerItem = item.copy();
        this.entityData.set(ID_FOIL, item.hasFoil());
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_FOIL, false);
    }

    public void tick() {
        if (this.inGroundTime > 1 && !hasImpacted) {
            this.hasImpacted = true;
        }

        super.tick();
    }

    protected @NotNull ItemStack getPickupItem() {
        return this.daggerItem.copy();
    }

    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unused")
    public boolean isFoil() {
        return this.entityData.get(ID_FOIL);
    }

    @Nullable
    protected EntityHitResult findHitEntity(@NotNull Vec3 position, @NotNull Vec3 projection) {
        return this.hasImpacted ? null : super.findHitEntity(position, projection);
    }

    public boolean GetHasImpacted() {
        return hasImpacted;
    }


    protected void onHitEntity(EntityHitResult hitResult) {
        Entity entity = hitResult.getEntity();
        LivingEntity living = entity instanceof LivingEntity ? (LivingEntity) entity : null;
        float damage = ((RPGDaggerWeapon) daggerItem.getItem()).getDamage();
        int durability= (int) ((RPGDaggerWeapon) daggerItem.getItem()).getWeight();
        Entity owner = this.getOwner();
        DamageSource src = owner == null ? new IndirectEntityDamageSource("dagger", this, this.getOwner()).setProjectile() : new IndirectEntityDamageSource("dagger.player", this, this.getOwner()).setProjectile();
        this.hasImpacted = true;
        if (entity.hurt(src, damage)) {
            if (living != null) {
                if (owner instanceof LivingEntity) {
                    if (!(living.getMobType().equals(MobType.UNDEAD))) {
                        if (new Random().nextInt(3) == 1) {
                            living.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 120, 0));
                            living.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                            daggerItem.hurt(durability, new Random(), null);
                        }
                    }
//TODO Abrage ob target Rüstung trägt oder blockt!!!!! NUR Piercing DMG
                    EnchantmentHelper.doPostHurtEffects(living, owner);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity) owner, living);

                }
                this.doPostHurtEffects(living);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));

        this.playSound(SoundEvents.TRIDENT_HIT, 1.0f, 1.0F);
    }

    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("dagger", 10)) {
            this.daggerItem = ItemStack.of(tag.getCompound("dagger"));
        }
        this.hasImpacted = tag.getBoolean("DealtDamage");
    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("dagger", this.daggerItem.save(new CompoundTag()));
        tag.putBoolean("DealtDamage", this.hasImpacted);
    }

    public void tickDespawn() {
        if (this.pickup != Pickup.ALLOWED) {
            super.tickDespawn();
        }
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldRender(double x, double y, double z) {
        return true;
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeItem(this.daggerItem);
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        this.daggerItem = additionalData.readItem();
    }

}
