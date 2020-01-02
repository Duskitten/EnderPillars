package io.github.duskitty.enderpillars.client.network.packet;

import io.github.duskitty.enderpillars.client.gui.screen.ingame.NewTeleportScreen;
import io.github.duskitty.enderpillars.container.WarpStorage;
import net.fabricmc.fabric.api.network.PacketConsumer;
import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.TeleportCommand;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.PacketByteBuf;

import java.util.UUID;
import java.util.stream.Stream;

public class TeleporterpacketC2S implements PacketConsumer {
    @Override
    public void accept(PacketContext ctx, PacketByteBuf buf) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        ctx.getTaskQueue().execute(() -> this.process(x,y,z, ctx.getPlayer()));
    }

    public void process(double x, double y, double z, PlayerEntity player) {
        player.teleport(x,y,z,true);
    }

}