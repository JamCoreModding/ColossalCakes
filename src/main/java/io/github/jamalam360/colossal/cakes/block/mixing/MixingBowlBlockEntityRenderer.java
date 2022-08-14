package io.github.jamalam360.colossal.cakes.block.mixing;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;

/**
 * @author Jamalam
 */
public class MixingBowlBlockEntityRenderer implements BlockEntityRenderer<MixingBowlBlockEntity> {
    @SuppressWarnings("unused")
    public MixingBowlBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(MixingBowlBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        double currentHeight = 2D / 24;
        int fixedLight = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());

        for (ItemStack stack : entity.inventory.getStacks()) {
            if (!stack.isEmpty()) {
                matrices.push();
                matrices.translate(0.5, currentHeight, 0.5);
                matrices.scale(0.85f, 1f, 0.85f);
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));
                matrices.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(this.getRotation(stack)));
                MinecraftClient.getInstance().getItemRenderer().renderItem(
                        stack,
                        ModelTransformation.Mode.GROUND,
                        fixedLight,
                        overlay,
                        matrices,
                        vertexConsumers,
                        0
                );
                matrices.pop();
                currentHeight += 1D / 24;
            }
        }
    }

    /**
     * Calculates a consistent rotation for an {@link ItemStack} based on its {@link ItemStack#hashCode()} value.
     * It generates relatively uniform distribution of rotations.
     */
    private float getRotation(ItemStack stack) {
        int hash = stack.hashCode();

        // This is a very simple hash function that generates a uniformly distributed rotation.
        // It is not very good, but it is good enough for our purposes.
        return (hash & 0xFFFF) / (float) 0xFFFF * 360;
    }
}
