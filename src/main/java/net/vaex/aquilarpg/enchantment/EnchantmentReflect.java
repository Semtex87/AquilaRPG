package net.vaex.aquilarpg.enchantment;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.util.RPGEnchantmentUtil;

import static net.vaex.aquilarpg.enchantment.RPGEnchantments.REFLECT;


@Mod.EventBusSubscriber(modid = AquilaRPG.MOD_ID)
public class EnchantmentReflect extends Enchantment {
    public EnchantmentReflect(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }
    @Override
    public int getMinLevel() {
        return 1;
    }
    @Override
    public int getMaxLevel() {
        return 2;
    }


    @SubscribeEvent
    public static void reflect(ProjectileImpactEvent e) {
        if (!(e.getRayTraceResult() instanceof EntityHitResult)) return;
        Entity projectile = e.getEntity();
        Entity target = ((EntityHitResult) e.getRayTraceResult()).getEntity();
        if (!(target instanceof Player)) return;
        Player player = (Player) target;
        if (RPGEnchantmentUtil.hasEnchant(player, REFLECT.get())) {
            projectile.setDeltaMovement(projectile.getDeltaMovement().reverse());
            if (projectile instanceof AbstractHurtingProjectile) {
                ((AbstractHurtingProjectile) projectile).xPower *= -1;
                ((AbstractHurtingProjectile) projectile).yPower *= -1;
                ((AbstractHurtingProjectile) projectile).zPower *= -1;
            }
            e.setCanceled(true);
            projectile.hurtMarked = true;
        }
    }
}

