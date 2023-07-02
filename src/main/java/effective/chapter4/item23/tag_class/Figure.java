package effective.chapter4.item23.tag_class;

public class Figure {

    public enum Shape{ RECTANGLE, CIRCLE};

    // 현재 모양을 나타냅니다
    private final Shape shape;

    // 사각형일 때만 사용합니다
    private double length;

    private double width;

    // 원일 때만 사용합니다
    private double radius;

    public Figure(double length, double width) {
        this.shape = Shape.RECTANGLE;
        this.length = length;
        this.width = width;
    }

    public Figure(double radius) {
        this.shape = Shape.CIRCLE;
        this.radius = radius;
    }

    public double area() {
        switch (shape) {
            case RECTANGLE:
                return length * width;
            case CIRCLE:
                return Math.PI * (radius * radius);
            default:
                throw new AssertionError(shape);
        }
    }

    public Shape getShape() {
        return shape;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double getRadius() {
        return radius;
    }
}
