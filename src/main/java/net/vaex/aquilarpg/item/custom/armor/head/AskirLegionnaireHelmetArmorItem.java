package net.vaex.aquilarpg.item.custom.armor.head;

import net.minecraft.world.entity.EquipmentSlot;
import net.vaex.aquilarpg.item.RPGArmorTiers;
import net.vaex.aquilarpg.util.RPGCreativeModeTab;

public class AskirLegionnaireHelmetArmorItem extends RPGBasicHelmetArmorItem {


    public AskirLegionnaireHelmetArmorItem(RPGArmorTiers material, Properties properties) {
        super(material, EquipmentSlot.HEAD, properties.tab(RPGCreativeModeTab.RPG_ARMOR));
    }
}