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
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.vaex.aquilarpg.capabilities.mana.ClientManaData;
import net.vaex.aquilarpg.capabilities.mana.ManaProvider;
import net.vaex.aquilarpg.entity.item.AxeEntity;
import net.vaex.aquilarpg.entity.item.CustomSmallFireball;
import net.vaex.aquilarpg.entity.item.DaggerEntity;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.RPGBasicMeleeWeapon;
import net.vaex.aquilarpg.item.custom.weapon.RPGAxeWeapon;
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

public class TeleportStaff extends RPGBasicMeleeWeapon {
    protected final Random random = new Random();
    private int manaConsume = 30;
    private int manaBonus = 10;
    private int duration = 800;
    private static float radius = 1.5f;
    int ticks;
    float currentMana;
    float currentMaxMana;
    int clicks;

    public TeleportStaff(RPGMaterialTiers pTier, Properties pProperties) {
        super(pTier, pProperties.tab(RPGCreativeModeTab.RPG_WEAPON));
    }


    @Override
    public void inventoryTick(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull Entity entity, int slot, boolean selected) {
        if (entity instanceof Player player && player.getItemInHand(InteractionHand.MAIN_HAND).equals(itemstack)) {
            if (new Random().nextInt(100) == 1) {
                addParticlesAroundSelf(entity, world);
            }
            if (entity instanceof ServerPlayer serverplayer) {
                serverplayer.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
                    mana.addMaxMana(manaBonus); // add Mana Bonus 10
                    NetworkHandler.sendPacketTo(new ManaSyncS2CPacket(mana.getMana()), serverplayer);
                });
            }
        }
        else {
            if (entity instanceof ServerPlayer serverplayer) {
                serverplayer.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
                    mana.setDefaultMaxMana();
                    /*
                    if (mana.getActualMaxMana() != mana.getDefaultMana()) { // actual Max Mana not default Max Mana Pool?  e.g. 110/100
                        int remaining = mana.getActualMaxMana() - mana.getDefaultMana(); // actual Max Mana Pool - default Max Mana Pool e.g. 110 - 100 = remaining
                        mana.subMaxMana(remaining); // sub remaining
                        //mana.subMana(manaBonus);
                    }

                    if (mana.getMana() > mana.getActualMaxMana()) { // actual Mana not more than actual Max Mana Pool?  e.g. 110/100
                        int remaining = mana.getMana() - mana.getActualMaxMana(); // actual Mana - Max Mana Pool e.g. 110 - 100 = remaining
                        mana.subMana(remaining); // sub remaining
                    }*/
                    NetworkHandler.sendPacketTo(new ManaSyncS2CPacket(mana.getMana()), serverplayer);
                });
            }
        }

        if (entity instanceof ServerPlayer serverplayer) {
            ticks++;
            if (ticks == 100) {
                serverplayer.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
                    mana.addMana(manaBonus);
                    NetworkHandler.sendPacketTo(new ManaSyncS2CPacket(mana.getMana()), serverplayer);
                });
                ticks = 0;
            }
        }
        super.inventoryTick(itemstack, world, entity, slot, selected);
    }






    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pstack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemStack) {
        return 7200;
    }
