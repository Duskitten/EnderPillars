package io.github.duskitty.enderpillars.client.network.packet;

import io.github.duskitty.enderpillars.client.gui.screen.ingame.NewTeleportScreen;
import io.github.duskitty.enderpillars.container.Warp;
import io.github.duskitty.enderpillars.container.WarpStorage;
import net.fabricmc.fabric.api.network.PacketConsumer;
import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.TeleportCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.PacketByteBuf;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class TeleporterpacketC2S implements PacketConsumer {
    @Override
    public void accept(PacketContext ctx, PacketByteBuf buf) {
        String teleportto = buf.readString();
        ctx.getTaskQueue().execute(() -> this.process(teleportto, ctx.getPlayer()));
    }

    public void process(String warpID, PlayerEntity player) {
        Optional<Warp> warp = WarpStorage.fromPlayer((ServerPlayerEntity) player).getWarp(warpID);

        if(warp.isPresent()){
            Warp newwarp = warp.get();
            ((ServerPlayerEntity) player).teleport(newwarp.getX(),newwarp.getY(),newwarp.getZ(),true);
        };
    }

}