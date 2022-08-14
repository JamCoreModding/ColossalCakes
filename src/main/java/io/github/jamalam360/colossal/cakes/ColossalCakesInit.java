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

package io.github.jamalam360.colossal.cakes;

import com.mojang.datafixers.util.Pair;
import io.github.jamalam360.colossal.cakes.cake.Cake;
import io.github.jamalam360.colossal.cakes.item.RollingPinItem;
import io.github.jamalam360.colossal.cakes.recipe.MixingRecipeSerializer;
import io.github.jamalam360.colossal.cakes.recipe.MixingRecipeType;
import io.github.jamalam360.colossal.cakes.registry.ColossalCakesBlocks;
import io.github.jamalam360.colossal.cakes.registry.ColossalCakesItems;
import io.github.jamalam360.jamlib.log.JamLibLogger;
import io.github.jamalam360.jamlib.registry.JamLibRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ColossalCakesInit implements ModInitializer {
    public static final String MOD_ID = "colossal_cakes";
    public static final String MOD_NAME = "Colossal Cakes";
    public static final JamLibLogger LOGGER = JamLibLogger.getLogger(MOD_ID);

    public static final TagKey<Block> CAKE = TagKey.of(Registry.BLOCK_KEY, idOf("cake"));

    public static Identifier idOf(String path) {
        return new Identifier(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        Registry.register(
                Registry.RECIPE_SERIALIZER,
                idOf("mixing"),
                MixingRecipeSerializer.INSTANCE
        );

        Registry.register(
                Registry.RECIPE_TYPE,
                idOf("mixing"),
                MixingRecipeType.INSTANCE
        );

        Registry.register(
                Registry.SOUND_EVENT,
                idOf("bonk"),
                RollingPinItem.BONK
        );

        Registry.register(
                Registry.SOUND_EVENT,
                idOf("bonk_sweep"),
                RollingPinItem.BONK_SWEEP
        );

        JamLibRegistry.register(
                ColossalCakesBlocks.class,
                ColossalCakesItems.class
        );

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (player.getStackInHand(hand).isEmpty()) {
                Cake cake = Cake.get(hitResult.getBlockPos());

                if (player.isSneaking()) {
                    player.sendMessage(Text.literal(cake == null ? "No Cake :(" : "Much Cake :)"), true);
                    return ActionResult.SUCCESS;
                }


                if (cake != null) {
                    Pair<Integer, Float> hungerValues = cake.eat(world);
                    player.getHungerManager().add(hungerValues.getFirst(), hungerValues.getSecond());
                    return ActionResult.SUCCESS;
                }
            }

            return ActionResult.PASS;
        });

        LOGGER.logInitialize();
    }
}
