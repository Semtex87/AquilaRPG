package net.vaex.aquilarpg.item.custom;


import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.util.RPGCreativeModeTab;
import net.vaex.aquilarpg.util.RPGTierInterface;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jline.utils.Log;

import java.util.*;


public class RPGPeelingIron extends RPGToolItem {
    HashMap<Item, List<Item>> itemListHashMap = new HashMap<>();
    public RPGPeelingIron(RPGTierInterface pTier, Item.Properties pProperties) {
        super(pTier, pProperties.tab(RPGCreativeModeTab.RPG_TOOLS));
        this.durability = pTier.getUses();
        this.materialType = pTier.getMaterialName();
        loadData(Items.OAK_LOG, Items.STRIPPED_OAK_LOG);
        loadData(Items.SPRUCE_LOG, Items.STRIPPED_SPRUCE_LOG);
        loadData(Items.BIRCH_LOG, Items.STRIPPED_BIRCH_LOG);
        loadData(Items.JUNGLE_LOG, Items.STRIPPED_JUNGLE_LOG);
        loadData(Items.ACACIA_LOG, Items.STRIPPED_ACACIA_LOG);
        loadData(Items.DARK_OAK_LOG, Items.STRIPPED_DARK_OAK_LOG);
        loadData(Items.CRIMSON_STEM, Items.STRIPPED_CRIMSON_STEM);
        loadData(Items.WARPED_STEM, Items.STRIPPED_WARPED_STEM);
                                                                                                                        //ToDo adding Conquest/Reforged Wood Logs and Stems
        loadData(Items.OAK_WOOD, Items.STRIPPED_OAK_WOOD);
        loadData(Items.SPRUCE_WOOD, Items.STRIPPED_SPRUCE_WOOD);
        loadData(Items.BIRCH_WOOD, Items.STRIPPED_BIRCH_WOOD);
        loadData(Items.JUNGLE_WOOD, Items.STRIPPED_JUNGLE_WOOD);
        loadData(Items.ACACIA_WOOD, Items.STRIPPED_ACACIA_WOOD);
        loadData(Items.DARK_OAK_WOOD, Items.STRIPPED_DARK_OAK_WOOD);
        loadData(Items.CRIMSON_HYPHAE, Items.STRIPPED_CRIMSON_HYPHAE);
        loadData(Items.WARPED_HYPHAE, Items.STRIPPED_WARPED_HYPHAE);
                                                                                                                        //ToDo adding Conquest/Reforged Wood Types


    }
    private void loadData(Item source, Item... result) {
        ArrayList<Item> itemList = new ArrayList<>(Arrays.asList(result));
        if (itemListHashMap.containsKey(source)) itemList.addAll(itemListHashMap.get(source));
        itemListHashMap.put(source, itemList);
    }

    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {          //ToDo adding a SHIFT function to convert sourceStack > new Stack
        Item source = pSlot.getItem().getItem();
        int damageValueTool = pStack.getDamageValue();
        int maxDurabilityTool = pStack.getMaxDamage();
        if (pAction == ClickAction.SECONDARY && pSlot.allowModification(pPlayer)) {
            if (!itemListHashMap.containsKey(source)) return false;
            for (Item item : itemListHashMap.get(source)) {
                pSlot.getItem().shrink(1);
                pPlayer.addItem(new ItemStack(item));
                Log.info(pPlayer + " converting " + source  + " to " + item + pStack);
            }
            pStack.hurt(1,new Random(),null);
            if (damageValueTool >= maxDurabilityTool) {
                pStack.shrink(1);
                playBreakSound(pPlayer);
            } else {
                playToolSound(pPlayer);
            }

            return true;
        } else  {
            return false;
        }
    }


    private void playToolSound(Entity entity) {
        entity.playSound(SoundEvents.AXE_STRIP, 0.8F, 1.0f);
    }
    private void playBreakSound(Entity entity) {
        entity.playSound(SoundEvents.ITEM_BREAK, 1.0F, 1.0f );
    }


    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            components.add(new TextComponent("Strip Wood: Drag this Item and right click on the Item u want to strip").withStyle(ChatFormatting.YELLOW).append(String.valueOf(ChatFormatting.UNDERLINE)));
        } else {
            components.add(new TextComponent("A Tool to Strip Wood or some Crafting Actions").withStyle(ChatFormatting.BLUE).append(String.valueOf(ChatFormatting.UNDERLINE)));
            components.add(new TranslatableComponent("tooltip.aquilarpg.shift"));
        }
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }
}

