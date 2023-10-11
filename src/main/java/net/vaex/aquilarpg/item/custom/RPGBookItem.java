package net.vaex.aquilarpg.item.custom;


import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.UseAnim;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.util.RPGCreativeModeTab;


public class RPGBookItem extends TieredItem {
    public String material;
    public String slot;
    public int durability;



    public RPGBookItem(RPGMaterialTiers pTier, EquipmentSlot pSlot, Properties pProperties ) {
        super(pTier, pProperties.tab(RPGCreativeModeTab.RPG_WEAPON));
        this.material = pTier.getMaterialName();
        this.slot = String.valueOf(pSlot);
        this.durability = pTier.getUses();
    }


    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.CROSSBOW;
    }
}
