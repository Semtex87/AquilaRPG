package net.vaex.aquilarpg.item.custom.armor.feet;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.item.RPGArmorTiers;
import net.vaex.aquilarpg.item.RPGItems;
import net.vaex.aquilarpg.util.RPGAttributeUUID;
import org.jetbrains.annotations.NotNull;
import org.jline.utils.Log;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Arrays;
import java.util.List;

public class RPGBasicFeetArmorItem extends GeoArmorItem implements IAnimatable {
    public AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public int enhancementValue;
    Ingredient repairMaterial;
    public int durability;
    public int thoughness;
    public int resist;
    public String name;
    public String materialType;
    public String armorClass;
    public float itemWeight;
    public boolean mythicalArmor;
    public boolean fantasyArmor;
    public int defense;
    boolean isFoil;
    int enchantmentPoolSize = 0;
    //private Multimap<Attribute, AttributeModifier> defaultModifiers;


    public RPGBasicFeetArmorItem(RPGArmorTiers material, EquipmentSlot slot, Properties settings) {
        super(material, slot, settings);
        this.armorClass = material.getArmorclass();
        this.materialType = material.getName();
        this.durability = material.getDurabilityForSlot(EquipmentSlot.FEET);
        this.defense = material.getDefenseForSlot(slot);
        this.itemWeight = material.getWeightForSlot(slot);
        this.thoughness = (int) material.getToughness();
        //this.resist = material.getElementaryResist(AQUA);
        //this.name = material.getElementaryResistName(AQUA);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.FEET)
        {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ARMOR, new AttributeModifier(RPGAttributeUUID.BASE_ARMOR_UUID, "Tool modifier", defense, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(RPGAttributeUUID.DENSITY_UUID, "Tool modifier", thoughness, AttributeModifier.Operation.ADDITION));
            //builder.put(RPGAttributes.RESIST_ALL.get(), new AttributeModifier(RPGAttributeUUID.RESIST_ALL_UUID, "Tool modifier", thoughness, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(slot, stack);
    }
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<RPGBasicFeetArmorItem>(this, "controller",
                20, this::predicate));
    }
    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        event.getController().setAnimation(new AnimationBuilder().loop("idle"));
        return PlayState.CONTINUE;
    }
    public RPGBasicFeetArmorItem setMythicalArmor(boolean mythical) { //for unique/mythical/god weapons
        mythicalArmor = mythical;
        return this;
    }
    public RPGBasicFeetArmorItem setFantasy(boolean pFantasy) {
        fantasyArmor = pFantasy;
        return this;
    }
    /*
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == this.slot ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }
*/
    public float[] getWeight() {
        return new float[]{this.itemWeight};
    }
    public int getEnhancementValue() {
        return this.enhancementValue;
    }
    public String getMaterialType() {
        return this.materialType;
    }
    public float getDurability() {
        return this.durability;
    }
    public int getElementaryResist() {
        return resist;
    }
    public String getElementaryResistName() {
        return name;
    }
    @Override
    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return mythicalArmor ?  this.repairMaterial.test(pRepair): pRepair.is(RPGItems.MAGIC_DUST.get());
    }
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }

    public boolean canEquip(ItemStack stack, EquipmentSlot slot, Entity entity) {
        return Mob.getEquipmentSlotForItem(stack) == slot;
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book){
        return true;
    }

    private void checkEnchantments(ItemStack stack, Level level) {
        if (!level.isClientSide()) {
            if (stack.isEnchanted()) {
                enhancementValue = stack.getEnchantmentTags().size();
                Log.info(enhancementValue);
            }
        }
    }
    @Override
    public boolean isFoil(@NotNull ItemStack itemstack) {
        return (itemstack.isEnchanted()) || isFoil;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @org.jetbrains.annotations.Nullable Level level, List<Component> pTooltip, TooltipFlag pFlag) {
        int damageValue = itemStack.getDamageValue();
        int maxDurability = itemStack.getMaxDamage();
        int currentDamage = maxDurability - damageValue;
        if (Screen.hasShiftDown()) {
            if (!itemStack.isEnchanted()) {
                pTooltip.add(new TextComponent("Enchant Capacity: " + this.getEnchantmentValue()).withStyle(ChatFormatting.BLUE));
            }
            if (itemStack.isEnchanted()) { //todo
                pTooltip.add(new TextComponent("todo....").withStyle(ChatFormatting.BLUE));
            }
            if (itemStack.getTag() != null && itemStack.getTag().contains("Enchantment")) {
                pTooltip.add(new TextComponent("Tag: " + getEnhancementValue()).withStyle(ChatFormatting.BLUE));
            }
            if (itemStack.hasTag()) {
                pTooltip.add(new TextComponent("No Tags: " + itemStack.getTag().getAllKeys()).withStyle(ChatFormatting.BLUE));
                if (itemStack.getTag().getBoolean("sharpened_effect")) {
                    pTooltip.add(new TextComponent("Boolean is: " + itemStack.getTag().getBoolean("sharpened_effect")).withStyle(ChatFormatting.BLUE));
                }
            }
            pTooltip.add(new TextComponent("" + Arrays.toString(this.getWeight()) + " ").append(new TranslatableComponent("item_weapon_weight")).withStyle(ChatFormatting.BLUE));
            if (this.fantasyArmor) {
                pTooltip.add(new TextComponent("#Fantasy").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.ITALIC));
            }
            if (!this.fantasyArmor){
                pTooltip.add(new TextComponent("#Realistic").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.ITALIC));
            }
        } else {
            pTooltip.add(new TranslatableComponent("tooltip.aquilarpg.shift"));
        }
        if (!itemStack.isDamaged()) {
            pTooltip.add(new TextComponent(durability + " / " + durability).withStyle(ChatFormatting.GREEN));
        } else {
            if (!itemStack.isDamaged()) {
                pTooltip.add(new TextComponent(currentDamage + " / " + maxDurability).withStyle(ChatFormatting.GREEN));
            } else {
                if (currentDamage >= (maxDurability * 90) / 100) {
                    pTooltip.add(new TextComponent(currentDamage + " / " + maxDurability).withStyle(ChatFormatting.GREEN));
                }
                if (currentDamage < (maxDurability * 90) / 100 && currentDamage >= (maxDurability * 20) / 100) {
                    pTooltip.add(new TextComponent(currentDamage + " / " + maxDurability).withStyle(ChatFormatting.YELLOW));
                }
                if (currentDamage <= (maxDurability * 20) / 100) {
                    pTooltip.add(new TextComponent(currentDamage + " / " + maxDurability).withStyle(ChatFormatting.RED));
                }
            }
        }
        //pTooltip.add(new TextComponent(getElementaryResistName()+ ": " + getElementaryResist()).withStyle(ChatFormatting.BLUE));
        pTooltip.add(new TextComponent("Material: " + materialType).withStyle(ChatFormatting.BLUE));
    }

}