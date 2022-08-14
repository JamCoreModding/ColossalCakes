package io.github.jamalam360.colossal.cakes.mixin;

import io.github.jamalam360.colossal.cakes.item.RollingPinItem;
import io.github.jamalam360.colossal.cakes.util.Sound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

/**
 * @author Jamalam
 */

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @ModifyArgs(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V",
                    ordinal = 1
            )
    )
    private void colossalcakes$useBonkSweep(Args args) {
        if (((PlayerEntity) (Object) this).getStackInHand(Hand.MAIN_HAND).getItem() instanceof RollingPinItem) {
            Sound sound = RollingPinItem.getSweepAttackSound();
            args.set(4, sound.sound());
            args.set(6, sound.volume());
            args.set(7, sound.pitch());
        }
    }

    @ModifyArgs(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V",
                    ordinal = 2
            )
    )
    private void colossalcakes$useBonkCrit(Args args) {
        if (((PlayerEntity) (Object) this).getStackInHand(Hand.MAIN_HAND).getItem() instanceof RollingPinItem) {
            Sound sound = RollingPinItem.getCritAttackSound();
            args.set(4, sound.sound());
            args.set(6, sound.volume());
            args.set(7, sound.pitch());
        }
    }

    @ModifyArgs(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V",
                    ordinal = 3
            )
    )
    private void colossalcakes$useBonkStrong(Args args) {
        if (((PlayerEntity) (Object) this).getStackInHand(Hand.MAIN_HAND).getItem() instanceof RollingPinItem) {
            Sound sound = RollingPinItem.getStrongAttackSound();
            args.set(4, sound.sound());
            args.set(6, sound.volume());
            args.set(7, sound.pitch());
        }
    }

    @ModifyArgs(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V",
                    ordinal = 4
            )
    )
    private void colossalcakes$useBonkWeak(Args args) {
        if (((PlayerEntity) (Object) this).getStackInHand(Hand.MAIN_HAND).getItem() instanceof RollingPinItem) {
            Sound sound = RollingPinItem.getWeakAttackSound();
            args.set(4, sound.sound());
            args.set(6, sound.volume());
            args.set(7, sound.pitch());
        }
    }
}
