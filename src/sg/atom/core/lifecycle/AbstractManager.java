package sg.atom.core.lifecycle;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import java.util.concurrent.Executors;
import org.apache.commons.configuration.Configuration;
import sg.atom.core.AtomMain;
import sg.atom.core.execution.Task;

/**
 *
 * @author cuong.nguyen
 */
public class AbstractManager implements IGameCycle, AppState{
    protected AtomMain app;
    protected Node guiNode;
    protected Node rootNode;
    //Managers
    protected AssetManager assetManager;
    protected AppStateManager stateManager;
    protected InputManager inputManager; 
    protected boolean actived;
    private boolean initialized;
    protected ListeningExecutorService executionService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
    protected boolean customCycle = false;

    protected AbstractManager() {
    }
    
    public AbstractManager(AtomMain app) {
        this.app = app;
        this.guiNode = app.getGuiNode();
        this.rootNode = app.getRootNode();
        this.assetManager = app.getAssetManager();
        this.stateManager = app.getStateManager();
        this.inputManager = app.getInputManager();
    }
    
    
    public void init() {
        
    }

    public void load() {
        
    }

    public void config(Configuration props) {
        
    }

    public void update(float tpf) {
        
    }

    public void finish() {
        
    }

    public void addTask(Task task){
        
    }
    public void initialize(AppStateManager stateManager, Application app) {
        this.initialized = true;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setEnabled(boolean active) {
        actived = active;
    }

    public boolean isEnabled() {
        return actived;
    }

    public void stateAttached(AppStateManager stateManager) {
        
    }

    public void stateDetached(AppStateManager stateManager) {
        
    }

    public void render(RenderManager rm) {
        
    }

    public void postRender() {
        
    }

    public void cleanup() {
        
    }

    public AtomMain getApp() {
        return app;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public Node getGuiNode() {
        return guiNode;
    }

    public AppStateManager getStateManager() {
        return stateManager;
    }

    public Node getRootNode() {
        return rootNode;
    }

    public ListeningExecutorService getExecutionService() {
        return executionService;
    }
    
    
}
