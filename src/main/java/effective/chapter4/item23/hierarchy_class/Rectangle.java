package effective.chapter4.item23.hierarchy_class;

public class Rectangle extends Figure {

    private final double length;

    private final double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    public double area() {
        return length * width;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }
}
