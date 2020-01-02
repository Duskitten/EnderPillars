package io.github.duskitty.enderpillars.block.entity;

import io.github.duskitty.enderpillars.init.EntityInit;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;


public class EnderTeleporterEntity extends BlockEntity {

    public PlayerEntity entity = null;

    public EnderTeleporterEntity() {
        super(EntityInit.ENDERTELEPORTER);
    }



}
