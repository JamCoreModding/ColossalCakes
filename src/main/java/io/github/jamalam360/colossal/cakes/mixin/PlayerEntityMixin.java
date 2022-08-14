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
