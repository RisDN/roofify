package hu.ris.roofify.modmenu.elements;

import hu.ris.roofify.configuration.RoofifyConfigLoader;
import hu.ris.roofify.modmenu.RoofifyModMenuImpl;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ToggleIncludeRoofLevelButton extends ButtonWidget {

    public ToggleIncludeRoofLevelButton(int x, int y) {
        super(x, y, RoofifyModMenuImpl.SIZE_WIDTH, RoofifyModMenuImpl.SIZE_HEIGHT, Text.empty(), new PressIncludeRoofLevel(() -> {
            RoofifyConfigLoader.config.toggleRoofLevel();
        }), ButtonWidget.DEFAULT_NARRATION_SUPPLIER);

        boolean isEnabled = RoofifyConfigLoader.config.include_roof_level;

        this.setMessage(Text.translatable("roofify.modmenu.include_roof_level", Text.translatable(isEnabled ? "options.on" : "options.off")));

        this.setTooltip(Tooltip.of(Text.translatable("roofify.modmenu.include_roof_level_tooltip")));
    }

    static class PressIncludeRoofLevel implements PressAction {

        private final Runnable onPress;

        public PressIncludeRoofLevel(Runnable onPress) {
            this.onPress = onPress;
        }

        @Override
        public void onPress(ButtonWidget button) {

            this.onPress.run();

            boolean isEnabled = RoofifyConfigLoader.config.include_roof_level;

            button.setMessage(Text.translatable("roofify.modmenu.include_roof_level", Text.translatable(isEnabled ? "options.on" : "options.off")));
        }

    }
}
