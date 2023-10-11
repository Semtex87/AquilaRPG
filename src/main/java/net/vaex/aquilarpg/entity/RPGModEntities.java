package net.vaex.aquilarpg.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.entity.item.*;
import net.vaex.aquilarpg.entity.item.BottleEntity;

public class RPGModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY =
            DeferredRegister.create(ForgeRegistries.ENTITIES, AquilaRPG.MOD_ID);
    public static final RegistryObject<EntityType<BottleEntity>> BOTTLE =
            ENTITY.register("bottle", () -> EntityType.Builder.<BottleEntity>of(BottleEntity::new, MobCategory.MISC)
                    .sized(0.4f, 0.4f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("bottle")
            );

    public static final RegistryObject<EntityType<DaggerEntity>> DAGGER =
            ENTITY.register("dagger", () -> EntityType.Builder.<DaggerEntity>of(DaggerEntity::new, MobCategory.MISC)
                    .sized(0.4f, 0.4f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("dagger")
                    );

    public static final RegistryObject<EntityType<AxeEntity>> AXE =
            ENTITY.register("axe", () -> EntityType.Builder.<AxeEntity>of(AxeEntity::new, MobCategory.MISC)
                    .sized(0.4f, 0.4f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("axe")
            );
    public static final RegistryObject<EntityType<SpearEntity>> SPEAR =
            ENTITY.register("spear", () -> EntityType.Builder.<SpearEntity>of(SpearEntity::new, MobCategory.MISC)
                    .sized(0.4f, 0.4f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("spear")
            );


    public static final RegistryObject<EntityType<FireArrowEntity>> FIRE_ARROW = ENTITY.register("fire_arrow",
            () -> EntityType.Builder.of((EntityType.EntityFactory<FireArrowEntity>) FireArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .build("fire_arrow"));
    public static final RegistryObject<EntityType<PoisonDartEntity>> POISON_DART = ENTITY.register("poison_dart",
            () -> EntityType.Builder.of((EntityType.EntityFactory<PoisonDartEntity>) PoisonDartEntity::new, MobCategory.MISC)
                    .sized(0.3F, 0.3F)
                    .build("poison_dart"));


    public static final RegistryObject<EntityType<OrbOfLifeEntity>> ORB_OF_LIFE =
            ENTITY.register("orb_of_life", () -> EntityType.Builder.<OrbOfLifeEntity>of(OrbOfLifeEntity::new, MobCategory.MISC)
                    .sized(0.4f, 0.4f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("orb_of_life")
            );
    public static final RegistryObject<EntityType<BulletEntity>> BULLET =
            ENTITY.register("bullet", () -> EntityType.Builder.<BulletEntity>of(BulletEntity::new, MobCategory.MISC)
                    .sized(0.4f, 0.4f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("bullet")
            );


    public static void register(IEventBus eventBus) {
        ENTITY.register(eventBus);
    }
}
