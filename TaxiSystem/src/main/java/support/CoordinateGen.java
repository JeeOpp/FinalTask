package support;

/**
 * Created by DNAPC on 08.12.2017.
 */
public class CoordinateGen {
    private static final Double lngLeftBorder = 27.404;
    private static final Double lngRightBorder = 27.695;
    private static final Double latTopBorder = 53.972;
    private static final Double latBottomBorder = 53.833;

    public static Double getLatitude() {
        return (Math.random()*(latTopBorder-latBottomBorder)) + latBottomBorder;
    }
    public static Double getLongitude() {
        return (Math.random()*(lngRightBorder-lngLeftBorder))+lngLeftBorder;
    }
}
