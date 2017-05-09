package cl.emora.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.AbstractMap.SimpleEntry;

import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MySnake extends JPanel implements ActionListener {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 1L;
    private static final int TIME_SLEEP = 140;
    private static final int ANCHO_MAX_PANEL = 500;
    private static final int ALTO_MAX_PANEL = 500;
    private static final int UP = 1;
    private static final int DOWN = 2;
    private static final int RIGHT = 3;
    private static final int LEFT = 4;
    private static final int FOOD_DIMENSION = 10;
    private static final int INITIAL_X = 250;
    private static final int INITIAL_Y = 400;
    private SimpleEntry<Integer, Rectangle2D> head;
    private Rectangle2D food;
    private ArrayList<SimpleEntry<Integer, Rectangle2D>> bodyList;
    private boolean goOn = true;
    private Timer timer;

    public MySnake() {
        addKeyListener(new TAdapter());
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(ANCHO_MAX_PANEL, ALTO_MAX_PANEL));
        setBackground(Color.BLACK);
        setFocusable(true);
        bodyList = new ArrayList<SimpleEntry<Integer, Rectangle2D>>();

        head = new SimpleEntry<Integer, Rectangle2D>(UP, new Rectangle2D.Double(INITIAL_X, INITIAL_Y, FOOD_DIMENSION, FOOD_DIMENSION));
        int i = 0;
        int x = (int) head.getValue().getX();
        int y = (int) head.getValue().getMaxY();
        while (i <= 2) {
            bodyList.add(
                    new SimpleEntry<Integer, Rectangle2D>(UP, new Rectangle2D.Double(x, y + (FOOD_DIMENSION * i), FOOD_DIMENSION, FOOD_DIMENSION)));
            i++;
        }
        food = new Rectangle2D.Double(10, 10, FOOD_DIMENSION, FOOD_DIMENSION);
        timer = new Timer(TIME_SLEEP, this);
        timer.start();
    }

    private void moveTo(int way) {
        way = way > 0 ? way : head.getKey();
        int x = (int) head.getValue().getX();
        int y = (int) head.getValue().getY();
        if (goOn) {
            ArrayList<SimpleEntry<Integer, Rectangle2D>> bodyListAux = new ArrayList<SimpleEntry<Integer, Rectangle2D>>();
            switch (way) {

            case UP:
                System.out.println("UP");
                bodyListAux.add(head);
                bodyList.remove(bodyList.size() - 1);
                bodyListAux.addAll(bodyList);
                head = new SimpleEntry<Integer, Rectangle2D>(way, new Rectangle2D.Double(x, y - FOOD_DIMENSION, FOOD_DIMENSION, FOOD_DIMENSION));
                bodyList = bodyListAux;
                y -= FOOD_DIMENSION;
                break;

            case DOWN:
                System.out.println("DOWN");
                bodyListAux.add(head);
                bodyList.remove(bodyList.size() - 1);
                bodyListAux.addAll(bodyList);
                head = new SimpleEntry<Integer, Rectangle2D>(way, new Rectangle2D.Double(x, y + FOOD_DIMENSION, FOOD_DIMENSION, FOOD_DIMENSION));
                bodyList = bodyListAux;
                y += FOOD_DIMENSION;
                break;

            case RIGHT:
                System.out.println("RIGHT");
                bodyListAux.add(head);
                bodyList.remove(bodyList.size() - 1);
                bodyListAux.addAll(bodyList);
                head = new SimpleEntry<Integer, Rectangle2D>(way, new Rectangle2D.Double(x + FOOD_DIMENSION, y, FOOD_DIMENSION, FOOD_DIMENSION));
                bodyList = bodyListAux;
                x += FOOD_DIMENSION;
                break;

            case LEFT:
                System.out.println("LEFT");
                bodyListAux.add(head);
                bodyList.remove(bodyList.size() - 1);
                bodyListAux.addAll(bodyList);
                head = new SimpleEntry<Integer, Rectangle2D>(way, new Rectangle2D.Double(x - FOOD_DIMENSION, y, FOOD_DIMENSION, FOOD_DIMENSION));
                bodyList = bodyListAux;
                x -= FOOD_DIMENSION;
                break;
                
            default:
                break;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        this.drawSnake(g2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("[actionPerformed]");
        moveTo(0);
        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("keyCode" + e.getKeyCode());
            synchronized (this) {
                switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT:
                    moveTo(RIGHT);
                    break;
                case KeyEvent.VK_UP:
                    moveTo(UP);
                    break;
                case KeyEvent.VK_DOWN:
                    moveTo(DOWN);
                    break;
                case KeyEvent.VK_LEFT:
                    moveTo(LEFT);
                    break;
                default:
                    System.out.println("Una raya en el agua");
                    break;
                }
            }
        }
    }

    private void drawSnake(Graphics2D g2) {
        g2.setPaint(Color.WHITE);
        g2.draw(head.getValue());
        g2.draw(food);
        for (SimpleEntry<Integer, Rectangle2D> bodyEntry : bodyList) {
            g2.draw(bodyEntry.getValue());
        }
        Toolkit.getDefaultToolkit().sync();
    }
}
