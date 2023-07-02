package effective.chapter4.item23;

import effective.chapter4.item23.hierarchy_class.Circle;
import effective.chapter4.item23.hierarchy_class.Rectangle;
import effective.chapter4.item23.tag_class.Figure;
import effective.chapter4.item23.tag_class.Figure.Shape;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class FigureTest {

    @Test
    @DisplayName("태그 달린 클래스로 구현하면 비효율적입니다")
    void test1() {
        // given
        Figure rectangle = new Figure(20, 10);
        Figure circle = new Figure(5);

        // when
        Shape shape1 = rectangle.getShape();
        Shape shape2 = circle.getShape();

        double area1 = rectangle.area();
        double area2 = circle.area();

        // then
        assertThat(shape1).isEqualTo(Shape.RECTANGLE);
        assertThat(shape2).isEqualTo(Shape.CIRCLE);
        assertThat(area1).isEqualTo(200);
        assertThat(area2).isEqualTo(25 * Math.PI);
    }

    @Test
    @DisplayName("클래스 계층구조로 활용하여 재사용성을 높입니다")
    void test2() {
        // given
        Rectangle rectangle = new Rectangle(20, 10);
        Circle circle = new Circle(5);

        // when
        double area1 = rectangle.area();
        double area2 = circle.area();

        // then
        assertThat(area1).isEqualTo(200);
        assertThat(area2).isEqualTo(25 * Math.PI);
    }
}