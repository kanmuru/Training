package cl.emora.test;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

public class MySnake {

    private static final int TIME_SLEEP = 200;
    private static final int ANCHO_MAX_FRAME = 800;
    private static final int ALTO_MAX_FRAME = 600;
    private static final int ANCHO_MAX_CANVAS = 500;
    private static final int ALTO_MAX_CANVAS = 500;
    private static final int UP = 1;
    private static final int DOWN = 2;
    private static final int RIGHT = 3;
    private static final int LEFT = 4;
    private static final int FOOD_DIMENSION = 10;
    private static final int INITIAL_LARGE = 30;
    private static final int INITIAL_X = 250;
    private static final int INITIAL_Y = 400;
    private Rectangle2D head;
    private Rectangle2D body;
    private Rectangle2D tail;
    private Rectangle2D food;
    private SimpleEntry<Integer, Rectangle2D> head2;
    private ArrayList<SimpleEntry<Integer, Rectangle2D>> body2;
    private Frame myFrame;
    private Panel myPanel;
    private Label myLabel;
    private Button myButton;
    private Dialog myDialog;
    private Window myWindow;
    private MyCanvas myCanvas;
    private int large;
    private boolean goOn = true;

    public MySnake() {
        myFrame = new Frame();
        myFrame.setLayout(new BorderLayout());
        myPanel = new Panel();
        myLabel = new Label("Label", Label.CENTER);
        myButton = new Button("Button");
        myWindow = new Window(myFrame);
        myDialog = new Dialog(myFrame);
        myCanvas = new MyCanvas();

        large = INITIAL_LARGE;

        myFrame.setSize(ANCHO_MAX_FRAME, ALTO_MAX_FRAME);

        // myPanel.setSize(ANCHO_MAX_FRAME - 10, ALTO_MAX_FRAME - 10);

        myCanvas.setSize(ANCHO_MAX_CANVAS, ALTO_MAX_CANVAS);

        // myDialog.add(myLabel);
        // myPanel.add(myLabel);
        // myPanel.add(myButton);
        myPanel.add(myCanvas);
        myFrame.add(myPanel);

        myCanvas.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // int xPoint = 0 + (int)(Math.random() * (ANCHO_MAX_FRAME - ANCHO_MAX_CANVAS));
                // int YPoint = 0 + (int)(Math.random() * (ALTO_MAX_FRAME - ALTO_MAX_CANVAS));
                // System.out.println("(x, y): (" + xPoint + ", " + YPoint + ")");
                // myCanvas.setLocation(new Point(xPoint, YPoint));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

        });

        myButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub

            }
        });

        myFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });

        myCanvas.addKeyListener(new KeyListener() {

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
                // Graphics2D g2 = (Graphics2D) myCanvas.getGraphics();
                switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT:
                    goOn = false;
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
        });
    }

    public static void main(String[] args) {
        MySnake mySnake = new MySnake();
        mySnake.show();
    }

    public void show() {
        myFrame.setVisible(true);
        Graphics2D g2 = (Graphics2D) myCanvas.getGraphics();
        g2.setPaint(Color.WHITE);
        head = new Rectangle2D.Double(INITIAL_X, INITIAL_Y, FOOD_DIMENSION, FOOD_DIMENSION);
        body = new Rectangle2D.Double(head.getX(), head.getMaxY(), FOOD_DIMENSION, FOOD_DIMENSION);
        tail = new Rectangle2D.Double(body.getX(), body.getMaxY(), FOOD_DIMENSION, FOOD_DIMENSION);
        food = new Rectangle2D.Double(10, 10, FOOD_DIMENSION, FOOD_DIMENSION);
        g2.fill(food);
        g2.fill(head);
        g2.fill(body);
        g2.fill(tail);
        moveTo(UP);
        changeTailForHead();
        moveTo(DOWN);
        // moveTo(RIGHT);
        // changeTailForHead();
        // moveTo(LEFT);
    }

    private void changeTailForHead() {
        Rectangle2D auxHead = head;
        head = tail;
        tail = auxHead;
    }

    private void moveTo(int way) {
        goOn = true;
        int x = (int) head.getX();
        int y = (int) head.getY();
        boolean condition = false;
        try {
            do {
                Graphics2D g2 = (Graphics2D) myCanvas.getGraphics();
                switch (way) {
                case UP:
                    System.out.println("UP");
                    g2.clearRect((int) tail.getX(), (int) tail.getY(), FOOD_DIMENSION, FOOD_DIMENSION);
                    head = new Rectangle2D.Double(x, y - FOOD_DIMENSION, FOOD_DIMENSION, FOOD_DIMENSION);
                    head2 = new SimpleEntry<Integer, Rectangle2D>(UP, head);
                    body = new Rectangle2D.Double(head.getX(), head.getMaxY(), FOOD_DIMENSION, FOOD_DIMENSION);
                    tail = new Rectangle2D.Double(body.getX(), body.getMaxY(), FOOD_DIMENSION, FOOD_DIMENSION);
                    y -= FOOD_DIMENSION;
                    condition = head.getY() > 0;
                    break;
                case DOWN:
                    g2.clearRect((int) tail.getX(), (int) tail.getY(), FOOD_DIMENSION, FOOD_DIMENSION);
                    head = new Rectangle2D.Double(x, y + FOOD_DIMENSION, FOOD_DIMENSION, FOOD_DIMENSION);
                    body = new Rectangle2D.Double(head.getX(), head.getY() - FOOD_DIMENSION, FOOD_DIMENSION, FOOD_DIMENSION);
                    tail = new Rectangle2D.Double(body.getX(), body.getY() - FOOD_DIMENSION, FOOD_DIMENSION, FOOD_DIMENSION);
                    y += FOOD_DIMENSION;
                    condition = head.getMaxY() < ALTO_MAX_CANVAS;
                    break;
                case RIGHT:
                    g2.clearRect((int) tail.getX(), (int) tail.getY(), FOOD_DIMENSION, FOOD_DIMENSION);
                    head = new Rectangle2D.Double(x + FOOD_DIMENSION, y, FOOD_DIMENSION, FOOD_DIMENSION);
                    if (head.getY() == body.getY()) {
                        body = new Rectangle2D.Double(head.getX() - FOOD_DIMENSION, y, FOOD_DIMENSION, FOOD_DIMENSION);
                        if (tail.getY() == body.getY()) {
                            tail = new Rectangle2D.Double(body.getX() - FOOD_DIMENSION, body.getY(), FOOD_DIMENSION, FOOD_DIMENSION);
                        }
                    }
                    x += FOOD_DIMENSION;
                    condition = head.getMaxX() < ANCHO_MAX_CANVAS;
                    break;
                case LEFT:
                    g2.clearRect((int) tail.getX(), (int) tail.getY(), FOOD_DIMENSION, FOOD_DIMENSION);
                    head = new Rectangle2D.Double(x - FOOD_DIMENSION, y, FOOD_DIMENSION, FOOD_DIMENSION);
                    body = new Rectangle2D.Double(head.getMaxX(), head.getY(), FOOD_DIMENSION, FOOD_DIMENSION);
                    tail = new Rectangle2D.Double(body.getMaxX(), body.getY(), FOOD_DIMENSION, FOOD_DIMENSION);
                    x -= FOOD_DIMENSION;
                    condition = head.getX() > 0;
                    break;

                default:
                    break;
                }
                g2.setPaint(Color.WHITE);
                g2.fill(head);
                g2.fill(body);
                g2.fill(tail);
                if (!goOn) {
                    return;
                }
                Thread.sleep(TIME_SLEEP);
            } while (condition && goOn);
            return;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
