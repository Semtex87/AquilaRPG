package net.vaex.aquilarpg.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.block.RPGBlocks;

public class RPGBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, AquilaRPG.MOD_ID);

    public static final RegistryObject<BlockEntityType<CobaltBlasterBlockEntity>> COBALT_BLASTER =
            BLOCK_ENTITIES.register("cobalt_blaster", () ->
                    BlockEntityType.Builder.of(CobaltBlasterBlockEntity::new,
                            RPGBlocks.COBALT_BLASTER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
