package sg.atom.corex.managers;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.eventbus.Subscribe;
import sg.atom.core.lifecycle.AbstractManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sg.atom.core.AtomMain;
import sg.atom.corex.config.DeviceInfo;
import sg.atom.corex.entity.Entity;
import sg.atom.corex.entity.EntityManager.EntityRemovalEvent;
import sg.atom.corex.entity.SpatialEntity;

/**
 *
 * @author cuong.nguyen
 */
public class WorldManager extends AbstractManager {

    public static final Logger logger = LoggerFactory.getLogger(WorldManager.class.getName());
    Node worldNode;
    Node levelNode;
    //Models and nodes
    protected HashMap<String, Node> nodes = new HashMap<String, Node>();
    //    HashMap<Geometry, Spatial> collisionMap;
    HashMap<String, String> modelMap = new HashMap<String, String>();
    LoadingCache<String, Spatial> modelCache = CacheBuilder.newBuilder()
            .maximumSize(20)
            .expireAfterWrite(4, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Spatial>() {
        public Spatial load(String modelName) {
            return loadModel(modelName);
        }
    });
    private Geometry boxGeom;

    public WorldManager(AtomMain app) {
        super(app);
    }

    public void loadLevels() {
    }

    @Override
    public void load() {
        //Init
        this.worldNode = new Node("WorldNode");
        //Load
        createModelMap();
        createFlatGround(100);
//        loadLevels();
//        createSkyBox();
//        createParticleEffects();
//        createHUD();
        createEntities();
        //Config
        setupInput();
        setupLights();
        attachNodes();

    }

    protected Spatial loadModel(String modelName) {
        return app.getAssetManager().loadModel(modelMap.get(modelName));
    }

    public void createModelMap() {
        modelMap.put("Soldier", "Models/Troop/Soldier1.j3o");
        modelMap.put("Tower", "Models/Architecture/Tower1.j3o");
        modelMap.put("Dragon", "Models/Dragon/dragon_fix.j3o");
    }

    public void createSkyBox() {
        //SkyFactory.createSky(assetManager, "Textures/Sky/Bright/BrightSky.dds", false);
        Spatial sky = SkyFactory.createSky(assetManager, "Textures/Beach/FullskiesSunset0068.dds", false);
        sky.setLocalScale(350);

        worldNode.attachChild(sky);
    }

    public void createLights() {
    }

    public void createMiniMap() {
    }

    public void createEntities() {
        Box b = new Box(1, 1, 1);
        boxGeom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        boxGeom.setMaterial(mat);

        createGroupNode("CollisionBoxes");
        createGroupNode("Tower");
        createGroupNode("Soldier");
        createGroupNode("Dragon");
    }

    public void createGroupNode(String name) {
        Node groupNode = new Node(name);
        nodes.put(name, groupNode);
        worldNode.attachChild(groupNode);
    }

    public Node getGroupNode(String name) {
        return nodes.get(name);
    }

    public void attachNodes() {
        for (Node node : nodes.values()) {
            worldNode.attachChild(node);
        }
        this.rootNode.attachChild(worldNode);
    }

    public static Vector3f createRandomPos(float x, float y, float z) {
        return new Vector3f(FastMath.nextRandomFloat() * x - x, FastMath.nextRandomFloat() * y, FastMath.nextRandomFloat() * z - z);
    }

    public static Vector2f createRandomPos(float x, float y) {
        return new Vector2f(FastMath.nextRandomFloat() * x - x, FastMath.nextRandomFloat() * y - y);
    }

    public void putEntity(Node parentNode, SpatialEntity entity, Vector2f mapPos) {
        Spatial spatial = entity.getSpatial();
        parentNode.attachChild(spatial);
        spatial.setLocalTranslation(new Vector3f(mapPos.x, 0, mapPos.y));
    }

    public void putEntity(SpatialEntity entity, Vector2f mapPos) {
        putEntity(worldNode, entity, mapPos);
    }

    public void putEntity(SpatialEntity entity, Vector2f mapPos, int state) {
    }

    public void putSpatial(Spatial spatial, Vector3f mapPos) {
        worldNode.attachChild(spatial);
        spatial.setLocalTranslation(new Vector3f(mapPos));
    }

    public void putEntity(Node parentNode, SpatialEntity entity, Vector3f mapPos) {
        Spatial spatial = entity.getSpatial();
        parentNode.attachChild(spatial);
        spatial.setLocalTranslation(new Vector3f(mapPos));
    }

    public void putEntity(SpatialEntity entity, Vector3f mapPos) {
        putEntity(worldNode, entity, mapPos);
    }

    public void putEntity(SpatialEntity entity, Vector3f mapPos, int state) {
    }

    public void putSpatial(Spatial spatial, Vector2f mapPos) {
    }

    @Subscribe
    public void removeEntity(EntityRemovalEvent removalEvent) {
        logger.info("Try to detach entity");
        Entity e = removalEvent.getEntity();
        if (e instanceof SpatialEntity) {
            ((SpatialEntity) e).getSpatial().removeFromParent();
        }
    }

    public void createFlatGround(int size) {
        Box b = new Box(new Vector3f(0, -0.1f, size / 2), size, 0.01f, size);
        b.scaleTextureCoordinates(new Vector2f(40, 40));
        Material matGround = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        Texture grass = assetManager.loadTexture("Textures/Terrain/splat/grass.jpg");
        grass.setWrap(Texture.WrapMode.Repeat);
        matGround.setTexture("DiffuseMap", grass);
        Geometry ground = new Geometry("Ground", b);

        ground.setMaterial(matGround);
        ground.setShadowMode(RenderQueue.ShadowMode.Receive);
        worldNode.attachChild(ground);
    }

    public void createForest() {
    }

    public void setupLights() {
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);
//
//        PointLight lamp = new PointLight();
//        lamp.setPosition(new Vector3f(0, 40, 0));
//        lamp.setColor(ColorRGBA.White);
//        rootNode.addLight(lamp);
    }

    public void setupInput() {
        //Mouse
        //Key
//        inputManager.addMapping("Shoot",
//                new KeyTrigger(KeyInput.KEY_SPACE));
//        inputManager.addMapping("Reload",
//                new KeyTrigger(KeyInput.KEY_SPACE));
//        inputManager.addListener(actionListener, "Shoot", "Reload");
//        inputManager.addListener(analogListener, "Shoot");
    }
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean pressed, float tpf) {
        }
    };
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float intensity, float tpf) {
        }
    };

    public Spatial createPlaceHolder(String type) {
        return boxGeom.clone();
    }

    public SpatialEntity getEntity(Geometry target) {
        SpatialEntity result = null;
//        worldNode.depthFirstTraversal(new SceneGraphVisitorAdapter(){
//
//            @Override
//            public void visit(Geometry geom) {
//                super.visit(geom); 
//                
//                
//            }
//            
//        });
        return result;
    }

    public Node getCollisionNode() {
        return worldNode;
    }

    public Spatial getModel(String modelName) {
        if (DeviceInfo.isAndroid()) {
            try {
                return modelCache.get(modelName);
            } catch (ExecutionException ex) {
                java.util.logging.Logger.getLogger(WorldManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return loadModel(modelName);
        }
        return new Node("EmptyNode");
    }
}
