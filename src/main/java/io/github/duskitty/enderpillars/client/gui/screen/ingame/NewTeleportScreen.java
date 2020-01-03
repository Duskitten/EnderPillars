package io.github.duskitty.enderpillars.client.gui.screen.ingame;

import com.google.common.collect.Sets;
import io.github.duskitty.enderpillars.container.Warp;
import io.github.duskitty.enderpillars.init.NetworkInit;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.*;

import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.PacketByteBuf;

import javax.sound.midi.Soundbank;
import java.util.List;
import java.util.Set;


@Environment(EnvType.CLIENT)
public class NewTeleportScreen extends Screen {
    private List playerwarps;
    private int OffsetNumber = 0;

    public NewTeleportScreen(List list) {
        super(new TranslatableText("sign.edit", new Object[0]));
        this.playerwarps = list;
    }

    protected void init() {
        buttonsClear();
        this.minecraft.keyboard.enableRepeatEvents(true);
        List<Warp> warps = this.playerwarps;
        for (int i = 0; i < 6; i++){
            if(6 * OffsetNumber + i >= warps.size())
                break;
            Warp warp = warps.get(i+6 * OffsetNumber);
                this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 2 -105 +(21*(i)), 200, 20,warp.getUniqueID(), (buttonWidget) -> {
                    PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                    System.out.println(buttonWidget.getMessage());
                    passedData.writeString(warp.getUniqueID());
                    ClientSidePacketRegistry.INSTANCE.sendToServer(NetworkInit.TELEPORTPLAYERPACKET, passedData);
                    MinecraftClient.getInstance().player.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT,1,1);
                    this.finishEditing();

            }));
            }

        if (OffsetNumber != 0) {
            this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 2 + 60, 75, 20, "Prev", (buttonWidget) -> {
                OffsetNumber -= 1;
                init();
            }));
        }
        if(OffsetNumber != warps.size()/6) {
            this.addButton(new ButtonWidget(this.width / 2 + 25, this.height / 2 + 60, 75, 20, "Next", (buttonWidget) -> {
                OffsetNumber += 1;
                init();
            }));
        }
    }

    public void removed() {
        this.minecraft.keyboard.enableRepeatEvents(false);
    }
    public void buttonsClear() {
        Set<Element> set = Sets.newHashSet(this.buttons);
        this.children.removeIf(set::contains);
        this.buttons.clear();
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
