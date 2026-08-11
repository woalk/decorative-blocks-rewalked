package lilypuree.decorative_blocks.items;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.blocks.state.ModBlockProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public class SeatItem extends SwitchableBlockItem {
    public static final Identifier OVERRIDE_TAG = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "post");

    public SeatItem(Block blockIn, Properties builder) {
        super(blockIn, builder, ModBlockProperties.POST, false);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(@NonNull ItemStack stack, @NonNull TooltipContext ctx, @NonNull TooltipDisplay display, @NonNull Consumer<Component> builder, @NonNull TooltipFlag tooltipFlag) {
        if (Minecraft.getInstance().hasShiftDown()) {
            builder.accept(Component.translatable("wiki.decorative_blocks.seat"));
        }
        super.appendHoverText(stack, ctx, display, builder, tooltipFlag);
    }
}
