package com.melonstudios.createlegacy.block;

import com.melonstudios.createlegacy.block.stone.BlockOrestone;
import com.melonstudios.createlegacy.item.ItemBlockVariants;
import com.melonstudios.createlegacy.item.ModItems;
import com.melonstudios.createlegacy.tileentity.TileEntityShaft;
import com.melonstudios.createlegacy.tileentity.render.TileEntityShaftRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public final class ModBlocks {
    public static final List<Block> BLOCKS = new ArrayList<>();

    public static final Block METAL = registerBlockWithItem(new BlockMetal(), true);

    public static final Block STONE = registerBlockWithItem(new BlockOrestone("orestone"), true);

    private static Block registerBlock(Block block) {
        BLOCKS.add(block);
        return block;
    }
    private static Block registerBlockWithItem(Block block, Item ib) {
        BLOCKS.add(block);
        ModItems.ITEMS.add(ib);
        return block;
    }
    private static Block registerBlockWithItem(Block block, boolean variants) {
        if (variants) return registerBlockWithItem(block, new ItemBlockVariants(block).setRegistryName(block.getRegistryName()));
        return registerBlockWithItem(block, new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }
    private static Block registerBlockWithItem(Block block) {
        return registerBlockWithItem(block, false);
    }

    public static void setTileEntities() {
        GameRegistry.registerTileEntity(TileEntityShaft.class, "create:shaft");
        TileEntityRendererDispatcher.instance.renderers.put(TileEntityShaft.class, new TileEntityShaftRenderer());
    }
}
