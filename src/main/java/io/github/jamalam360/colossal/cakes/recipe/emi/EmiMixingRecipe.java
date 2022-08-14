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

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import io.github.jamalam360.colossal.cakes.recipe.MixingRecipe;
import io.github.jamalam360.colossal.cakes.registry.ColossalCakesBlocks;
import io.github.jamalam360.colossal.cakes.registry.ColossalCakesItems;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jamalam
 */
public class EmiMixingRecipe implements EmiRecipe {
    private static final EmiStack MIXING_BOWL = EmiStack.of(ColossalCakesBlocks.MIXING_BOWL);
    private static final EmiStack WHISK = EmiStack.of(ColossalCakesItems.WHISK);

    private final Identifier id;
    private final List<EmiIngredient> inputs;
    private final EmiStack output;

    public EmiMixingRecipe(MixingRecipe recipe) {
        this.id = recipe.getId();
        this.inputs = recipe.getMixingIngredients().stream().map(EmiIngredient::of).collect(Collectors.toList());
        Collections.shuffle(this.inputs);
        this.output = EmiStack.of(recipe.getOutput());
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        for (int i = 0; i < 10; i++) {
            if (i < inputs.size() && inputs.get(i) != null) {
                widgets.addSlot(inputs.get(i), i % 2 == 0 ? 0 : 18, (i / 2) * 18);
            } else {
                widgets.addSlot(EmiStack.EMPTY, i % 2 == 0 ? 0 : 18, (i / 2) * 18);
            }
        }

        widgets.addTexture(EmiTexture.EMPTY_ARROW, 42, 37);

        widgets.addSlot(MIXING_BOWL, 72, 26);
        widgets.addSlot(WHISK, 72, 48).catalyst(true);

        widgets.addTexture(EmiTexture.EMPTY_ARROW, 96, 37);
        widgets.addSlot(output, 126, 33).recipeContext(this).output(true);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return ColossalCakesEmiPlugin.CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return this.id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return this.inputs;
    }

    @Override
    public List<EmiIngredient> getCatalysts() {
        return List.of(EmiStack.of(ColossalCakesItems.WHISK));
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(this.output);
    }

    @Override
    public int getDisplayWidth() {
        return 152;
    }

    @Override
    public int getDisplayHeight() {
        return 89;
    }
}
