package io.github.duskitty.enderpillars.client.network.packet;

import io.github.duskitty.enderpillars.client.gui.screen.ingame.NewTeleportScreen;
import io.github.duskitty.enderpillars.container.WarpStorage;
import net.fabricmc.fabric.api.network.PacketConsumer;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.PacketByteBuf;

public class TeleporterpacketS2C implements PacketConsumer {
    @Override
    public void accept(PacketContext ctx, PacketByteBuf buf) {
        WarpStorage warp = new WarpStorage();
        warp.readFromBuf(buf);
        ctx.getTaskQueue().execute(() -> this.process(warp));
    }

    public void process(WarpStorage warp) {
        MinecraftClient.getInstance().openScreen(new NewTeleportScreen(warp.getWarps()));

    }

}