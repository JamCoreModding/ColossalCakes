package io.github.jamalam360.colossal.cakes.item;

import io.github.jamalam360.colossal.cakes.registry.ColossalCakesItems;
import io.github.jamalam360.colossal.cakes.util.Ducks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

/**
 * @author Jamalam
 */
public class WhiskItem extends ToolItem {
    public WhiskItem(Settings settings) {
        super(ToolMaterials.IRON, settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        ((Ducks.LivingEntity) entity).onWhisked(user);

        if (!user.world.isClient) {
            stack.damage(1, user, (player) -> player.sendToolBreakStatus(hand));
            user.getItemCooldownManager().set(ColossalCakesItems.WHISK, 20);
        }

        return ActionResult.SUCCESS;
    }
}
