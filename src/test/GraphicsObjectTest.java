package test;

import junit.framework.TestCase;
import org.junit.Test;
import org.ulco.*;
import org.junit.Assert.*;

public class GraphicsObjectTest extends TestCase {

    @Test
    public void testColor() throws Exception {
        Square s = new Square(new Point(0,0), 5);
        Circle c = new Circle(new Point(5,5), 4);
        Rectangle r = new Rectangle(new Point(-6,10), 5.2, 9);

        assertEquals(s.getM_border_color(), "TRANSPARENT");
        assertEquals(s.getM_inside_color(), "TRANSPARENT");
        assertEquals(c.getM_border_color(), "TRANSPARENT");
        assertEquals(c.getM_inside_color(), "TRANSPARENT");
        assertEquals(r.getM_border_color(), "TRANSPARENT");
        assertEquals(r.getM_inside_color(), "TRANSPARENT");

        s.setM_border_color("RED");
        s.setM_inside_color("BLACK");
        assertEquals(s.getM_border_color(), "RED");
        assertEquals(s.getM_inside_color(), "BLACK");

        c.setM_border_color("BLUE");
        c.setM_inside_color("WHITE");
        assertEquals(c.getM_border_color(), "BLUE");
        assertEquals(c.getM_inside_color(), "WHITE");

        r.setM_border_color("GREEN");
        r.setM_inside_color("YELLOW");
        assertEquals(r.getM_border_color(), "GREEN");
        assertEquals(r.getM_inside_color(), "YELLOW");
    }
}