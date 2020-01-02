package com.duskitty.enderpillars.client.gui.screen.ingame;

import com.duskitty.enderpillars.container.Warp;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.*;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.TranslatableText;

import java.util.List;


@Environment(EnvType.CLIENT)
public class NewTeleportScreen extends Screen {
    private final List playerwarps;


    public NewTeleportScreen(List list) {
        super(new TranslatableText("sign.edit", new Object[0]));
        this.playerwarps = list;
    }

    protected void init() {
        this.minecraft.keyboard.enableRepeatEvents(true);
        List<Warp> warps = this.playerwarps;
        int number = 0;
        for (Warp warp : warps){
            number++;
            this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 2 - 25*number, 200, 20,warp.getName(), (buttonWidget) -> {
                this.finishEditing();
            }));
        }
        System.out.println(number);
        this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 120, 200, 20, I18n.translate("gui.done"), (buttonWidget) -> {
            this.finishEditing();
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
