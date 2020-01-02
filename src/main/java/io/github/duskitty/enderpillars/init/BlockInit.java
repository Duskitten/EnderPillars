package io.github.duskitty.enderpillars.init;

import io.github.duskitty.enderpillars.Reference;
import io.github.duskitty.enderpillars.block.EnderTeleporterBlock;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockInit {
    public static Block ENDERTELEPORTER;


    public static Block registerBlock(Block item, String name)
    {
        Registry.register(Registry.BLOCK, new Identifier(Reference.MODID, name), item);
        return item;
    }

    public static void Loader()
    {
        ENDERTELEPORTER = registerBlock(new EnderTeleporterBlock(), "enderpillar");
    }
}