package net.vaex.aquilarpg.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
//import net.vaex.aquilarpg.effects.RPGEffectManager;

public class RPGFoodItem {


    public static final FoodProperties SEEDS = new FoodProperties.Builder()
            .fast()
            .alwaysEat()
            .nutrition(1)
            .saturationMod(0.1F)
            .build();

    public static final FoodProperties BASIC_INGREDIENTS = new FoodProperties.Builder()
            .fast()
            .alwaysEat()
            .nutrition(1)
            .saturationMod(0.1F)
            .build();

    public static final FoodProperties SIMPLE_FOOD = new FoodProperties.Builder()
            .alwaysEat()
            .nutrition(1)
            .saturationMod(0.1F)
            .build();
    public static final FoodProperties MANA = new FoodProperties.Builder()
            .fast()
            .alwaysEat()
            .build();
    public static final FoodProperties MANA_FOOD = new FoodProperties.Builder()
            .fast()
            .nutrition(4)
            .saturationMod(0.2F)
            .alwaysEat()
            .build();
    public static final FoodProperties BASIC_FOOD = new FoodProperties.Builder()
            .fast()
            .alwaysEat()
            .nutrition(2)
            .saturationMod(0.5F)
            .build();
    //MEAL
    public static final FoodProperties COMMON_FEAST = new FoodProperties.Builder()
            .nutrition(6)
            .saturationMod(1.0F)
            .build();

    public static final FoodProperties HEARTY_FEAST = new FoodProperties.Builder()
            .nutrition(10)
            .saturationMod(2.0F)
            .build();

    //DRINKS
    public static final FoodProperties ALC_LIGHT = new FoodProperties.Builder()
            .fast()
            .alwaysEat()
            .nutrition(2)
            .saturationMod(0.3F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 600), 0.8f)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600), 0.4f)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600), 0.5f)
            .build();
    public static final FoodProperties ALC_MEDIUM = new FoodProperties.Builder()
            .fast()
            .alwaysEat()
            .nutrition(2)
            .saturationMod(0.3F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 600), 0.8f)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600), 0.8f)
            .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 600), 0.1f)
            .effect(() -> new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 600), 0.5f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600), 0.2f)
            .build();

    public static final FoodProperties ALC_STRONG = new FoodProperties.Builder()
            .fast()
            .alwaysEat()
            .nutrition(2)
            .saturationMod(0.3F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 1200), 0.8f)
            .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 600), 0.2f)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1200), 0.5f)
            .effect(() -> new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 600), 0.8f)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 400), 0.2f)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600), 0.5f)
            .build();

    //SPECIAL
    public static final FoodProperties ENDERSOUP = new FoodProperties.Builder()
            .nutrition(2)
            .alwaysEat()
            .saturationMod(2)
            //.effect(() -> new MobEffectInstance(RPGEffectManager.RANDOM_TP.get(), 100), 1.0f)
            .build();


}
