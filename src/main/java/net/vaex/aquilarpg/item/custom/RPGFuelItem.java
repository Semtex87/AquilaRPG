package net.vaex.aquilarpg.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RPGFuelItem extends Item {
    private int burnTime; //lokal wird durch this.burntime geändet auf übergebenen Wert vom Konstruktor
    private int heat;

    public RPGFuelItem(int burnTime, int heat, Properties properties) {
        super(properties);
        this.burnTime = burnTime; //this.burntime übergibt Wert aus Kontruktor
        this.heat = heat;
    }
    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return burnTime;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
                pTooltip.add((new TextComponent("Burn Time " + this.burnTime/20 + " ")).append(new TranslatableComponent("burn_time")).withStyle(ChatFormatting.WHITE));
                pTooltip.add((new TextComponent("Max Heat " + this.heat + " ")).append(new TranslatableComponent("max_heat")).withStyle(ChatFormatting.WHITE));
    }
}
