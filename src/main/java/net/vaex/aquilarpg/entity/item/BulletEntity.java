package net.vaex.aquilarpg.entity.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.entity.RPGModEntities;
import net.vaex.aquilarpg.util.RPGSoundEvents;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BulletEntity extends Projectile {
    private static final EntityDataAccessor<Boolean> HIT =
            SynchedEntityData.defineId(OrbOfLifeEntity.class, EntityDataSerializers.BOOLEAN);
    private int counter = 0;
    private boolean hasImpacted;

    public BulletEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(RPGModEntities.BULLET.get(), pLevel);
    }

    public BulletEntity(EntityType<BulletEntity> bulletEntityEntityType, LivingEntity shooter, Level world) {
        super(bulletEntityEntityType, world);

    }

    public BulletEntity(Level pLevel, Player player) {
        super(RPGModEntities.BULLET.get(), pLevel);
        setOwner(player);
        BlockPos blockpos = player.blockPosition();
        double d0 = (double)blockpos.getX() + 0.5D;
        double d1 = (double)blockpos.getY() + 1.75D;
        double d2 = (double)blockpos.getZ() + 0.5D;
        this.moveTo(d0, d1, d2, this.getYRot(), this.getXRot());
    }

    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        LivingEntity ptarget = entity instanceof LivingEntity ? (LivingEntity) entity : null;
        float damage = 10.0f;
        Entity owner = this.getOwner();
        DamageSource src = owner == null ? new IndirectEntityDamageSource("poison_dart", this, this.getOwner()).setProjectile() : new IndirectEntityDamageSource("poison_dart.player", this, this.getOwner()).setProjectile();
        this.hasImpacted = true;
        if (entity.hurt(src, damage)) {
            if (ptarget != null) {
                super.onHitEntity(result);
                if (!this.level.isClientSide) {
                    this.level.playSound(null, this.getX(), this.getY(), this.getZ(), RPGSoundEvents.BLEEDING_EFFECT.get(), SoundSource.NEUTRAL,2F, 1F);
                }
                    if (new Random().nextInt(5) == 1) {
                    ptarget.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 300, 1), owner);

                }
                this.discard();
            }
        }
        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult ray) {
        super.onHitBlock(ray);
        this.discard();
    }


    @Override
    public void tick() {
        super.tick();
        if(this.entityData.get(HIT)) {
            if(this.tickCount >= counter) {
                this.discard();
            }
        }

        if (this.tickCount >= 300) {
            this.remove(RemovalReason.DISCARDED);
        }
        Vec3 vec3 = this.getDeltaMovement();
        HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult))
            this.onHit(hitresult);

        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.updateRotation();

        double d5 = vec3.x;
        double d6 = vec3.y;
        double d7 = vec3.z;

        for(int i = 1; i < 5; ++i) {
            this.level.addParticle(ParticleTypes.ASH, d0-(d5*2), d1-(d6*2), d2-(d7*2),
                    -d5, -d6 - 0.1D, -d7);
        }

        if (this.level.getBlockStates(this.getBoundingBox()).noneMatch(BlockBehaviour.BlockStateBase::isAir)) {
            this.discard();
        } else if (this.isInWaterOrBubble()) {
            this.discard();
        } else {
            this.setDeltaMovement(vec3.scale(1.99F));
            this.setPos(d0, d1, d2);
        }
    }



    @Override
    protected void defineSynchedData() {
        this.entityData.define(HIT, false);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}