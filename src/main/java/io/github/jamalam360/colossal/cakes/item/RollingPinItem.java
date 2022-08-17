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

package io.github.jamalam360.colossal.cakes.item;

import io.github.jamalam360.colossal.cakes.ColossalCakesInit;
import io.github.jamalam360.colossal.cakes.cake.Cake;
import io.github.jamalam360.colossal.cakes.cake.CakeTraverser;
import io.github.jamalam360.colossal.cakes.registry.ColossalCakesSounds;
import io.github.jamalam360.colossal.cakes.util.Sound;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;

/**
 * @author Jamalam
 */
public class RollingPinItem extends SwordItem {
    public RollingPinItem(Settings settings) {
        super(ToolMaterials.WOOD, 6, -3.1f, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (Cake.get(context.getBlockPos()) != null) return ActionResult.PASS;
        return CakeTraverser.traverse(context.getWorld(), context.getBlockPos()) ? ActionResult.SUCCESS : ActionResult.FAIL;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, target.world.random.nextInt(40) + 40, 4), attacker);
        return super.postHit(stack, target, attacker);
    }

    public static Sound getWeakAttackSound() {
        return new Sound(ColossalCakesSounds.ITEM_ROLLING_PIN_BONK, 0.8f, 1.3f);
    }

    public static Sound getStrongAttackSound() {
        return new Sound(ColossalCakesSounds.ITEM_ROLLING_PIN_BONK, 1f, 1f);
    }

    public static Sound getCritAttackSound() {
        return new Sound(ColossalCakesSounds.ITEM_ROLLING_PIN_BONK, 1.25f, 0.8f);
    }

    public static Sound getSweepAttackSound() {
        return new Sound(ColossalCakesSounds.ITEM_ROLLING_PIN_BONK_SWEEP, 1f, 1f);
    }
}
