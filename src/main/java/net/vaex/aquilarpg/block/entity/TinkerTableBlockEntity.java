package net.vaex.aquilarpg.block.entity;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.vaex.aquilarpg.recipe.TinkerTableAnvilRecipe;
import net.vaex.aquilarpg.recipe.TinkerTableCommonRecipe;
import net.vaex.aquilarpg.screen.TinkerTableMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jline.utils.Log;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class TinkerTableBlockEntity extends BlockEntity{

    private LazyOptional<IItemHandler> lazyItemHandler =  LazyOptional.empty();
    private static boolean anvilRecipes = false;
    private static boolean grindStoneRecipes = false;

    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    public TinkerTableBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(RPGBlockEntities.TINKER_TABLE.get(), pWorldPosition, pBlockState);
    }
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }


    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inventory);
        }
    }

    public static boolean getAnvilStatus() {
        return anvilRecipes;
    }
    public static boolean getGrindstoneStatus() {
        return grindStoneRecipes;
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, TinkerTableBlockEntity pBlockEntity) {
        if (hasAnvilNear(pBlockEntity, pLevel, 2)) {
            anvilRecipes = true;
            //Log.info("Anvil connected");
        }
        else {
            anvilRecipes = false;
            //Log.info("Anvil not connected");
        }
        //Log.info(anvilRecipes);
        //anvilRecipes = hasSmithingTableNear(pBlockEntity, pLevel, 2);
        //anvilRecipes = hasFletchingTableNear(pBlockEntity, pLevel, 2);
        //anvilRecipes = hasBlastFurnaceNear(pBlockEntity, pLevel, 2);
        //anvilRecipes = hasFurnaceNear(pBlockEntity, pLevel, 2);
        //grindStoneRecipes = hasGrindstoneTableNear(pBlockEntity, pLevel, 2);
        //Log.info("grindStoneRecipes "+ grindStoneRecipes);
        //anvilRecipes = hasLoomTableNear(pBlockEntity, pLevel, 2);

    }

    private static boolean hasAnvilNear(BlockEntity blockEntity, Level level, int size) {
        return level.getBlockStates(blockEntity.getRenderBoundingBox().inflate(size)).filter(state -> state.is(Blocks.ANVIL)).toArray().length > 0;
    }
    private static boolean hasFurnaceNear(BlockEntity blockEntity, Level level, int size) {
        return level.getBlockStates(blockEntity.getRenderBoundingBox().inflate(size)).filter(state -> state.is(Blocks.FURNACE)).toArray().length > 0;
    }
    private static boolean hasBlastFurnaceNear(BlockEntity blockEntity, Level level, int size) {
        return level.getBlockStates(blockEntity.getRenderBoundingBox().inflate(size)).filter(state -> state.is(Blocks.BLAST_FURNACE)).toArray().length > 0;
    }
    private static boolean hasSmithingTableNear(BlockEntity blockEntity, Level level, int size) {
        return level.getBlockStates(blockEntity.getRenderBoundingBox().inflate(size)).filter(state -> state.is(Blocks.SMITHING_TABLE)).toArray().length > 0;
    }
    private static boolean hasFletchingTableNear(BlockEntity blockEntity, Level level, int size) {
        return level.getBlockStates(blockEntity.getRenderBoundingBox().inflate(size)).filter(state -> state.is(Blocks.FLETCHING_TABLE)).toArray().length > 0;
    }
    private static boolean hasGrindstoneTableNear(BlockEntity blockEntity, Level level, int size) {
        return level.getBlockStates(blockEntity.getRenderBoundingBox().inflate(size)).filter(state -> state.is(Blocks.GRINDSTONE)).toArray().length > 0;
    }
    private static boolean hasLoomTableNear(BlockEntity blockEntity, Level level, int size) {
        return level.getBlockStates(blockEntity.getRenderBoundingBox().inflate(size)).filter(state -> state.is(Blocks.LOOM)).toArray().length > 0;
    }


        /*
        boolean hasItemInMaterialSlotOne = entity.itemHandler.getStackInSlot(0).getItem() == RPGItems.RAW_COPPER.get();
        boolean hasItemInMaterialSlotTwo = entity.itemHandler.getStackInSlot(1).getItem() == RPGItems.RAW_LEAD.get();
        boolean hasItemInMaterialSlotThree = entity.itemHandler.getStackInSlot(2).getItem() == RPGItems.RAW_COBALT.get();
        boolean hasUniquePatternSlot = entity.itemHandler.getStackInSlot(3).getItem() == RPGItems.RUNE_DUST.get();
        if (entity.itemHandler.getStackInSlot(3).isEmpty()) {
            return hasItemInMaterialSlotOne && hasItemInMaterialSlotTwo && hasItemInMaterialSlotThree;
        } else {
            return hasItemInMaterialSlotOne && hasItemInMaterialSlotTwo && hasItemInMaterialSlotThree && hasUniquePatternSlot;
        }
        */


    /*
private void consumeFuel() {
    if(!itemHandler.getStackInSlot(0).isEmpty()) {
        this.fuelTime = ForgeHooks.getBurnTime(this.itemHandler.extractItem(0, 1, false),
                RecipeType.SMELTING);
        this.maxFuelTime = this.fuelTime;
    }
}

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, TinkerTableBlockEntity pBlockEntity) {
        if(isConsumingFuel(pBlockEntity)) {
            pBlockEntity.fuelTime--;
        }

        if(hasRecipe(pBlockEntity)) {
            if(hasFuelInFuelSlot(pBlockEntity) && !isConsumingFuel(pBlockEntity)) {
                pBlockEntity.consumeFuel();
                setChanged(pLevel, pPos, pState);
            }
            if(isConsumingFuel(pBlockEntity)) {
                pBlockEntity.progress++;
                setChanged(pLevel, pPos, pState);
                if(pBlockEntity.progress > pBlockEntity.maxProgress) {
                    craftItem(pBlockEntity);
                }
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private static boolean hasFuelInFuelSlot(TinkerTableBlockEntity entity) {
        return !entity.itemHandler.getStackInSlot(0).isEmpty();
    }

    private static boolean isConsumingFuel(TinkerTableBlockEntity entity) {
        return entity.fuelTime > 0;
    }

    private static boolean hasRecipe(TinkerTableBlockEntity entity) {
        Level level = entity.getLevel();
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<TinkerTableRecipe> match = level.getRecipeManager()
                .getRecipeFor(TinkerTableRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().getResultItem());
    }

    private static void craftItem(TinkerTableBlockEntity entity) {
        Level level = entity.getLevel();
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<TinkerTableRecipe> match = level.getRecipeManager()
                .getRecipeFor(TinkerTableRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            entity.itemHandler.extractItem(0,1, false);
            entity.itemHandler.extractItem(1,1, false);
            entity.itemHandler.extractItem(2,1, false);
            entity.itemHandler.extractItem(3,1, false);


            entity.itemHandler.setStackInSlot(4, new ItemStack(match.get().getResultItem().getItem(),
                    entity.itemHandler.getStackInSlot(4).getCount() + 1));

            entity.resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }
    */
    private static boolean hasNotReachedStackLimit(TinkerTableBlockEntity entity) {
        return entity.itemHandler.getStackInSlot(4).getCount() < entity.itemHandler.getStackInSlot(4).getMaxStackSize();
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(4).getItem() == output.getItem() || inventory.getItem(4).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(4).getMaxStackSize() > inventory.getItem(4).getCount();
    }

}

