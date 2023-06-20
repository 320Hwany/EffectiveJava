package effective.item10;

import java.awt.*;
import java.util.Objects;

public class ColorPointBetterWay {

    private final Point point;

    private final Color color;

    public ColorPointBetterWay(Point point, Color color) {
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
