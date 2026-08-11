package lilypuree.decorative_blocks.blocks.types;

import lilypuree.decorative_blocks.blocks.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jspecify.annotations.NonNull;

import java.util.function.BiFunction;

public record WoodDecorativeBlockTypes<T extends Block> (String name, BiFunction<WoodType, BlockBehaviour.Properties, T> constructor) {
    public static final WoodDecorativeBlockTypes<BeamBlock> BEAM = new WoodDecorativeBlockTypes<>("beam", BeamBlock::new);
    public static final WoodDecorativeBlockTypes<PalisadeBlock> PALISADE = new WoodDecorativeBlockTypes<>("palisade", PalisadeBlock::new);
    public static final WoodDecorativeBlockTypes<SeatBlock> SEAT = new WoodDecorativeBlockTypes<>("seat", SeatBlock::new);
    public static final WoodDecorativeBlockTypes<SupportBlock> SUPPORT = new WoodDecorativeBlockTypes<>("support", SupportBlock::new);

    public @NonNull String toString() {
        return this.getName();
    }

    public String getName() {
        return this.name;
    }

    public T create(WoodType woodType, BlockBehaviour.Properties properties) {
        return constructor.apply(woodType, properties);
    }

}
