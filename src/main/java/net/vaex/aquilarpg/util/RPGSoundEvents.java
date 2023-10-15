package net.vaex.aquilarpg.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vaex.aquilarpg.AquilaRPG;

public class RPGSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, AquilaRPG.MOD_ID);


    public static final RegistryObject<SoundEvent> BLEEDING_EFFECT =
            registerSoundEvent("bleeding_effect");

    public static final RegistryObject<SoundEvent> FLAIL_SWING =
            registerSoundEvent("flail_swing");
    public static final RegistryObject<SoundEvent> BLUNT_HIT =
            registerSoundEvent("blunt_hit");
    public static final RegistryObject<SoundEvent> HEAVY_WEAPON_SWING =
            registerSoundEvent("heavy_weapon_swing");
    public static final RegistryObject<SoundEvent> SWORD_IMPACT =
            registerSoundEvent("sword_impact");
    public static final RegistryObject<SoundEvent> FULL_CHARGE =
            registerSoundEvent("full_charge");
    public static final RegistryObject<SoundEvent> BLOWGUN_USE =
            registerSoundEvent("blowgun_use");
    public static final RegistryObject<SoundEvent> MUSKET_USE =
            registerSoundEvent("musket_use");
    public static final RegistryObject<SoundEvent> BLUNDERBUSS_USE =
            registerSoundEvent("blunderbuss_use");
    public static final RegistryObject<SoundEvent>FLINTLOCK_PISTOL_USE =
            registerSoundEvent("flintlock_pistol_use");
    public static final RegistryObject<SoundEvent>GUN_RELOAD =
            registerSoundEvent("gun_reload");
    public static final RegistryObject<SoundEvent>GUN_CLICK =
            registerSoundEvent("gun_click");
    public static final RegistryObject<SoundEvent>NINJA_STAR_THROWING =
            registerSoundEvent("ninja_star_throwing");
    public static final RegistryObject<SoundEvent>NINJA_STAR_IMPACT =
            registerSoundEvent("ninja_star_impact");

    public static final RegistryObject<SoundEvent> FREEZE_EFFECT =
            registerSoundEvent("freeze_effect");
    public static final RegistryObject<SoundEvent> ENCH_WEAPON_HIT =
            registerSoundEvent("ench_weapon_hit");
    public static final RegistryObject<SoundEvent> FORCE_FIELD_HIT =
            registerSoundEvent("force_field_hit");

    public static final RegistryObject<SoundEvent> CHAINMAIL_MOVING =
            registerSoundEvent("chainmail_moving");
    public static final RegistryObject<SoundEvent> CLOTH_MOVING =
            registerSoundEvent("cloth_moving");

    public static final RegistryObject<SoundEvent> BROTHERHOOD_HOWL =
            registerSoundEvent("brotherhood_howl");
    public static final RegistryObject<SoundEvent> FORCE_FIELD =
            registerSoundEvent("force_field");
    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(AquilaRPG.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

}


