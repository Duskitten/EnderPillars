package io.github.duskitty.enderpillars.client.gui.screen.ingame;

import io.github.duskitty.enderpillars.container.Warp;
import io.github.duskitty.enderpillars.init.NetworkInit;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.*;

import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.PacketByteBuf;

import javax.sound.midi.Soundbank;
import java.util.List;


@Environment(EnvType.CLIENT)
public class NewTeleportScreen extends Screen {
    private List playerwarps;
    private int OffsetNumber = 1;

    public NewTeleportScreen(List list) {
        super(new TranslatableText("sign.edit", new Object[0]));
        this.playerwarps = list;
    }

    protected void init() {
        int mainoffset = 6*OffsetNumber;
        this.buttons.clear();
        this.minecraft.keyboard.enableRepeatEvents(true);
        List<Warp> warps = this.playerwarps;
        int number = 0;
        for (Warp warp : warps){
            number++;
            int nummod = number-(mainoffset)+6;
            if(number > (mainoffset)-6 && number <= mainoffset){

                this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 2 -105 +(21*(nummod)), 200, 20,warp.getUniqueID(), (buttonWidget) -> {
                    PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                    System.out.println(warp.getUniqueID());
                    passedData.writeDouble(warp.getX());
                    passedData.writeDouble(warp.getY());
                    passedData.writeDouble(warp.getZ());
                    ClientSidePacketRegistry.INSTANCE.sendToServer(NetworkInit.TELEPORTPLAYERPACKET, passedData);
                    this.finishEditing();
                    MinecraftClient.getInstance().player.setPositionAndAngles(warp.getX(),warp.getY(),warp.getZ(),warp.getYaw(),warp.getPitch());
                    MinecraftClient.getInstance().player.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT,1,1);

            }));
            }
        }

        System.out.println(number);
        if (OffsetNumber != 1) {
            this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 2 + 60, 75, 20, "Prev", (buttonWidget) -> {
                OffsetNumber -= 1;
                init();
            }));
        }
        this.addButton(new ButtonWidget(this.width / 2 + 25, this.height / 2 + 60, 75, 20, "Next", (buttonWidget) -> {
            OffsetNumber += 1;
            init();
        }));
    }

    public void removed() {
        this.minecraft.keyboard.enableRepeatEvents(false);
    }

    public void tick() {

    }

    private void finishEditing() {
        this.minecraft.openScreen((Screen)null);
    }


    public void onClose() {
        this.finishEditing();
    }


    public void render(int mouseX, int mouseY, float delta) {
        DiffuseLighting.disableGuiDepthLighting();
        this.renderBackground();
        super.render(mouseX, mouseY, delta);
    }

    public boolean isPauseScreen() {
        return false;
    }

}
