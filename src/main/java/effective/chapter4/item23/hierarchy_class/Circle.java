package effective.chapter4.item23.hierarchy_class;

public class Circle extends Figure {

    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * (radius * radius);
    }
}
