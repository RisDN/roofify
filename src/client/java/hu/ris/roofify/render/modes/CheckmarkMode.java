package hu.ris.roofify.render.modes;

import java.util.List;

import hu.ris.roofify.render.RenderMode;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class CheckmarkMode extends RenderMode {

    private final String bedrockDisplay = "§c✖";
    private final String nonBedrockDisplay = "§a✔";

    public CheckmarkMode() {
        super(RenderModeType.CHECKMARK);
    }

    public float getTextScale() {
        return 1f / 64f;
    }

    public String getDisplayText(List<BlockState> blocksBelow) {

        String text = "";

        for (BlockState blockState : blocksBelow) {
            if (blockState.getBlock().equals(Blocks.BEDROCK)) {
                text += bedrockDisplay;
            } else {
                text += nonBedrockDisplay;
            }
        }

        return text;
    }

}
