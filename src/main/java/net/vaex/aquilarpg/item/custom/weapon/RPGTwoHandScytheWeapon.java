package net.vaex.aquilarpg.item.custom.weapon;

import net.minecraft.world.item.Item;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.RPGBasicTwoHandMeleeWeapon;


public class RPGTwoHandScytheWeapon extends RPGBasicTwoHandMeleeWeapon {


    public RPGTwoHandScytheWeapon(RPGMaterialTiers pTier, Properties pProperties) {
        super(pTier, pProperties);
        this.armorPiercing = 1; //TWOHAND SWORD
        //this.rangeByType = 4.0d + weaponSubType; //TWOHAND SWORD
        this.damageByType = 7.5f; //TWOHAND SWORD
        this.speedByType = 1.2f; //TWOHAND SWORD
        this.itemWeight = 5.0f + pTier.getWeight(); //TWOHAND SWORD
        this.knockback = 0; //TWOHAND SWORD
        //////////////////////////////////////////
        this.attackDamage = damageByType + pTier.getMaterialHardness();
        this.attackSpeed = speedByType + (pTier.getMaterialHardness() / pTier.getWeight());
        this.actualAttackDamage = attackDamage - 1.0f;
        this.actualAttackSpeed = attackSpeed - 4.0f;

    }
}


