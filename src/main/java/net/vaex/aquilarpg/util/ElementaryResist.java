package net.vaex.aquilarpg.util;



public enum ElementaryResist {
    AERO(ElementaryResist.Type.ARMOR, 0, "aero"),
    AQUA(ElementaryResist.Type.ARMOR, 1,  "aqua"),
    TERRA(ElementaryResist.Type.ARMOR, 2,  "terra"),
    PYRO(ElementaryResist.Type.ARMOR, 3, "pyro"),
    ;


    private final ElementaryResist.Type type;
    private final int index;
    private final String name;

    ElementaryResist(Type type, int i,String name) {
        this.type = type;
        this.index = i;
        this.name = name;
    }

    public ElementaryResist.Type getType() {
        return this.type;
    }

    public int getIndex() {
        return this.index;
    }

    public int getIndex(int pBaseIndex) {
        return pBaseIndex + this.index;
    }

    public String getName() {
        return this.name;
    }

    public static ElementaryResist byName(String pTargetName) {
        for(ElementaryResist elementaryResist : values()) {
            if (elementaryResist.getName().equals(pTargetName)) {
                return elementaryResist;
            }
        }

        throw new IllegalArgumentException("Invalid value '" + pTargetName + "'");
    }

    public static ElementaryResist byTypeAndIndex(ElementaryResist.Type pSlotType, int pSlotIndex) {
        for(ElementaryResist elementaryResist : values()) {
            if (elementaryResist.getType() == pSlotType && elementaryResist.getIndex() == pSlotIndex) {
                return elementaryResist;
            }
        }

        throw new IllegalArgumentException("Invalid slot '" + pSlotType + "': " + pSlotIndex);
    }
    public enum Type {
        HAND,
        ARMOR;
    }
}