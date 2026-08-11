package lilypuree.decorative_blocks.entity;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.jspecify.annotations.NonNull;

public class EmptyRenderer extends EntityRenderer<DummyEntityForSitting, EntityRenderState> {
    public EmptyRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public boolean shouldRender(@NonNull DummyEntityForSitting livingEntityIn, @NonNull Frustum camera, double camX, double camY, double camZ) {
        return false;
    }

    @Override
    public @NonNull EntityRenderState createRenderState() {
        return new EntityRenderState();
    }
}
