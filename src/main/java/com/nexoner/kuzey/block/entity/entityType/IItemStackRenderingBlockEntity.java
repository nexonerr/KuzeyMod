package com.nexoner.kuzey.block.entity.entityType;

import net.minecraftforge.items.ItemStackHandler;

public interface IItemStackRenderingBlockEntity {
    void setHandler(ItemStackHandler handler);
    ItemStackHandler getItemStackHandler();
}
