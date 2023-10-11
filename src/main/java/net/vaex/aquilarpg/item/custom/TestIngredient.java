package net.vaex.aquilarpg.item.custom;


import net.minecraft.world.item.Item;
import net.vaex.aquilarpg.item.RPGIngedientTiers;
import net.vaex.aquilarpg.util.RPGCreativeModeTab;

public class TestIngredient extends Item  {
    public float weight;
    public int ingredientLevel;
    public String hiddenBasic;
    public String hiddenNovice;
    public String hiddenExpert;
    public String hiddenMaster;
    public String discription;


    public TestIngredient(Properties pProperties, float weight, RPGIngedientTiers tiers) {
        super(pProperties.tab(RPGCreativeModeTab.RPG_MISC).food(RPGFoodItem.BASIC_INGREDIENTS));
        this.weight = weight;
        this.discription = tiers.getMaterialName();
        this.ingredientLevel=tiers.getLevel();
        this.hiddenBasic =tiers.getBasic();
        this.hiddenNovice =tiers.getNovice();
        this.hiddenExpert =tiers.getExpert();
        this.hiddenMaster =tiers.getMaster();

    }


}

