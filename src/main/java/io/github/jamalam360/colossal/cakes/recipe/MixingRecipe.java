package io.github.jamalam360.colossal.cakes.recipe;

import io.github.jamalam360.colossal.cakes.util.SingleStackSimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author Jamalam
 */
public class MixingRecipe implements Recipe<SingleStackSimpleInventory> {
    private final Identifier id;
    private final List<Ingredient> ingredients;
    private final ItemStack output;

    public MixingRecipe(Identifier id, List<Ingredient> ingredients, ItemStack output) {
        this.id = id;
        this.ingredients = ingredients;
        this.output = output;
    }

    public List<Ingredient> getMixingIngredients() {
        return ingredients;
    }

    @Override
    public boolean matches(SingleStackSimpleInventory inventory, World world) {
        List<ItemStack> items = inventory.getStacks();

        for (Ingredient ingredient : this.ingredients) {
            for (int i = 0; i < items.size(); i++) {
                ItemStack invStack = items.get(i);

                if (invStack.isEmpty()) continue;
                if (invStack.getCount() != 1)
                    throw new IllegalArgumentException("Invalid stack count for mixing recipe: " + invStack.getCount());

                if (ingredient.test(invStack)) {
                    items.set(i, ItemStack.EMPTY);
                    break;
                } else {
                    if (i == items.size() - 1) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public ItemStack getOutput() {
        return this.output;
    }

    public List<ItemStack> getRemainders(SingleStackSimpleInventory inventory) {
        return inventory.getStacks().stream()
                .filter((s) -> s.getItem().hasRecipeRemainder())
                .map((s) -> s.getItem().getRecipeRemainder())
                .map(ItemStack::new)
                .toList();
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MixingRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return MixingRecipeType.INSTANCE;
    }

    @Override
    public ItemStack craft(SingleStackSimpleInventory inventory) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }
}
