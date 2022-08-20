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
import io.github.jamalam360.colossal.cakes.block.edible.EdibleBlock;
import io.github.jamalam360.colossal.cakes.block.edible.EdibleBlockEntity;
import io.github.jamalam360.colossal.cakes.block.mixing.MixingBowlBlock;
import io.github.jamalam360.colossal.cakes.block.mixing.MixingBowlBlockEntity;
import io.github.jamalam360.jamlib.registry.annotation.BlockItemFactory;
import io.github.jamalam360.jamlib.registry.annotation.ContentRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

/**
 * @author Jamalam
 */

@SuppressWarnings("unused")
@ContentRegistry(ColossalCakesInit.MOD_ID)
public class ColossalCakesBlocks {
    public static final Block MIXING_BOWL = new MixingBowlBlock(AbstractBlock.Settings.of(Material.METAL).strength(4.0f).requiresTool());
    public static final BlockEntityType<MixingBowlBlockEntity> MIXING_BOWL_BLOCK_ENTITY = BlockEntityType.Builder.create(MixingBowlBlockEntity::new, MIXING_BOWL).build(null);

    private static final AbstractBlock.Settings SPONGE_SETTINGS = AbstractBlock.Settings.of(Material.CAKE).sounds(BlockSoundGroup.WOOL).requiresTool().strength(0.3f);

    public static final Block CARROT_CAKE = new EdibleBlock(SPONGE_SETTINGS, new FoodComponent.Builder().hunger(3).saturationModifier(0.4f).statusEffect(
            new StatusEffectInstance(StatusEffects.NIGHT_VISION, 180, 2),
            0.15f
    ).build());
    public static final Block CHOCOLATE_CAKE = new EdibleBlock(SPONGE_SETTINGS, new FoodComponent.Builder().hunger(1).saturationModifier(0.7f).statusEffect(
            new StatusEffectInstance(StatusEffects.JUMP_BOOST, 140, 2),
            0.20f
    ).build());
    public static final Block RED_VELVET = new EdibleBlock(SPONGE_SETTINGS, new FoodComponent.Builder().hunger(2).saturationModifier(0.5f).build());
    public static final Block VICTORIA_SPONGE = new EdibleBlock(SPONGE_SETTINGS, new FoodComponent.Builder().hunger(2).saturationModifier(0.6f).build());

    public static final Block ICING = new EdibleBlock(SPONGE_SETTINGS, new FoodComponent.Builder().hunger(1).saturationModifier(0.3f).statusEffect(
            new StatusEffectInstance(StatusEffects.SPEED, 80, 1),
            0.28f
    ).build());
    public static final Block BERRY_ICING = new EdibleBlock(SPONGE_SETTINGS, new FoodComponent.Builder().hunger(1).saturationModifier(0.3f).statusEffect(
            new StatusEffectInstance(StatusEffects.SPEED, 120, 1),
            0.35f
    ).build());
    public static final BlockEntityType<EdibleBlockEntity> EDIBLE_BLOCK_ENTITY = BlockEntityType.Builder.create(EdibleBlockEntity::new, CARROT_CAKE, CHOCOLATE_CAKE, RED_VELVET, VICTORIA_SPONGE, ICING, BERRY_ICING).build(null);


    @BlockItemFactory
    public static Item createBlockItem(Block block) {
        Item.Settings settings = new Item.Settings().group(ColossalCakesInit.ITEMS);
        return new BlockItem(block, settings);
    }
}
