package effective.chapter3.item10;

import java.awt.*;

public class ColorPointBetterWay {

    private final effective.chapter3.item10.Point point;

    private final Color color;

    public ColorPointBetterWay(effective.chapter3.item10.Point point, Color color) {
        this.point = point;
        this.color = color;
    }

    public Point asPoint() {
        return point;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ColorPoint)) {
            return false;
        }
        ColorPointBetterWay cp = (ColorPointBetterWay) o;
        return cp.point.equals(point) && cp.color.equals(color);
    }
}
