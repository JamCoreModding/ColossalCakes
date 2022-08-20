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
