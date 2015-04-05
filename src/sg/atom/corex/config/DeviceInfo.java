package sg.atom.corex.config;

/**
 *
 * @author CuongNguyen
 */
public class DeviceInfo {

    public static boolean isDesktopApp() {
        return !isMobileApp();
    }
    
    public static boolean isMobileApp() {
        return isAndroid() || isIOS();
    }
    
    public static boolean isAndroid(){
        return System.getProperty("java.vm.name").equalsIgnoreCase("Dalvik");
    }
    
    
    public static boolean isIOS(){
        return System.getProperty("java.vm.name").equalsIgnoreCase("iOS");
    }
}
