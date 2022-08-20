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

package io.github.jamalam360.colossal.cakes.registry;

import io.github.jamalam360.colossal.cakes.ColossalCakesInit;
import io.github.jamalam360.colossal.cakes.item.RollingPinItem;
import io.github.jamalam360.colossal.cakes.item.WhiskItem;
import io.github.jamalam360.jamlib.registry.annotation.ContentRegistry;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;

/**
 * @author Jamalam
 */

@SuppressWarnings("unused")
@ContentRegistry(ColossalCakesInit.MOD_ID)
public class ColossalCakesItems {
    public static final Item WHISK = new WhiskItem(new Item.Settings().group(ColossalCakesInit.ITEMS));
    public static final Item ROLLING_PIN = new RollingPinItem(new Item.Settings().group(ColossalCakesInit.ITEMS));
    private static final Item.Settings BATTER_SETTINGS = new Item.Settings().group(ColossalCakesInit.ITEMS).recipeRemainder(Items.BUCKET).maxCount(16);
    public static final Item CARROT_CAKE_BATTER = new Item(BATTER_SETTINGS);
    public static final Item CHOCOLATE_CAKE_BATTER = new Item(BATTER_SETTINGS);
    public static final Item RED_VELVET_BATTER = new Item(BATTER_SETTINGS);
    public static final Item VICTORIA_SPONGE_BATTER = new Item(BATTER_SETTINGS);
}
