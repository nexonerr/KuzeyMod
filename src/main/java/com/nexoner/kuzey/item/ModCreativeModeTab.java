package com.nexoner.kuzey.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {

    public static final CreativeModeTab KUZEY_TAB = new CreativeModeTab("kuzeytab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.KUZEYIUM_INGOT.get());
        }
    };

}
