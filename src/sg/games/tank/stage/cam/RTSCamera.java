package sg.games.tank.stage.cam;

/**
 *
 * @author cuong.nguyenmanh2
 */
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.TouchInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.TouchListener;
import com.jme3.input.controls.TouchTrigger;
import com.jme3.input.event.TouchEvent;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import sg.atom.corex.config.DeviceInfo;

public class RTSCamera extends AbstractAppState implements ActionListener, TouchListener {

    private BitmapText txtTouch;

    public enum Degree {

        SIDE,
        FWD,
        ROTATE,
        TILT,
        DISTANCE
    }
    private InputManager inputManager;
    private Camera cam;
    private int[] direction = new int[5];
    private float[] accelPeriod = new float[5];
    private float[] maxSpeed = new float[5];
    private float[] maxAccelPeriod = new float[5];
    private float[] minValue = new float[5];
    private float[] maxValue = new float[5];
    private Vector3f position = new Vector3f();
    private Vector3f center = new Vector3f();
    private Vector3f delta = new Vector3f();
    private float tilt = (float) (Math.PI / 4);
    private float rot = 0;
    private float distance = 40;
    private static final int SIDE = Degree.SIDE.ordinal();
    private static final int FWD = Degree.FWD.ordinal();
    private static final int ROTATE = Degree.ROTATE.ordinal();
    private static final int TILT = Degree.TILT.ordinal();
    private static final int DISTANCE = Degree.DISTANCE.ordinal();
    boolean isTouchDevice = false;

