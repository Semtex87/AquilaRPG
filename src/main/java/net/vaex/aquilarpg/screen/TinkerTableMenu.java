package net.vaex.aquilarpg.screen;

import com.google.common.collect.Lists;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.block.RPGBlocks;
import net.vaex.aquilarpg.block.entity.TinkerTableBlockEntity;
import net.vaex.aquilarpg.recipe.TinkerTableAnvilRecipe;
import net.vaex.aquilarpg.recipe.TinkerTableRecipe;
import net.vaex.aquilarpg.screen.slot.TinkerTablePatternSlot;
import java.util.List;


public class TinkerTableMenu extends AbstractContainerMenu {
    public static final int INPUT_SLOT = 0;
    public static final int RESULT_SLOT = 1;
    private static final int INV_SLOT_START = 2;
    private static final int INV_SLOT_END = 29;
    private static final int USE_ROW_SLOT_START = 29;
    private static final int USE_ROW_SLOT_END = 38;
    private final ContainerLevelAccess access;
    private final DataSlot selectedRecipeIndex = DataSlot.standalone();
    private final Level level;
    private List<TinkerTableRecipe> recipes = Lists.newArrayList();
    private List<TinkerTableAnvilRecipe> recipesAnvil = Lists.newArrayList();
    private ItemStack input = ItemStack.EMPTY;
    long lastSoundTime;
    final Slot inputSlot1;
    final Slot inputSlot2;
    final Slot inputSlot3;
    final Slot patternSlot;
    private boolean uniqueFlag;
    final Slot resultSlot;
    Runnable slotUpdateListener = () -> {
    };
    public final Container container = new SimpleContainer(4) {
        public void setChanged() {
            super.setChanged();
            TinkerTableMenu.this.slotsChanged(this);
            TinkerTableMenu.this.slotUpdateListener.run();
        }
    };
    final ResultContainer resultContainer = new ResultContainer();


    public TinkerTableMenu(int i, Inventory inventory) {
        this(i, inventory, ContainerLevelAccess.NULL);
    }

