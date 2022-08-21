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
import io.github.jamalam360.colossal.cakes.block.edible.EdibleBlock;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Jamalam
 */
public class Cake {
    protected static final List<Cake> CAKES = new ArrayList<>();
    private final List<BlockPos> positions;
    private int eatProgress = 0;
    private int eatProgressMax = 0;

    protected Cake() {
        this.positions = new ArrayList<>();
        CAKES.add(this);
    }

    @Nullable
    public static Cake get(BlockPos pos) {
        for (Cake cake : CAKES) {
            if (cake.anyMatch((cakePos) -> cakePos.equals(pos))) return cake;
        }

        return null;
    }

    public static void read(World world, NbtCompound nbt) {
        BlockPos pos = new BlockPos(nbt.getInt("X"), nbt.getInt("Y"), nbt.getInt("Z"));
        CakeTraverser.traverse(world, pos);
        Cake.get(pos).eatProgress = nbt.getInt("EatProgress");
    }

    public void discard() {
        CAKES.remove(this);
    }

    protected void add(BlockPos pos) {
        this.positions.add(pos);
        this.recalculateEatProgress();
    }

    protected void addAll(Collection<BlockPos> positions) {
        this.positions.addAll(positions);
        this.recalculateEatProgress();
    }

    private void recalculateEatProgress() {
        float f = this.positions.size() * 4.2F;
        this.eatProgressMax = Math.round(f);
    }

    public void remove(BlockPos pos) {
        this.positions.remove(pos);

        if (this.positions.size() == 0) {
            this.discard();
        }
    }

    public boolean hasEatingBegun() {
        return this.eatProgress - 1 > 0;
    }

    public int getNormalizedEatProgress() {
        return Math.round((this.eatProgress / (float) this.eatProgressMax) * 9);
    }

    public ActionResult onBlockUsed(World world, PlayerEntity player) {
        if (!player.canConsume(false)) return ActionResult.FAIL;

        BlockPos random = this.positions.get(world.random.nextInt(this.positions.size()));

        if (!world.isClient) {
            if (world.getBlockState(random).getBlock() instanceof EdibleBlock edible) {
                FoodComponent food = edible.getFoodComponent();
                player.getHungerManager().add(food.getHunger(), food.getSaturationModifier());

                for (Pair<StatusEffectInstance, Float> pair : food.getStatusEffects()) {
                    if (pair.getFirst() != null && player.world.random.nextFloat() < pair.getSecond() && player.world.random.nextInt(4) == 0) {
                        player.addStatusEffect(new StatusEffectInstance(pair.getFirst()));
                    }
                }

                world.playSoundFromEntity(null, player, SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.9F);
            } else {
                return ActionResult.FAIL;
            }
        }

        this.eatProgress++;

        if (this.eatProgress >= this.eatProgressMax && !world.isClient) {
            for (BlockPos pos : this.positions) {
                world.breakBlock(pos, false);
            }

            this.positions.clear();
            this.discard();
        } else if (this.eatProgress >= this.eatProgressMax) {
            this.positions.clear();
            this.discard();
        }

        return ActionResult.SUCCESS;
    }

    public List<BlockPos> getPositions() {
        return this.positions;
    }

    public NbtCompound write() {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("EatProgress", this.eatProgress);
        nbt.putInt("X", this.positions.get(0).getX());
        nbt.putInt("Y", this.positions.get(0).getY());
        nbt.putInt("Z", this.positions.get(0).getZ());
        return nbt;
    }

    public boolean anyMatch(Predicate<BlockPos> predicate) {
        for (BlockPos pos : this.positions) {
            if (predicate.test(pos)) {
                return true;
            }
        }

        return false;
    }
}
