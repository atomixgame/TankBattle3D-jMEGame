package sg.atom.corex.world.tiled;

import com.google.common.io.Files;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.scene.BatchNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import com.jme3.util.BufferUtils;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import sg.atom.corex.world.tile.LayerDefinition;
import sg.atom.corex.world.tile.MapStructure;
import sg.atom.corex.world.tile.TileSetDefinition;

/**
 *
 * @author cuong.nguyen
 */
public class TiledMap3D extends Node {

    private final Logger logger = Logger.getLogger(TiledMap3D.class);
    protected ArrayList<Texture> textures;
    protected MapStructure map;
    protected Node mapNode;
    private AssetManager assetManager;

    public TiledMap3D(AssetManager assetManager, MapStructure map) {
        this.assetManager = assetManager;
        this.map = map;
        //We should use a Pool?
        textures = new ArrayList<Texture>(10);
    }

    public void loadTextures(String mapDir) {
        //Load the textures first
        List<TileSetDefinition> tilesets = map.getTilesets();

        //assetManager.registerLocator(mapDir, FileLocator.class);
        for (TileSetDefinition set : tilesets) {

//            logger.info("TILESET firstGID: '%d' name: '%s' %d %d %d\n",set.getFirstGid(), set.getSource(), set.numX, set.numY, set.numOfTiles);
            String source = set.getSource();
            //source = source.substring(source.lastIndexOf("..") + 1);
//            source = FileNameUtils.normalize(mapDir + "/" + source);
            source = Files.simplifyPath(mapDir + "/" + source);
            source = source.replace("\\", "/");

            Texture loadedTexture = assetManager.loadTexture(source);
            //loadTexture.setWrap(Texture.WrapMode.Repeat);
            textures.add(loadedTexture);
            logger.info("Try to load texture: " + source);

        }
    }

//    public void texturesDebug() {
//
//        Box b = new Box(1, 1, 1);
//
//        for (Texture tex : textures) {
//            Geometry geom = new Geometry("Box", b);
//            Material mat = getTileMat(tex);
//            geom.setMaterial(mat);
//
//            geom.setLocalTranslation(textures.indexOf(tex) * 3, 0, 0);
//            rootNode.attachChild(geom);
//        }
//    }
    public Node createMap() {
        if (isBatched()) {
            mapNode = new BatchNode("Batch");
        } else {
            mapNode = new Node("MapNode");
        }
        //Load the layers
        List<LayerDefinition> layers = map.getLayers();

        for (final LayerDefinition layer : layers) {
            //logger.info("LAYER name: %s\n", layer.getName());
            final int w = layer.getWidth();
            final int h = layer.getHeight();

            if (!isIgnored(layer.getName())) {

                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        //FIXME: Tile is indicate by an Integer index!
                        final int gid = layer.getTileAt(x, y);

                        if (gid != 0) {
                            TileSetDefinition set = findTileSet(gid);

                            if (set != null) {

                                Vector3f pos = transformToPosition(x, y, layer);
                                Vector4f uv = transformToUV(set, gid);
                                Material mat = transformToMaterial(set);
                                Spatial tileGeo = transformToSpatial(mat, pos, uv);

//                                logger.info("Add a tile at" + x + " " + y);
                                mapNode.attachChild(tileGeo);
                            } else {
                            }
                        }
                    }
                }
                // FIXME: Other option beside of Batch!
                // Batch it
            }
//            mapNode.setQueueBucket(RenderQueue.Bucket.Transparent);
            if (isBatched()) {
                ((BatchNode)mapNode).batch();
            }
        }

        return mapNode;
    }

    protected boolean isBatched() {
        return true;
    }

    protected List<String> getIgnoredLayers() {
        return null;
    }

    protected boolean isIgnored(String layerName) {
//        for (String ignoredLayerName : getIgnoredLayers()) {
//            if (ignoredLayerName.equalsIgnoreCase(layerName)) {
//                return true;
//            }
//        }
//        return false;
        return false;
    }

    protected boolean isIgnored(LayerDefinition layer) {
        return isIgnored(layer.getName());
    }

//    public DefaultMap2D convertToDefaultGridMap() {
//        return null;
//    }
    // Transform : index -> TileSetDefinition
    public TileSetDefinition findTileSet(int index) {

        final List<TileSetDefinition> tilesets = map.getTilesets();
        for (final TileSetDefinition set : tilesets) {

            int first = set.getFirstGid();

            if (index >= first && index <= first + set.numOfTiles) {
                return set;
            }
        }
        return null;
    }
//
//    public Mesh createTiledMesh() {
//        return null;
//    }
//
//    public Node createTiledNode() {
//        return null;
//    }

    // TRANSFORM ---------------------------------------------------------------
    public Vector3f transformToPosition(int x, int y, LayerDefinition layer) {
        List<LayerDefinition> layers = map.getLayers();
        int orderOfLayer = layers.lastIndexOf(layer);
        return new Vector3f(x, y, 0.01f * orderOfLayer);
    }
    // Mapping: TileSetDefinition + Position -> Spatial

    public Vector4f transformToUV(TileSetDefinition set, int gid) {
        int lgid = (gid - set.getFirstGid());
        int cx = lgid % set.numX;
        int cy = lgid / set.numX;

        float u = 1f / set.numX * cx;
        float v = 1 - 1f / set.numY * (cy + 1);
        float u1 = 1f / set.numX * (cx + 1);
        float v1 = 1 - 1f / set.numY * cy;

        return new Vector4f(u, v, u1, v1);
    }

    public Material transformToMaterial(TileSetDefinition set) {
        List<TileSetDefinition> tilesets = map.getTilesets();

        Material mat;
        int tileSetIndex = tilesets.indexOf(set);
        Texture tileTex = textures.get(tileSetIndex);
        mat = getTileMat(tileTex);
        return mat;
    }

    public Spatial transformToSpatial(Material mat, Vector3f pos, Vector4f uv) {

        Quad quad = new Quad(1, 1);

        float u = uv.x;
        float v = uv.y;
        float u1 = uv.z;
        float v1 = uv.w;

        //System.out.println(lgid + " Num:" + set.numX + " " + set.numY);
        //System.out.println(" cx:" + cx + " cy:" + cy + " u: " + u + " v:" + v);
        // Set the UV
        FloatBuffer floatBuffer;
        boolean flipCoords = false;
        if (flipCoords) {
            floatBuffer = BufferUtils.createFloatBuffer(u, v1, u1, v1, u1, v, u, v);
        } else {
            floatBuffer = BufferUtils.createFloatBuffer(u, v, u1, v, u1, v1, u, v1);

        }
        
        quad.setBuffer(VertexBuffer.Type.TexCoord, 2, floatBuffer);

        Geometry tileGeo = new Geometry("Tile" + pos.y + " " + pos.x, quad);
        //tileGeo.setQueueBucket(RenderQueue.Bucket.Transparent);
        tileGeo.setMaterial(mat);
        tileGeo.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.PI*3/2, new Vector3f(1,0,0)));
        tileGeo.setLocalTranslation(pos.x, - pos.z, pos.y);

        return tileGeo;
    }

    public Material getTileMat(Texture tileTex) {
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        mat.getAdditionalRenderState().setAlphaFallOff(0.1f);
        mat.getAdditionalRenderState().setPolyOffset(0.01f, 0.01f);
        mat.setTexture("ColorMap", tileTex);
        return mat;
    }

    public MapStructure getMap() {
        return map;
    }

    public Node getMapNode() {
        return mapNode;
    }

    public ArrayList<Texture> getTextures() {
        return textures;
    }
}
