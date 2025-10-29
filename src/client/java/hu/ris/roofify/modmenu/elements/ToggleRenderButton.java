package hu.ris.roofify.modmenu.elements;

import hu.ris.roofify.configuration.RoofifyConfigLoader;
import hu.ris.roofify.modmenu.RoofifyModMenuImpl;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ToggleRenderButton extends ButtonWidget {

    public ToggleRenderButton(int x, int y) {
        super(x, y, RoofifyModMenuImpl.SIZE_WIDTH, RoofifyModMenuImpl.SIZE_HEIGHT, Text.empty(), new PressToggleRender(() -> {
            RoofifyConfigLoader.config.toggleEnabled();
        }), ButtonWidget.DEFAULT_NARRATION_SUPPLIER);

        boolean isEnabled = RoofifyConfigLoader.config.enabled;
        this.setMessage(Text.translatable("roofify.modmenu.toggle", Text.translatable(isEnabled ? "options.on" : "options.off")));
        this.setTooltip(Tooltip.of(Text.translatable("roofify.modmenu.toggle_tooltip")));
    }

    static class PressToggleRender implements PressAction {

        private final Runnable onPress;

        public PressToggleRender(Runnable onPress) {
            this.onPress = onPress;
        }

        @Override
        public void onPress(ButtonWidget button) {

            this.onPress.run();

            boolean isEnabled = RoofifyConfigLoader.config.enabled;

            button.setMessage(Text.translatable("roofify.modmenu.toggle", Text.translatable(isEnabled ? "options.on" : "options.off")));
        }

    }
}
