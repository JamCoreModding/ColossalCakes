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

package io.github.jamalam360.colossal.cakes.util;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jamalam
 */
@SuppressWarnings({"UnusedReturnValue", "unused"})
public class SingleStackSimpleInventory implements Inventory, RecipeInputProvider {
    private final int size;
    private final DefaultedList<ItemStack> stacks;
    @Nullable
    private List<InventoryChangedListener> listeners;

    public SingleStackSimpleInventory(int i) {
        this.size = i;
        this.stacks = DefaultedList.ofSize(i, ItemStack.EMPTY);
    }

    public SingleStackSimpleInventory(ItemStack... itemStacks) {
        this.size = itemStacks.length;
        this.stacks = DefaultedList.copyOf(ItemStack.EMPTY, itemStacks);
    }

    public List<ItemStack> getStacks() {
        return this.stacks;
    }

    public void addListener(InventoryChangedListener listener) {
        if (this.listeners == null) {
            this.listeners = Lists.newArrayList();
        }

        this.listeners.add(listener);
    }

    public void removeListener(InventoryChangedListener listener) {
        if (this.listeners != null) {
            this.listeners.remove(listener);
        }
    }

    public ItemStack getStack(int slot) {
        return slot >= 0 && slot < this.stacks.size() ? this.stacks.get(slot) : ItemStack.EMPTY;
    }

    public List<ItemStack> clearToList() {
        List<ItemStack> list = this.stacks.stream().filter(stack -> !stack.isEmpty()).collect(Collectors.toList());
        this.clear();
        return list;
    }

    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = Inventories.splitStack(this.stacks, slot, amount);
        if (!itemStack.isEmpty()) {
            this.markDirty();
        }

        return itemStack;
    }

    public ItemStack removeItem(Item item, int count) {
        ItemStack itemStack = new ItemStack(item, 0);

        for (int i = this.size - 1; i >= 0; --i) {
            ItemStack itemStack2 = this.getStack(i);
            if (itemStack2.getItem().equals(item)) {
                int j = count - itemStack.getCount();
                ItemStack itemStack3 = itemStack2.split(j);
                itemStack.increment(itemStack3.getCount());
                if (itemStack.getCount() == count) {
                    break;
                }
            }
        }

        if (!itemStack.isEmpty()) {
            this.markDirty();
        }

        return itemStack;
    }

    public ItemStack addStack(ItemStack stack) {
        ItemStack itemStack = stack.copy();

        if (itemStack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.addToNewSlot(itemStack);
            return itemStack.isEmpty() ? ItemStack.EMPTY : itemStack;
        }
    }

    public boolean canInsert(ItemStack stack) {
        for (ItemStack itemStack : this.stacks) {
            if (itemStack.isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public ItemStack removeLastStack() {
        for (int i = this.size - 1; i >= 0; --i) {
            ItemStack stack = this.removeStack(i);

            if (!stack.isEmpty()) {
                return stack;
            }
        }

        return ItemStack.EMPTY;
    }

    public ItemStack removeStack(int slot) {
        ItemStack itemStack = this.stacks.get(slot);
        if (itemStack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            ItemStack copy = itemStack.copy();
            setStack(slot, ItemStack.EMPTY);
            return copy;
        }
    }

    public void setStack(int slot, ItemStack stack) {
        this.stacks.set(slot, stack);
        if (!stack.isEmpty() && stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }

        this.markDirty();
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        for (ItemStack itemStack : this.stacks) {
            if (!itemStack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public void markDirty() {
        if (this.listeners != null) {
            for (InventoryChangedListener inventoryChangedListener : this.listeners) {
                inventoryChangedListener.onInventoryChanged(this);
            }
        }
    }

    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    public void clear() {
        this.stacks.clear();
        this.markDirty();
    }

    public void provideRecipeInputs(RecipeMatcher finder) {
        for (ItemStack itemStack : this.stacks) {
            finder.addInput(itemStack);
        }
    }

    public String toString() {
        return this.stacks.stream().filter(stack -> !stack.isEmpty()).toList().toString();
    }

    private void addToNewSlot(ItemStack stack) {
        for (int i = 0; i < this.size; ++i) {
            ItemStack itemStack = this.getStack(i);
            if (itemStack.isEmpty()) {
                ItemStack set = stack.copy();
                set.setCount(1);
                this.setStack(i, set);
                stack.decrement(1);
                return;
            }
        }
    }

    public void fromNbtList(NbtList nbtList) {
        for (int i = 0; i < nbtList.size(); ++i) {
            ItemStack itemStack = ItemStack.fromNbt(nbtList.getCompound(i));
            if (!itemStack.isEmpty()) {
                this.addStack(itemStack);
            }
        }
    }

    public NbtList toNbtList() {
        NbtList nbtList = new NbtList();

        for (int i = 0; i < this.size(); ++i) {
            ItemStack itemStack = this.getStack(i);
            if (!itemStack.isEmpty()) {
                nbtList.add(itemStack.writeNbt(new NbtCompound()));
            }
        }

        return nbtList;
    }
}
