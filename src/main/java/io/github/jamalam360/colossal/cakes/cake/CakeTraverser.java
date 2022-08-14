package io.github.jamalam360.colossal.cakes.cake;

import io.github.jamalam360.colossal.cakes.ColossalCakesInit;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jamalam
 */
public class CakeTraverser {
    public static boolean traverse(BlockView blockView, BlockPos start) {
        if (!blockView.getBlockState(start).isIn(ColossalCakesInit.CAKE) || Cake.get(start) != null) return false;

        Cake cake = new Cake();
        cake.add(start);
        ArrayList<BlockPos> explored = new ArrayList<>();
        explored.add(start);
        traverse(blockView, cake, explored, start);

        if (cake.getPositions().size() == 0) {
            cake.discard();
            return false;
        }

        return true;
    }

    private static void traverse(BlockView blockView, Cake cake, List<BlockPos> explored, BlockPos start) {
        BlockPos center = start.toImmutable();

        for (Direction dir : Direction.values()) {
            BlockPos.Mutable mutable = new BlockPos.Mutable(start.getX(), start.getY(), start.getZ());
            mutable.move(dir);

            if (explored.stream().anyMatch((pos) -> pos.equals(mutable))) continue;
            explored.add(mutable);

            BlockState state = blockView.getBlockState(mutable);
            if (state.isIn(ColossalCakesInit.CAKE)) {
                cake.add(mutable);
                traverse(blockView, cake, explored, mutable);
            }
        }
    }
}
