package net.vaex.aquilarpg.effects;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vaex.aquilarpg.AquilaRPG;

public class RPGPotionManager {

    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, AquilaRPG.MOD_ID);

    public static final RegistryObject<Potion> RANDOM_TP = POTIONS.register("random_tp_potion",
            () -> new Potion(new MobEffectInstance(RPGEffectManager.RANDOM_TP.get(), 200, 0)));
    public static final RegistryObject<Potion> INFRAVISION = POTIONS.register("infravision_potion",
            () -> new Potion(new MobEffectInstance(RPGEffectManager.INFRAVISION.get(), 200, 0)));
    public static final RegistryObject<Potion> FREEZE = POTIONS.register("freeze_potion",
            () -> new Potion(new MobEffectInstance(RPGEffectManager.FREEZE.get(), 200, 0)));
    public static final RegistryObject<Potion> BERSERK = POTIONS.register("berserk_potion",
            () -> new Potion(new MobEffectInstance(RPGEffectManager.BERSERKER.get(), 200, 0)));
    public static final RegistryObject<Potion> BLEEDING = POTIONS.register("bleeding_potion",
            () -> new Potion(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 200, 0)));
    public static final RegistryObject<Potion> PARALYSIS = POTIONS.register("paralysis_potion",
            () -> new Potion(new MobEffectInstance(RPGEffectManager.PARALYSIS.get(), 200, 0)));
    public static final RegistryObject<Potion> SANGUINE = POTIONS.register("sanguine_potion",
            () -> new Potion(new MobEffectInstance(RPGEffectManager.SANGUINE.get(), 200, 0)));
    public static final RegistryObject<Potion> MARK = POTIONS.register("mark_potion",
            () -> new Potion(new MobEffectInstance(RPGEffectManager.MARK.get(), 0, 0)));
    public static final RegistryObject<Potion> RECALL = POTIONS.register("recall_potion",
            () -> new Potion(new MobEffectInstance(RPGEffectManager.RECALL.get(), 0, 0)));
    public static final RegistryObject<Potion> MANA_REGEN = POTIONS.register("mana_regen_potion",
            () -> new Potion(new MobEffectInstance(RPGEffectManager.MANA_REGEN.get(), 1000, 10)));

//Without EFFECTS

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
