package net.vaex.aquilarpg.entity.item;

import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import net.vaex.aquilarpg.entity.RPGModEntities;
import net.vaex.aquilarpg.item.custom.RPGBrokenBottleItem;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class BottleEntity extends AbstractArrow implements IEntityAdditionalSpawnData {
    public ItemStack bottleItem;
    private BlockState lastState;
    public boolean LightBlock=false;
    private static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.defineId(BottleEntity.class, EntityDataSerializers.BOOLEAN);
    private boolean hasImpacted;

    public BottleEntity(EntityType<? extends BottleEntity> type, Level world) {
        super(type, world);
    }

    public BottleEntity(Level world, LivingEntity owner, ItemStack item) {
        super(RPGModEntities.BOTTLE.get(), owner, world);
        this.bottleItem = item.copy();
        this.entityData.set(ID_FOIL, item.hasFoil());
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_FOIL, false);
    }

    int ticksSpinning = 0;

    public int GetSpinTicks() {
        return ticksSpinning;
    }

    public void tick() {
        if (this.inGroundTime > 1 && !hasImpacted) {
            this.hasImpacted = true;
        }

        if (!hasImpacted) {
            ticksSpinning++;
        }

        super.tick();
    }


    protected @NotNull ItemStack getPickupItem() {
        return this.bottleItem.copy();
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
        float damage = ((RPGBrokenBottleItem) bottleItem.getItem()).getActualAttackDamage();
        Entity owner = this.getOwner();
        DamageSource src = owner == null ? new IndirectEntityDamageSource("bottle", this, this.getOwner()).setProjectile() : new IndirectEntityDamageSource("bottle.player", this, this.getOwner()).setProjectile();
        this.hasImpacted = true;
        if (entity.hurt(src, damage)) {
            if (living != null) {
                if (!(living.getMobType().equals(MobType.UNDEAD))) {
                    if (!(living.isBlocking())) {
                        for (MobEffectInstance mobeffectinstance : PotionUtils.getMobEffects(bottleItem)) {
                            if (mobeffectinstance.getEffect().isInstantenous()) {
                                mobeffectinstance.getEffect().applyInstantenousEffect(owner, owner, living, mobeffectinstance.getAmplifier(), 1.0D);
                            } else {
                                living.addEffect(new MobEffectInstance(mobeffectinstance));

                            }
                        }
                    }
                }

                living.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 0));
                this.level.addParticle(ParticleTypes.SPLASH, living.xo, living.yo, living.zo, 0.0D, 0.0D, 0.0D);
                this.discard();
                living.playSound(SoundEvents.GLASS_BREAK, 2.5F, 1.0F);
                EnchantmentHelper.doPostHurtEffects(living, owner);
                EnchantmentHelper.doPostDamageEffects((LivingEntity) owner, living);
                this.doPostHurtEffects(living);
            }
        }
        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult ray) {
        super.onHitBlock(ray);
        this.playSound(SoundEvents.GLASS_BREAK, 2.5F, 1.0F);
        this.spawnLingeringCloud();
        this.discard();

    }

    private void spawnLingeringCloud() {
        AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
        areaeffectcloud.setParticle(ParticleTypes.COMPOSTER);
        areaeffectcloud.setOwner((LivingEntity) this.getOwner());
        areaeffectcloud.setRadius(1.5F);
        areaeffectcloud.setRadiusOnUse(-0.5F);
        areaeffectcloud.setWaitTime(10);
        areaeffectcloud.setDuration(areaeffectcloud.getDuration() / 2);
        areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float) areaeffectcloud.getDuration() /2);
        for (MobEffectInstance mobeffectinstance : PotionUtils.getMobEffects(bottleItem)) {
            areaeffectcloud.addEffect(new MobEffectInstance(mobeffectinstance));
        }
        this.level.addFreshEntity(areaeffectcloud);
    }




    protected float getWaterInertia() {
        return 0.99F;
    }

    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("dagger", 10)) {
            this.bottleItem = ItemStack.of(tag.getCompound("dagger"));
        }
        this.hasImpacted = tag.getBoolean("DealtDamage");
    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("dagger", this.bottleItem.save(new CompoundTag()));
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
        buffer.writeItem(this.bottleItem);
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        this.bottleItem = additionalData.readItem();
    }

}
