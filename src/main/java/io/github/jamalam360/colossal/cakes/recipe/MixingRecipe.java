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
