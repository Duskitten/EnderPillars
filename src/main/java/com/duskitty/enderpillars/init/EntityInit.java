package com.duskitty.enderpillars.init;

import com.duskitty.enderpillars.Reference;
import com.duskitty.enderpillars.block.entity.EnderTeleporterEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityInit {
    public static BlockEntityType<EnderTeleporterEntity> ENDERTELEPORTER;

    public static BlockEntityType registerBlockEntity(BlockEntityType<?> item, String name)
    {
        Registry.register(Registry.BLOCK_ENTITY, new Identifier(Reference.MODID, name), item);
        return item;
    }

    public static void Loader()
    {

        ENDERTELEPORTER = registerBlockEntity(BlockEntityType.Builder.create(EnderTeleporterEntity::new, BlockInit.ENDERTELEPORTER).build(null),"enderteleporterentity");
    }

}