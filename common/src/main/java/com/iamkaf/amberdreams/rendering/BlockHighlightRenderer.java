package com.iamkaf.amberdreams.rendering;

import com.iamkaf.amber.api.aabb.ShapeMerger;
import com.iamkaf.amberdreams.item.HammerItem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.util.*;

import static net.minecraft.client.renderer.RenderStateShard.*;

public class BlockHighlightRenderer {
    public static final RenderType LINES_NORMAL = RenderType.create("amberdreams_lines_normal",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.DEBUG_LINES,
            256,
            false,
            false,
            RenderType.CompositeState.builder()
                    .setShaderState(new ShaderStateShard(GameRenderer::getPositionColorShader))
                    .setLineState(new LineStateShard(OptionalDouble.empty()))
                    .setLayeringState(NO_LAYERING)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setWriteMaskState(COLOR_WRITE)
                    .setCullState(CULL)
                    .createCompositeState(false)
    );
    public static final RenderType LINES_TRANSPARENT = RenderType.create("amberdreams_lines_transparent",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.DEBUG_LINES,
            256,
            false,
            false,
            RenderType.CompositeState.builder()
                    .setShaderState(new ShaderStateShard(GameRenderer::getPositionColorShader))
                    .setLineState(new LineStateShard(OptionalDouble.empty()))
                    .setLayeringState(NO_LAYERING)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setWriteMaskState(COLOR_DEPTH_WRITE)
                    .setCullState(CULL)
                    .setDepthTestState(NO_DEPTH_TEST)
                    .createCompositeState(false)
    );

    public static boolean renderHammerHighlight(PoseStack poseStack) {
        Minecraft mc = Minecraft.getInstance();
        Level level = mc.level;
        Player player = mc.player;
        if (level == null || player == null) {
            return true;
        }

        ItemStack tool = player.getMainHandItem();
        if (tool.isEmpty() || !(tool.getItem() instanceof HammerItem)) {
            return true;
        }

        if (player.isShiftKeyDown()) {
            return true;
        }

        HitResult result = mc.hitResult;
        if (result == null || result.getType() != HitResult.Type.BLOCK) {
            return true;
        }

        BlockPos origin = BlockBreaker.getRayTracedBlockPosition(level, player);

        List<BlockPos> breakBlocks = BlockBreaker.getBreakBlocks(level, player, 1);
        if (breakBlocks.isEmpty()) {
            return true;
        }

        Camera renderInfo = mc.gameRenderer.getMainCamera();
        Vec3 projectedView = renderInfo.getPosition();
        assert poseStack != null;
        poseStack.pushPose();
        poseStack.translate(origin.getX() - projectedView.x,
                origin.getY() - projectedView.y,
                origin.getZ() - projectedView.z
        );
        Matrix4f matrix = poseStack.last().pose();

        // TODO: optimize this
        Collection<VoxelShape> shapes = new HashSet<>();
        for (AABB aabb : ShapeMerger.merge(breakBlocks.stream()
                .filter(blockPos -> tool.isCorrectToolForDrops(level.getBlockState(blockPos)))
                .toList(), origin)) {
            shapes.add(Shapes.create(aabb.inflate(0.005d)));
        }

        MultiBufferSource.BufferSource buffers = mc.renderBuffers().bufferSource();

        VertexConsumer vertexBuilder = buffers.getBuffer(LINES_NORMAL);

        orShapes(shapes).forAllEdges((x1, y1, z1, x2, y2, z2) -> {
            vertexBuilder.addVertex(matrix, (float) x1, (float) y1, (float) z1).setColor(1f, 1f, 1f, 1f);
            vertexBuilder.addVertex(matrix, (float) x2, (float) y2, (float) z2).setColor(1f, 1f, 1f, 1f);
        });
        buffers.endBatch(LINES_NORMAL);

        VertexConsumer vertexBuilder2 = buffers.getBuffer(LINES_TRANSPARENT);

        orShapes(shapes).forAllEdges((x1, y1, z1, x2, y2, z2) -> {
            float q = (float) (x2 - x1);
            float r = (float) (y2 - y1);
            float s = (float) (z2 - z1);
            float t = Mth.sqrt(q * q + r * r + s * s);
            q /= t;
            r /= t;
            s /= t;
            vertexBuilder2.addVertex(matrix, (float) x1, (float) y1, (float) z1)
                    .setColor(1f, 1f, 1f, 0.6f)
                    .setNormal(poseStack.last(), q, r, s);
            vertexBuilder2.addVertex(matrix, (float) x2, (float) y2, (float) z2)
                    .setColor(1f, 1f, 1f, 0.6f)
                    .setNormal(poseStack.last(), q, r, s);
        });
        buffers.endBatch(LINES_TRANSPARENT);


        poseStack.popPose();
        return false;
    }

    static VoxelShape orShapes(Collection<VoxelShape> shapes) {
        VoxelShape combinedShape = Shapes.empty();
        for (VoxelShape shape : shapes) {
            combinedShape = Shapes.joinUnoptimized(combinedShape, shape, BooleanOp.OR);
        }
        return combinedShape;
    }

    public static class BlockBreaker {

        protected static List<BlockPos> getBreakBlocks(Level level, Player player, int radius) {
            List<BlockPos> potentialBrokenBlocks = new ArrayList<>();

            BlockHitResult rayTraceResult = getRayTraceResult(level, player);

            if (rayTraceResult.getType() == HitResult.Type.BLOCK) {
                Direction direction = rayTraceResult.getDirection();
                for (int x = -radius; x <= radius; x++) {
                    for (int y = -radius; y <= radius; y++) {
                        switch (direction) {
                            case UP, DOWN ->
                                    potentialBrokenBlocks.add(new BlockPos(rayTraceResult.getBlockPos()
                                            .getX() + x,
                                            rayTraceResult.getBlockPos().getY(),
                                            rayTraceResult.getBlockPos().getZ() + y
                                    ));
                            case NORTH, SOUTH ->
                                    potentialBrokenBlocks.add(new BlockPos(rayTraceResult.getBlockPos()
                                            .getX() + x,
                                            rayTraceResult.getBlockPos().getY() + y,
                                            rayTraceResult.getBlockPos().getZ()
                                    ));
                            case EAST, WEST ->
                                    potentialBrokenBlocks.add(new BlockPos(rayTraceResult.getBlockPos()
                                            .getX(),
                                            rayTraceResult.getBlockPos().getY() + y,
                                            rayTraceResult.getBlockPos().getZ() + x
                                    ));
                        }
                    }
                }
            }

            return potentialBrokenBlocks;
        }

        public static @NotNull BlockHitResult getRayTraceResult(Level level, Player player) {
            Vec3 eyePosition = player.getEyePosition();
            Vec3 rotation = player.getViewVector(1);
            double reach = player.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE);
            Vec3 combined = eyePosition.add(rotation.x * reach, rotation.y * reach, rotation.z * reach);

            return level.clip(new ClipContext(eyePosition,
                    combined,
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                    player
            ));
        }

        public static @NotNull BlockPos getRayTracedBlockPosition(Level level, Player player) {
            var rayTraceResult = getRayTraceResult(level, player);

            Direction direction = rayTraceResult.getDirection();

            return switch (direction) {
                case DOWN, NORTH, WEST -> rayTraceResult.getBlockPos();
                case UP -> rayTraceResult.getBlockPos().below();
                case SOUTH -> rayTraceResult.getBlockPos().north();
                case EAST -> rayTraceResult.getBlockPos().west();
            };
        }
    }
}
