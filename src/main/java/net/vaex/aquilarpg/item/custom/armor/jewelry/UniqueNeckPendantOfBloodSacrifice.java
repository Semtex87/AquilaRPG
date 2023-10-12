package net.vaex.aquilarpg.item.custom.armor.jewelry;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.util.RPGCreativeModeTab;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class UniqueNeckPendantOfBloodSacrifice extends Item implements ICurioItem {
    final int durability;
    final String materialType;
    boolean isFoil;
    public UniqueNeckPendantOfBloodSacrifice(RPGMaterialTiers pTier, Properties pProperties) {
        super(pProperties.tab(RPGCreativeModeTab.RPG_ARMOR));
        this.durability = pTier.getUses();
        this.materialType = pTier.getMaterialName();
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        Player player = (Player) livingEntity;
        if(!player.level.isClientSide()) {
            boolean hasPlayerFireResistance =
                    !Objects.equals(player.hasEffect(MobEffects.REGENERATION), null);
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100,0,false,false,false));
                if(new Random().nextFloat() > 0.6f) {
                    stack.hurtAndBreak(1, player, p -> CuriosApi.getCuriosHelper().onBrokenCurio(
                            SlotTypePreset.NECKLACE.getIdentifier(), index, p));
                    stack.hurt(1, new Random(), null);
                }
            }
        ICurioItem.super.curioTick(identifier, index, livingEntity, stack);
    }


    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> pTooltip, TooltipFlag pFlag) {
        int var;
        if (Screen.hasShiftDown()) {
            pTooltip.add(new TextComponent("This old Necklace pulsates like a Heart as you hold it in your Hand").withStyle(ChatFormatting.ITALIC));
            } else {
            pTooltip.add(new TranslatableComponent("item_tooltip_shift"));
        }
        if (!itemStack.isDamaged()) {
            pTooltip.add(new TextComponent(durability + " / " + durability).withStyle(ChatFormatting.GREEN));
        } else {
            var = Integer.parseInt(itemStack.getTag().getString("damage"));
            if (var >= (durability * 90) / 100) {
                pTooltip.add(new TextComponent(var + " / " + durability).withStyle(ChatFormatting.GREEN));
            }
            if (var < (durability * 90) / 100 && var >= (durability * 20) / 100) {
                pTooltip.add(new TextComponent(var + " / " + durability).withStyle(ChatFormatting.YELLOW));
            }
            if (var <= (durability * 20) / 100) {
                pTooltip.add(new TextComponent(var + " / " + durability).withStyle(ChatFormatting.RED));
            }
        }
        pTooltip.add(new TextComponent("Material: " + materialType + " ").withStyle(ChatFormatting.BLUE));
        pTooltip.add(new TextComponent("Wearing: " + "Regeneration ").withStyle(ChatFormatting.BLUE));
        super.appendHoverText(itemStack, level, pTooltip, pFlag);
    }

    public UniqueNeckPendantOfBloodSacrifice setFoil(boolean foil)
    {
        isFoil = foil;
        return this;
    }
    @Override
    public boolean isFoil(@NotNull ItemStack itemstack) {
        if (isFoil)
        {
            return true;
        }else{
            return false;
        }
    }
}
