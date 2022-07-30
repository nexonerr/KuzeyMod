package com.nexoner.kuzey.screen;

import com.nexoner.kuzey.KuzeyMod;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, KuzeyMod.MOD_ID);

    public static final RegistryObject<MenuType<KuzeyiumPurificationChamberMenu>> KUZEYIUM_PURIFICATION_CHAMBER_MENU =
            registerMenuType(KuzeyiumPurificationChamberMenu::new,"kuzeyium_purification_chamber_menu");

    public static final RegistryObject<MenuType<KuzeyiumWorkstationMenu>> KUZEYIUM_WORKSTATION_MENU =
            registerMenuType(KuzeyiumWorkstationMenu::new,"kuzeyium_workstation_menu");

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name){
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }


    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }

}