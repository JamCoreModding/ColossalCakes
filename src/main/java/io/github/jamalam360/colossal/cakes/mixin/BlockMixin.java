package io.github.jamalam360.colossal.cakes.mixin;

import io.github.jamalam360.colossal.cakes.cake.Cake;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Jamalam
 */

@Mixin(Block.class)
public class BlockMixin {
    @Inject(
            method = "onBreak",
            at = @At("HEAD")
    )
    public void colossalcakes$onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo ci) {
        Cake cake = Cake.get(pos);

        if (cake != null) {
            cake.discard();
        }
    }
}
