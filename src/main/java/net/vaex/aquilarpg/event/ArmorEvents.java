package net.vaex.aquilarpg.event;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.IModBusEvent;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.item.custom.armor.head.RPGBasicHelmetArmorItem;
import net.vaex.aquilarpg.util.RPGSoundEvents;
import net.vaex.aquilarpg.util.RPGTags;
import org.jline.utils.Log;

import static net.minecraft.world.level.block.Block.pushEntitiesUp;


@Mod.EventBusSubscriber(modid = AquilaRPG.MOD_ID)
public class ArmorEvents implements IModBusEvent {
    static int ticks;

    @SubscribeEvent
    public static void onMove(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Level level = entity.getLevel();
        if (!entity.level.isClientSide()) {
            if (entity instanceof ServerPlayer serverPlayer) {
                if (serverPlayer.isAlive() && serverPlayer.isSprinting())
                    ticks++;
                if (ticks == 20) {
                    if (serverPlayer.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ArmorItem item && item.getMaterial().getEquipSound().equals(SoundEvents.ARMOR_EQUIP_CHAIN)) {
                        serverPlayer.level.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), RPGSoundEvents.CHAINMAIL_MOVING.get(), SoundSource.PLAYERS, 4.0F, 1.0f);
                    }
                    ticks = 0;
                }
            }
        }
    }


    @SubscribeEvent
    public static void renderPlayerPre(RenderPlayerEvent.Pre event) {
        PlayerRenderer render = event.getRenderer();
        PlayerModel<AbstractClientPlayer> model = render.getModel();
        if (event.getEntity() instanceof Player player) {
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(RPGTags.Items.RPG_FULLHELM_HIDE_HAT)) {
                event.getRenderer().getModel().hat.visible = false;
            }
        }
    }

    @SubscribeEvent
    public static void onEquipHelmet(LivingEvent.LivingUpdateEvent event) {

        LivingEntity entity = event.getEntityLiving();
        Level level = entity.getLevel();
        if (!entity.level.isClientSide()) {
            if (entity instanceof ServerPlayer serverPlayer) {
                if (serverPlayer.isAlive() && serverPlayer.isSprinting())
                    ticks++;
                if (ticks == 20) {
                    if (serverPlayer.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ArmorItem item && item.getMaterial().getEquipSound().equals(SoundEvents.ARMOR_EQUIP_CHAIN)) {
                        serverPlayer.level.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), RPGSoundEvents.CHAINMAIL_MOVING.get(), SoundSource.PLAYERS, 4.0F, 1.0f);
                    }
                    ticks = 0;
                }
            }
        }
    }



/*
    @SubscribeEvent
    public static void replaceGround(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Level level = entity.getLevel();
        if (!entity.level.isClientSide()) {
            if (entity instanceof ServerPlayer serverPlayer) {
                BlockPos blockpos = serverPlayer.blockPosition();
                BlockPos blockpos1 = blockpos.below();
                if (level.getBlockState(blockpos1).is(Blocks.GRASS_BLOCK)) {
                    level.setBlock(blockpos1, Blocks.DIRT.defaultBlockState(), 2);
                    serverPlayer.level.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), SoundEvents.GRASS_FALL, SoundSource.PLAYERS, 4.0F, 1.0f);

                }

                if (level.getBlockState(blockpos1).is(Blocks.DIRT)) {
                    level.setBlock(blockpos1, Blocks.DIRT_PATH.defaultBlockState(), 2);
                    serverPlayer.level.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), SoundEvents.HOE_TILL, SoundSource.PLAYERS, 4.0F, 1.0f);
                }
            }
        }
    }
*/
}







