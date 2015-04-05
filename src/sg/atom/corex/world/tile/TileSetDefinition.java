package sg.atom.corex.world.tile;


/**
 * Stores a definition of a tileset. Mainly its name, the source image used and
 * the starting global id.
 *
 * @author miguel
 *
 */
public class TileSetDefinition {

    /**
     * The name of the tileset. Useless
     */
    private String name;
    /**
     * The source image of this tileset.
     */
    private String source = null;
    /**
     * Width of the tile.
     */
    public int tileWidth;
    /**
     * Height of the tile.
     */
    public int tileHeight;
    /**
     * Width of the source image.
     */
    public int sourceWidth;
    /**
     * Height of the source image.
     */
    public int sourceHeight;
    /**
     * The id where this tileset begins to number tiles.
     */
    private int gid;
    public int numOfTiles;
    public int numX;
    public int numY;

    /**
     * Constructor.
     *
     * @param name the *useless* name of the tileset.
     * @param firstGid the id where this tileset begins to number tiles.
     */
    public TileSetDefinition(final String name, final int firstGid) {
        this.name = name;
        this.gid = firstGid;
    }

    /**
     * Returns the id where this tileset begins to number tiles.
     *
     * @return the id where this tileset begins to number tiles
     */
    public int getFirstGid() {
        return gid;
    }

    /**
     * Set the filename of the source image of the tileset.
     *
     * @param attributeValue the filename
     */
    public void setSource(final String attributeValue) {
        this.source = attributeValue;
    }

    /**
     * Returns the filename of the source image of the tileset.
     *
     * @return the filename of the source image of the tileset.
     */
    public String getSource() {
        return source;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof TileSetDefinition) {

            final TileSetDefinition set = (TileSetDefinition) object;
            return set.name.equals(name) && set.source.equals(source)
                    && (set.gid == gid);
        } else {
            return false;
        }

    }

    @Override
    public int hashCode() {
        int hash = 1;
        if (name != null) {
            hash = hash * name.hashCode();
        }
        if (source != null) {
            hash = hash * source.hashCode();
        }
        hash = hash + gid;
        return hash * super.hashCode();
    }
}
