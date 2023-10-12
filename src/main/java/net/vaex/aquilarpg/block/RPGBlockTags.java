package net.vaex.aquilarpg.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.vaex.aquilarpg.AquilaRPG;

public class RPGBlockTags {
    public static final TagKey<Block> NEEDS_RUBY_TOOL = BlockTags.create(new ResourceLocation(AquilaRPG.MOD_ID, "needs_ruby_tool"));
}
