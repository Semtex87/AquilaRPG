package net.vaex.aquilarpg.capabilities.mana;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.network.ManaSyncS2CPacket;
import net.vaex.aquilarpg.network.NetworkHandler;

@Mod.EventBusSubscriber(modid = AquilaRPG.MOD_ID)
public class ManaEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(ManaProvider.PLAYER_MANA).isPresent()) {
                event.addCapability(new ResourceLocation(AquilaRPG.MOD_ID, "mana"), new ManaProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(ManaProvider.PLAYER_MANA).ifPresent(oldStore -> {
                event.getOriginal().getCapability(ManaProvider.PLAYER_MANA).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
        event.getOriginal().reviveCaps();
    }


    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(Mana.class);
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player && player.isDeadOrDying()) {
            if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                player.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
                    mana.setDefaultMaxMana();
                    NetworkHandler.sendPacketTo(new ManaSyncS2CPacket(mana.getMana()), serverPlayer);
                });
            }
        }
    }


    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.side == LogicalSide.SERVER) {
            event.player.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
                    if (event.player.getRandom().nextFloat() < 0.005f) {
                        mana.addMana(1);
                        NetworkHandler.sendPacketTo(new ManaSyncS2CPacket(mana.getMana()), ((ServerPlayer) event.player));
                    }
            });
        }
    }

    @SubscribeEvent
    public static void playerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player playernew = event.getPlayer();
        if (!playernew.level.isClientSide()) {
            if (event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
                    mana.addMana(mana.getDefaultMana());
                    NetworkHandler.sendPacketTo(new ManaSyncS2CPacket(mana.getMana()), player);
                });
            }
        }
    }


    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinWorldEvent event) {
        if (!event.getWorld().isClientSide()) {
            if (event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
                    NetworkHandler.sendPacketTo(new ManaSyncS2CPacket(mana.getMana()), player);
                });
            }
        }
    }

    //@SubscribeEvent
    public static void AABB(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        Level level = player.getLevel();
        if (hasSpecificAroundThem(player, level, 2)) {

        }
    }

    private static boolean hasSpecificAroundThem(Player player, Level level, int size) {
        return level.getBlockStates(player.getBoundingBox().inflate(size))
                .filter(state -> state.is(Blocks.TORCH)).toArray().length > 0;
    }

}


