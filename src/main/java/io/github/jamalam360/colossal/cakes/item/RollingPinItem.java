package io.github.jamalam360.colossal.cakes.item;

import io.github.jamalam360.colossal.cakes.ColossalCakesInit;
import io.github.jamalam360.colossal.cakes.cake.Cake;
import io.github.jamalam360.colossal.cakes.cake.CakeTraverser;
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
    public static final SoundEvent BONK = new SoundEvent(ColossalCakesInit.idOf("bonk"));
    public static final SoundEvent BONK_SWEEP = new SoundEvent(ColossalCakesInit.idOf("bonk_sweep"));

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
        return new Sound(BONK, 0.8f, 1.3f);
    }

    public static Sound getStrongAttackSound() {
        return new Sound(BONK, 1f, 1f);
    }

    public static Sound getCritAttackSound() {
        return new Sound(BONK, 1.25f, 0.8f);
    }

    public static Sound getSweepAttackSound() {
        return new Sound(BONK_SWEEP, 1f, 1f);
    }
}
