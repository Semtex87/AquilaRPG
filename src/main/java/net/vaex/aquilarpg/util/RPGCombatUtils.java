package net.vaex.aquilarpg.util;


import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Random;

public class RPGCombatUtils {
    static ItemStack stack;


    public static void getCustomDropsOnHit(LivingEntity pTarget ) {
        if (pTarget instanceof Skeleton && new Random().nextInt(5) == 1){
            pTarget.spawnAtLocation(Items.BONE);
        }
        if (pTarget instanceof Zombie && new Random().nextInt(5) == 1){
            pTarget.spawnAtLocation(Items.ROTTEN_FLESH);

        }
    }

    public static void getGuillotineEffect(LivingEntity pTarget) {
        if (new Random().nextInt(10) == 1) {
            if (pTarget instanceof Skeleton){
                pTarget.spawnAtLocation(Items.SKELETON_SKULL);
            }
            else if (pTarget instanceof WitherSkeleton){
                pTarget.spawnAtLocation(Items.WITHER_SKELETON_SKULL);
            }
            else if (pTarget instanceof EnderDragon){
                pTarget.spawnAtLocation(Items.DRAGON_HEAD);
            }
            else if (pTarget instanceof Zombie){
                pTarget.spawnAtLocation(Items.ZOMBIE_HEAD);
            }
            else if (pTarget instanceof Creeper){
                pTarget.spawnAtLocation(Items.CREEPER_HEAD);
            }
            else if (pTarget instanceof ServerPlayer){
                pTarget.spawnAtLocation(Items.PLAYER_HEAD);
            }
        }

    }












    public static void register() { }
}