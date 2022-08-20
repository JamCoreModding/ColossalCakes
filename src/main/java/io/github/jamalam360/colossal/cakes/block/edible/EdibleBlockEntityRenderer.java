/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022 Jamalam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
