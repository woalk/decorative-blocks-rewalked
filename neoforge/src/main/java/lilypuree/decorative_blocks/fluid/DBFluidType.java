package lilypuree.decorative_blocks.fluid;

import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.fluids.FluidType;

public class DBFluidType extends FluidType {
    private final Identifier flowingTexture;
    private final Identifier stillTexture;

    private final Identifier overlayTexture;

    private final int fogColor;

    /**
     * Default constructor.
     *
     * @param properties the general properties of the fluid type
     */
    public DBFluidType(Properties properties, Identifier stillTexture, Identifier flowingTexture, Identifier overlayTexture, int fogColor) {
        super(properties);
        this.flowingTexture = flowingTexture;
        this.stillTexture = stillTexture;
        this.overlayTexture = overlayTexture;
        this.fogColor = fogColor;
    }
}
