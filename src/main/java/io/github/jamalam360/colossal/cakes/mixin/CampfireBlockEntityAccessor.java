package io.github.jamalam360.colossal.cakes.mixin;

import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author Jamalam
 */

@Mixin(CampfireBlockEntity.class)
public interface CampfireBlockEntityAccessor {
    @Accessor
    RecipeManager.C_bvtkxdyi<Inventory, CampfireCookingRecipe> getRecipeCache();
}
