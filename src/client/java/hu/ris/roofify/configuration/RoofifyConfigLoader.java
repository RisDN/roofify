package hu.ris.roofify.configuration;

import java.io.File;

import net.minecraft.client.MinecraftClient;

public class RoofifyConfigLoader {

    private static final String CONFIG_FILE_NAME = "roofify.yaml";

    public static File FILE;

    public static RoofifyConfig config;

    public static void init() {

        File configFolder = new File(MinecraftClient.getInstance().runDirectory, "config");

        if (!configFolder.exists()) {
            configFolder.mkdir();
        }

        File config = new File(configFolder, CONFIG_FILE_NAME);
        FILE = config;

        RoofifyConfig conf = new RoofifyConfig();

        conf.save();
        conf.load();

        RoofifyConfigLoader.config = conf;
    }

}
