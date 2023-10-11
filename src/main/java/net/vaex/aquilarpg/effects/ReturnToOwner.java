package net.vaex.aquilarpg.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReturnToOwner extends MobEffect{
    private Entity followingEntity;

    protected ReturnToOwner(MobEffectCategory pCategory, Color pColor) {
        super(pCategory,pColor.getRed());
    }


    public void applyEffectTick(AreaEffectCloud pLivingEntity, int pAmplifier) {
        Level level = Minecraft.getInstance().level;
        LivingEntity owner = pLivingEntity.getOwner();
        if (owner==null) return;
        setFire(level,pLivingEntity.getX(),pLivingEntity.getY(),pLivingEntity.getZ(),pLivingEntity);
        super.applyEffectTick(owner, pAmplifier);
    }

    public static void setFire(LevelAccessor world, double x, double y, double z, Entity entity) {
        final Vec3 _center = new Vec3(x, y, z);//INFRAVISION
        if (entity instanceof AreaEffectCloud cloud) {
            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).expandTowards(Vec3.fromRGB24(1+cloud.getDurationOnUse())), e -> true).stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
            for (Entity entityiterator : _entfound) {
                if (entityiterator.equals(cloud.getOwner())) {
                    if (entityiterator instanceof LivingEntity _entity) {
                        _entity.addEffect(new MobEffectInstance(MobEffects.GLOWING));
                    }
                }
                if (!(entityiterator == entity)) {
                    if (entityiterator instanceof LivingEntity _entity) {
                        if (!_entity.hasEffect(RPGEffectManager.RETURNOWNER.get())) {
                            _entity.setPos(cloud.getOwner().getX(), cloud.getOwner().getY(), cloud.getOwner().getZ());
                        }

                    }
                }

            }
        }
    }




    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
