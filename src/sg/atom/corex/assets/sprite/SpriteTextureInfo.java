package sg.atom.corex.assets.sprite;

import jme3tools.optimize.TextureAtlas.TextureAtlasTile;

/**
 * SpriteTextureInfo represent a Sprite slot in SpriteSheet. Its concept is like
 * TextureAtlasTile, but used for loading TextureAtlasTile cell.
 *
 * @author cuong.nguyen
 */
public class SpriteTextureInfo{

    int x, y, width, height;
    String name;
    String path;
    String dir;

    @Override
    public String toString() {
        return "x=" +x+ "|y="+y+"|w="+width+"|h="+height;
    }
    
    public String toTonegodGUIFormat(){
        return "x=" +x+ "|y="+y+"|w="+width+"|h="+height;
    }
}
