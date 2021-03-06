package cl.emora.test;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
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

public class TestAwt {

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
    private Frame myFrame;
    private Panel myPanel;
    private Label myLabel;
    private Button myButton;
    private Dialog myDialog;
    private Window myWindow;
    private MyCanvas myCanvas;
    private Rectangle2D snake;
    private Rectangle2D food;
    private int large;
    private boolean goOn = true;
    private Point head;
    private Point tail;

    public TestAwt() {
        myFrame = new Frame();
        myFrame.setLayout(new BorderLayout());
        myPanel = new Panel();
        myLabel = new Label("Label", Label.CENTER);
        myButton = new Button("Button");
        myWindow = new Window(myFrame);
        myDialog = new Dialog(myFrame);
        myCanvas = new MyCanvas();

        large = INITIAL_LARGE;
        head = new Point(INITIAL_X, INITIAL_Y);
        tail = new Point(INITIAL_X, INITIAL_Y + large);
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
                    /*
                     * g2.clearRect(250, 400, 20, 60); Rectangle2D.Double snakeBody = new Rectangle2D.Double(250, 400, FOOD_DIMENSION, FOOD_DIMENSION
                     * * 2); Rectangle2D.Double snakeHead = new Rectangle2D.Double(250 + FOOD_DIMENSION, 400, FOOD_DIMENSION, FOOD_DIMENSION);
                     * g2.setPaint(Color.WHITE); g2.fill(snakeHead); g2.fill(snakeBody);
                     */
                    break;
                case KeyEvent.VK_UP:
                    // avanzar(new Point(250, 400), 3);
                    break;

                default:
                    System.out.println("Una raya en el agua");
                    break;
                }
            }
        });
    }

    public static void main(String[] args) {
        TestAwt testAwt = new TestAwt();
        testAwt.show();
    }

    public void show() {
        myFrame.setBackground(Color.BLACK);
        myPanel.setBackground(Color.GRAY);
        myFrame.setVisible(true);
        Graphics2D g2 = (Graphics2D) myCanvas.getGraphics();
        food = new Rectangle2D.Double(10, 10, FOOD_DIMENSION, FOOD_DIMENSION);
        g2.setPaint(Color.WHITE);
        g2.fill(food);

        snake = new Rectangle2D.Double(250, 400, FOOD_DIMENSION, 30);
        // snake = new Rectangle2D.Double(100, 250, 30, FOOD_DIMENSION);
        g2.setPaint(Color.WHITE);
        g2.fill(snake);
        moveTo(UP);
        moveTo(DOWN);
        // moveTo(RIGHT);
        // moveTo(LEFT);
    }

    private void moveTo(int way) {
        int x = (int) snake.getX();
        int y = (int) snake.getY();
        boolean condition = false;
        try {
            do {
                goOn = true;
                Graphics2D g2 = (Graphics2D) myCanvas.getGraphics();
                switch (way) {
                case UP:
                    // large = (int) snake.getMaxY() - (int) snake.getMinY();
                    g2.clearRect(x, y + (large - FOOD_DIMENSION), FOOD_DIMENSION, FOOD_DIMENSION);
                    snake = new Rectangle2D.Double(x, y - FOOD_DIMENSION, FOOD_DIMENSION, large);
                    y -= FOOD_DIMENSION;
                    condition = y > 0;
                    break;
                case DOWN:
                    // large = (int) snake.getMaxY() - (int) snake.getMinY();
                    g2.clearRect(x, y, FOOD_DIMENSION, FOOD_DIMENSION);
                    snake = new Rectangle2D.Double(x, y + FOOD_DIMENSION, FOOD_DIMENSION, large);
                    y += FOOD_DIMENSION;
                    condition = (y + large) < ALTO_MAX_CANVAS;
                    break;
                case RIGHT:
                    // large = (int) snake.getMaxX() - (int) snake.getMinX();
                    g2.clearRect(tail.x, tail.y, FOOD_DIMENSION, FOOD_DIMENSION);
                    if (head.y == tail.y) {
                        g2.clearRect(x, y, FOOD_DIMENSION, FOOD_DIMENSION);
                        tail.setLocation(x, y - FOOD_DIMENSION);
                    } else {
                        g2.clearRect(x, y, FOOD_DIMENSION, FOOD_DIMENSION);
                    }
                    snake = new Rectangle2D.Double(x + FOOD_DIMENSION, y, large, FOOD_DIMENSION);
                    x += FOOD_DIMENSION;
                    head.setLocation(x, y);
                    condition = (x + large) < ANCHO_MAX_CANVAS;
                    break;
                case LEFT:
                    // large = (int) snake.getMaxX() - (int) snake.getMinX();
                    g2.clearRect(x + (large - FOOD_DIMENSION), y, FOOD_DIMENSION, FOOD_DIMENSION);
                    snake = new Rectangle2D.Double(x - FOOD_DIMENSION, y, large, FOOD_DIMENSION);
                    x -= FOOD_DIMENSION;
                    condition = x > 0;
                    break;

                default:
                    break;
                }
                g2.setPaint(Color.WHITE);
                g2.fill(snake);
                Thread.sleep(TIME_SLEEP);
            } while (condition && goOn);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
