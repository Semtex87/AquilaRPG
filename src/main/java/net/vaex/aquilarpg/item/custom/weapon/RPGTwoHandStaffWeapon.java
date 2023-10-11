package net.vaex.aquilarpg.item.custom.weapon;

import net.minecraft.world.item.Item;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.RPGBasicTwoHandMeleeWeapon;


public class RPGTwoHandStaffWeapon extends RPGBasicTwoHandMeleeWeapon {


    public RPGTwoHandStaffWeapon(RPGMaterialTiers pTier, Properties pProperties) {
        super(pTier, pProperties);
        this.armorPiercing = 1; //TWOHAND SWORD
        //this.rangeByType = 4.0d + weaponSubType; //TWOHAND SWORD
        this.damageByType = 10.0f; //TWOHAND SWORD
        this.speedByType = 1.3f; //TWOHAND SWORD
        this.itemWeight = 4.0f + pTier.getWeight(); //TWOHAND SWORD
        this.knockback = 0; //TWOHAND SWORD
        //////////////////////////////////////////
        this.attackDamage = damageByType + pTier.getMaterialHardness();
        this.attackSpeed = speedByType + (pTier.getMaterialHardness() / pTier.getWeight());
        this.actualAttackDamage = attackDamage - 1.0f;
        this.actualAttackSpeed = attackSpeed - 4.0f;
        ;
    }
    
    
}