/*
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        int currentMana = ClientManaData.getPlayerMana(player);
        if (currentMana > manaConsume) {
            player.startUsingItem(hand);
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.PORTAL_AMBIENT, SoundSource.PLAYERS, 1.0F, 1.0F);
            level.addParticle(ParticleTypes.REVERSE_PORTAL, player.getX(), player.getY(), player.getZ(), 0.5, 0.5, 0.5);
        }
        if (currentMana <= manaConsume) {
            clicks++;
            if (clicks == 50) {
                player.sendMessage(new TextComponent("not enough mana"), player.getUUID());
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.LAVA_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.0F);
                clicks = 0;
            }
        }
        Log.info(currentMana);
        return super.use(level, player, hand);
    }
*/
public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pHand) {
    ItemStack itemstack = pPlayer.getItemInHand(pHand);
    pPlayer.startUsingItem(pHand);
    return InteractionResultHolder.consume(itemstack);
}
    private void addParticlesAroundSelf(Entity entity, Level level) {
        for (int i = 0; i < 5; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            level.addParticle(getParticle(), entity.getRandomX(0.5D), entity.getRandomY() + 0.5D, entity.getRandomZ(0.5D), d0, d1, d2);
        }
    }

    private ParticleOptions getParticle() {
        return ParticleTypes.REVERSE_PORTAL;
    }

    /*
    private void spawnItemParticles(ItemStack pStack, Entity entity, Level level, int pCount) {
        for(int i = 0; i < pCount; ++i) {
            Vec3 vec3 = new Vec3(((double)this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
            vec3 = vec3.xRot(-entity.getXRot() *((float)Math.PI / 180F));
            vec3 = vec3.yRot(-entity.getYRot()* ((float)Math.PI / 180F));
            double d0 = (double)(-this.random.nextFloat()) * 0.6D - 0.3D;
            Vec3 vec31 = new Vec3(((double)this.random.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
            vec31 = vec31.xRot(-entity.getXRot()  * ((float)Math.PI / 180F));
            vec31 = vec31.yRot(-entity.getYRot()  * ((float)Math.PI / 180F));
            vec31 = vec31.add(entity.getX(), entity.getEyeY()-0.5f, entity.getZ()-0.1f);
            if (level instanceof ServerLevel serverLevel) //Forge: Fix MC-2518 spawnParticle is nooped on server, need to use server specific variant
                serverLevel.addParticle(ParticleTypes.END_ROD, true, vec31.x, vec31.y, vec31.z, vec3.x, vec3.y + 0.05D, vec3.z);
            else
                level.addParticle(new ItemParticleOption(ParticleTypes.ITEM_SNOWBALL , pStack), vec31.x, vec31.y, vec31.z, vec3.x, vec3.y + 0.05D, vec3.z);
        }
    }
*/
    public void releaseUsing(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pEntityLiving, int pTimeLeft) {
        if (!pLevel.isClientSide) {
            if (pEntityLiving instanceof ServerPlayer serverplayer && !serverplayer.isCreative()) {
                InteractionHand hand = serverplayer.getUsedItemHand();
                BlockPos leftPos1 = new BlockPos(pEntityLiving.getX(), pEntityLiving.getY() + 1, pEntityLiving.getZ());
                int i = this.getUseDuration(pStack) - pTimeLeft;
                serverplayer.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
                    if (i > 10) {
                        if (mana.getMana() > manaConsume) {
                            mana.subMana(manaConsume);
                            Log.info(manaConsume);
                            BlockHitResult ray = getTeleportHitResult(pLevel, serverplayer, ClipContext.Fluid.NONE);
                            BlockPos lookPos = ray.getBlockPos().relative(ray.getDirection());
                            serverplayer.setPos(lookPos.getX(), lookPos.getY(), lookPos.getZ());
                            serverplayer.getCooldowns().addCooldown(this, 60);
                            serverplayer.fallDistance = 0F;
                            pLevel.playSound(null, serverplayer.getX(), serverplayer.getY(), serverplayer.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                            ItemStack stack = serverplayer.getItemInHand(hand);
                            stack.setDamageValue(stack.getDamageValue() + 1);
                            if (stack.getDamageValue() >= stack.getMaxDamage()) {
                                stack.hurtAndBreak(1, serverplayer, (breakEvent) -> {
                                    breakEvent.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                                });
                            }
                            for (int j = 0; j < 4; ++j) {
                                addParticlesAroundSelf(serverplayer, pLevel);
                            }
                            spawnLingeringCloud(pLevel, leftPos1, pEntityLiving);
                        } else {
                            serverplayer.sendMessage(new TextComponent("not enough mana"), serverplayer.getUUID());
                            pLevel.playSound(null, serverplayer.getX(), serverplayer.getY(), serverplayer.getZ(), SoundEvents.LAVA_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.0F);

                        }

                    }
                });
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
            setFire(level, areaeffectcloud.getX(), areaeffectcloud.getY(), areaeffectcloud.getZ(), areaeffectcloud);
            level.addFreshEntity(areaeffectcloud);
        }
    }

    public void setFire(LevelAccessor world, double x, double y, double z, Entity entity) {
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
                        _entity.setPos(cloud.getOwner().getX(), cloud.getOwner().getY(), cloud.getOwner().getZ());
                    }
                }

            }
        }
    }


    protected static BlockHitResult getTeleportHitResult(Level pLevel, LivingEntity pPlayer, ClipContext.Fluid pFluidMode) {
        double range = 100;
        float f = pPlayer.getXRot();
        float f1 = pPlayer.getYRot();
        Vec3 vec3 = pPlayer.getEyePosition(1.0F);
        float f2 = Mth.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = Mth.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -Mth.cos(-f * ((float) Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float) Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vec3 vec31 = vec3.add((double) f6 * range, (double) f5 * range, (double) f7 * range);
        return pLevel.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, pFluidMode, pPlayer));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(new TextComponent("teleports you where you're looking"));
        tooltip.add(new TextComponent("Mana consume: " + manaConsume + " / cast"));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
