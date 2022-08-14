package io.github.jamalam360.colossal.cakes.registry;

import io.github.jamalam360.colossal.cakes.ColossalCakesInit;
import io.github.jamalam360.jamlib.registry.annotation.ContentRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;

/**
 * @author Jamalam
 */

@SuppressWarnings("unused")
@ContentRegistry(ColossalCakesInit.MOD_ID)
public class ColossalCakesItems {
    public static final Item VICTORIA_SPONGE_BATTER = new Item(new Item.Settings().group(ItemGroup.FOOD).recipeRemainder(Items.BUCKET));

    public static final Item WHISK = new Item(new Item.Settings().group(ItemGroup.FOOD));
}
