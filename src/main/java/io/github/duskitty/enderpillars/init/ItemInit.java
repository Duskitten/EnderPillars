package io.github.duskitty.enderpillars.init;

import io.github.duskitty.enderpillars.Reference;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemInit {
    public static Item ENDERTELEPORTER;


    public static Item registerItem(Item item, String name)
    {
        Registry.register(Registry.ITEM, new Identifier(Reference.MODID, name), item);
        return item;
    }

    public static void Loader()
    {

        ENDERTELEPORTER = registerItem(new BlockItem(BlockInit.ENDERTELEPORTER, new Item.Settings().group(ItemGroup.DECORATIONS)),"enderpillar");
    }
}
