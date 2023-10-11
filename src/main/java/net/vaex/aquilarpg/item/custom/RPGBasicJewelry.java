package net.vaex.aquilarpg.item.custom;


import net.minecraft.world.item.TieredItem;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.util.RPGCreativeModeTab;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurioItem;


public class RPGBasicJewelry extends TieredItem implements ICurioItem {
    String material;

    public RPGBasicJewelry(RPGMaterialTiers pTier, SlotTypePreset pSlot, Properties pProperties ) {
        super(pTier, pProperties.tab(RPGCreativeModeTab.RPG_ARMOR));
        this.material = pTier.getMaterialName();
    }

}
