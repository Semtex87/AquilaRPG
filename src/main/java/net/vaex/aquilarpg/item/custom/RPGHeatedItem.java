package net.vaex.aquilarpg.item.custom;


import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.vaex.aquilarpg.item.RPGItems;
import net.vaex.aquilarpg.util.RPGTierInterface;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class RPGHeatedItem extends Item {

    int heat;
    public RPGHeatedItem(RPGTierInterface ptier, Properties pProperties) {
        super(pProperties.durability(ptier.getForgingHeatpoint()));
        this.heat = ptier.getForgingHeatpoint();
        pProperties.durability(heat);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);

        if (new Random().nextInt(60) == 1) {
            stack.hurt((heat / 20), null, null);
        }
        if (entity instanceof Player player) {
            if (!selected && !(player.getOffhandItem().is(RPGItems.TONGS.get()) || (player.getOffhandItem().is(RPGItems.CRUCIBLE_TONGS.get())))) {
                player.hurt(DamageSource.ON_FIRE, 1.0F);
            }
            if (stack.getDamageValue() >= stack.getMaxDamage()) {
                checkValidItem(stack, player);
                stack.shrink(1);
            }
        }

    }
    private void checkValidItem(ItemStack stack, Player player) {
        if (stack.is(RPGItems.HOT_COBALT_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.COBALT_INGOT.get()));
        if (stack.is(RPGItems.HOT_STEEL_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.STEEL_INGOT.get()));
        if (stack.is(RPGItems.HOT_IRON_INGOT.get()))
            player.addItem(new ItemStack(Items.IRON_INGOT));
        if (stack.is(RPGItems.HOT_LEAD_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.LEAD_INGOT.get()));
        if (stack.is(RPGItems.HOT_ADAMANT_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.ADAMANT_INGOT.get()));
        if (stack.is(RPGItems.HOT_BRASS_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.BRASS_INGOT.get()));
        if (stack.is(RPGItems.HOT_ASTRALSILVER_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.ASTRALSILVER_INGOT.get()));
        if (stack.is(RPGItems.HOT_BRONZE_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.BRONZE_INGOT.get()));
        if (stack.is(RPGItems.HOT_COPPER_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.COPPER_INGOT.get()));
        if (stack.is(RPGItems.HOT_CRIMSON_STEEL_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.CRIMSON_STEEL_INGOT.get()));
        if (stack.is(RPGItems.HOT_DARKIRON_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.DARKIRON_INGOT.get()));
        if (stack.is(RPGItems.HOT_DWARVEN_DARKSTEEL_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.DWARVEN_DARKSTEEL_INGOT.get()));
        if (stack.is(RPGItems.HOT_EBONY_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.EBONY_INGOT.get()));
        if (stack.is(RPGItems.HOT_GLASS_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.GLASS_INGOT.get()));
        if (stack.is(RPGItems.HOT_ELEMENTIUM_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.ELEMENTIUM_INGOT.get()));
        if (stack.is(RPGItems.HOT_ELVEN_STEEL_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.ELVEN_STEEL_INGOT.get()));
        if (stack.is(RPGItems.HOT_HELSTONE_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.HELSTONE_INGOT.get()));
        if (stack.is(RPGItems.HOT_HEXXIN_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.HEXXIN_INGOT.get()));
        if (stack.is(RPGItems.HOT_MITHRIL_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.MITHRIL_INGOT.get()));
        if (stack.is(RPGItems.HOT_ORICALCUM_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.ORICALCUM_INGOT.get()));
        if (stack.is(RPGItems.HOT_SILVER_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.SILVER_INGOT.get()));
        if (stack.is(RPGItems.HOT_STAHLRIM_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.STAHLRIM_INGOT.get()));
        if (stack.is(RPGItems.HOT_TIN_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.TIN_INGOT.get()));
        if (stack.is(RPGItems.HOT_VOIDSTEEL_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.VOIDSTEEL_INGOT.get()));
        if (stack.is(RPGItems.HOT_ZINC_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.ZINC_INGOT.get()));
        if (stack.is(RPGItems.HOT_GOLD_INGOT.get()))
            player.addItem(new ItemStack(Items.GOLD_INGOT));
        /////////////////////////////////////////////////////////////////
        if (stack.is(RPGItems.CRUCIBLE_MOLTEN_ASTRALSILVER.get()))
            stack.hurt(stack.getMaxDamage(), new Random(),null);
        if (stack.is(RPGItems.CRUCIBLE_MOLTEN_BRASS.get()))
            stack.hurt(stack.getMaxDamage(), new Random(),null);
        if (stack.is(RPGItems.CRUCIBLE_MOLTEN_BRONZE.get()))
            stack.hurt(stack.getMaxDamage(), new Random(),null);
        if (stack.is(RPGItems.CRUCIBLE_MOLTEN_CRIMSON_STEEL.get()))
            stack.hurt(stack.getMaxDamage(), new Random(),null);
        if (stack.is(RPGItems.CRUCIBLE_MOLTEN_ELEMENTIUM.get()))
            stack.hurt(stack.getMaxDamage(), new Random(),null);
        if (stack.is(RPGItems.CRUCIBLE_MOLTEN_ELVEN_STEEL.get()))
            stack.hurt(stack.getMaxDamage(), new Random(),null);
        if (stack.is(RPGItems.CRUCIBLE_MOLTEN_DWARVEN_DARKSTEEL.get()))
            stack.hurt(stack.getMaxDamage(), new Random(),null);
        if (stack.is(RPGItems.CRUCIBLE_MOLTEN_VOID_STEEL.get()))
            stack.hurt(stack.getMaxDamage(), new Random(),null);
        if (stack.is(RPGItems.CRUCIBLE_MOLTEN_STEEL.get()))
            stack.hurt(stack.getMaxDamage(), new Random(),null);
        if (stack.is(RPGItems.CRUCIBLE_MOLTEN_ORICALCUM.get()))
            stack.hurt(stack.getMaxDamage(), new Random(),null);

    }
    
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();
        BlockPos blockpos = pContext.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        if (player != null) {
            double d = (double) blockpos.getX() + 0.5D;
            double e = (double) blockpos.getY() + 1.2D;
            double f = (double) blockpos.getZ() + 0.5D;
            if (blockstate.is(Blocks.WATER_CAULDRON)) {
                if (!level.isClientSide) {
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_COBALT_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.COBALT_INGOT.get()));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_STEEL_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.STEEL_INGOT.get()));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_IRON_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(Items.IRON_INGOT));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_GOLD_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(Items.GOLD_INGOT));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_ZINC_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.ZINC_INGOT.get()));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_VOIDSTEEL_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.VOIDSTEEL_INGOT.get()));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_SILVER_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.SILVER_INGOT.get()));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_TIN_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.TIN_INGOT.get()));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_STAHLRIM_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.STAHLRIM_INGOT.get()));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_ORICALCUM_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.ORICALCUM_INGOT.get()));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_HEXXIN_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.HEXXIN_INGOT.get()));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_MITHRIL_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.MITHRIL_INGOT.get()));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_HELSTONE_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.HELSTONE_INGOT.get()));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_ELVEN_STEEL_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.ELVEN_STEEL_INGOT.get()));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_ELEMENTIUM_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.ELEMENTIUM_INGOT.get()));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_EBONY_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.EBONY_INGOT.get()));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_DWARVEN_DARKSTEEL_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.DWARVEN_DARKSTEEL_INGOT.get()));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_DARKIRON_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.DARKIRON_INGOT.get()));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_CRIMSON_STEEL_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.CRIMSON_STEEL_INGOT.get()));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_COPPER_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.COPPER_INGOT.get()));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_BRONZE_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.BRONZE_INGOT.get()));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_ASTRALSILVER_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.ASTRALSILVER_INGOT.get()));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_BRASS_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.BRASS_INGOT.get()));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_ADAMANT_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.ADAMANT_INGOT.get()));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_GLASS_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.GLASS_INGOT.get()));
                    }
                    if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_LEAD_INGOT.get())) {
                        pContext.getPlayer().addItem(new ItemStack(RPGItems.LEAD_INGOT.get()));
                    }
                    pContext.getPlayer().getMainHandItem().shrink(1);
                    return InteractionResult.SUCCESS;
                }
                level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, d, e, f, 0.0, 0.01, 0.0);
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.FIRE_EXTINGUISH, SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
        }
        return InteractionResult.PASS;
    }
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> pTooltip, TooltipFlag pFlag) {
        int var = itemStack.getMaxDamage() - itemStack.getDamageValue();
        if (Screen.hasShiftDown()) {
            pTooltip.add(new TextComponent("Caution! Very hot").withStyle(ChatFormatting.ITALIC));
            pTooltip.add(new TextComponent("Use Tongs(Offhand) to avoid damage").withStyle(ChatFormatting.ITALIC));
        } else {
            pTooltip.add(new TranslatableComponent("item_tooltip_shift"));
        }
        pTooltip.add(new TextComponent("heat " + var).withStyle(ChatFormatting.RED));
        super.appendHoverText(itemStack, level, pTooltip, pFlag);
    }

}




