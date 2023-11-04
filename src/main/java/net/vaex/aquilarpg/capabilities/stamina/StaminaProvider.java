package net.vaex.aquilarpg.capabilities.stamina;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.vaex.aquilarpg.capabilities.mana.Mana;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StaminaProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<Stamina> PLAYER_STAMINA = CapabilityManager.get(new CapabilityToken<Stamina>() { });

    private Stamina stamina = null;
    private final LazyOptional<Stamina> optional = LazyOptional.of(this::createIRPGMana);

    public StaminaProvider() {
    }

    private Stamina createIRPGMana() {
        if(this.stamina == null) {
            this.stamina = new Stamina();
        }
        return this.stamina;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_STAMINA) {
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
