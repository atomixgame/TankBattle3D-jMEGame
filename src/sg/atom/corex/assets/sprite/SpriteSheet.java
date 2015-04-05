package sg.atom.corex.assets.sprite;

import com.jme3.texture.Texture;
import java.util.HashMap;

/**
 * Represent a Spritesheet.
 * @author cuong.nguyen
 */
public class SpriteSheet {
    String imagePath;
    String sourcePath;
    String simpleName;
    HashMap<String,SpriteTextureInfo> spriteMap = new HashMap<String, SpriteTextureInfo>();
    private Texture texture;
    
    public void addSprite(SpriteTextureInfo sprite){
        spriteMap.put(sprite.name, sprite);
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
    
    public HashMap<String, SpriteTextureInfo> getSpriteMap() {
        return spriteMap;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public SpriteTextureInfo getSprite(String spriteName) {
        return spriteMap.get(spriteName);
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }
    
    
}
