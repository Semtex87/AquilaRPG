package net.vaex.aquilarpg.util;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;

public class RPGRarity implements net.minecraftforge.common.IExtensibleEnum {

    public static final Rarity RPG_CLUTTER = Rarity.create("aquilarpg_trash", ChatFormatting.GRAY);
    public static final Rarity RPG_COMMON = Rarity.create("aquilarpg_common", ChatFormatting.WHITE);
    public static final Rarity RPG_UNCOMMON = Rarity.create("aquilarpg_uncommon", ChatFormatting.GREEN);
    public static final Rarity RPG_RARE = Rarity.create("aquilarpg_rare", ChatFormatting.BLUE);
    public static final Rarity RPG_EPIC = Rarity.create("aquilarpg_epic", ChatFormatting.DARK_PURPLE);
    public static final Rarity RPG_SET = Rarity.create("aquilarpg_set", ChatFormatting.DARK_GREEN);
    public static final Rarity RPG_LEGENDARY = Rarity.create("aquilarpg_legendary", ChatFormatting.GOLD);
    public static final Rarity RPG_ARTIFACT = Rarity.create("aquilarpg_artifact", ChatFormatting.DARK_RED);
    public static final Rarity RPG_CREATIVE = Rarity.create("aquilarpg_artifact", ChatFormatting.BOLD);
}