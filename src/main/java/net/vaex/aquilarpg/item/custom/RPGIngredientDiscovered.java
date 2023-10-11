package net.vaex.aquilarpg.item.custom;


import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.item.RPGIngedientTiers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class RPGIngredientDiscovered extends TestIngredient {

    public String effectBasic;
    public String effectNovice;
    public String effectExpert;
    public String effectMaster;

    public RPGIngredientDiscovered(Properties pProperties, float weight, RPGIngedientTiers tiers, String pEffectBasic, String pEffectNovice, String pEffectExpert, String pEffectMaster) {
        super(pProperties, weight, tiers);
            this.effectBasic = pEffectBasic;
            this.effectNovice = pEffectNovice;
            this.effectExpert = pEffectExpert;
            this.effectMaster = pEffectMaster;
    }




    @Override
    public @NotNull ItemStack finishUsingItem(@Nonnull ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        super.finishUsingItem(pStack, pLevel, pEntityLiving);

        CheckEffects(pEntityLiving);
        if (ingredientLevel == 1) {
            if (!hiddenBasic.equals(effectBasic) && new Random().nextInt(2) == 1) {
                hiddenBasic = this.effectBasic;
                pEntityLiving.sendMessage(new TextComponent("Discovered " + effectBasic).withStyle(ChatFormatting.GREEN), pEntityLiving.getUUID());
            }
        }
        if (ingredientLevel == 2) {
            if (!hiddenBasic.equals(effectBasic) && new Random().nextInt(2) == 1) {
                hiddenBasic = this.effectBasic;
                pEntityLiving.sendMessage(new TextComponent("Discovered " + effectBasic).withStyle(ChatFormatting.GREEN), pEntityLiving.getUUID());
            }
            if (!hiddenNovice.equals(effectNovice) && new Random().nextInt(10) == 1) {
                hiddenNovice = this.effectNovice;
                pEntityLiving.sendMessage(new TextComponent("Discovered  " + effectNovice).withStyle(ChatFormatting.GREEN), pEntityLiving.getUUID());
            }
        }
        /*


        if (ingredientLevel == 3) {
            if (!unlock && new Random().nextInt(2) == 1) {
                unlock = true;
                pEntityLiving.sendMessage(new TextComponent("Discovered basic Effect " + effectBasic), pEntityLiving.getUUID());
            }
            if (!unlockNovice && new Random().nextInt(10) == 1) {
                unlockNovice = true;
                pEntityLiving.sendMessage(new TextComponent("Discovered Novice Effect " + effectNovice).withStyle(ChatFormatting.GREEN), pEntityLiving.getUUID());
            }
            if (!unlockExpert && new Random().nextInt(20) == 1) {
                unlockExpert = true;
                pEntityLiving.sendMessage(new TextComponent("Discovered Expert Effect " + effectExpert).withStyle(ChatFormatting.BLUE), pEntityLiving.getUUID());
            }
        }

        if (ingredientLevel == 4) {
            if (!unlock && new Random().nextInt(2) == 1) {
               unlock = true;
                pEntityLiving.sendMessage(new TextComponent("Discovered basic Effect" + effectBasic), pEntityLiving.getUUID());
            }
            if (!unlockNovice && new Random().nextInt(10) == 1) {
                unlockNovice = true;
                pEntityLiving.sendMessage(new TextComponent("Discovered Novice Effect" + effectNovice).withStyle(ChatFormatting.GREEN), pEntityLiving.getUUID());
            }
            if (!unlockExpert && new Random().nextInt(20) == 1) {
                unlockExpert = true;
                pEntityLiving.sendMessage(new TextComponent("Discovered Expert Effect " + effectExpert).withStyle(ChatFormatting.BLUE), pEntityLiving.getUUID());
            }
            if (!unlockMaster && new Random().nextInt(30) == 1) {
                unlockMaster = true;
                pEntityLiving.sendMessage(new TextComponent("Discovered Master Effect" + effectMaster).withStyle(ChatFormatting.YELLOW), pEntityLiving.getUUID());
            }
        }
*/
        pStack.shrink(1);
        return pStack;
    }




    private void CheckEffects(LivingEntity pEntityLiving){
        if(effectBasic.contains("blindness") || effectNovice.contains("blindness") ||effectExpert.contains("blindness") ||effectMaster.contains("blindness"))
            pEntityLiving.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,100,0,false,false,false));
        if(effectBasic.contains("restore_heal") || effectNovice.contains("restore_heal") ||effectExpert.contains("restore_heal") ||effectMaster.contains("restore_heal"))
            pEntityLiving.addEffect(new MobEffectInstance(MobEffects.REGENERATION,100,0));
        if(effectBasic.contains("poison") || effectNovice.contains("poison") ||effectExpert.contains("poison") ||effectMaster.contains("poison"))
            pEntityLiving.addEffect(new MobEffectInstance(MobEffects.POISON,100,0));
        if(effectBasic.contains("glow") || effectNovice.contains("glow") ||effectExpert.contains("glow") ||effectMaster.contains("glow"))
            pEntityLiving.addEffect(new MobEffectInstance(MobEffects.GLOWING,100,0));
        if(effectBasic.contains("infravision") || effectNovice.contains("infravision") ||effectExpert.contains("infravision") ||effectMaster.contains("infravision"))
            pEntityLiving.addEffect(new MobEffectInstance(RPGEffectManager.INFRAVISION.get(),100,0));
        if(effectBasic.contains("harm") || effectNovice.contains("harm") ||effectExpert.contains("harm") ||effectMaster.contains("harm"))
            pEntityLiving.addEffect(new MobEffectInstance(MobEffects.HARM, 10,0));
    }


    public int getUseDuration(ItemStack stack) {
        return 16;
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
    }
    public int getLevel() {
        return this.ingredientLevel;
    }


    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> pTooltip, TooltipFlag pFlag) {
        Item item = itemStack.getItem();
        if (ingredientLevel == 1) {
            if (!hiddenBasic.equals(effectBasic)) {
                pTooltip.add(new TextComponent("I: " + effectBasic).withStyle(ChatFormatting.WHITE));
            } else {
                pTooltip.add(new TextComponent("I: " + hiddenBasic).withStyle(ChatFormatting.WHITE));
            }
        }
        if (ingredientLevel == 2) {
            if (!hiddenBasic.equals(effectBasic)) {
                pTooltip.add(new TextComponent("I: " + effectBasic).withStyle(ChatFormatting.WHITE));
            } else {
                pTooltip.add(new TextComponent("I: " + hiddenBasic).withStyle(ChatFormatting.WHITE));
            }
            if (!hiddenNovice.equals(effectNovice)) {
                pTooltip.add(new TextComponent("II: " + effectNovice).withStyle(ChatFormatting.GREEN));
            } else {
                pTooltip.add(new TextComponent("II: " + hiddenNovice).withStyle(ChatFormatting.GREEN));
            }
        }/*
        if (ingredientLevel == 3) {
            if (unlock) {
                pTooltip.add(new TextComponent("I: " + effectBasic + " ").withStyle(ChatFormatting.WHITE));
            } else {
                pTooltip.add(new TextComponent("I: " + hiddenEffect).withStyle(ChatFormatting.WHITE));
            }
            if (unlockNovice) {
                pTooltip.add(new TextComponent("II: " + effectNovice + " ").withStyle(ChatFormatting.GREEN));
            } else {
                pTooltip.add(new TextComponent("II: " + hiddenEffect).withStyle(ChatFormatting.GREEN));
            }
            if (unlockExpert) {
                pTooltip.add(new TextComponent("II: " + effectExpert + " ").withStyle(ChatFormatting.BLUE));
            } else {
                pTooltip.add(new TextComponent("II: " + hiddenEffect).withStyle(ChatFormatting.BLUE));
            }

        }
        if (ingredientLevel == 4) {
            if (unlock) {
                pTooltip.add(new TextComponent("I: " + effectBasic + " ").withStyle(ChatFormatting.WHITE));
            } else {
                pTooltip.add(new TextComponent("I: " + hiddenEffect).withStyle(ChatFormatting.WHITE));
            }
            if (unlockNovice) {
                pTooltip.add(new TextComponent("II: " + effectNovice + " ").withStyle(ChatFormatting.GREEN));
            } else {
                pTooltip.add(new TextComponent("II: " + hiddenEffect).withStyle(ChatFormatting.GREEN));
            }
            if (unlockExpert) {
                pTooltip.add(new TextComponent("II: " + effectExpert + " ").withStyle(ChatFormatting.BLUE));
            } else {
                pTooltip.add(new TextComponent("II: " + hiddenEffect).withStyle(ChatFormatting.BLUE));
            }
            if (unlockMaster) {
                pTooltip.add(new TextComponent("IV: " + effectMaster + " ").withStyle(ChatFormatting.YELLOW));
            } else {
                pTooltip.add(new TextComponent("IV: " + hiddenEffect).withStyle(ChatFormatting.YELLOW));
            }

        }*/
        pTooltip.add(new TextComponent("Tier: " + discription).withStyle(ChatFormatting.BLUE));
        pTooltip.add(new TextComponent("Weight: " + weight).withStyle(ChatFormatting.BLUE));
        super.appendHoverText(itemStack, level, pTooltip, pFlag);
    }

}

