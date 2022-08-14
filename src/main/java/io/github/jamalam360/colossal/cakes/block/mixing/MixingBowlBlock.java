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

package io.github.jamalam360.colossal.cakes.block.mixing;

import io.github.jamalam360.colossal.cakes.recipe.MixingRecipe;
import io.github.jamalam360.colossal.cakes.recipe.MixingRecipeType;
import io.github.jamalam360.colossal.cakes.registry.ColossalCakesItems;
import io.github.jamalam360.colossal.cakes.util.SingleStackSimpleInventory;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Jamalam
 */
@SuppressWarnings("deprecation")
public class MixingBowlBlock extends BlockWithEntity {
    public MixingBowlBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof MixingBowlBlockEntity mixingBowlBlockEntity) {
            if (player.getStackInHand(hand).getItem() == ColossalCakesItems.WHISK) {
                List<ItemStack> invCopy = new ArrayList<>(mixingBowlBlockEntity.inventory.getStacks().stream().toList());
                Optional<MixingRecipe> match = world.getRecipeManager().getFirstMatch(MixingRecipeType.INSTANCE, mixingBowlBlockEntity.inventory, world);

                if (match.isPresent()) {
                    if (!world.isClient) {
                        for (ItemStack stack : match.get().getRemainders(new SingleStackSimpleInventory(invCopy.toArray(new ItemStack[0])))) {
                            player.getInventory().offerOrDrop(stack);
                        }

                        player.getInventory().offerOrDrop(match.get().getOutput().copy());
                    } else {
                        world.playSound(
                                pos.getX(),
                                pos.getY(),
                                pos.getZ(),
                                SoundEvents.ITEM_BUCKET_FILL,
                                SoundCategory.BLOCKS,
                                1.0F,
                                world.random.range(4, 12) / 10.0F,
                                true
                        );
                    }

                    return ActionResult.SUCCESS;
                } else {
                    return ActionResult.FAIL;
                }
            } else {
                if (player.isSneaking() && player.getStackInHand(hand).isEmpty()) {
                    ItemStack returnedStack = mixingBowlBlockEntity.inventory.removeLastStack();

                    if (!returnedStack.isEmpty()) {
                        if (!world.isClient && !player.getAbilities().creativeMode) {
                            player.getInventory().offerOrDrop(returnedStack);
                        }

                        return ActionResult.SUCCESS;
                    } else {
                        return ActionResult.FAIL;
                    }
                } else if (!player.getStackInHand(hand).isEmpty()) {
                    ItemStack returnedStack = mixingBowlBlockEntity.inventory.addStack(player.getStackInHand(hand));

                    if (player.getStackInHand(hand).getCount() != returnedStack.getCount() || !player.getStackInHand(hand).isItemEqual(returnedStack)) {
                        if (!world.isClient && !player.getAbilities().creativeMode) {
                            player.setStackInHand(hand, returnedStack);
                        }

                        return ActionResult.SUCCESS;
                    } else {
                        return ActionResult.FAIL;
                    }
                }
            }
        }

        return ActionResult.PASS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof MixingBowlBlockEntity mixingBowlBlockEntity) {
                ItemScatterer.spawn(world, pos, mixingBowlBlockEntity.inventory);
            }

        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MixingBowlBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(2D / 16D, 0D, 2D / 16D, 1D - 2D / 16D, 0.5D, 1D - 2D / 16D);
    }
}
