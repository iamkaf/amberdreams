package com.iamkaf.amberdreams.neoforge.event;

import com.iamkaf.amberdreams.rendering.BlockHighlightRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderHighlightEvent;

@EventBusSubscriber(Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void renderBlockHighlights(RenderHighlightEvent.Block event) {
        event.setCanceled(!BlockHighlightRenderer.renderHammerHighlight(event.getPoseStack()));
    }
}
