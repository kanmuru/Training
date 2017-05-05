package cl.emora.test;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class MyCanvas extends Canvas {

    /**
     * Serial number.
     */
    private static final long serialVersionUID = 1L;

    public MyCanvas() {
        setBackground(Color.BLACK);
        // setLocation(new Point(0, 0));
    }

    public MyCanvas(GraphicsConfiguration config) {
        super(config);
    }

    @Override
    public void paint(Graphics g) {
        System.out.println("Aqui");
    }
}
