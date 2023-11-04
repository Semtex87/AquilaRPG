package net.vaex.aquilarpg.item.custom.weapon.magic;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.vaex.aquilarpg.capabilities.mana.ClientManaData;
import net.vaex.aquilarpg.capabilities.mana.ManaProvider;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.entity.item.CustomSmallFireball;
import net.vaex.aquilarpg.entity.item.OrbOfLifeEntity;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.RPGBasicMeleeWeapon;
import net.vaex.aquilarpg.item.custom.weapon.RPGMagicWeapon;
import net.vaex.aquilarpg.network.ManaC2SPacket;
import net.vaex.aquilarpg.network.ManaSyncS2CPacket;
import net.vaex.aquilarpg.network.NetworkHandler;
import net.vaex.aquilarpg.util.RPGCreativeModeTab;
import org.jetbrains.annotations.NotNull;
import org.jline.utils.Log;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ScepterOfLife extends RPGMagicWeapon {
    protected final Random random = new Random();
private int manaConsume = 50;
private int lifeconsume = 2;
    private static float radius = 1.5f;
    private int duration = 800;

    public ScepterOfLife(RPGMaterialTiers pTier, Properties pProperties) {
        super(pTier, pProperties.tab(RPGCreativeModeTab.RPG_WEAPON));
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull Entity entity, int slot, boolean selected) {
        if (entity instanceof Player) {
            //auto recharge durability
            if (!selected &&new Random().nextInt(200) == 1) {
                itemstack.setDamageValue(itemstack.getDamageValue() + (-1));
            }
        }
        super.inventoryTick(itemstack, world, entity, slot, selected);
    }

    private void addParticlesAroundSelf(Entity entity, Level level) {
        for (int i = 0; i < 5; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            level.addParticle(getParticle(), entity.getRandomX(0.5D), entity.getRandomY() + 0.5D, entity.getRandomZ(0.5D), d0, d1, d2);
        }
    }
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pstack) {
        return UseAnim.BOW;
    }

    private ParticleOptions getParticle() {
        return ParticleTypes.CRIMSON_SPORE;
    }
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pPlayer.startUsingItem(pHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (!pLevel.isClientSide) {
            if (pEntityLiving instanceof ServerPlayer serverplayer && !serverplayer.isCreative()) {
                InteractionHand hand = serverplayer.getUsedItemHand();
                serverplayer.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
                    int i = this.getUseDuration(pStack) - pTimeLeft;
                    if (i > 10) {
                        if (mana.getMana() > manaConsume) {
                            mana.subMana(manaConsume);
                            Log.info(manaConsume);
                            pLevel.playSound(null, serverplayer.getX(), serverplayer.getY(), serverplayer.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.5F, 1F);
                            OrbOfLifeEntity orbOfLife = new OrbOfLifeEntity(pLevel, serverplayer);
                            orbOfLife.shootFromRotation(serverplayer, serverplayer.getXRot(), serverplayer.getYRot(), 0.0F, 1.5F, 0.25F);
                            pLevel.addFreshEntity(orbOfLife);
                            NetworkHandler.sendPacketTo(new ManaSyncS2CPacket(mana.getMana()), serverplayer);
                            for (int j = 0; j < 4; ++j) {
                                addParticlesAroundSelf(serverplayer, pLevel);
                            }
                        } else {
                            serverplayer.sendMessage(new TextComponent("not enough mana"), serverplayer.getUUID());
                            pLevel.playSound(null, serverplayer.getX(), serverplayer.getY(), serverplayer.getZ(), SoundEvents.LAVA_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.0F);

                        }
                    }
                });
                //spawnLingeringCloud(pLevel, leftPos1, pEntityLiving);
                serverplayer.awardStat(Stats.ITEM_USED.get(this));
                if (!serverplayer.getAbilities().instabuild) {
                    pStack.hurtAndBreak(1, serverplayer, p -> p.broadcastBreakEvent(hand));
                }
            }
        }
    }




    private void spawnLingeringCloud(Level level, BlockPos cloud, LivingEntity livingEntity) {
        if (!level.isClientSide()) {
            AreaEffectCloud areaeffectcloud = new AreaEffectCloud(level, cloud.getX(), cloud.getY() - 1, cloud.getZ());
            areaeffectcloud.setParticle(getParticle());
            areaeffectcloud.setRadius(radius);
            areaeffectcloud.setOwner(livingEntity);
            areaeffectcloud.setRadiusOnUse(0.5F);
            areaeffectcloud.setWaitTime(10);
            areaeffectcloud.setDuration(duration / 2);
            areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float) areaeffectcloud.getDuration() / 2);
            areaeffectcloud.canCollideWith(livingEntity);
            areaeffectcloud.addEffect(new MobEffectInstance(RPGEffectManager.AGING.get()));
            //setAge(level, areaeffectcloud.getX(), areaeffectcloud.getY(), areaeffectcloud.getZ(), areaeffectcloud);
            level.addFreshEntity(areaeffectcloud);
        }
    }

    public void setAge(LevelAccessor world, double x, double y, double z, Entity entity) {
        final Vec3 _center = new Vec3(x, y, z);
        if (entity instanceof AreaEffectCloud cloud) {
            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(radius), e -> true).stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
            for (Entity entityiterator : _entfound) {
                if (entityiterator.equals(cloud.getOwner())) {
                    if (entityiterator instanceof LivingEntity _entity) {
                        _entity.addEffect(new MobEffectInstance(MobEffects.GLOWING));
                    }
                }
                if (!(entityiterator == entity)) {
                    if (entityiterator instanceof LivingEntity _entity) {
                        if(_entity instanceof AgeableMob livingHitEntity) {
                            livingHitEntity.ageUp(2000);
                        }
                    }
                }

            }
        }
    }
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(new TextComponent("this Scepter looks like a root and regenerate itself"));
        tooltip.add(new TextComponent("It drains life of the holder to create..."));
        tooltip.add(new TextComponent("Mana consume: " + manaConsume + " / cast"));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
