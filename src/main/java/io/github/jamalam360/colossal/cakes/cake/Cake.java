package io.github.jamalam360.colossal.cakes.cake;

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Jamalam
 */
public class Cake {
    private static final List<Cake> CAKES = new ArrayList<>();

    @Nullable
    public static Cake get(BlockPos pos) {
        for (Cake cake : CAKES) {
            if (cake.anyMatch((cakePos) -> cakePos.equals(pos))) return cake;
        }

        return null;
    }

    private final List<BlockPos> positions;
    private final List<BlockPos> eaten;

    protected Cake() {
        this.positions = new ArrayList<>();
        this.eaten = new ArrayList<>();
        CAKES.add(this);
    }

    public void discard() {
        CAKES.remove(this);
    }

    protected void add(BlockPos pos) {
        this.positions.add(pos);
    }

    public Pair<Integer, Float> eat(World world) {
        List<BlockPos> notEaten = this.getUneatenPositions();
        int numberToEat = Math.min(1, (world.random.nextInt(notEaten.size()) + 1) / 2);

        int hunger = 0;
        float saturation = 0;

        for (int i = 0; i < numberToEat; i++) {
            BlockPos pos = notEaten.get(world.random.nextInt(notEaten.size()));
            this.eaten.add(pos);
            notEaten.remove(pos);
            hunger++;
            saturation += 0.1f;
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }

        return Pair.of(hunger, saturation);
    }

    private List<BlockPos > getUneatenPositions() {
        return this.positions.stream().filter((pos) -> this.eaten.stream().noneMatch((eatenPos) -> eatenPos.equals(pos))).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<BlockPos> getPositions() {
        return this.positions;
    }

    public boolean isIn(BlockPos pos) {
        return this.positions.contains(pos);
    }

    public boolean anyMatch(Predicate<BlockPos> predicate) {
        for (BlockPos pos : this.positions) {
            if (predicate.test(pos)) {
                return true;
            }
        }

        return false;
    }

    public boolean allMatch(Predicate<BlockPos> predicate) {
        for (BlockPos pos : this.positions) {
            if (!predicate.test(pos)) {
                return false;
            }
        }

        return true;
    }
}
