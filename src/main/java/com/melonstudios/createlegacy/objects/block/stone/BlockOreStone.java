package com.melonstudios.createlegacy.objects.block.stone;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public final class BlockOreStone extends AbstractBlockOreStone {
    public BlockOreStone(String registry) {
        super(registry);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "tile.create.stone_" + StoneType.fromID(stack.getMetadata()).getName();
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this, 1, 0));
        items.add(new ItemStack(this, 1, 1));
        items.add(new ItemStack(this, 1, 2));
        items.add(new ItemStack(this, 1, 3));
        items.add(new ItemStack(this, 1, 4));
        items.add(new ItemStack(this, 1, 5));
        items.add(new ItemStack(this, 1, 6));
    }
}
