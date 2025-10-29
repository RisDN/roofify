package hu.ris.roofify.render.modes;

import java.util.List;

import hu.ris.roofify.render.RenderMode;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

/**
 * A render mode that counts the number of bedrock blocks directly below the
 * bedrock. When a non-bedrock block is encountered, the counting stops.
 */
public class CountBedrocksBreakMode extends RenderMode {

    public CountBedrocksBreakMode() {
        super(RenderModeType.COUNT_BEDROCKS_BREAK);
    }

    public float getTextScale() {
        return 1f / 32f;
    }

    public String getDisplayText(List<BlockState> blocksBelow) {

        int count = 0;

        for (BlockState blockState : blocksBelow) {
            if (blockState.getBlock().equals(Blocks.BEDROCK)) {
                count++;
            } else {
                break;
            }
        }

        return count + "";
    }

}