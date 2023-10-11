package net.vaex.aquilarpg.entity.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.entity.RPGModEntities;
import net.vaex.aquilarpg.item.custom.weapon.RPGSpearWeapon;
import net.vaex.aquilarpg.util.RPGSoundEvents;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;

public class SpearEntity extends AbstractArrow implements IEntityAdditionalSpawnData {
    public ItemStack spearItem;
    private static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.defineId(SpearEntity.class, EntityDataSerializers.BOOLEAN);
    private boolean hasImpacted;
    private int knockback;
    private boolean hasFirstUpdated=false;
    private float armorPiercing;
    public boolean GLASS_HIT=false;
    public double initPosX;
    public double initPosY;
    public double initPosZ;
    public double dirX;
    public double dirY;
    public double dirZ;

    public SpearEntity(EntityType<? extends SpearEntity> type, Level world)
    {
        super(type, world);
    }

    public SpearEntity(Level world, LivingEntity owner, ItemStack item) {
        super(RPGModEntities.SPEAR.get(), owner, world);
        this.spearItem = item.copy();
        this.entityData.set(ID_FOIL, item.hasFoil());
        this.knockback = (int) (((RPGSpearWeapon) spearItem.getItem()).getKnockback() + 1);
        this.armorPiercing = ((RPGSpearWeapon) spearItem.getItem()).getArmorPiercing() + 3;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_FOIL, false);
    }

    public void tick() {
        if (this.inGroundTime > 4 && !hasImpacted) {
            this.hasImpacted = true;
        }
        super.tick();

        //Flameparticles on throw
       /*
       if(this.level.isClientSide)
        {
            if(!this.inGround)
            {
                this.level.addParticle(ParticleTypes.FLAME, this.xo, this.yo, this.zo, 0.0D, 0.0D, 0.0D);
            }
        }
        */

    }
    protected float getWaterInertia() {
        return 0.99F;
    }
    protected @NotNull ItemStack getPickupItem() {
        return this.spearItem.copy();
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

    protected void onHitEntity(EntityHitResult p_213868_1_) {
        Entity entity = p_213868_1_.getEntity();
        LivingEntity ptarget = entity instanceof LivingEntity ? (LivingEntity) entity : null;
        float damage = ((RPGSpearWeapon) spearItem.getItem()).getDamage();
        Entity owner = this.getOwner();
        DamageSource src = owner == null ? new IndirectEntityDamageSource("spear", this, this.getOwner()).setProjectile() : new IndirectEntityDamageSource("spear.player", this, this.getOwner()).setProjectile();
        this.hasImpacted = true;
        if (entity.hurt(src, damage + getArmorPiercing() +getKnockback())){
            if (ptarget !=null) {
                ptarget.hurt(DamageSource.GENERIC, 1);
                if (owner instanceof LivingEntity) {
                    if (!(ptarget.getMobType().equals(MobType.UNDEAD))) {
                        if (new Random().nextInt(3) == 1) {
                            ptarget.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 120, 0));
                            ptarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                        }
                    }
                    if (ptarget.isPassenger()) {
                        ptarget.stopRiding();
                    }
                    //TODO Abrage ob target Rüstung trägt oder blockt!!!!! Für Knochback und Piercing DMG
                    EnchantmentHelper.doPostHurtEffects(ptarget, owner);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)owner, ptarget);
                }
                spawnBloodParticles(this.xo, this.yo+1, this.zo);
                this.doPostHurtEffects(ptarget);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));

        this.playSound(SoundEvents.TRIDENT_HIT, 1.0f, 1.0F);
    }
    private float getArmorPiercing() {
        return this.armorPiercing + 2;
    }

    public int getKnockback() {
        return this.knockback + 1;
    }
    @Override
    public void playerTouch(Player playerHit) {
        Entity entityShooter = this.getOwner();
        if (entityShooter == null || entityShooter.getUUID() == playerHit.getUUID()) {
            super.playerTouch(playerHit);
        }
    }
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Spear", 10)) {
            this.spearItem = ItemStack.of(tag.getCompound("Spear"));
        }

        this.hasImpacted = tag.getBoolean("DealtDamage");
    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("Spear", this.spearItem.save(new CompoundTag()));
        tag.putBoolean("DealtDamage", this.hasImpacted);
    }

    public void tickDespawn() {
        if (this.pickup != Pickup.ALLOWED) {
            super.tickDespawn();
        }
    }


    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if(!this.level.isClientSide()) {
            checkAdditionalImpact();
        }
        super.onHitBlock(pResult);
    }

    private void checkAdditionalImpact() {
        for(int x=-2; x<=2; x++)
        {
            for(int y=-2; y<=2; y++)
            {
                for(int z=-2; z<=2; z++)
                {
                    if(this.level.getBlockState(new BlockPos(this.xo+x, this.yo+y, this.zo+z)).getMaterial() == Material.GLASS)
                    {
                        this.level.playSound(null, this.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.AMBIENT, 1.0F, 1.0F);
                        GLASS_HIT=true;
                        BlockPos hitPos = new BlockPos(new BlockPos(this.xo+x, this.yo+y, this.zo+z));
                        ItemEntity droppedItem = new ItemEntity(this.level, this.xo+x, this.yo+y, this.zo+z, new ItemStack(Blocks.GLASS_PANE));
                        if(!this.level.isClientSide) {
                            this.level.addFreshEntity(droppedItem);
                        }
                        this.level.removeBlock(hitPos, false);
                        spawnSparkParticles(this.xo+x, this.yo+y, this.zo+z);
                    }
                }
            }
        }
    }

    private void spawnGroundParticles(double x, double y, double z)
    {        for(int i=0; i<5; i++) {
            this.level.addParticle(ParticleTypes.CLOUD, x, y, z, (0.25-Math.random()*0.5)*0.3, (0.25-Math.random()*0.5)*0.3, (0.25-Math.random()*0.5)*0.3);
        }
    }    private void spawnSparkParticles(double x, double y, double z)
    {
        for(int i=0; i<5; i++) {
            this.level.addParticle(ParticleTypes.CRIT, x+Math.random()*1, y+Math.random()*1, z+Math.random()*1, 1-Math.random()*2, 1-Math.random()*2, 1-Math.random()*2);
        }
    }  private void spawnBloodParticles(double x, double y, double z)
    {        for(int i=0; i<10; i++) {
            this.level.addParticle(ParticleTypes.TOTEM_OF_UNDYING, x, y, z, (1-Math.random()*2)*0.5, (1-Math.random()*2)*0.5, (1-Math.random()*2)*0.5);
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
        buffer.writeItem(this.spearItem);
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        this.spearItem = additionalData.readItem();
    }
}
