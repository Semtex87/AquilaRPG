package net.vaex.aquilarpg.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.vaex.aquilarpg.AquilaRPG;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = AquilaRPG.MOD_ID)
public class ReflectEffect extends MobEffect {
    public ReflectEffect() {
        super(MobEffectCategory.NEUTRAL, 3333333);
    }

    @SubscribeEvent
    public static void reflect(ProjectileImpactEvent e) {
        if (!(e.getRayTraceResult() instanceof EntityHitResult)) return;
        Entity projectile = e.getEntity();
        Entity target = ((EntityHitResult) e.getRayTraceResult()).getEntity();
        if (!(target instanceof Player)) return;
        Player player = (Player) target;
        if (player.hasEffect(RPGEffectManager.REFLECTION.get())) {
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
    public @NotNull String getDescriptionId() {
        return "projectile reflection";
    }
}
