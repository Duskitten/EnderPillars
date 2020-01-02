package io.github.duskitty.enderpillars;


import io.github.duskitty.enderpillars.client.network.packet.TeleporterpacketC2S;
import io.github.duskitty.enderpillars.client.network.packet.TeleporterpacketS2C;
import io.github.duskitty.enderpillars.init.BlockInit;
import io.github.duskitty.enderpillars.init.EntityInit;
import io.github.duskitty.enderpillars.init.ItemInit;
import io.github.duskitty.enderpillars.init.NetworkInit;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.client.MinecraftClient;


public class Reference implements ModInitializer{
	public static final String MODID = "enderpillars";

	@Override
	public void onInitialize() {
		BlockInit.Loader();
		EntityInit.Loader();
		ItemInit.Loader();
		ServerSidePacketRegistry.INSTANCE.register(NetworkInit.TELEPORTPLAYERPACKET, new TeleporterpacketC2S());
	}

}