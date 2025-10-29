package hu.ris.roofify.configuration;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.esotericsoftware.yamlbeans.YamlConfig;
import com.esotericsoftware.yamlbeans.YamlConfig.WriteClassName;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

import hu.ris.roofify.render.RenderMode;
import hu.ris.roofify.render.modes.RenderModeType;

public class RoofifyConfig {

    public boolean enabled = true;
    public boolean include_roof_level = false;
    public RenderModeType render_mode = RenderModeType.CHECKMARK;

    public int render_interval = 20;
    public int render_distance_horizontal = 8;
    public int render_distance_vertical = 4;

    protected RoofifyConfig() {
    }

    public RenderMode getRenderMode() {
        return RenderMode.getByType(this.render_mode);
    }

    public boolean toggleRoofLevel() {
        this.include_roof_level = !this.include_roof_level;
        this.save();
        return this.include_roof_level;
    }

    public void setRenderMode(RenderMode renderMode) {
        this.render_mode = renderMode.getType();
        this.save();
    }

    public RenderMode cycleRenderMode() {
        List<RenderMode> renderModes = RenderMode.getRenderModes();
        RenderMode[] renderModeArray = renderModes.toArray(new RenderMode[0]);

        RenderMode currentRenderMode = this.getRenderMode();
        int currentIndex = -1;
        for (int i = 0; i < renderModeArray.length; i++) {
            if (renderModeArray[i].getType().equals(currentRenderMode.getType())) {
                currentIndex = i;
                break;
            }
        }

        int nextIndex = (currentIndex + 1) % renderModeArray.length;
        RenderMode nextRenderMode = renderModeArray[nextIndex];
        this.setRenderMode(nextRenderMode);

        return this.getRenderMode();
    }

    public boolean toggleEnabled() {

        this.enabled = !this.enabled;
        this.save();

        return this.enabled;
    }

    public int setHorizontalRadius(int horizontalRadius) {
        this.render_distance_horizontal = horizontalRadius;
        this.save();
        return this.render_distance_horizontal;
    }

    public int setVerticalRadius(int verticalRadius) {
        this.render_distance_vertical = verticalRadius;
        this.save();
        return this.render_distance_vertical;
    }

    public RoofifyConfig load() {

        if (!RoofifyConfigLoader.FILE.exists()) {
            RoofifyConfig config = new RoofifyConfig();
            config.save();
            return config;
        }
        try (FileReader reader = new FileReader(RoofifyConfigLoader.FILE)) {
            YamlConfig yamlConfig = new YamlConfig();

            YamlReader yamlReader = new YamlReader(reader, yamlConfig);
            RoofifyConfig config = yamlReader.read(RoofifyConfig.class);
            yamlReader.close();

            return config;
        } catch (IOException e) {
            return new RoofifyConfig();
        }
    }

    public void save() {

        try (FileWriter writer = new FileWriter(RoofifyConfigLoader.FILE)) {
            YamlConfig yamlConfig = new YamlConfig();
            yamlConfig.writeConfig.setVersion(null);
            yamlConfig.writeConfig.setExplicitFirstDocument(false);
            yamlConfig.writeConfig.setWriteRootTags(false);
            yamlConfig.writeConfig.setWriteDefaultValues(true);
            yamlConfig.writeConfig.setWriteClassname(WriteClassName.NEVER);
            yamlConfig.writeConfig.setKeepBeanPropertyOrder(true);

            YamlWriter yamlWriter = new YamlWriter(writer, yamlConfig);
            yamlWriter.write(this);
            yamlWriter.close();

        } catch (IOException e) {
            throw new RuntimeException("Could not save Roofify's configuration file: ", e);
        }
    }

}
