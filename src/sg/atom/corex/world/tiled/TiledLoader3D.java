package sg.atom.corex.world.tiled;

import sg.atom.corex.world.tile.MapStructure;
import com.google.common.base.Converter;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import org.apache.log4j.Logger;

/**
 * TiledLoader3D load Tiled file into a Node.
 *
 * @author cuong.nguyenmanh2
 */
public class TiledLoader3D extends AbstractAppState {

    private final Logger logger = Logger.getLogger(TiledLoader3D.class);
    
    protected AssetManager assetManager;
    // Facilities use converters and mapping
    protected Converter uvConverter;
    protected Converter matConverter;
    protected Converter spatialConverter;
    protected Converter nodeConverter;
    protected Converter geometryConverter;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        this.assetManager = app.getAssetManager();
        assetManager.registerLoader(TMXLoader.class, "tmx");
    }

    public void alignCam(Camera cam, MapStructure map) {
        // Align the camera
        Vector3f mapCenter= new Vector3f(map.width / 2, -map.height / 2, 0);
        cam.setLocation(mapCenter.add(0,0,map.width/2));
        cam.lookAt(mapCenter, Vector3f.UNIT_Z);
    }
    
    public MapStructure loadMap(String mapFileName) throws Exception {
        MapStructure map = (MapStructure) assetManager.loadAsset(mapFileName);
        map.build();
//        logger.info("Loaded map "+ map.getWidth() + " x " + map.getHeight());
        return map;
    }

    public TiledMap3D loadMap3D(String mapFileName) throws Exception {
        MapStructure map = loadMap(mapFileName);
        String mapDir = mapFileName.substring(0, mapFileName.lastIndexOf("/"));
        TiledMap3D map3D = new TiledMap3D(assetManager, map);
        map3D.loadTextures(mapDir);
        return map3D;
    }
    

    // GETTER & SETTER ----------------------------------------------------------
    public Material getTileMat() {
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mat.setColor("Color", ColorRGBA.Blue);
        return mat;
    }
}
