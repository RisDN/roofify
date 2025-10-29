package hu.ris.roofify.render;

import java.util.ArrayList;
import java.util.List;

import hu.ris.roofify.render.modes.CheckmarkMode;
import hu.ris.roofify.render.modes.CountBedrocksBreakMode;
import hu.ris.roofify.render.modes.CountBedrocksMode;
import hu.ris.roofify.render.modes.RenderModeType;
import net.minecraft.block.BlockState;

public abstract class RenderMode {

    private static final List<RenderMode> RENDER_TYPES = new ArrayList<>();

    private final RenderModeType type;

    public RenderMode(RenderModeType type) {
        this.type = type;

        RENDER_TYPES.add(this);
    }

    public RenderModeType getType() {
        return type;
    }

    public abstract float getTextScale();

    public abstract String getDisplayText(List<BlockState> blocksBelow);

    public static List<RenderMode> getRenderModes() {
        return RENDER_TYPES;
    }

    public static RenderMode getDefault() {
        return getByType(RenderModeType.CHECKMARK);
    }

    public static RenderMode getByType(RenderModeType type) {
        for (RenderMode renderMode : RENDER_TYPES) {
            if (renderMode.getType().equals(type)) {
                return renderMode;
            }
        }
        return RenderMode.getDefault();
    }

    public static void init() {
        new CheckmarkMode();
        new CountBedrocksMode();
        new CountBedrocksBreakMode();
    }
}
