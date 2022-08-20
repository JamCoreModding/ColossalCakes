package io.github.jamalam360.colossal.cakes.block.edible;

import io.github.jamalam360.colossal.cakes.cake.Cake;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jamalam
 */
@SuppressWarnings("deprecation")
public class EdibleBlock extends Block implements BlockEntityProvider {
    private final FoodComponent foodComponent;

    public EdibleBlock(Settings settings, FoodComponent foodComponent) {
        super(settings);
        this.foodComponent = foodComponent;
    }

    public FoodComponent getFoodComponent() {
        return this.foodComponent;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Cake cake = Cake.get(pos);
        if (cake != null && player.getStackInHand(hand).isEmpty()) return cake.onBlockUsed(world, player);
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EdibleBlockEntity(pos, state);
    }
}
