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
