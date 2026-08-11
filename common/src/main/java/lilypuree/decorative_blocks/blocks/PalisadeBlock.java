package lilypuree.decorative_blocks.blocks;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

import java.util.Map;

public class PalisadeBlock extends CrossCollisionBlock implements IWoodenBlock {
    public static final MapCodec<PalisadeBlock> CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(WoodType.CODEC.fieldOf("wood_type").forGetter(block -> block.woodType), propertiesCodec()).apply(inst, PalisadeBlock::new)
    );
    @Override
    protected MapCodec<? extends CrossCollisionBlock> codec() {
        return CODEC;
    }

    private WoodType woodType;

    private final Map<BlockState, VoxelShape> shapeByIndex;
    private final Map<BlockState, VoxelShape> collisionShapeByIndex;

    public PalisadeBlock(WoodType woodType, Properties properties) {
        super(3.0F, 3.0F, 16.0F, 16.0F, 24.0F, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, Boolean.FALSE).setValue(EAST, Boolean.FALSE).setValue(SOUTH, Boolean.FALSE).setValue(WEST, Boolean.FALSE).setValue(WATERLOGGED, Boolean.FALSE));
        this.woodType = woodType;
        this.shapeByIndex = this.makeShapes(3.0F, 3.0F, 16.0F, 0.0F, 16.0F, 16.0F);
        this.collisionShapeByIndex = this.makeShapes(3.0F, 3.0F, 24.0F, 0.0F, 24.0F, 24.0F);
    }

    private Map<BlockState, VoxelShape> makeShapes(float width, float depth, float postHeight, float wallMinY, float wallLowHeight, float wallTallHeight) {
        float f = 8.0F - width;
        float f1 = 8.0F + width;
        float f2 = 8.0F - depth;
        float f3 = 8.0F + depth;
        VoxelShape voxelshape = Block.box(f, 0.0, f, f1, postHeight, f1);
        VoxelShape voxelshape1 = Block.box(f2, wallMinY, 0.0, f3, wallLowHeight, f3);
        VoxelShape voxelshape2 = Block.box(f2, wallMinY, f2, f3, wallLowHeight, 16.0);
        VoxelShape voxelshape3 = Block.box(0.0, wallMinY, f2, f3, wallLowHeight, f3);
        VoxelShape voxelshape4 = Block.box(f2, wallMinY, f2, 16.0, wallLowHeight, f3);
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        for (BlockState blockstate : this.stateDefinition.getPossibleStates()) {
            VoxelShape voxelshape9 = voxelshape;
            if (blockstate.getValue(NORTH)) {
                voxelshape9 = Shapes.or(voxelshape9, voxelshape1);
            }
            if (blockstate.getValue(SOUTH)) {
                voxelshape9 = Shapes.or(voxelshape9, voxelshape2);
            }
            if (blockstate.getValue(WEST)) {
                voxelshape9 = Shapes.or(voxelshape9, voxelshape3);
            }
            if (blockstate.getValue(EAST)) {
                voxelshape9 = Shapes.or(voxelshape9, voxelshape4);
            }
            builder.put(blockstate, voxelshape9);
        }
        return builder.build();
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.shapeByIndex.get(state);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.collisionShapeByIndex.get(state);
    }

    @Override
    public WoodType getWoodType() {
        return woodType;
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    public boolean canConnect(BlockState state, boolean flag0, Direction direction) {
        Block block = state.getBlock();
//        boolean flag = block.isIn(BlockTags.FENCES) && p_220111_1_.getMaterial() == this.material;
        boolean flag = block instanceof PalisadeBlock || block instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(state, direction);
        return !isExceptionForConnection(state) && flag0 || flag;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockGetter iblockreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
        BlockPos blockpos1 = blockpos.north();
        BlockPos blockpos2 = blockpos.east();
        BlockPos blockpos3 = blockpos.south();
        BlockPos blockpos4 = blockpos.west();
        BlockState blockstate = iblockreader.getBlockState(blockpos1);
        BlockState blockstate1 = iblockreader.getBlockState(blockpos2);
        BlockState blockstate2 = iblockreader.getBlockState(blockpos3);
        BlockState blockstate3 = iblockreader.getBlockState(blockpos4);
        return super.getStateForPlacement(context)
                .setValue(NORTH, this.canConnect(blockstate, blockstate.isFaceSturdy(iblockreader, blockpos1, Direction.SOUTH), Direction.SOUTH))
                .setValue(EAST, this.canConnect(blockstate1, blockstate1.isFaceSturdy(iblockreader, blockpos2, Direction.WEST), Direction.WEST))
                .setValue(SOUTH, this.canConnect(blockstate2, blockstate2.isFaceSturdy(iblockreader, blockpos3, Direction.NORTH), Direction.NORTH))
                .setValue(WEST, this.canConnect(blockstate3, blockstate3.isFaceSturdy(iblockreader, blockpos4, Direction.EAST), Direction.EAST))
                .setValue(WATERLOGGED, ifluidstate.getType() == Fluids.WATER);
    }

    @Override
    protected @NonNull BlockState updateShape(BlockState stateIn, @NonNull LevelReader worldIn, @NonNull ScheduledTickAccess ticks, @NonNull BlockPos currentPos, @NonNull Direction directionToNeighbour, @NonNull BlockPos neighbourPos, @NonNull BlockState neighbourState, @NonNull RandomSource random) {
        if (stateIn.getValue(WATERLOGGED)) {
            ticks.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return directionToNeighbour.getAxis().getPlane() == Direction.Plane.HORIZONTAL ?
                stateIn.setValue(PROPERTY_BY_DIRECTION.get(directionToNeighbour), this.canConnect(neighbourState, neighbourState.isFaceSturdy(worldIn, neighbourPos, directionToNeighbour.getOpposite()), directionToNeighbour.getOpposite()))
                : super.updateShape(stateIn, worldIn, ticks, currentPos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }
}
