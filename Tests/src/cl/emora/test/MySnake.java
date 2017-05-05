package cl.emora.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.AbstractMap.SimpleEntry;

import java.util.ArrayList;
import java.util.Collections;


public class MySnake extends Panel {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 1L;
    private static final int TIME_SLEEP = 200;
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
    private SimpleEntry<Integer, Rectangle2D> tail;
    private Rectangle2D food;
    private ArrayList<SimpleEntry<Integer, Rectangle2D>> bodyList;
    private boolean goOn = true;

    public MySnake() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(ANCHO_MAX_PANEL, ALTO_MAX_PANEL));
        setBackground(Color.BLACK);
        setFocusable(true);
        bodyList = new ArrayList<SimpleEntry<Integer, Rectangle2D>>();

        this.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("keyCode" + e.getKeyCode());
                synchronized (this) {
                    switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT:
                        goOn = false;
                        moveTo(RIGHT, (Graphics2D) getGraphics());
                        break;
                    case KeyEvent.VK_UP:
                        moveTo(UP, (Graphics2D) getGraphics());
                        break;
                    case KeyEvent.VK_DOWN:
                        moveTo(DOWN, (Graphics2D) getGraphics());
                        break;
                    case KeyEvent.VK_LEFT:
                        moveTo(LEFT, (Graphics2D) getGraphics());
                        break;
                    default:
                        System.out.println("Una raya en el agua");
                        break;
                    }
                }
            }
        });
    }

    public void showGame(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.WHITE);
        head = new SimpleEntry<Integer, Rectangle2D>(UP, new Rectangle2D.Double(INITIAL_X, INITIAL_Y, FOOD_DIMENSION, FOOD_DIMENSION));
        SimpleEntry<Integer, Rectangle2D> bodyPart1 = new SimpleEntry<Integer, Rectangle2D>(UP,
                new Rectangle2D.Double(head.getValue().getX(), head.getValue().getMaxY(), FOOD_DIMENSION, FOOD_DIMENSION));
        SimpleEntry<Integer, Rectangle2D> bodyPart2 = new SimpleEntry<Integer, Rectangle2D>(UP,
                new Rectangle2D.Double(bodyPart1.getValue().getX(), bodyPart1.getValue().getMaxY(), FOOD_DIMENSION, FOOD_DIMENSION));
        bodyList.add(bodyPart1);
        bodyList.add(bodyPart2);
        tail = new SimpleEntry<Integer, Rectangle2D>(UP,
                new Rectangle2D.Double(bodyPart2.getValue().getX(), bodyPart2.getValue().getMaxY(), FOOD_DIMENSION, FOOD_DIMENSION));
        food = new Rectangle2D.Double(0, 0, FOOD_DIMENSION, FOOD_DIMENSION);

        g2.fill(food);
        g2.fill(head.getValue());
        g2.fill(bodyPart1.getValue());
        g2.fill(bodyPart2.getValue());
        g2.fill(tail.getValue());
        moveTo(UP, g2);
         changeTailForHead();
         moveTo(DOWN, g2);
        // moveTo(RIGHT);
        // changeTailForHead();
        // moveTo(LEFT);
    }

    private void changeTailForHead() {
        SimpleEntry<Integer, Rectangle2D> auxHead = head;
        head = tail;
        tail = auxHead;
        Collections.reverse(bodyList);        
    }

    private void moveTo(int way, Graphics2D g2) {
        int x = (int) head.getValue().getX();
        int y = (int) head.getValue().getY();
        boolean condition = false;
        try {
            synchronized (this) {
                goOn = true;
                do {
                    ArrayList<SimpleEntry<Integer, Rectangle2D>> bodyListAux = new ArrayList<SimpleEntry<Integer, Rectangle2D>>();
                    Rectangle2D lastBody;
                    switch (way) {
                    case UP:
                        System.out.println("UP");
                        g2.setPaint(Color.BLACK);
                        g2.fill(tail.getValue());
                        bodyListAux.add(head);
                        tail = bodyList.get(bodyList.size() - 1);
                        bodyList.remove(bodyList.size() - 1);
                        bodyListAux.addAll(bodyList);
                        for (SimpleEntry<Integer, Rectangle2D> bodyEntry : bodyListAux) {
                            g2.setPaint(Color.WHITE);
                            g2.fill(bodyEntry.getValue());
                        }
                        head = new SimpleEntry<Integer, Rectangle2D>(UP,
                                new Rectangle2D.Double(x, y - FOOD_DIMENSION, FOOD_DIMENSION, FOOD_DIMENSION));
                        bodyList = bodyListAux;
                        y -= FOOD_DIMENSION;
                        condition = head.getValue().getY() > 0;
                        break;
                    case DOWN:
                        System.out.println("DOWN");
                        g2.setPaint(Color.BLACK);
                        g2.fill(tail.getValue());
                        bodyListAux.add(head);
                        tail = bodyList.get(bodyList.size() - 1);
                        bodyList.remove(bodyList.size() - 1);
                        bodyListAux.addAll(bodyList);
                        for (SimpleEntry<Integer, Rectangle2D> bodyEntry : bodyListAux) {
                            g2.setPaint(Color.WHITE);
                            g2.fill(bodyEntry.getValue());
                        }
                        head = new SimpleEntry<Integer, Rectangle2D>(UP,
                                new Rectangle2D.Double(x, y + FOOD_DIMENSION, FOOD_DIMENSION, FOOD_DIMENSION));
                        bodyList = bodyListAux;
                        y += FOOD_DIMENSION;
                        condition = head.getValue().getMaxY() < ALTO_MAX_PANEL;
                        break;
                    case RIGHT:
                        System.out.println("RIGHT");
                        g2.clearRect((int) tail.getValue().getX(), (int) tail.getValue().getY(), FOOD_DIMENSION, FOOD_DIMENSION);
                        head = new SimpleEntry<Integer, Rectangle2D>(UP,
                                new Rectangle2D.Double(x + FOOD_DIMENSION, y, FOOD_DIMENSION, FOOD_DIMENSION));
                        for (SimpleEntry<Integer, Rectangle2D> bodyEntry : bodyList) {
                            Rectangle2D bodyLocal = bodyEntry.getValue();
                            if (head.getValue().getY() == bodyLocal.getY()) {
                                bodyLocal = new Rectangle2D.Double(head.getValue().getX() - FOOD_DIMENSION, y, FOOD_DIMENSION, FOOD_DIMENSION);
                                bodyEntry = new SimpleEntry<Integer, Rectangle2D>(RIGHT, bodyLocal);
                            }
                            bodyListAux.add(bodyEntry);
                            g2.setPaint(Color.WHITE);
                            g2.fill(bodyLocal);
                        }
                        bodyList = bodyListAux;
                        System.out.println(bodyList.size());
                        lastBody = bodyList.get(bodyList.size() - 1).getValue();
                        if (tail.getValue().getY() == lastBody.getY()) {
                            tail = new SimpleEntry<Integer, Rectangle2D>(UP,
                                    new Rectangle2D.Double(lastBody.getX() - FOOD_DIMENSION, lastBody.getY(), FOOD_DIMENSION, FOOD_DIMENSION));
                        } else if (bodyList.get(bodyList.size() - 1).getKey() == UP) {
                            tail = new SimpleEntry<Integer, Rectangle2D>(UP,
                                    new Rectangle2D.Double(lastBody.getX(), lastBody.getMaxY(), FOOD_DIMENSION, FOOD_DIMENSION));
                        } else if (bodyList.get(bodyList.size() - 1).getKey() == RIGHT) {
                            tail = new SimpleEntry<Integer, Rectangle2D>(UP,
                                    new Rectangle2D.Double(lastBody.getX(), lastBody.getMaxY(), FOOD_DIMENSION, FOOD_DIMENSION));
                        }
                        condition = head.getValue().getMaxX() < ANCHO_MAX_PANEL;
                        break;
                    case LEFT:
                        System.out.println("LEFT");
                        g2.clearRect((int) tail.getValue().getX(), (int) tail.getValue().getY(), FOOD_DIMENSION, FOOD_DIMENSION);
                        head = new SimpleEntry<Integer, Rectangle2D>(UP,
                                new Rectangle2D.Double(x - FOOD_DIMENSION, y, FOOD_DIMENSION, FOOD_DIMENSION));
                        // SimpleEntry<Integer, Rectangle2D> body = new SimpleEntry<Integer, Rectangle2D>(UP, new
                        // Rectangle2D.Double(head.getValue().getMaxX(), head.getValue().getY(), FOOD_DIMENSION, FOOD_DIMENSION));
                        // tail = new SimpleEntry<Integer, Rectangle2D>(UP, new Rectangle2D.Double(body.getValue().getMaxX(), body.getValue().getY(),
                        // FOOD_DIMENSION, FOOD_DIMENSION));
                        x -= FOOD_DIMENSION;
                        condition = head.getValue().getX() > 0;
                        break;

                    default:
                        break;
                    }
                    g2.setPaint(Color.WHITE);
                    g2.fill(head.getValue());
                    g2.fill(tail.getValue());
                    Toolkit.getDefaultToolkit().sync();
                    Thread.sleep(TIME_SLEEP);
                } while (condition && goOn);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.showGame(g);
    }
}
