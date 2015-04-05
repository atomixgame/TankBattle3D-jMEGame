package sg.atom.corex.assets.sprite;

import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Load .sprites XML file.
 * @author cuong.nguyen
 */
public class SpriteSheetLoader implements AssetLoader{

    public Object load(AssetInfo assetInfo) throws IOException {
        SpriteSheet result = null;
        try {
            InputStream is = assetInfo.openStream();
            
            SpritesHandlerImpl handler = new SpritesHandlerImpl();
            SpritesParser parser = new SpritesParser(handler, null);
            
            parser.parse(new InputSource(is));
            result = handler.getResult();
            String sourcePath = assetInfo.getKey().getName();
            result.setSourcePath(sourcePath);
            result.setSimpleName(sourcePath.substring(sourcePath.lastIndexOf("/")+1,sourcePath.lastIndexOf(".")));
            String imagePath = assetInfo.getKey().getFolder() + result.getImagePath();
            result.setTexture(assetInfo.getManager().loadTexture(imagePath));
        } catch (SAXException ex) {
            Logger.getLogger(SpriteSheetLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(SpriteSheetLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
}
