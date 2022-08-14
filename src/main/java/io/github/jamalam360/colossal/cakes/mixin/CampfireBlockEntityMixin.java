package io.github.jamalam360.colossal.cakes.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * @author Jamalam
 */

@Mixin(CampfireBlockEntity.class)
public abstract class CampfireBlockEntityMixin {
    @Inject(
            method = "litServerTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/ItemScatterer;spawn(Lnet/minecraft/world/World;DDDLnet/minecraft/item/ItemStack;)V"
            ),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"
                    )
            ),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION
    )
    private static void colossalcakes$addRecipeRemaindersToCampfire(World world, BlockPos pos, BlockState state, CampfireBlockEntity campfire, CallbackInfo ci, boolean bl, int i, ItemStack itemStack, Inventory inventory, ItemStack itemStack2) {
        ItemScatterer.spawn(
                world,
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                ((CampfireBlockEntityAccessor) campfire).getRecipeCache()
                        .m_ltqsvwgf(inventory, world)
                        .map(recipe -> recipe.getRemainder(inventory).get(0))
                        .orElse(ItemStack.EMPTY)
        );
    }
}
