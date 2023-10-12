package net.vaex.aquilarpg.overlay;


import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;

public class Overlays {
       public static ManaOverlay mana = new ManaOverlay();
       public static HealthOverlay health = new HealthOverlay();
       public static MainHUDDeco hud = new MainHUDDeco();
       public static MainHUDClockWidget clockWidget = new MainHUDClockWidget();
       public static MainHUDProjectileWidget projectileWidget = new MainHUDProjectileWidget();
       public static MainHUDCompassWidget compassWidget = new MainHUDCompassWidget();
       public static DurabilityInformation durability = new DurabilityInformation();


        public static void registerOverlays() {
            OverlayRegistry.registerOverlayBelow(ForgeIngameGui.PLAYER_HEALTH_ELEMENT, "mana",Overlays.mana);
            OverlayRegistry.registerOverlayBelow(ForgeIngameGui.PLAYER_HEALTH_ELEMENT, "health",Overlays.health);
            OverlayRegistry.registerOverlayBelow(ForgeIngameGui.PLAYER_HEALTH_ELEMENT, "hud",Overlays.hud);
            OverlayRegistry.registerOverlayAbove(ForgeIngameGui.PLAYER_HEALTH_ELEMENT, "clockWidget",Overlays.clockWidget);
            OverlayRegistry.registerOverlayAbove(ForgeIngameGui.PLAYER_HEALTH_ELEMENT, "projectileWidget",Overlays.projectileWidget);
            OverlayRegistry.registerOverlayAbove(ForgeIngameGui.PLAYER_HEALTH_ELEMENT, "compassWidget",Overlays.compassWidget);
            OverlayRegistry.registerOverlayAbove(ForgeIngameGui.PLAYER_HEALTH_ELEMENT, "durability",Overlays.durability);
        }
}
