package io.github.jamalam360.colossal.cakes.block.edible;

import com.mojang.blaze3d.vertex.OverlayVertexConsumer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.jamalam360.colossal.cakes.cake.Cake;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

/**
 * @author Jamalam
 */
public class EdibleBlockEntityRenderer implements BlockEntityRenderer<EdibleBlockEntity> {
    @SuppressWarnings("unused")
    public EdibleBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(EdibleBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        Cake cake = Cake.get(entity.getPos());

        if (cake != null) {
            BlockPos pos = entity.getPos();
            BlockState state = entity.getWorld().getBlockState(pos);

            if (cake.hasEatingBegun()) {
                int correctedProgress = cake.getNormalizedEatProgress();

                matrices.push();
                matrices.scale(1.01f, 1.01f, 1.01f);
                MatrixStack.Entry entry = matrices.peek();
                VertexConsumer vertexConsumer2 = new OverlayVertexConsumer(
                        MinecraftClient.getInstance().getBufferBuilders().getEffectVertexConsumers().getBuffer(ModelLoader.BLOCK_DESTRUCTION_RENDER_LAYERS.get(correctedProgress)),
                        entry.getPosition(),
                        entry.getNormal()
                );

                BakedModel bakedModel = MinecraftClient.getInstance().getBlockRenderManager().getModels().getModel(state);
                long l = state.getRenderingSeed(pos);
                MinecraftClient.getInstance().getBlockRenderManager().getModelRenderer().render(
                        entity.getWorld(), bakedModel, state, pos, matrices, vertexConsumer2, true, entity.getWorld().random, l, OverlayTexture.DEFAULT_UV
                );
                matrices.pop();
            }
        }
    }
}
