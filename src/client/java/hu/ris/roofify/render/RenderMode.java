package hu.ris.roofify.render;

import java.util.ArrayList;
import java.util.List;

import hu.ris.roofify.render.modes.CheckmarkMode;
import hu.ris.roofify.render.modes.CountBedrocksBreakMode;
import hu.ris.roofify.render.modes.CountBedrocksMode;
import hu.ris.roofify.render.modes.RenderModeType;
import net.minecraft.block.BlockState;

/**
 * Represents a rendering mode on how to display information below the bedrock
 * layer. If you want to create your own here are the steps to do so:
 * <ul>
 * 
 * <li>1. Create the mode in the {@link RenderModeType} enum.</li>
 * 
 * <li>2. Create a new class that extends this {@link RenderMode} class.</li>
 * 
 * <li>3. Implement the required methods: {@link #getDisplayText(List)} and
 * {@link #getTextScale()}.</li>
 * 
 * <li>4. In the {@link #init()} method, instantiate your new class so it gets
 * registered.</li>
 * 
 * </ul>
 */
public abstract class RenderMode {

    private static final List<RenderMode> RENDER_TYPES = new ArrayList<>();

    private final RenderModeType type;

    public RenderMode(RenderModeType type) {
        this.type = type;

        RENDER_TYPES.add(this);
    }

    /**
     * Returns the type of this render mode.
     * 
     * @return the render mode type
     */
    public RenderModeType getType() {
        return type;
    }

    /**
     * Returns the scale of the text to be rendered.
     * 
     * @return the text scale
     */
    public abstract float getTextScale();

    /**
     * Returns the display text based of the blocks below.
     * 
     * @param blocksBelow
     * @return the display text
     */
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
