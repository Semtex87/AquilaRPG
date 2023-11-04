package net.vaex.aquilarpg.item.custom;


import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.vaex.aquilarpg.block.RPGBlocks;
import net.vaex.aquilarpg.capabilities.mana.ManaProvider;
import net.vaex.aquilarpg.network.ManaC2SPacket;
import net.vaex.aquilarpg.network.NetworkHandler;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RPGDisassemblerItem extends Item {
    HashMap<Block, List<Item>> blockMap = new HashMap<>();

    public RPGDisassemblerItem(Properties pProperties) {
        super(pProperties);
        loadData(Blocks.CRAFTING_TABLE, Items.OAK_PLANKS, 4);
        loadData(Blocks.FURNACE, Items.COBBLESTONE, 8);


        loadData(Blocks.PISTON, Items.OAK_PLANKS, 3);
        loadData(Blocks.PISTON, Items.COBBLESTONE, 4);
        loadData(Blocks.PISTON, Items.REDSTONE, Items.IRON_INGOT);
        loadData(RPGBlocks.COBALT_BLOCK.get(), Items.COBBLESTONE);                                                      //TODO TESTING


    }


    private void loadData(Block block, Item item, int count) {
        ArrayList<Item> itemList = new ArrayList<>();
        for (int i = 0; i < count; i++) itemList.add(item);
        if (blockMap.containsKey(block)) itemList.addAll(blockMap.get(block));
        blockMap.put(block, itemList);
    }

    private void loadData(Block block, Item... items) {
        ArrayList<Item> itemList = new ArrayList<>(Arrays.asList(items));
        if (blockMap.containsKey(block)) itemList.addAll(blockMap.get(block));
        blockMap.put(block, itemList);
    }


    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
        if (player.getLevel().isClientSide()) return false;
        Level level = player.getLevel();
        Block block = level.getBlockState(pos).getBlock();
        if (!blockMap.containsKey(block)) return false;
        for (Item item : blockMap.get(block)) {
            ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(item)); //spawn a new Entity Item
            level.addFreshEntity(itemEntity);
        }
        level.destroyBlock(pos, false); //destroy block without dropping itself
        itemstack.hurtAndBreak(8, player, (p_43076_) -> {
            p_43076_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });

        return super.onBlockStartBreak(itemstack, pos, player);
    }
    @Override
    @SubscribeEvent
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        double d2 = pPlayer.getX() ;
        double d3 = pPlayer.getY();
        double d4 = pPlayer.getZ();
        SmallFireball smallfireball = new SmallFireball(pLevel, pPlayer, -d2, -d3, -d4);
        smallfireball.setPos(smallfireball.getX(),0.5D, smallfireball.getZ());
        pLevel.addFreshEntity(smallfireball);
        pPlayer.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
            mana.subMana(50);
            NetworkHandler.sendPacketToServer((new ManaC2SPacket()));
        });
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState pBlock) {
        return blockMap.containsKey(pBlock.getBlock());
    }

    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        if (blockMap.containsKey(pState.getBlock())) return 3;
        return 1;
    }
}
