package sg.games.tank.states;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.renderer.Camera;
import com.simsilica.lemur.Container;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import com.simsilica.lemur.ProgressBar;
import sg.atom.core.AtomMain;
import sg.atom.core.execution.CombinedProgressMeter;
import sg.atom.core.execution.ProgressRecord;

/**
 * Show a loading progressbar and watch the progress of loading tasks.
 *
 * @author cuong.nguyenmanh2
 */
public class GameLoadingState extends AbstractAppState{

    Logger logger = Logger.getLogger(GameLoadingState.class.getName());
    private AtomMain app;
//    private ServiceManager loadingServiceManager;
//    private Set<Service> services = new HashSet<Service>();

    private Container loadingScreen;
    private ProgressBar progressBar;

    private CombinedProgressMeter progressMeter = new CombinedProgressMeter() {
        
    };
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (AtomMain) app;
        createLoadingScreen();
        setEnabled(false);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            progressMeter.start();
            showLoadingScreen();
        } else {
            removeLoadingScreen();
        }
    }

    public void createLoadingScreen() {
        Camera cam = app.getCamera();
//        loadingScreen = new  Container(new BorderLayout(),"scifi");
//        loadingScreen.setPreferredSize(new Vector3f(cam.getWidth(),cam.getHeight(),1));
//        loadingScreen.setLocalTranslation(0, cam.getHeight(), 0);
//        loadingScreen.setBackground(new QuadBackgroundComponent(ColorRGBA.Blue));
//        progressBar = new ProgressBar("scifi");
//        progressBar.setPreferredSize(new Vector3f(400, 40, 1));
//        
//        Label title = loadingScreen.addChild(new Label("", new ElementId(ScifiStyles.MENU_TITLE_ID), "scifi"), BorderLayout.Position.Center);
//
//        IconComponent titleImage = new IconComponent("Interface/Images/Brand/logo-512x256.png",
//                new Vector2f(1, 0.6f),
//                5, 5, 0, false);
//        title.setBackground(titleImage);
//        
//        loadingScreen.addChild(progressBar, BorderLayout.Position.South);
    }
    public void showLoadingScreen(){
//        this.app.getGuiNode().attachChild(loadingScreen);
    }
    public void removeLoadingScreen() {
//        if (this.app.getGuiNode().hasChild(loadingScreen)){
//            this.app.getGuiNode().detachChild(loadingScreen);
//        }
            
    }
    
    public <V extends Object> ProgressRecord<V> addTask(Callable<V> task, final FutureCallback<V> callback, final float percentage) {
        ListenableFuture<V> submittedTask = app.getExecutorService().submit(task);
        ProgressRecord<V> progressRecord = new ProgressRecord(callback, percentage, progressMeter);
        Futures.addCallback(submittedTask, progressRecord);
        return progressRecord;
    }

//    public <V extends Object> void addTasks(Callable<V>... tasks, FutureCallback<V> callback){
//        ListenableFuture<V> submittedTask = app.getExecutorService().submit(task);       
//        ListenableFuture<List<V>> Futures.successfulAsList(queries);
//        (submittedTask, callback);
//    }
    @Override
    public void update(float tpf) {
        super.update(tpf);
        
        if (isEnabled()){
            if (progressMeter.isFinished()){
                System.out.println("Finished!");
                setEnabled(false);
            } else {
                updateUI();
            }
        }
    }
   
    public void reportError(String message){
        System.out.println(" Error " + message);
    }
    
    public void updateUI(){
        progressBar.setProgressPercent(progressMeter.getTotalProgress());
    }
}
