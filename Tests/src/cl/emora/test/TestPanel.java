package cl.emora.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;


public class TestPanel extends Panel implements ActionListener {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;


    /**
     * 
     */
    public TestPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 500));
        setBackground(Color.BLACK);
    }



    @Override
    public void paint(Graphics g) {
        super.paintComponents(g);
        doDrawing(g);
    }

    
    
    private void doDrawing(Graphics g) {
        System.out.println("Hi!");
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.WHITE);
        g2.draw(new Rectangle2D.Double(10, 10, 50, 50));
        Toolkit.getDefaultToolkit().sync();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        chao();
        repaint();
    }
    

    private void chao() {
        System.out.println("chao");
    }

}
