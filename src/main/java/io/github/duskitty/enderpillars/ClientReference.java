package io.github.duskitty.enderpillars;

import io.github.duskitty.enderpillars.block.entity.renderer.EnderTeleporterEntityRenderer;
import io.github.duskitty.enderpillars.client.network.packet.TeleporterpacketS2C;
import io.github.duskitty.enderpillars.init.BlockInit;
import io.github.duskitty.enderpillars.init.EntityInit;
import io.github.duskitty.enderpillars.init.NetworkInit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.render.RenderLayer;

public class ClientReference implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.INSTANCE.register(EntityInit.ENDERTELEPORTER, EnderTeleporterEntityRenderer::new);

        ClientSidePacketRegistry.INSTANCE.register(NetworkInit.TELEPORTPACKET, new TeleporterpacketS2C());
    }
}
