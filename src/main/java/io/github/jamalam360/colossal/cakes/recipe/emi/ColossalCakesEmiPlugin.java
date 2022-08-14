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

package io.github.jamalam360.colossal.cakes.recipe.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import io.github.jamalam360.colossal.cakes.ColossalCakesInit;
import io.github.jamalam360.colossal.cakes.recipe.MixingRecipe;
import io.github.jamalam360.colossal.cakes.recipe.MixingRecipeType;
import io.github.jamalam360.colossal.cakes.registry.ColossalCakesBlocks;
import net.minecraft.util.Identifier;

/**
 * @author Jamalam
 */
public class ColossalCakesEmiPlugin implements EmiPlugin {
    public static final Identifier SPRITE_SHEET = ColossalCakesInit.idOf("textures/gui/emi.png");
    public static final EmiStack MIXING_BOWL = EmiStack.of(ColossalCakesBlocks.MIXING_BOWL.asItem());
    public static final EmiRecipeCategory CATEGORY = new EmiRecipeCategory(ColossalCakesInit.idOf("mixing"), MIXING_BOWL, new EmiTexture(SPRITE_SHEET, 0, 0, 16, 16));

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(CATEGORY);
        registry.addWorkstation(CATEGORY, MIXING_BOWL);

        for (MixingRecipe recipe : registry.getRecipeManager().listAllOfType(MixingRecipeType.INSTANCE)) {
            registry.addRecipe(new EmiMixingRecipe(recipe));
        }
    }
}
