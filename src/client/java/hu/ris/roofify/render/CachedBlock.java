package hu.ris.roofify.render;

import net.minecraft.util.math.BlockPos;

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