    public RTSCamera() {
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        setMinMaxValues(Degree.SIDE, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
        setMinMaxValues(Degree.FWD, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
        setMinMaxValues(Degree.ROTATE, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
        setMinMaxValues(Degree.TILT, 0.2f, (float) (Math.PI / 2) - 0.001f);
        setMinMaxValues(Degree.DISTANCE, 2, Float.POSITIVE_INFINITY);

        setMaxSpeed(Degree.SIDE, 10f, 0.4f);
        setMaxSpeed(Degree.FWD, 10f, 0.4f);
        setMaxSpeed(Degree.ROTATE, 2f, 0.4f);
        setMaxSpeed(Degree.TILT, 1f, 0.4f);
        setMaxSpeed(Degree.DISTANCE, 15f, 0.4f);

        this.cam = app.getCamera();
        this.setCenter(new Vector3f());
        registerWithInput(app.getInputManager());

        BitmapFont guiFont = app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        txtTouch = new BitmapText(guiFont, false);
        txtTouch.setSize(guiFont.getCharSet().getRenderedSize());
        txtTouch.setText("Hello World");
        txtTouch.setLocalTranslation(300, txtTouch.getLineHeight(), 0);
        ((SimpleApplication) app).getGuiNode().attachChild(txtTouch);

        isTouchDevice = DeviceInfo.isMobileApp();
    }

    public void setMaxSpeed(Degree deg, float maxSpd, float accelTime) {
        maxSpeed[deg.ordinal()] = maxSpd / accelTime;
        maxAccelPeriod[deg.ordinal()] = accelTime;
    }

    public void registerWithInput(InputManager inputManager) {
        this.inputManager = inputManager;

        if (isTouchDevice) {

            inputManager.addMapping("Touch", new TouchTrigger(TouchInput.ALL));
            inputManager.addListener(this, new String[]{"Touch"});


        } else {
            String[] mappings = new String[]{"+SIDE", "+FWD", "+ROTATE", "+TILT", "+DISTANCE",
                "-SIDE", "-FWD", "-ROTATE", "-TILT", "-DISTANCE",};

            inputManager.addMapping("-SIDE", new KeyTrigger(KeyInput.KEY_A));
            inputManager.addMapping("+SIDE", new KeyTrigger(KeyInput.KEY_D));
            inputManager.addMapping("+FWD", new KeyTrigger(KeyInput.KEY_S));
            inputManager.addMapping("-FWD", new KeyTrigger(KeyInput.KEY_W));
            inputManager.addMapping("+ROTATE", new KeyTrigger(KeyInput.KEY_Q));
            inputManager.addMapping("-ROTATE", new KeyTrigger(KeyInput.KEY_E));
            inputManager.addMapping("+TILT", new KeyTrigger(KeyInput.KEY_R));
            inputManager.addMapping("-TILT", new KeyTrigger(KeyInput.KEY_F));
            inputManager.addMapping("-DISTANCE", new KeyTrigger(KeyInput.KEY_Z));
            inputManager.addMapping("+DISTANCE", new KeyTrigger(KeyInput.KEY_X));

            inputManager.addListener(this, mappings);
            inputManager.setCursorVisible(true);
        }
    }

    @Override
    public void update(final float tpf) {

        if (isTouchDevice) {
            System.out.println("Update detla");
            position.addLocal(delta);
            delta.set(0, 0, 0);
        } else {
            for (int i = 0; i < direction.length; i++) {
                int dir = direction[i];
                switch (dir) {
                    case -1:
                        accelPeriod[i] = clamp(-maxAccelPeriod[i], accelPeriod[i] - tpf, accelPeriod[i]);
                        break;
                    case 0:
                        if (accelPeriod[i] != 0) {
                            double oldSpeed = accelPeriod[i];
                            if (accelPeriod[i] > 0) {
                                accelPeriod[i] -= tpf;
                            } else {
                                accelPeriod[i] += tpf;
                            }
                            if (oldSpeed * accelPeriod[i] < 0) {
                                accelPeriod[i] = 0;
                            }
                        }
                        break;
                    case 1:
                        accelPeriod[i] = clamp(accelPeriod[i], accelPeriod[i] + tpf, maxAccelPeriod[i]);
                        break;
                }

            }

            distance += maxSpeed[DISTANCE] * accelPeriod[DISTANCE] * tpf;
            tilt += maxSpeed[TILT] * accelPeriod[TILT] * tpf;
            rot += maxSpeed[ROTATE] * accelPeriod[ROTATE] * tpf;

            distance = clamp(minValue[DISTANCE], distance, maxValue[DISTANCE]);
            rot = clamp(minValue[ROTATE], rot, maxValue[ROTATE]);
            tilt = clamp(minValue[TILT], tilt, maxValue[TILT]);

            double offX = maxSpeed[SIDE] * accelPeriod[SIDE] * tpf;
            double offZ = maxSpeed[FWD] * accelPeriod[FWD] * tpf;

            center.x += offX * Math.cos(-rot) + offZ * Math.sin(rot);
            center.z += offX * Math.sin(-rot) + offZ * Math.cos(rot);

            position.x = center.x + (float) (distance * Math.cos(tilt) * Math.sin(rot));
            position.y = center.y + (float) (distance * Math.sin(tilt));
            position.z = center.z + (float) (distance * Math.cos(tilt) * Math.cos(rot));
            System.out.println("Update keyboard & mouse");
        }

        cam.setLocation(position);
        cam.lookAt(center, new Vector3f(0, 1, 0));
    }

    private static float clamp(float min, float value, float max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }

    public float getMaxSpeed(Degree dg) {
        return maxSpeed[dg.ordinal()];
    }

    public float getMinValue(Degree dg) {
        return minValue[dg.ordinal()];
    }

    public float getMaxValue(Degree dg) {
        return maxValue[dg.ordinal()];
    }

    // SIDE and FWD min/max values are ignored
    public void setMinMaxValues(Degree dg, float min, float max) {
        minValue[dg.ordinal()] = min;
        maxValue[dg.ordinal()] = max;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setCenter(Vector3f center) {
        this.center.set(center);
    }

    public void render(RenderManager rm, ViewPort vp) {
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        int press = isPressed ? 1 : 0;

        char sign = name.charAt(0);
        if (sign == '-') {
            press = -press;
        } else if (sign != '+') {
            return;
        }

        Degree deg = Degree.valueOf(name.substring(1));
        direction[deg.ordinal()] = press;
    }

    public void onTouch(String name, TouchEvent event, float tpf) {
        txtTouch.setText(event.toString());

        if (event.getType() == TouchEvent.Type.SCROLL) {
            delta.set(event.getDeltaX() * 100, 0, event.getDeltaY() * 100);
        }
    }
}
