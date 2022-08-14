package io.github.jamalam360.colossal.cakes.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jamalam
 */
public class MixingRecipeSerializer implements RecipeSerializer<MixingRecipe> {
    public static final MixingRecipeSerializer INSTANCE = new MixingRecipeSerializer();
    private static final Gson GSON = new Gson();

    private MixingRecipeSerializer() {
    }

    @Override
    public MixingRecipe read(Identifier id, JsonObject json) {
        MixingRecipeJson parsed = GSON.fromJson(json, MixingRecipeJson.class);

        if (parsed.output.count == 0) {
            parsed.output.count = 1;
        }

        List<Ingredient> ingredients = parsed.ingredients.stream().map(Ingredient::fromJson).toList();
        ItemStack output = new ItemStack(Registry.ITEM.get(new Identifier(parsed.output.item)), parsed.output.count);
        return new MixingRecipe(id, ingredients, output);
    }

    @Override
    public void write(PacketByteBuf buf, MixingRecipe recipe) {
        buf.writeInt(recipe.getMixingIngredients().size());
        recipe.getMixingIngredients().forEach(ingredient -> ingredient.write(buf));
        buf.writeItemStack(recipe.getOutput());
    }

    @Override
    public MixingRecipe read(Identifier id, PacketByteBuf buf) {
        int ingredientCount = buf.readInt();
        List<Ingredient> ingredients = new ArrayList<>();

        for (int i = 0; i < ingredientCount; i++) {
            ingredients.add(Ingredient.fromPacket(buf));
        }

        ItemStack output = buf.readItemStack();
        return new MixingRecipe(id, ingredients, output);
    }
}
