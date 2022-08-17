package io.github.jamalam360.colossal.cakes.registry;

import io.github.jamalam360.colossal.cakes.ColossalCakesInit;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

/**
 * @author Jamalam
 */
public class ColossalCakesTags {
    public static final TagKey<Block> CAKE = TagKey.of(Registry.BLOCK_KEY, ColossalCakesInit.idOf("cake"));
}
