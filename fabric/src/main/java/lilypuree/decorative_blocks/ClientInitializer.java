package lilypuree.decorative_blocks;

import lilypuree.decorative_blocks.entity.EmptyRenderer;
import lilypuree.decorative_blocks.fluid.ThatchFluid;
import lilypuree.decorative_blocks.registration.Registration;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderingRegistry;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.util.ARGB;


public class ClientInitializer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRenderers.register(Registration.DUMMY_ENTITY_TYPE.get(), EmptyRenderer::new);
        registerThatchlike(Registration.referenceHolder);
    }

    public static void registerThatchlike(ThatchFluid.FluidReferenceHolder referenceHolder) {
        FluidRenderingRegistry.register(referenceHolder.getSourceFluid(), referenceHolder.getFlowingFluid(),
                new FluidModel.Unbaked(
                        new Material(referenceHolder.thatchStillTexture()),
                        new Material(referenceHolder.thatchFlowingTexture()),
                        null,
                        BlockTintSources.constant(ARGB.color(255, 255, 25))
                ));
    }
}
