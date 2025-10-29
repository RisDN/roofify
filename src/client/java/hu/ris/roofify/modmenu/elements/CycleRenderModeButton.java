package hu.ris.roofify.modmenu.elements;

import hu.ris.roofify.configuration.RoofifyConfigLoader;
import hu.ris.roofify.modmenu.RoofifyModMenuImpl;
import hu.ris.roofify.render.RenderMode;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CycleRenderModeButton extends ButtonWidget {

    public CycleRenderModeButton(int x, int y) {
        super(x, y, RoofifyModMenuImpl.SIZE_WIDTH, RoofifyModMenuImpl.SIZE_HEIGHT, Text.empty(), new PressToggleRender(() -> {
            RoofifyConfigLoader.config.cycleRenderMode();
        }), ButtonWidget.DEFAULT_NARRATION_SUPPLIER);

        RenderMode renderMode = RoofifyConfigLoader.config.getRenderMode();

        String translationKey = "roofify.render_mode." + renderMode.getType().name().toLowerCase();
        this.setMessage(Text.translatable("roofify.modmenu.cycle_render_mode", Text.translatable(translationKey)));
        this.setTooltip(Tooltip.of(Text.translatable("roofify.modmenu.cycle_render_mode_tooltip")));
    }

    static class PressToggleRender implements PressAction {

        private final Runnable onPress;

        public PressToggleRender(Runnable onPress) {
            this.onPress = onPress;
        }

        @Override
        public void onPress(ButtonWidget button) {

            this.onPress.run();

            RenderMode newRenderMode = RoofifyConfigLoader.config.cycleRenderMode();

            String translationKey = "roofify.render_mode." + newRenderMode.getType().name().toLowerCase();
            button.setMessage(Text.translatable("roofify.modmenu.cycle_render_mode", Text.translatable(translationKey)));
        }

    }
}