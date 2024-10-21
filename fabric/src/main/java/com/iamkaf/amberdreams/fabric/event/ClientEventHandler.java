package com.iamkaf.amberdreams.fabric.event;

import com.iamkaf.amberdreams.rendering.BlockHighlightRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

public class ClientEventHandler {

    public static void init() {
        WorldRenderEvents.BLOCK_OUTLINE.register((worldRenderContext, blockOutlineContext) -> BlockHighlightRenderer.renderHammerHighlight(
                worldRenderContext.matrixStack()));
    }
}
