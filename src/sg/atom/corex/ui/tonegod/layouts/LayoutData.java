package sg.atom.corex.ui.tonegod.layouts;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector4f;

/**
 *
 * @author cuong.nguyen
 */
public class LayoutData {
    public static int UNIT_PERCENT = 0;
    public static int UNIT_PIXEL = 1;
    //        public static int UNIT_DPI = 2;
    //        public static int UNIT_METTER = 3;
    //Runtime data
    Vector2f pos;
    Vector2f absPos;
    Vector2f size;
    Vector2f scale;
    //Prototyping data
    Vector4f boundary;
    Vector4f padding;
    Vector4f margin;
    Vector4f constraints;
    int[] constraintsRef;
    //Units
    int posUnit = UNIT_PIXEL;
    int sizeUnit = UNIT_PIXEL;
    int[] constraintUnit;
    int[] boundaryUnit;
    int[] paddingUnit;
    int[] marginUnit;
    boolean naturalContainment;
    boolean specificContainment;
    boolean smartActive = true;
    LayoutData linked;

    public LayoutData() {
        this.pos = new Vector2f();
        this.size = new Vector2f();
    }

    public LayoutData(LayoutData original) {
    }

    public Vector2f getPos() {
        return pos;
    }

    public Vector2f getSize() {
        return size;
    }

    public void setPos(Vector2f pos) {
        this.pos = pos;
    }

    public void setSize(Vector2f size) {
        this.size = size;
    }

    public String toString() {
        return pos.toString() + " " + size.toString();
    }
    
}
