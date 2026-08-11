package lilypuree.decorative_blocks;

import lilypuree.decorative_blocks.fluid.ThatchBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import org.jspecify.annotations.NonNull;

public class FabricThatchFluidBlock extends LiquidBlock implements ThatchBlock {
    public FabricThatchFluidBlock(FlowingFluid fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override
    protected void entityInside(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Entity entity, @NonNull InsideBlockEffectApplier effectApplier, boolean isPrecise) {
        if (entity instanceof Player player) {
            playSoundIfMoving(player, level, pos);
        }
    }

    @Override
    public @NonNull ItemStack pickupBlock(LivingEntity user, @NonNull LevelAccessor level, @NonNull BlockPos pos, @NonNull BlockState state) {
        return ItemStack.EMPTY;
    }
}
