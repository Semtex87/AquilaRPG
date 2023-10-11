package net.vaex.aquilarpg.entity.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.vaex.aquilarpg.entity.RPGModEntities;
import net.vaex.aquilarpg.item.RPGItems;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class FireArrowEntity extends AbstractArrow {
    private int knockback = 1;
    private boolean hasImpacted;
    private float fireDamage = 6.0F;


    public FireArrowEntity(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(RPGModEntities.FIRE_ARROW.get(), p_36722_);
    }

    public FireArrowEntity(EntityType<? extends AbstractArrow> p_36717_, LivingEntity p_36718_, Level p_36719_) {
        super(RPGModEntities.FIRE_ARROW.get(), p_36718_, p_36719_);
    }

    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        LivingEntity ptarget = entity instanceof LivingEntity ? (LivingEntity) entity : null;
        float damage = 6.0f;
        Entity owner = this.getOwner();
        DamageSource src = owner == null ? new IndirectEntityDamageSource("fire_arrow", this, this.getOwner()).setProjectile() : new IndirectEntityDamageSource("fire_arrow.player", this, this.getOwner()).setProjectile();
        this.hasImpacted = true;
        if (entity.hurt(src, damage)) {
            if (ptarget != null) {
                    if (!this.level.isClientSide) {
                        if (!ptarget.fireImmune()) {
                            ptarget.setSecondsOnFire(10);
                        }
                    EnchantmentHelper.doPostHurtEffects(ptarget, owner);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity) owner, ptarget);
                }
                this.doPostHurtEffects(ptarget);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));


    }
    @Nullable
    protected EntityHitResult findHitEntity(@NotNull Vec3 position, @NotNull Vec3 projection) {
        return this.hasImpacted ? null : super.findHitEntity(position, projection);
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult ray) {
        super.onHitBlock(ray);
        BlockState theBlockYouHit = this.level.getBlockState(ray.getBlockPos());
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(RPGItems.FIRE_ARROW.get());
    }

    public void tick() {
        if (this.inGroundTime > 4 && !hasImpacted) {
            this.hasImpacted = true;
        }
        this.level.addParticle(ParticleTypes.FLAME, this.xo, this.yo, this.zo, 0.0D, 0.0D, 0.0D);
        super.tick();

    }

    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.ARROW_HIT;
    }
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
