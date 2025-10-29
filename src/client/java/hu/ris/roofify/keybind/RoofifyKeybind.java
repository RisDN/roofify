package hu.ris.roofify.keybind;

import org.lwjgl.glfw.GLFW;

import hu.ris.roofify.configuration.RoofifyConfigLoader;
import hu.ris.roofify.render.RenderMode;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;

public class RoofifyKeybind {

    private static KeyBinding toggleKey;
    private static KeyBinding renderModeKey;

    public static void init() {

        toggleKey = new KeyBinding("key.roofify.toggle_roofify", GLFW.GLFW_KEY_B, "category.roofify.keybindings");
        renderModeKey = new KeyBinding("key.roofify.cycle_render_mode", GLFW.GLFW_KEY_N, "category.roofify.keybindings");

        KeyBindingHelper.registerKeyBinding(toggleKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            handleToggleInputEvent();
            handleCycleRenderModeInputEvent();
        });

    }

    public static void handleToggleInputEvent() {
        while (toggleKey.wasPressed()) {
            handleToggleKey();
        }
    }

    private static void handleCycleRenderModeInputEvent() {
        while (renderModeKey.wasPressed()) {
            handleRenderModeKey();
        }
    }

    private static void handleRenderModeKey() {

        RenderMode newRenderMode = RoofifyConfigLoader.config.cycleRenderMode();

        String translationKey = "roofify.render_mode." + newRenderMode.getType().name().toLowerCase();
        Text changedMessage = Text.translatable("roofify.render_mode.switched", Text.translatable(translationKey));

        MinecraftClient.getInstance().inGameHud.setOverlayMessage(changedMessage, false);
    }

    private static void handleToggleKey() {

        boolean newState = RoofifyConfigLoader.config.toggleEnabled();

        String translationKey = newState ? "roofify.keybind.enabled" : "roofify.keybind.disabled";

        MinecraftClient.getInstance().inGameHud.setOverlayMessage(Text.translatable(translationKey), false);

    }

}
