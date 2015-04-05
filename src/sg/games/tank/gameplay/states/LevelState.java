package sg.games.tank.gameplay.states;

import com.google.common.util.concurrent.FutureCallback;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.debug.Grid;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.simsilica.lemur.event.BaseAppState;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import sg.atom.corex.managers.WorldManager;
import sg.atom.corex.world.tiled.TiledLoader3D;
import sg.atom.corex.world.tiled.TiledMap3D;
import sg.games.tank.gameplay.GameLevel;
import sg.games.tank.states.GameLoadingState;


/**
 *
 * @author cuong.nguyen
 */
public class LevelState extends BaseAppState {

    private Node levelNode;
    private ArrayList<String> levelNames;
    private ArrayList<GameLevel> levels;
    private TiledLoader3D tiledLoader;
    private GameLevel currentLevel;
    private int currentLevelIndex = 0;
    protected Node gizmo = new Node("gizmo");
    protected Geometry groundGeo;
    protected Geometry gridGeo;
    protected Geometry markGeo;
    //Physics
    //Lights
    //Materials
    protected Material unshadedMat;
    protected Material lightMat;
    protected Material matGround;
    private Node worldNode;
    private AssetManager assetManager;

    public void createLevels() {
        levelNames.add("Maps/Level1.tmx");
    }

    public void loadLevels() {
        for (String levelName : levelNames) {
        }
    }

    public void loadLevel(String levelName) {
        try {
            TiledMap3D tiledMap3D = tiledLoader.loadMap3D(levelName);
            this.levelNode = tiledMap3D.createMap();
//            tiledLoader.alignCam(app.getCamera(), tiledMap3D.getMap());
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(WorldManager.class.getName()).log(Level.SEVERE, "Can not load level :" + levelName, ex);
        }
    }

    public Node getLevelNode() {
        return levelNode;
    }

    public void loadLevel(GameLevel level) {
        levelNode = new Node();
        levelNode.attachChild(getApplication().getAssetManager().loadModel(level.getPath()));
        this.currentLevel = level;
    }

    public void loadLevel(int levelNum) {
        String levelName = levelNames.get(levelNum);
        loadLevel(levelName);
    }

    public GameLevel getCurrentLevel() {
        return (GameLevel) currentLevel;
    }

    @Override
    protected void initialize(Application app) {

        this.worldNode = new Node("WorldNode");
        this.levelNames = new ArrayList<String>();
        this.tiledLoader = new TiledLoader3D();
        this.assetManager = app.getAssetManager();
        tiledLoader.initialize(app.getStateManager(), app);
//        stateManager.attach(tiledLoader);

    }

    @Override
    protected void cleanup(Application arg0) {
    }

    @Override
    protected void enable() {
        createTestWorld();
        createLevels();
        loadLevels();
        try {
            GameLoadingState loadingState = getState(GameLoadingState.class);
            loadingState.addTask(new Callable<Void>() {
                public Void call() throws Exception {
                    loadLevel(currentLevelIndex);
                    return null;
                }
            }, new FutureCallback<Void>() {
                public void onSuccess(Void v) {

                    getApplication().enqueue(new Callable<Void>() {
                        public Void call() throws Exception {
                            attachLevel();
                            return null;
                        }
                    });
                }

                public void onFailure(Throwable thrwbl) {
                    throw new RuntimeException("Can not load Level " + currentLevelIndex);
                }
            }, 0.5f);


        } catch (NullPointerException ex) {
            throw new RuntimeException("LevelState depends in GameLoadingState");
        }

    }

    @Override
    protected void disable() {
    }

    private void attachLevel() {
        final SimpleApplication app = (SimpleApplication) getApplication();
        app.getRootNode().attachChild(worldNode);
        worldNode.attachChild(levelNode);
    }

    public void createTestWorld() {
        createGizmo();
        createGrid(15, 15);
    }

    public void createFlatGround(int size) {
        Box b = new Box(new Vector3f(0, -0.1f, size / 2), size, 0.01f, size);
        b.scaleTextureCoordinates(new Vector2f(40, 40));
        groundGeo = new Geometry("Ground", b);
        matGround = getMatGround();

        groundGeo.setMaterial(matGround);
        groundGeo.setShadowMode(RenderQueue.ShadowMode.Receive);
        worldNode.attachChild(groundGeo);
    }

    public Geometry createBox(ColorRGBA color) {
        Box box2 = new Box(new Vector3f(1, 3, 1), 1, 1, 1);
        Geometry red = new Geometry("Box", box2);
        red.setMaterial(getColoredMat(color));
        return red;
    }

    public Geometry putShape(Node node, Mesh shape, ColorRGBA color) {
        Geometry g = new Geometry("shape", shape);
        Material mat = getColoredMat(color).clone();
        mat.getAdditionalRenderState().setWireframe(true);
        g.setMaterial(mat);
        node.attachChild(g);
        return g;
    }

    public void putArrow(Vector3f pos, Vector3f dir, ColorRGBA color) {
        Arrow arrow = new Arrow(dir);
        arrow.setLineWidth(4); // make arrow thicker

        putShape(gizmo, arrow, color).setLocalTranslation(pos);
        worldNode.attachChild(gizmo);
        gizmo.scale(1);
    }

    public void createGizmo() {
        putArrow(Vector3f.ZERO, Vector3f.UNIT_X, ColorRGBA.Red);
        putArrow(Vector3f.ZERO, Vector3f.UNIT_Y, ColorRGBA.Green);
        putArrow(Vector3f.ZERO, Vector3f.UNIT_Z, ColorRGBA.Blue);
    }

    public void createGrid(int gw, int gh) {
        Material mat = getColoredMat(ColorRGBA.White).clone();

        Grid grid = new Grid(gw - 1, gh - 1, 1);
        gridGeo = new Geometry("Grid", grid);
        gridGeo.setMaterial(mat);
        gridGeo.setLocalTranslation(-gw / 2, 0.1f, -gh / 2);
        worldNode.attachChild(gridGeo);
    }

    // GETTER & SETTER
    //Material . Should move to MaterialManager ------------------------------
    //    public Material getColoredMat(ColorRGBA color) {
//        //return MaterialManager.getDefaultInstance(assetManager).getColoredMat(color);
//    }
    public Material getMatGround() {
        if (matGround == null) {
            matGround = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
            Texture grass = assetManager.loadTexture("Textures/Terrain/splat/grass.jpg");
            grass.setWrap(Texture.WrapMode.Repeat);
            matGround.setTexture("DiffuseMap", grass);
        }
        return matGround;
    }

    public Material getLightMat() {
        return lightMat;
    }

    public Material getUnshadedMat() {
        if (unshadedMat == null) {
            unshadedMat = new Material(assetManager,
                    "Common/MatDefs/Misc/Unshaded.j3md");
        }
        return unshadedMat;
    }

    public Material getColoredMat(ColorRGBA color) {
        Material mat = getUnshadedMat().clone();
        mat.setColor("Color", color);
        return mat;
    }
}
