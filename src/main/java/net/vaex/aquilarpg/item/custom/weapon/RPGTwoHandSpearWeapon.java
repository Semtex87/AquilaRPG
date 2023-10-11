package net.vaex.aquilarpg.item.custom.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.RPGBasicTwoHandMeleeWeapon;
import net.vaex.aquilarpg.util.RPGAttributeUUID;


public class RPGTwoHandSpearWeapon extends RPGBasicTwoHandMeleeWeapon {
    public RPGTwoHandSpearWeapon(RPGMaterialTiers pTier, Properties pProperties) {
        super(pTier, pProperties);
        this.armorPiercing = 2;
        this.damageByType = 6.0f;
        this.speedByType = 1.2f;
        this.itemWeight = 5.0f + pTier.getWeight();
        this.knockback = 0;
        //////////////////////////////////////////
        this.attackDamage = damageByType + pTier.getMaterialHardness();
        this.attackSpeed = speedByType + (pTier.getMaterialHardness() / pTier.getWeight());
        this.actualAttackDamage = attackDamage - 1.0f;
        this.actualAttackSpeed = attackSpeed - 4.0f;

    }



    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", actualAttackDamage, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", actualAttackSpeed, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(RPGAttributeUUID.ATTACK_KNOCKBACK_UUID, "Knockback modifier", knockback, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(slot, stack);
    }
}


