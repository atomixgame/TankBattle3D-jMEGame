package sg.atom.corex.world.tile;

import java.util.List;



/**
 * The class that stores the definition of a layer. A Layer consists mainly of:
 * <ul> <li>width and height <li>name <b>VERY IMPORTANT</b> <li>data </ul>
 *
 * @author miguel
 *
 */
public class LayerDefinition{

    /**
     * To which map this layer belong.
     */
    private MapStructure map = null;
    /**
     * Width of the layer that SHOULD be the same that the width of the map.
     */
    private int width;
    /**
     * Height of the layer that SHOULD be the same that the height of the map.
     */
    private int height;
    private String name;
    /**
     * The data encoded as int in a array of size width*height .
     */
    private int[] data = null;
    /**
     * The same data in a raw byte array, so we save reencoding it again for
     * serialization.
     */
    private byte[] raw;

    /**
     * Constructor.
     *
     * @param layerWidth the width of the layer.
     * @param layerHeight the height of the layer
     */
    public LayerDefinition(final int layerWidth, final int layerHeight) {
        raw = new byte[4 * layerWidth * layerHeight];
        width = layerWidth;
        height = layerHeight;
    }

    /**
     * Sets the map to which this layer belong to.
     *
     * @param map the map
     */
    public void setMap(final MapStructure map) {
        this.map = map;
    }

    /**
     * Builds the real data array based on the byte array. It is only needed for
     * objects, collision and protection, which is at most 40% of the layers.
     */
    public void build() {
        data = new int[height * width];
        int offset = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int tileId = 0;
                tileId |= (raw[0 + offset] & 0xFF);
                tileId |= (raw[1 + offset] & 0xFF) << 8;
                tileId |= (raw[2 + offset] & 0xFF) << 16;
                tileId |= (raw[3 + offset] & 0xFF) << 24;

                data[x + y * width] = tileId;
                offset += 4;
            }
        }
    }

    /**
     * @return the allocated raw array so it can be modified.
     */
    public byte[] exposeRaw() {
        return raw;
    }

    /**
     * @return the allocated data array of size width*height containing ints.
     */
    public int[] expose() {
        return data;
    }

    /**
     * Set a tile at the given x,y position.
     *
     * @param x the x position
     * @param y the y position
     * @param tileId the tile code to set ( Use 0 for none ).
     */
    public void set(final int x, final int y, final int tileId) {
        final int offset = 4 * (x + y * width);

        raw[0 + offset] = (byte) (tileId & 0xFF);
        raw[1 + offset] = (byte) ((tileId >>> 8) & 0xFF);
        raw[2 + offset] = (byte) ((tileId >>> 16) & 0xFF);
        raw[3 + offset] = (byte) ((tileId >>> 24) & 0xFF);

        data[y * width + x] = tileId;
    }

    /**
     * Returns the tile at the x,y position.
     *
     * @param x the x position
     * @param y the y position
     * @return the tile that exists at that position or 0 for none.
     */
    public int getTileAt(final int x, final int y) {
        return data[y * width + x];
    }

    /**
     * Returns the width of the layer.
     *
     * @return the layer's width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the layer.
     *
     * @return the layer's height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the name of the tileset a tile belongs to.
     *
     * @param value the tile id
     * @return the name of the tileset
     */
    public TileSetDefinition getTilesetFor(final int value) {
        if (value == 0) {
            return null;
        }

        final List<TileSetDefinition> tilesets = map.getTilesets();

        int pos = 0;
        for (pos = 0; pos < tilesets.size(); pos++) {
            if (value < tilesets.get(pos).getFirstGid()) {
                break;
            }
        }

        return tilesets.get(pos - 1);
    }

    /**
     * Sets the name of the layer.
     *
     * @param layerName the name of the layer
     */
    public void setName(final String layerName) {
        name = layerName;
    }

    /**
     * Returns the name of the layer.
     *
     * @return the layer's name
     */
    public String getName() {
        return name;
    }

}