    public TinkerTableMenu(int i, Inventory inventory, final ContainerLevelAccess containerLevelAccess) {
        super(RPGMenuTypes.TINKER_TABLE_MENU.get(), i);
        this.access = containerLevelAccess;
        this.level = inventory.player.level;
        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        this.inputSlot1 = this.addSlot(new Slot(this.container, 0, 8, 15));
        this.inputSlot2 = this.addSlot(new Slot(this.container, 1, 8, 34));
        this.inputSlot3 = this.addSlot(new Slot(this.container, 2, 8, 53));
        this.patternSlot = this.addSlot(new TinkerTablePatternSlot(this.container, 3, 30, 34));
        this.resultSlot = this.addSlot(new Slot(this.resultContainer, 4, 148, 49)
        {
            public boolean mayPlace(ItemStack pStack) {
                return false;
            }

            public void onTake(Player pPlayer, ItemStack pStack) {
                pStack.onCraftedBy(pPlayer.level, pPlayer, pStack.getCount());
                TinkerTableMenu.this.resultContainer.awardUsedRecipes(pPlayer);
                ItemStack itemstack = TinkerTableMenu.this.inputSlot1.remove(1);
                ItemStack itemstack2 = TinkerTableMenu.this.inputSlot2.remove(1);
                ItemStack itemstack3 = TinkerTableMenu.this.inputSlot3.remove(1);
                if (!itemstack.isEmpty() && !itemstack2.isEmpty() && !itemstack3.isEmpty()) {
                    TinkerTableMenu.this.setupResultSlot();
                }
                containerLevelAccess.execute((level, blockPos) -> {
                    long l = level.getGameTime();
                    if (TinkerTableMenu.this.lastSoundTime != l) {
                        level.playSound(null, blockPos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                        TinkerTableMenu.this.lastSoundTime = l;
                    }

                });
                super.onTake(pPlayer, pStack);
            }
        });

        this.addDataSlot(this.selectedRecipeIndex);
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }
    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
    public int getSelectedRecipeIndex() {
        return this.selectedRecipeIndex.get();
    }

    public List<TinkerTableRecipe> getRecipes() {
        return this.recipes;
    }

    public int getNumRecipes() {
        return this.recipes.size();
    }

    public boolean hasInputItem() {
        return this.inputSlot1.hasItem() && this.inputSlot2.hasItem() && this.inputSlot3.hasItem() && !this.recipes.isEmpty();
    }

    public boolean stillValid(Player p_40307_) {
        return stillValid(this.access, p_40307_, RPGBlocks.TINKER_TABLE.get());
    }

    public boolean clickMenuButton(Player p_40309_, int p_40310_) {
        if (this.isValidRecipeIndex(p_40310_)) {
            this.selectedRecipeIndex.set(p_40310_);
            this.setupResultSlot();
        }

        return true;
    }

    private boolean isValidRecipeIndex(int i) {
        return i >= 0 && i < this.recipes.size();
    }

    public void slotsChanged(Container container) {
        ItemStack itemStack1 = this.inputSlot1.getItem();
        ItemStack itemStack2 = this.inputSlot2.getItem();
        ItemStack itemStack3 = this.inputSlot3.getItem();
        if (!itemStack1.is(this.input.getItem()) && !itemStack2.is(this.input.getItem()) && !itemStack3.is(this.input.getItem())) {
            this.input = itemStack1.copy();
            this.input = itemStack2.copy();
            this.input = itemStack3.copy();
            this.setupRecipeList(container, itemStack1,itemStack2,itemStack3);
        }

    }

    private void setupRecipeList(Container container, ItemStack itemStack1,ItemStack itemStack2, ItemStack itemStack3) {
        this.recipes.clear();
        this.selectedRecipeIndex.set(-1);
        this.resultSlot.set(ItemStack.EMPTY);
        if (TinkerTableBlockEntity.getAnvilStatus()){
            if (!itemStack1.isEmpty() && !itemStack2.isEmpty() && !itemStack3.isEmpty()) {
                this.recipes = this.level.getRecipeManager().getRecipesFor(TinkerTableRecipe.Type.INSTANCE, container, this.level);
            }
    }

    }

    void setupResultSlot() {
        if (!this.recipes.isEmpty() && this.isValidRecipeIndex(this.selectedRecipeIndex.get())) {
            TinkerTableRecipe tinkerTableRecipe = this.recipes.get(this.selectedRecipeIndex.get());
            this.resultContainer.setRecipeUsed(tinkerTableRecipe);
            ItemStack itemStack = tinkerTableRecipe.assemble(this.container);
            this.resultContainer.setItem(0, itemStack);
        } else {
            this.resultContainer.setItem(0, ItemStack.EMPTY);
        }

        this.broadcastChanges();
    }

    public MenuType<?> getType() {
        return RPGMenuTypes.TINKER_TABLE_MENU.get();
    }

    public void registerUpdateListener(Runnable p_40324_) {
        this.slotUpdateListener = p_40324_;
    }

    public boolean canTakeItemForPickAll(ItemStack p_40321_, Slot p_40322_) {
        return p_40322_.container != this.resultContainer && super.canTakeItemForPickAll(p_40321_, p_40322_);
    }

    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            Item item = itemstack1.getItem();
            itemstack = itemstack1.copy();
            if (pIndex == 1) {
                item.onCraftedBy(itemstack1, pPlayer.level, pPlayer);
                if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (pIndex == 0) {
                if (!this.moveItemStackTo(itemstack1, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.level.getRecipeManager().getRecipeFor(TinkerTableRecipe.Type.INSTANCE, new SimpleContainer(itemstack1), this.level).isPresent()) {
                if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (pIndex >= 2 && pIndex < 29) {
                if (!this.moveItemStackTo(itemstack1, 29, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (pIndex >= 29 && pIndex < 38 && !this.moveItemStackTo(itemstack1, 2, 29, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            }

            slot.setChanged();
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
            this.broadcastChanges();
        }

        return itemstack;
    }

    public void removed(Player p_40326_) {
        super.removed(p_40326_);
        this.resultContainer.removeItemNoUpdate(1);
        this.access.execute((p_40313_, p_40314_) -> {
            this.clearContainer(p_40326_, this.container);
        });
    }

}
