package sg.atom.corex.config;

import java.io.Reader;
import java.io.Writer;
import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import static sg.atom.corex.config.QualitySetting.*;

/**
 * Common implementation for visual settings with detection.
 *
 * @author atomix
 */
public class VisualSettings {
    // Render

    public QualitySetting visualQuality = NORMAL;
    // Enviroments
    public QualitySetting enviromentQuality = NORMAL;
    // Effect
    public QualitySetting effectQuality = NORMAL;
    // Shader
    public String shaderVersion = "GL1.5";
    public QualitySetting shaderQuality = NORMAL;
    // Sound
    public float soundVolume = 1;
    public float musicVolume = 1;
    public boolean muted = false;
    public QualitySetting soundQuality = NORMAL;

    public VisualSettings() {
        this.soundVolume = 1;
        this.musicVolume = 1;
        this.muted = false;

    }

    public void isSupported(String shaderCapacity) {
    }
//
//    public AbstractConfiguration getConfiguration() throws ConfigurationException {
//        return new null;
//    }
}
