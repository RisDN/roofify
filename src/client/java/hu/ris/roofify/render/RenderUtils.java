package hu.ris.roofify.render;

import java.util.Optional;

import org.joml.Matrix4f;
import org.joml.Quaternionf;

import hu.ris.roofify.configuration.RoofifyConfigLoader;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class RenderUtils {

    public static void draw(MatrixStack matrices, TextRenderer textRenderer, VertexConsumerProvider.Immediate bufferSource, BlockPos positionToDraw, Quaternionf cameraRotation, String textToDraw, float textScale, int textColor, float textOffsetY) {
        matrices.push();

        matrices.translate(positionToDraw.getX() + 0.5, positionToDraw.getY() + textOffsetY, positionToDraw.getZ() + 0.5);

        matrices.multiply(cameraRotation);

        matrices.scale(textScale, -textScale, textScale);

        float textWidth = textRenderer.getWidth(textToDraw);
        Matrix4f positionMatrix = matrices.peek().getPositionMatrix();
        textRenderer.draw(textToDraw, -textWidth / 2.0f, -textRenderer.fontHeight / 2.0f, textColor, true, positionMatrix, bufferSource, TextRenderer.TextLayerType.NORMAL, 0, LightmapTextureManager.MAX_LIGHT_COORDINATE);

        matrices.pop();
    }

    public static float getTextOffsetY(Optional<Box> blockBoundingBoxDrawAt, float textWidthScaled, float textHeightScaled) {
        float textOffsetY = 0.1f; // config

        if (blockBoundingBoxDrawAt.isPresent()) {
            float textMaxLength = (float) Math.hypot(textWidthScaled, textHeightScaled);
            boolean textOverlapped = blockBoundingBoxDrawAt.get().intersects(0.5f - textMaxLength / 2f, 0, 0.5f - textMaxLength / 2f, 0.5f + textMaxLength / 2f, textHeightScaled, 0.5f + textMaxLength / 2f);
            if (textOverlapped) {
                textOffsetY += (float) blockBoundingBoxDrawAt.get().getLengthY();
            }
        }
        return textOffsetY;
    }

    public static boolean shouldRender(WorldRenderContext worldRenderContext) {

        if (!RoofifyConfigLoader.config.enabled) {
            return false;
        }

        MinecraftClient client = MinecraftClient.getInstance();

        if (client == null || client.player == null || client.world == null || client.textRenderer == null) {
            return false;
        }

        MatrixStack matrices = worldRenderContext.matrixStack();
        if (matrices == null) {
            return false;
        }

        World world = worldRenderContext.world();
        RegistryKey<World> worldKey = world.getRegistryKey();
        boolean isInNether = worldKey.equals(World.NETHER);

        if (!isInNether) {
            return false;
        }

        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        double yLevel = player.getY();

        if (yLevel < 125) {
            return false;
        }

        return true;
    }

}
