package hu.ris.roofify.render;

import net.minecraft.util.math.BlockPos;

/**
 * Represents a cached block with its position and associated text rendering
 * properties. Used when rendering text on the bedrocks, so we don't have to
 * recalculate everything every frame. See {@link RoofifyRender#frames}
 */
class CachedBlock {

    public final BlockPos position;
    public final String text;
    public final float textScale;
    public final int textColor;
    public final float textOffsetY;

    protected CachedBlock(BlockPos position, String text, float textScale, int textColor, float textYOffset) {
        this.position = position.toImmutable();
        this.text = text;
        this.textScale = textScale;
        this.textColor = textColor;
        this.textOffsetY = textYOffset;
    }

}
