package net.vaex.aquilarpg.capabilities.mana;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ManaProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<Mana> PLAYER_MANA = CapabilityManager.get(new CapabilityToken<Mana>() { });

    private Mana mana = null;
    private final LazyOptional<Mana> optional = LazyOptional.of(this::createIRPGMana);

    public ManaProvider() {
    }

    private Mana createIRPGMana() {
        if(this.mana == null) {
            this.mana = new Mana();
        }
        return this.mana;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_MANA) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createIRPGMana().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createIRPGMana().loadNBTData(nbt);
    }
}
