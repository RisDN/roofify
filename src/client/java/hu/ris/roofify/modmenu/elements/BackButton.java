package hu.ris.roofify.modmenu.elements;

import hu.ris.roofify.modmenu.RoofifyModMenuImpl;
import hu.ris.roofify.modmenu.RoofifyModMenuImpl.RoofifyConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class BackButton extends ButtonWidget {

    public BackButton(int x, int y, RoofifyConfigScreen parent) {
        super(x, y, RoofifyModMenuImpl.SIZE_WIDTH, RoofifyModMenuImpl.SIZE_HEIGHT, Text.translatable("gui.back"), new PressBack(() -> {
            MinecraftClient.getInstance().setScreen(parent.getPrevious());
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