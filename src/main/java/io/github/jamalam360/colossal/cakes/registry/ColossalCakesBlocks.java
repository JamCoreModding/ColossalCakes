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
import io.github.jamalam360.colossal.cakes.block.mixing.MixingBowlBlock;
import io.github.jamalam360.colossal.cakes.block.mixing.MixingBowlBlockEntity;
import io.github.jamalam360.jamlib.registry.annotation.BlockItemFactory;
import io.github.jamalam360.jamlib.registry.annotation.ContentRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

/**
 * @author Jamalam
 */

@SuppressWarnings("unused")
@ContentRegistry(ColossalCakesInit.MOD_ID)
public class ColossalCakesBlocks {
    public static final Block MIXING_BOWL = new MixingBowlBlock(AbstractBlock.Settings.of(Material.STONE).requiresTool());
    private static final AbstractBlock.Settings SPONGE_SETTINGS = AbstractBlock.Settings.of(Material.CAKE);    public static final BlockEntityType<MixingBowlBlockEntity> MIXING_BOWL_BLOCK_ENTITY = BlockEntityType.Builder.create(MixingBowlBlockEntity::new, MIXING_BOWL).build(null);
    public static final Block VICTORIA_SPONGE = new Block(SPONGE_SETTINGS);

    @BlockItemFactory
    public static Item createBlockItem(Block block) {
        Item.Settings settings = new Item.Settings().group(ItemGroup.FOOD);
        return new BlockItem(block, settings);
    }


}
