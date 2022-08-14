package io.github.jamalam360.colossal.cakes.recipe;

import com.google.gson.JsonObject;

import java.util.List;

/**
 * @author Jamalam
 */
public class MixingRecipeJson {
    List<JsonObject> ingredients;
    Output output;

    static class Output {
        String item;
        int count;
    }
}
