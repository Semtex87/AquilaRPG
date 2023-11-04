package net.vaex.aquilarpg.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CobaltLampBlock extends Block implements BlockGetter {
    private int requiredPlayerRange = 16;
    public static final BooleanProperty CLICKED = BooleanProperty.create("clicked");

    public CobaltLampBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(CLICKED, false));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pLevel.isClientSide() && pHand == InteractionHand.MAIN_HAND) {
            boolean currentState = pState.getValue(CLICKED);
            pLevel.setBlock(pPos, pState.setValue(CLICKED, !currentState), 3);
        }

        return InteractionResult.SUCCESS;
    }

    private boolean isNearPlayer(Level pLevel, BlockPos pPos) {
        return pLevel.hasNearbyAlivePlayer((double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D, (double)this.requiredPlayerRange);
    }
    @SubscribeEvent
    public void clientTick(Level pLevel, BlockPos pPos) {
        double d0 = (double)pPos.getX() + pLevel.random.nextDouble();
        double d1 = (double)pPos.getY() + pLevel.random.nextDouble();
        double d2 = (double)pPos.getZ() + pLevel.random.nextDouble();
        pLevel.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        pLevel.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }
    @SubscribeEvent
    public void serverTick(ServerLevel pServerLevel, BlockPos pPos) {

        if (this.isNearPlayer(pServerLevel, pPos)) {
            BlockState pState = pServerLevel.getBlockState(pPos);
            boolean currentState = pState.getValue(CLICKED);
            pServerLevel.setBlock(pPos, pState.setValue(CLICKED, !currentState), 3);
        }
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(CLICKED);
    }
    public void tick(ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        Entity entity = (Entity) pLevel.getEntities();
        final Vec3 _center = new Vec3(pPos.getX(), pPos.getY(), pPos.getZ());
        BlockState pState = pLevel.getBlockState(pPos);
        if(!pLevel.isClientSide) {
            List<Entity> _entfound = pLevel.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10), e -> true).stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
            for (Entity entityiterator : _entfound) {
                if (!(entityiterator == entity)) {
                    if (entityiterator instanceof LivingEntity _entity && _entity.getMobType().equals(MobType.UNDEAD)) {
                        if (entity instanceof Player player) {
                            boolean currentState = pState.getValue(CLICKED);
                            pLevel.setBlock(pPos, pState.setValue(CLICKED, !currentState), 3);
                        }
                    }
                }
            }

        }
    }











    @Nullable
    @Override
    public BlockEntity getBlockEntity(BlockPos pPos) {
        return null;
    }

    @Override
    public BlockState getBlockState(BlockPos p_45571_) {
        return null;
    }

    @Override
    public FluidState getFluidState(BlockPos pPos) {
        return null;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getMinBuildHeight() {
        return 0;
    }
}