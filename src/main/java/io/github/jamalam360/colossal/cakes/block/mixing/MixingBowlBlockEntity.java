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

import io.github.jamalam360.colossal.cakes.registry.ColossalCakesBlocks;
import io.github.jamalam360.colossal.cakes.util.SingleStackSimpleInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Predicate;

/**
 * @author Jamalam
 */
public class MixingBowlBlockEntity extends BlockEntity implements InventoryChangedListener, Inventory {
    public final SingleStackSimpleInventory inventory = new SingleStackSimpleInventory(10);

    public MixingBowlBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ColossalCakesBlocks.MIXING_BOWL_BLOCK_ENTITY, blockPos, blockState);
    }

    @Override
    public void onInventoryChanged(Inventory sender) {
        if (!this.world.isClient) {
            this.world.getPlayers().forEach(player -> ((ServerPlayerEntity) player).networkHandler.sendPacket(this.toUpdatePacket()));
            ((ServerWorld) this.world).getChunkManager().markForUpdate(this.pos);
        }

        this.markDirty();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        inventory.fromNbtList(nbt.getList("inventory", 10));
        super.readNbt(nbt);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.put("Inventory", inventory.toNbtList());
        super.writeNbt(nbt);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.of(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbt = super.toInitialChunkDataNbt();
        this.writeNbt(nbt);
        this.markDirty();
        return nbt;
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return this.inventory.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.getStack(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return this.inventory.removeStack(slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return this.inventory.removeStack(slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.inventory.setStack(slot, stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    @Override
    public int getMaxCountPerStack() {
        return this.inventory.getMaxCountPerStack();
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        return this.inventory.isValid(slot, stack);
    }

    @Override
    public int count(Item item) {
        return this.inventory.count(item);
    }

    @Override
    public boolean containsAny(Set<Item> items) {
        return this.inventory.containsAny(items);
    }

    @Override
    public boolean anyMatch(Predicate<ItemStack> predicate) {
        return this.inventory.anyMatch(predicate);
    }
}
