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

package io.github.jamalam360.colossal.cakes.cake;

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
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

    protected void addAll(Collection<BlockPos> positions) {
        this.positions.addAll(positions);
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
