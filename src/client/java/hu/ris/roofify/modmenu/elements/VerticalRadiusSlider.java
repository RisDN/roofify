package hu.ris.roofify.modmenu.elements;

import hu.ris.roofify.configuration.RoofifyConfigLoader;
import hu.ris.roofify.modmenu.RoofifyModMenuImpl;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class VerticalRadiusSlider extends SliderWidget {

    public VerticalRadiusSlider(int x, int y, Text text, double value) {
        super(x, y, RoofifyModMenuImpl.SIZE_WIDTH, RoofifyModMenuImpl.SIZE_HEIGHT, text, value);
        this.updateMessage();
        this.setTooltip(Tooltip.of(Text.translatable("roofify.modmenu.radius.vertical_tooltip")));
    }

    @Override
    protected void updateMessage() {
        int value = (int) Math.round(this.value * 44) + 4;
        this.setMessage(Text.translatable("roofify.modmenu.radius.vertical", value));
    }

    @Override
    protected void applyValue() {
        int value = (int) Math.round(this.value * 44) + 4;
        RoofifyConfigLoader.config.setVerticalRadius(value);
    }

}
