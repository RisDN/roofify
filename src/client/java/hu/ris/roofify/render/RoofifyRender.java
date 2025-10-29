package hu.ris.roofify.render;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.joml.Quaternionf;

import hu.ris.roofify.configuration.RoofifyConfigLoader;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

public class RoofifyRender {

    private static final int maxY = 127; // roof level
    private static final int maxMinY = 123; // the lowest level where bedrock can be

    private static final List<CachedBlock> blocksCached = new ArrayList<>();
    private static int frames = 0;

    public static int getMaxY() {
        return maxY;
    }

    public static int getMaxMinY() {
        return maxMinY;
    }

    public static void render(WorldRenderContext worldRenderContext) {

        if (!RenderUtils.shouldRender(worldRenderContext)) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();

        int renderInterval = RoofifyConfigLoader.config.render_interval;

        World world = client.world;
        Optional<Frustum> frustum = Optional.ofNullable(worldRenderContext.frustum());
        TextRenderer gameTextRenderer = client.textRenderer;
        PlayerEntity player = client.player;
        BlockPos playerPosition = player.getBlockPos();
        Camera camera = worldRenderContext.camera();
        Vec3d cameraPosition = camera.getPos();
        MatrixStack matrices = worldRenderContext.matrixStack();

        frames++;

        if (frames >= renderInterval) {
            getRenderTargets(world, player, frustum, gameTextRenderer, playerPosition, cameraPosition);
            frames = 0;
        }

        matrices.push();

        matrices.translate(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);

        VertexConsumerProvider.Immediate bufferSource = client.getBufferBuilders().getEntityVertexConsumers();
        Quaternionf cameraRotation = new Quaternionf(camera.getRotation());

        for (CachedBlock target : blocksCached) {
            RenderUtils.draw(matrices, gameTextRenderer, bufferSource, target.position, cameraRotation, target.text, target.textScale, target.textColor, target.textOffsetY);
        }

        matrices.pop();

    }

    private static void getRenderTargets(World world, PlayerEntity player, Optional<Frustum> frustum, TextRenderer textRenderer, BlockPos playerPosition, Vec3d cameraPosition) {
        blocksCached.clear();

        BlockPos.Mutable positionToRenderAt = new BlockPos.Mutable();
        int renderRangeHorizontal = RoofifyConfigLoader.config.render_distance_horizontal;
        int renderRangeVertical = RoofifyConfigLoader.config.render_distance_vertical;

        double maxSquaredDistance = renderRangeHorizontal * renderRangeHorizontal * 1.5;

        for (int dx = -renderRangeHorizontal; dx <= renderRangeHorizontal; dx++) {
            for (int dz = -renderRangeHorizontal; dz <= renderRangeHorizontal; dz++) {
                for (int dy = -renderRangeVertical; dy <= renderRangeVertical; dy++) {
                    positionToRenderAt.set(playerPosition.getX() + dx, playerPosition.getY() + dy, playerPosition.getZ() + dz);

                    if (positionToRenderAt.getSquaredDistance(playerPosition) > maxSquaredDistance) {
                        continue;
                    }

                    BlockState blockStateRenderAt = world.getBlockState(positionToRenderAt);

                    if (blockStateRenderAt.isAir()) {
                        continue;
                    }

                    Block blockRenderAtType = blockStateRenderAt.getBlock();
                    if (!blockRenderAtType.equals(Blocks.BEDROCK)) {
                        continue;
                    }

                    BlockPos blockPosRenderAt = positionToRenderAt.toImmutable();

                    if (blockPosRenderAt.getY() != maxY) {
                        continue;
                    }

                    List<BlockState> blocksBelow = new ArrayList<>();

                    int maxYlevel = RoofifyConfigLoader.config.include_roof_level ? maxY : maxY - 1;

                    for (int y = maxYlevel; y >= maxMinY; y--) {
                        BlockPos checkPos = new BlockPos(blockPosRenderAt.getX(), y, blockPosRenderAt.getZ());
                        BlockState checkBlockState = world.getBlockState(checkPos);
                        blocksBelow.add(checkBlockState);
                    }

                    RenderMode renderMode = RoofifyConfigLoader.config.getRenderMode();

                    String text = renderMode.getDisplayText(blocksBelow);

                    int textColor = 0xFF00FF00;

                    VoxelShape blockVisualShapeRenderAt = blockStateRenderAt.getOutlineShape(world, positionToRenderAt);
                    Optional<Box> blockBoundingBoxRenderAt;
                    if (blockVisualShapeRenderAt.isEmpty()) {
                        blockBoundingBoxRenderAt = Optional.empty();
                    } else {
                        blockBoundingBoxRenderAt = Optional.of(blockVisualShapeRenderAt.getBoundingBox());
                    }

                    String textToRender = text;

                    float textScale = renderMode.getTextScale();

                    float textWidthScaled = textRenderer.getWidth(textToRender) * textScale;
                    float textHeightScaled = textRenderer.fontHeight * textScale;
                    float textOffsetY = RenderUtils.getTextOffsetY(blockBoundingBoxRenderAt, textWidthScaled, textHeightScaled);

                    blocksCached.add(new CachedBlock(positionToRenderAt.toImmutable(), textToRender, textScale, textColor, textOffsetY));
                }
            }
        }
    }

}
