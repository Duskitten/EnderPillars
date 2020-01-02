package com.duskitty.enderpillars.block.entity;

import com.duskitty.enderpillars.init.EntityInit;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;


public class EnderTeleporterEntity extends BlockEntity {

    public PlayerEntity entity = null;

    public EnderTeleporterEntity() {
        super(EntityInit.ENDERTELEPORTER);
    }



}
