package io.github.jamalam360.colossal.cakes;

import io.github.jamalam360.colossal.cakes.block.mixing.MixingBowlBlockEntityRenderer;
import io.github.jamalam360.colossal.cakes.registry.ColossalCakesBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

/**
 * @author Jamalam
 */
public class ColossalCakesClientInit implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(ColossalCakesBlocks.MIXING_BOWL_BLOCK_ENTITY, MixingBowlBlockEntityRenderer::new);
    }
}
