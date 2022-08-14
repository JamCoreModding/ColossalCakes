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
