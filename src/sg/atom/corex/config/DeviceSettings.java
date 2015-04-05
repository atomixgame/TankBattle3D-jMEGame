package sg.atom.corex.config;

/**
 *
 * @author CuongNguyen
 */
public enum DeviceSettings {
    Phone(800,480), PC(1024,768);

    private DeviceSettings(int width, int height) {
        this.width = width;
        this.height = height;
    }
     
    public int width;
    public int height;
    
}
