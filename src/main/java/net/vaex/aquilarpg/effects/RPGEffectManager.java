package net.vaex.aquilarpg.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vaex.aquilarpg.AquilaRPG;
import org.checkerframework.checker.units.qual.A;

import java.awt.*;

public class RPGEffectManager {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, AquilaRPG.MOD_ID);
    //Playereffects
    public static final RegistryObject<MobEffect> ENCUMBERED = MOB_EFFECTS.register("encumbered_effect",
            EncumberedEffect::new);
    public static final RegistryObject<MobEffect> OVERLOADED = MOB_EFFECTS.register("overloaded_effect",
            OverloadedEffect::new);
    public static final RegistryObject<MobEffect> BERSERKER = MOB_EFFECTS.register("berserker_effect",
            BerserkEffect::new);
    public static final RegistryObject<MobEffect> MANTLE_OF_SHADOWS = MOB_EFFECTS.register("mantle_of_shadows_effect",
            MantleOfShadowsEffect::new);
    public static final RegistryObject<MobEffect> CALLTOARMS = MOB_EFFECTS.register("call_to_arms_effect",
            ReadyToFightEffect::new);

    public static final RegistryObject<MobEffect> BLESSED = MOB_EFFECTS.register("blessed_effect",
            () -> new BlessedEffect(MobEffectCategory.NEUTRAL, Color.MAGENTA.getRGB()));

    public static final RegistryObject<MobEffect> RANDOM_TP = MOB_EFFECTS.register("random_tp",
            () -> new PhasingEffect(MobEffectCategory.NEUTRAL, Color.MAGENTA.getRGB()));
    public static final RegistryObject<MobEffect> UNHOLY_STRENGTH = MOB_EFFECTS.register("unholy_strength_effect",
            () -> new UnholyStrengthEffect(MobEffectCategory.NEUTRAL, Color.MAGENTA.getRGB()));
    public static final RegistryObject<MobEffect> HEALTHINCREASE = MOB_EFFECTS.register("health_increase_effect",
            () -> new HealIncreaseEffect(MobEffectCategory.NEUTRAL, Color.RED.getRGB()));

    public static final RegistryObject<MobEffect> INFRAVISION = MOB_EFFECTS.register("infravision_effect",
            InfravisionEffect::new);
    public static final RegistryObject<MobEffect> MANA_REGEN = MOB_EFFECTS.register("mana_regeneration_effect",
            () -> new ManaRegen(MobEffectCategory.NEUTRAL, Color.BLUE.getRGB()));
    public static final RegistryObject<MobEffect> REFLECTION = MOB_EFFECTS.register("reflection_effect",
            ReflectEffect::new);
    public static final RegistryObject<MobEffect> LIFELEECH = MOB_EFFECTS.register("lifeleech_effect",
            LifeLeech::new);
    public static final RegistryObject<MobEffect> PARALYSIS = MOB_EFFECTS.register("paralysis_effect",
            () -> new ParalysisEffect(MobEffectCategory.HARMFUL, Color.TRANSLUCENT));

    public static final RegistryObject<MobEffect> BLEEDING = MOB_EFFECTS.register("bleeding_effect",
            () -> new BleedingEffect(MobEffectCategory.HARMFUL, Color.TRANSLUCENT));
    public static final RegistryObject<MobEffect> MANA_SHIELD = MOB_EFFECTS.register("force_field_effect",
            () -> new ManaShieldEffect(MobEffectCategory.HARMFUL));

    public static final RegistryObject<MobEffect> AGING = MOB_EFFECTS.register("aging_effect",
            () -> new AgingEffect(MobEffectCategory.HARMFUL, 0));
    public static final RegistryObject<MobEffect> LACERATION = MOB_EFFECTS.register("laceration_effect",
            LacerationEffect::new);
    public static final RegistryObject<MobEffect> FREEZE = MOB_EFFECTS.register("freeze_effect",
            () -> new FreezeEffect(MobEffectCategory.HARMFUL, Color.CYAN));
    public static final RegistryObject<MobEffect> SANGUINE = MOB_EFFECTS.register("sanguine_effect",
            SanguineRose::new);

    public static final RegistryObject<MobEffect> SUNBURN = MOB_EFFECTS.register("sunburn_effect",
            () -> new SunburnEffect(MobEffectCategory.HARMFUL, Color.RED));
    public static final RegistryObject<MobEffect> RECALL = MOB_EFFECTS.register("recall_effect",
            () -> new RecallEffect(MobEffectCategory.HARMFUL, Color.CYAN));

    public static final RegistryObject<MobEffect> MARK = MOB_EFFECTS.register("mark_effect",
            () -> new MarkEffect(MobEffectCategory.HARMFUL, Color.CYAN));





    public static final RegistryObject<MobEffect> SHARPEN_EFFECT = MOB_EFFECTS.register("sharpen_effect",
            SharpenEffect::new);

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
