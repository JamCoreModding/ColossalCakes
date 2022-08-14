package io.github.jamalam360.colossal.cakes.mixin;

import io.github.jamalam360.colossal.cakes.util.Ducks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Jamalam
 */

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Ducks.LivingEntity {
    private int colossalcakes$whiskedTicks = 0;

    public LivingEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void onWhisked(PlayerEntity whisker) {
        this.colossalcakes$whiskedTicks += 20;
    }

    @Inject(
            method = "tickMovement",
            at = @At("HEAD")
    )
    private void colossalcakes$tickWhiskSpin(CallbackInfo ci) {
        if (this.colossalcakes$whiskedTicks > 0) {
            this.colossalcakes$whiskedTicks--;

            this.refreshPositionAndAngles(this.getBlockPos(), this.getYaw() + 10, this.getPitch());
        }
    }
}
