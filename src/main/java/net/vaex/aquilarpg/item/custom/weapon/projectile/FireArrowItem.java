package net.vaex.aquilarpg.item.custom.weapon.projectile;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.entity.RPGModEntities;
import net.vaex.aquilarpg.entity.item.FireArrowEntity;
import org.jetbrains.annotations.NotNull;

public class FireArrowItem extends ArrowItem {
    public FireArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull AbstractArrow createArrow(Level world, ItemStack ammoStack, LivingEntity shooter) {
        return new FireArrowEntity(RPGModEntities.FIRE_ARROW.get(), shooter, world);
    }
}
