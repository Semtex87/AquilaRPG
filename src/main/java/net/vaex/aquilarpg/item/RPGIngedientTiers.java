package net.vaex.aquilarpg.item;

import net.vaex.aquilarpg.util.RPGIngredientInterface;

public class RPGIngedientTiers implements RPGIngredientInterface {
    public String tier;
    public int level;
    //SoundEvent = ;
    public static RPGIngedientTiers Tier1;
    public static RPGIngedientTiers Tier2;
    public static RPGIngedientTiers Tier3;
    public static RPGIngedientTiers Tier4;

    public String effectBasic;
    public String effectNovice;
    public String effectExpert;
    public String effectMaster;
    public RPGIngedientTiers(String tier, int level, String effectBasic, String effectNovice, String effectExpert, String effectMaster) {
        this.tier = tier;
        this.level = level;
        this.effectBasic = effectBasic;
        this.effectNovice = effectNovice;
        this.effectExpert = effectExpert;
        this.effectMaster = effectMaster;

    }
    static {
        Tier1 = new RPGIngedientTiers("basic ingredient",1,"???","???","???","???");
        Tier2 = new RPGIngedientTiers("novice ingredient",2,"???","???","???","???");
        Tier3 = new RPGIngedientTiers("expert ingredient",3,"???","???","???","???");
        Tier4 = new RPGIngedientTiers("master ingredient",4,"???","???","???","???");
    }
    public String getMaterialName() {
        return this.tier;
    }
    public String getBasic() {
        return this.effectBasic;
    }
    public String getNovice() {
        return this.effectNovice;
    }
    public String getExpert() {
        return this.effectExpert;
    }
    public String getMaster() {
        return this.effectMaster;
    }
    public int getLevel() {
        return this.level;
    }

    //SoundEvent getSound();
}


