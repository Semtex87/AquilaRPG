package net.vaex.aquilarpg.effects;


import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.common.Mod;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.util.RPGCustomUtils;
import org.jetbrains.annotations.NotNull;


@Mod.EventBusSubscriber(modid = AquilaRPG.MOD_ID)
public class InfravisionEffect extends MobEffect {

    public InfravisionEffect() {
        super(MobEffectCategory.NEUTRAL, 3333333);
    }

    @Override
    public @NotNull String getDescriptionId() {
        return "Infravision";
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        RPGCustomUtils.setMobGlow(entity.level, entity.getX(), entity.getY(), entity.getZ(), entity);

    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }


}
