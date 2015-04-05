package sg.atom.corex.ui.tonegod.layouts;

import sg.atom.corex.ui.tonegod.controls.ExElementBuilder;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector4f;
import java.util.HashMap;
import sg.atom.corex.assets.sprite.SpriteSheet;
import sg.atom.corex.ui.tonegod.controls.ExButtonAdapter;
import sg.atom.corex.ui.tonegod.layouts.QLayout.LayoutBuilder;
import tonegod.gui.core.Element;
import tonegod.gui.core.Screen;

/**
 * Advanced version of QLayout with new experiments.
 *
 * @author cuong.nguyen
 */
public class QuickLayouts extends QLayout {

    public HashMap<String, SpriteSheet> spriteSheets;

    public QuickLayouts(Screen screen) {
        super(screen);
        spriteSheets = new HashMap<String, SpriteSheet>();
    }

    public void addSpriteSheet(SpriteSheet sheet) {
        spriteSheets.put(sheet.getSimpleName(), sheet);
    }

    public void addSpriteSheet(String name, SpriteSheet sheet) {
        spriteSheets.put(name, sheet);
    }

    public ExButtonAdapter.Builder button(String id, String text) {
        ExButtonAdapter newButton = new ExButtonAdapter(screen, id);
        newButton.setText(text);
//            ids.put(id, image);
        ExButtonAdapter.Builder builder = new ExButtonAdapter.Builder(newButton, QuickLayouts.this);
        return builder;
    }

    public ExElementBuilder image(String id, String path) {
        Element newImage = new Element(screen, id, Vector2f.ZERO, Vector2f.ZERO, Vector4f.ZERO, path);
//            ids.put(id, image);
        ExElementBuilder builder = new ExElementBuilder(newImage, QuickLayouts.this);
        return builder;
    }

    public ExElementBuilder sprite(String id, String spriteSheetName, String spriteName) {
        Element newImage = new Element(screen, id, Vector2f.ZERO, Vector2f.ZERO, Vector4f.ZERO, null);
        sprite(newImage, spriteSheetName, spriteName);
//            ids.put(id, image);
        ExElementBuilder builder = new ExElementBuilder(newImage, QuickLayouts.this);
        return builder;
    }

    public void sprite(Element element, String spriteSheetName, String spriteName){
        SpriteSheet spriteSheet = spriteSheets.get(spriteSheetName);
        element.setTextureAtlasImage(spriteSheet.getTexture(), spriteSheet.getSprite(spriteName).toTonegodGUIFormat());
    }
    
    public GroupBuilder group(LayoutBuilder... elements) {
        Element newGroup = new Element(screen, "", Vector2f.ZERO, Vector2f.ZERO, Vector4f.ZERO, null);
        //FIXME: Make a real group attributes
        return new GroupBuilder(elements, newGroup);
    }

    public class GroupBuilder extends ListLayoutBuilder {

        private LayoutBuilder groupElementBuilder;

        public GroupBuilder(LayoutBuilder[] elements, Element element) {
            super(elements, QuickLayouts.this);
            this.groupElementBuilder = new LayoutBuilder(element, QuickLayouts.this);
            this.addTo(element);
        }

        public LayoutBuilder panel() {
            return groupElementBuilder;
        }

        @Override
        public void set() {
            this.groupElementBuilder.fill();
            this.groupElementBuilder.set();
            super.set();
        }
    }

    public HashMap<String, SpriteSheet> getSpriteSheets() {
        return spriteSheets;
    }
}
