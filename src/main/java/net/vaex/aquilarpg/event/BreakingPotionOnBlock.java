package net.vaex.aquilarpg.event;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.IModBusEvent;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.item.RPGItems;
import org.jline.utils.Log;


@Mod.EventBusSubscriber(modid = AquilaRPG.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class BreakingPotionOnBlock implements IModBusEvent {

@SubscribeEvent
    public static void onBlockRightClick(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getEntityLiving() instanceof Player playerEntity) {
            Level level = event.getWorld();
            BlockPos pos = event.getPos();
            BlockState blockstate = level.getBlockState(pos);
            ItemStack itemStack2 = RPGItems.BROKEN_BOTTLE.get().getDefaultInstance();
            if (!level.isClientSide()) {
                if (blockstate.is(Blocks.STONE) && playerEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof PotionItem) {
                    level.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1, 1);
                    if (!level.isClientSide) {
                            if (playerEntity.getItemInHand(InteractionHand.MAIN_HAND).getTag() != null) {
                                itemStack2.setTag(playerEntity.getItemInHand(InteractionHand.MAIN_HAND).getTag());
                                Log.info(" break bottle with:" + playerEntity.getItemInHand(InteractionHand.MAIN_HAND).getTag());
                                playerEntity.addItem(itemStack2);
                                Log.info(" added broken bottle with:" + playerEntity.getItemInHand(InteractionHand.MAIN_HAND).getTag().toString());
                            }
                        playerEntity.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
                    }
                }
            }
        }
    }
}




