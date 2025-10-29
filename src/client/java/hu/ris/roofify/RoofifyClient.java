package hu.ris.roofify;

import hu.ris.roofify.configuration.RoofifyConfigLoader;
import hu.ris.roofify.keybind.RoofifyKeybind;
import hu.ris.roofify.render.RenderMode;
import hu.ris.roofify.render.RoofifyRender;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

public class RoofifyClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        RoofifyConfigLoader.init();
        RoofifyKeybind.init();
        RenderMode.init();

        WorldRenderEvents.AFTER_ENTITIES.register(RoofifyRender::render);
    }

}
