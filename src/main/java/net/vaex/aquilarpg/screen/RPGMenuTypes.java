package net.vaex.aquilarpg.screen;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vaex.aquilarpg.AquilaRPG;

public class RPGMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, AquilaRPG.MOD_ID);
    //public static final RegistryObject<MenuType<TinkerTableMenu>> TINKER_TABLE_MENU =
           // registerMenuType(TinkerTableMenu::new, "tinker_table_menu");
    public static final RegistryObject<MenuType<TinkerTableMenu>> TINKER_TABLE_MENU = registerMenuType((p1, p2, p3) -> new TinkerTableMenu(p1, p2), "arms_station_menu");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}