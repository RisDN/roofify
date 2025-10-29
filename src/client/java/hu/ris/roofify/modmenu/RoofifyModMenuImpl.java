package hu.ris.roofify.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import hu.ris.roofify.configuration.RoofifyConfigLoader;
import hu.ris.roofify.modmenu.elements.BackButton;
import hu.ris.roofify.modmenu.elements.CycleRenderModeButton;
import hu.ris.roofify.modmenu.elements.HorizontalRadiusSlider;
import hu.ris.roofify.modmenu.elements.ToggleIncludeRoofLevelButton;
import hu.ris.roofify.modmenu.elements.ToggleRenderButton;
import hu.ris.roofify.modmenu.elements.VerticalRadiusSlider;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

public class RoofifyModMenuImpl implements ModMenuApi {

    public static final int SIZE_WIDTH = 200;
    public static final int SIZE_HEIGHT = 20;
    private static final int SPACING_Y = 5;
    private static final int BACK_OFFSET_Y = 40;

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (Screen parent) -> new RoofifyConfigScreen(parent);
    }

    public static class RoofifyConfigScreen extends Screen {

        private final Screen previous;

        protected RoofifyConfigScreen(Screen parent) {
            super(Text.translatable("roofify.modmenu.title"));
            this.previous = parent;
        }

        public Screen getPrevious() {
            return this.previous;
        }

        @Override
        protected void init() {

            int x = (this.width / 2) - 100;
            int baseY = (this.height / 2) - 100;

            TextWidget titleWidget = new TextWidget(x, baseY, 200, 40, Text.translatable("roofify.modmenu.title"), this.textRenderer);

            this.addDrawableChild(titleWidget);

            int currentY = baseY + 40;
            ToggleRenderButton toggleRenderButton = new ToggleRenderButton(x, currentY);
            this.addDrawableChild(toggleRenderButton);

            currentY += SIZE_HEIGHT + SPACING_Y;

            ToggleIncludeRoofLevelButton toggleIncludeRoofLevelButton = new ToggleIncludeRoofLevelButton(x, currentY);
            this.addDrawableChild(toggleIncludeRoofLevelButton);

            currentY += SIZE_HEIGHT + SPACING_Y;

            CycleRenderModeButton cycleRenderModeButton = new CycleRenderModeButton(x, currentY);
            this.addDrawableChild(cycleRenderModeButton);

            int initialHorizontal = Math.max(4, Math.min(48, RoofifyConfigLoader.config.render_distance_horizontal));

            currentY += SIZE_HEIGHT + SPACING_Y;
            HorizontalRadiusSlider horizontalSlider = new HorizontalRadiusSlider(x, currentY, Text.translatable("roofify.modmenu.radius.horizontal", initialHorizontal), (initialHorizontal - 4) / 44.0);

            this.addDrawableChild(horizontalSlider);

            int initialVertical = Math.max(4, Math.min(48, RoofifyConfigLoader.config.render_distance_vertical));

            currentY += SIZE_HEIGHT + SPACING_Y;
            VerticalRadiusSlider verticalSlider = new VerticalRadiusSlider(x, currentY, Text.translatable("roofify.modmenu.radius.vertical", initialVertical), (initialVertical - 4) / 44.0);

            this.addDrawableChild(verticalSlider);

            BackButton backButton = new BackButton(x, currentY + SIZE_HEIGHT + BACK_OFFSET_Y, this);

            this.addDrawableChild(backButton);

            super.init();
        }

    }
}
