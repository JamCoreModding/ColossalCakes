package io.github.jamalam360.colossal.cakes.block.edible;

import io.github.jamalam360.colossal.cakes.registry.ColossalCakesBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

/**
 * @author Jamalam
 */
public class EdibleBlockEntity extends BlockEntity {
    public EdibleBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ColossalCakesBlocks.EDIBLE_BLOCK_ENTITY, blockPos, blockState);
    }
}
