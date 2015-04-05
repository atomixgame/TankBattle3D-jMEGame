package sg.atom.corex.config;

/**
 *
 * @author CuongNguyen
 */
public enum QualitySetting {

    NONE(0),
    MINIMUM(1),
    POOR(2),
    NORMAL(3),
    GOOD(4),
    BEST(5);
    int intValue;

    private QualitySetting(int intValue) {
        this.intValue = intValue;
    }
}
