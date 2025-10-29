package hu.ris.roofify.render.modes;

import java.util.List;

import hu.ris.roofify.render.RenderMode;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class CountBedrocksMode extends RenderMode {

    public CountBedrocksMode() {
        super(RenderModeType.COUNT_BEDROCKS);
    }

    public float getTextScale() {
        return 1f / 32f;
    }

    public String getDisplayText(List<BlockState> blocksBelow) {

        int count = 0;

        for (BlockState blockState : blocksBelow) {
            if (blockState.getBlock().equals(Blocks.BEDROCK)) {
                count++;
            }
        }

        return count + "";
    }

}