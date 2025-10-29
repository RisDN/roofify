package hu.ris.roofify.modmenu.elements;

import com.terraformersmc.modmenu.gui.ModsScreen;

import hu.ris.roofify.modmenu.RoofifyModMenuImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class BackButton extends ButtonWidget {

    public BackButton(int x, int y, Screen parent) {
        super(x, y, RoofifyModMenuImpl.SIZE_WIDTH, RoofifyModMenuImpl.SIZE_HEIGHT, Text.translatable("gui.back"), new PressBack(() -> {
            MinecraftClient.getInstance().setScreen(new ModsScreen(parent));
        }), ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
        this.setTooltip(Tooltip.of(Text.translatable("roofify.modmenu.back_tooltip")));
    }

    static class PressBack implements PressAction {

        private final Runnable onPress;

        public PressBack(Runnable onPress) {
            this.onPress = onPress;
        }

        @Override
        public void onPress(ButtonWidget button) {
            this.onPress.run();
        }

    }
}