package net.vaex.aquilarpg.util;



import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.vaex.aquilarpg.item.custom.weapon.RPGLanceWeapon;
import org.jline.utils.Log;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RPGCustomUtils {
    public static boolean isDarkness(LivingEntity entity) {
        BlockPos blockpos = entity.blockPosition();
        Level level = entity.level;
        int i = level.getLightEmission(blockpos);
        level.isThundering();
        return i < 8;
    }

    public static void setMobGlow(LevelAccessor world, double x, double y, double z, Entity entity) {
        final Vec3 _center = new Vec3(x, y, z);//INFRAVISION
        List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(20), e -> true).stream()
                .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
        for (Entity entityiterator : _entfound) {
            if (!(entityiterator == entity)) {
                if (entityiterator instanceof LivingEntity _entity && _entity.getMobType().equals(MobType.UNDEAD)) {
                    _entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 60, 1, (true), (true)));
                }
            }
        }
    }

    public static void setMobOnFire(LevelAccessor world, double x, double y, double z, Entity entity) {
        final Vec3 _center = new Vec3(x, y, z);//INFRAVISION
        List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(20), e -> true).stream()
                .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
        for (Entity entityiterator : _entfound) {
            if (!(entityiterator == entity)) {
                if (entityiterator instanceof LivingEntity _entity) {
                    if (!_entity.fireImmune()) {
                        _entity.setSecondsOnFire(10);
                    }
                }
            }
        }
    }







    public static void setUndeadDetect(LevelAccessor world, double x, double y, double z, Entity entity) {
        final Vec3 _center = new Vec3(x, y, z);//STING ITEM UNDEAD DETECT (No orcs lol...)
        List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(20), e -> true).stream()
                .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
        for (Entity entityiterator : _entfound) {
            if (!(entityiterator == entity)) {
                if (entityiterator instanceof LivingEntity _entity && _entity.getMobType().equals(MobType.UNDEAD)) {
                    if (entity instanceof Player player) {
                        player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 0, 0, (false), (false), (false)));
                    }
                }
            }
        }

    }

    public static void killonSight(LevelAccessor level, double x, double y, double z, Entity entity) {
        final Vec3 _center = new Vec3(x, y, z);
        if (level != null && !level.isClientSide()) {
            List<Entity> _entfound = level.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(1, -1, 2), e -> true).stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
            if (entity instanceof Player player && player.getMainHandItem().getItem() instanceof RPGLanceWeapon lance) {
                if (player.isPassenger() && player.getRootVehicle() instanceof AbstractHorse horse) {
                    if (!lance.getRaised()) {
                        Log.info(player.getMainHandItem().getItem() + "rised?" + (lance.getRaised()));
                        for (Entity entityiterator : _entfound) {
                            if (!(entityiterator == entity)) {
                                if (entityiterator instanceof LivingEntity _entity && !(_entity instanceof Horse)) {
                                    ((LivingEntity) entityiterator).knockback(1, 0, 0);
                                    level.addParticle(ParticleTypes.CRIT, entityiterator.getX(), entityiterator.getY(), entityiterator.getZ(), 0.0D, 0.0D, 0.0D);
                                    player.playSound(SoundEvents.PLAYER_ATTACK_CRIT, 1.0F, 1.0F);
                                    if (horse instanceof Horse breed) {
                                        float breedSpeed = (float) breed.getAttributeValue(Attributes.MOVEMENT_SPEED);
                                        float impactDamageMultiplicator = (float) (Math.PI * ((lance.getActualAttackDamage() * 3.0f) * breedSpeed));
                                        float toughness = (float) ((LivingEntity) entityiterator).getAttributeValue(Attributes.ARMOR_TOUGHNESS);
                                        float finalDamage = CombatRules.getDamageAfterAbsorb((lance.getActualAttackDamage() * impactDamageMultiplicator), (float) ((LivingEntity) entityiterator).getArmorValue(), toughness);
                                        if (!((LivingEntity) entityiterator).isBlocking()) {
                                            entityiterator.hurt(DamageSource.GENERIC, finalDamage);
                                            Log.info(" hurt: " + entityiterator + "with" + player.getMainHandItem().getItem() + " " + finalDamage + "  damage");
                                        } else if (((LivingEntity) entityiterator).isBlocking()) {
                                            ItemStack offHandTarget = ((LivingEntity) entityiterator).getOffhandItem();
                                            ((LivingEntity) entityiterator).knockback(1, 1, 1);
                                            offHandTarget.hurtAndBreak((int) finalDamage, ((LivingEntity) entityiterator), (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                                        }
                                        Log.info(horse + " has : " + breedSpeed + " speed");
                                        lance.setRiseAfterHit(true);
                                        player.getCooldowns().addCooldown(player.getMainHandItem().getItem(), 60);
                                    }
                                }
                            }
                            if (entityiterator instanceof LivingEntity _entity && (_entity instanceof Horse)) {
                                if (!(horse.isSaddled())) {
                                    entityiterator.hurt(DamageSource.GENERIC, 3);
                                }
                            }
                        }

                    }

                }
            }

        }
    }


    public static boolean isOnTeam(Player player, LivingEntity entity) {
        if (entity == player) {
            return true;
        } else if (entity.isAlliedTo(player) || player.isAlliedTo(entity)) {
            return true;
        } else if (entity instanceof TamableAnimal) {
            return ((TamableAnimal) entity).getOwner() == player;
        } else {
            return false;
        }
    }

    public static boolean canEntitySeeSky(Level level, Entity entity) {
        BlockPos blockpos1 = entity.blockPosition();
        while (blockpos1.getY() < level.getHeight()) {
            if (!level.canSeeSky(blockpos1)) {
                return false;
            }
            blockpos1 = blockpos1.above();
        }
        return true;
    }

    public static boolean behindTarget(Level level, double x, double y, double z,Player player, Entity entity) {
        BlockPos blockpos1 = player.blockPosition();
        BlockPos blockpos2 = entity.blockPosition();
        final Vec3 _center = new Vec3(x, y, z);
        return true;
    }

    public static void isNotLookingAtPlayer(Level level, double x, double y, double z, Player player, Entity target) {
        AABB entityAABB = target.getBoundingBox().inflate(2.0D, 1.0D, 2.0D);
        List<Entity> _entfound = level.getEntitiesOfClass(Entity.class, entityAABB);
        for (Entity entityiterator : _entfound) {
            Log.info(entityiterator + " entered area of " + target);
            if (entityiterator instanceof Player) {
                if (target instanceof LivingEntity livingEntity) {
                    if (!livingEntity.hasLineOfSight(player)) {
                        Log.info(player + " is hidden...");
                    }
                }
            }
        }
    }


    public static boolean isInLiquid(Entity entity) {
        BlockState state = entity.getLevel().getBlockState(new BlockPos((Position) entity));
        return state.getMaterial().isLiquid();
    }

    public static boolean isColliding(Player player, Entity entity) {
        BlockPos blockpos1 = player.blockPosition();
        BlockPos blockpos2 = entity.blockPosition();
        if (blockpos1.equals(blockpos2)) {
            return true;
        } else {
            return false;
        }
    }


    public static void knockTargetBack(LivingEntity pushedEntity, LivingEntity pushingEntity) {
        if (pushedEntity.isPushable()) {
            double dx = pushedEntity.getX() - pushingEntity.getX();
            double dz;
            for (dz = pushedEntity.getZ() - pushingEntity.getZ(); dx * dx + dz * dz < 1.0E-4D; dz = (Math.random() - Math.random()) * 0.01D){
                dx = (Math.random() - Math.random()) * 0.01D;
            }
            pushedEntity.push( 0, -dx, -dz);
        }
    }


    public static void register() { }
}