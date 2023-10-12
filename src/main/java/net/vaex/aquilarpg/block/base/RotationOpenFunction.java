package net.vaex.aquilarpg.block.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class RotationOpenFunction extends BaseHorizontal{

    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;



    //Constructor:
    public RotationOpenFunction(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, Boolean.FALSE));
    }

    //Add Blockstates:

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, OPEN);
    }


    //Rightclickevent + Sound based on Material
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        {
            pState = pState.cycle(OPEN);
            pLevel.setBlock(pPos, pState, 2);
            this.playSound(pPlayer, pLevel, pPos, pState.getValue(OPEN));


            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
    }


    //Soundevent des Trapdoor Blocks:
    protected void playSound(@Nullable Player pPlayer, Level pLevel, BlockPos pPos, boolean pIsOpened) {
        if (pIsOpened) {
            int i = this.material == Material.METAL ? 1037 : 1007;
            pLevel.levelEvent(pPlayer, i, pPos, 0);
        } else {
            int j = this.material == Material.METAL ? 1036 : 1013;
            pLevel.levelEvent(pPlayer, j, pPos, 0);
        }

        pLevel.gameEvent(pPlayer, pIsOpened ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pPos);
    }


    //Placement:
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite())
                .setValue(OPEN, Boolean.FALSE);
    }
}
