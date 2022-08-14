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
import net.minecraft.item.Items;

/**
 * @author Jamalam
 */

@SuppressWarnings("unused")
@ContentRegistry(ColossalCakesInit.MOD_ID)
public class ColossalCakesBlocks {
    public static final Block MIXING_BOWL = new MixingBowlBlock(AbstractBlock.Settings.of(Material.STONE).requiresTool());
    public static final BlockEntityType<MixingBowlBlockEntity> MIXING_BOWL_BLOCK_ENTITY = BlockEntityType.Builder.create(MixingBowlBlockEntity::new, MIXING_BOWL).build(null);

    private static final AbstractBlock.Settings SPONGE_SETTINGS = AbstractBlock.Settings.of(Material.CAKE);
    public static final Block VICTORIA_SPONGE = new Block(SPONGE_SETTINGS);

    @BlockItemFactory
    public static Item createBlockItem(Block block) {
        Item.Settings settings = new Item.Settings().group(ItemGroup.FOOD);
        return new BlockItem(block, settings);
    }
}
