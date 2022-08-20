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

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;

/**
 * @author Jamalam
 */
public class CakeState extends PersistentState {
    private static CakeState INSTANCE;

    public static CakeState get(ServerWorld world) {
        if (INSTANCE == null) {
            INSTANCE = world.getPersistentStateManager().getOrCreate((nbt) -> {
                int length = nbt.getInt("CakeCount");
                Cake.CAKES.clear();

                for (int i = 0; i < length; i++) {
                    Cake.read(world, nbt.getCompound("Cake" + i));
                }

                return new CakeState();
            }, CakeState::new, "cake_state");
        }

        return INSTANCE;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        int i = 0;
        for (Cake cake : Cake.CAKES) {
            nbt.put("Cake" + i, cake.write());
            i++;
        }

        nbt.putInt("CakeCount", i);
        return nbt;
    }
}
