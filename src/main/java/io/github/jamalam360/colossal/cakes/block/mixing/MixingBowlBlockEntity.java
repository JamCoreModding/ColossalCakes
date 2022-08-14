package io.github.jamalam360.colossal.cakes.block.mixing;

import io.github.jamalam360.colossal.cakes.registry.ColossalCakesBlocks;
import io.github.jamalam360.colossal.cakes.util.SingleStackSimpleInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jamalam
 */
public class MixingBowlBlockEntity extends BlockEntity implements InventoryChangedListener {
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
}
