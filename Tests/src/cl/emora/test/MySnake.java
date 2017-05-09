package cl.emora.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
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
    private static final int ANCHO_MAX_PANEL = 300;
    private static final int ALTO_MAX_PANEL = 300;
    private static final int UP = 1;
    private static final int DOWN = 2;
    private static final int RIGHT = 3;
    private static final int LEFT = 4;
    private static final int PART_DIMENSION = 10;
    private static final int INITIAL_X = 150;
    private static final int INITIAL_Y = 250;
    private static final int INITIAL_BODY_LENGTH = 2;
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

        head = new SimpleEntry<Integer, Rectangle2D>(UP, new Rectangle2D.Double(INITIAL_X, INITIAL_Y, PART_DIMENSION, PART_DIMENSION));
        int i = 0;
        int x = (int) head.getValue().getX();
        int y = (int) head.getValue().getMaxY();
        while (i < INITIAL_BODY_LENGTH) {
            bodyList.add(
                    new SimpleEntry<Integer, Rectangle2D>(UP, new Rectangle2D.Double(x, y + (PART_DIMENSION * i), PART_DIMENSION, PART_DIMENSION)));
            i++;
        }
        locateFood();
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
                bodyListAux.add(head);
                bodyList.remove(bodyList.size() - 1);
                bodyListAux.addAll(bodyList);
                head = new SimpleEntry<Integer, Rectangle2D>(way, new Rectangle2D.Double(x, y - PART_DIMENSION, PART_DIMENSION, PART_DIMENSION));
                bodyList = bodyListAux;
                y -= PART_DIMENSION;
                break;
            case DOWN:
                bodyListAux.add(head);
                bodyList.remove(bodyList.size() - 1);
                bodyListAux.addAll(bodyList);
                head = new SimpleEntry<Integer, Rectangle2D>(way, new Rectangle2D.Double(x, y + PART_DIMENSION, PART_DIMENSION, PART_DIMENSION));
                bodyList = bodyListAux;
                y += PART_DIMENSION;
                break;
            case RIGHT:
                bodyListAux.add(head);
                bodyList.remove(bodyList.size() - 1);
                bodyListAux.addAll(bodyList);
                head = new SimpleEntry<Integer, Rectangle2D>(way, new Rectangle2D.Double(x + PART_DIMENSION, y, PART_DIMENSION, PART_DIMENSION));
                bodyList = bodyListAux;
                x += PART_DIMENSION;
                break;
            case LEFT:
                bodyListAux.add(head);
                bodyList.remove(bodyList.size() - 1);
                bodyListAux.addAll(bodyList);
                head = new SimpleEntry<Integer, Rectangle2D>(way, new Rectangle2D.Double(x - PART_DIMENSION, y, PART_DIMENSION, PART_DIMENSION));
                bodyList = bodyListAux;
                x -= PART_DIMENSION;
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
        if (goOn) {
            checkRules();
            moveTo(0);
        }
        repaint();
    }

    private void checkRules() {
        if ((head.getValue().getX() == 0 && head.getKey() == LEFT) || (head.getValue().getMaxX() == ANCHO_MAX_PANEL && head.getKey() == RIGHT)
                || (head.getValue().getY() == 0 && head.getKey() == UP) || (head.getValue().getMaxY() == ALTO_MAX_PANEL && head.getKey() == DOWN)) {
            goOn = false;
        }
        for (SimpleEntry<Integer, Rectangle2D> bodyEntry : bodyList) {
            if (head.getKey() == UP && bodyEntry.getKey() != DOWN && bodyEntry.getValue().getMaxY() == head.getValue().getY()
                    || head.getKey() == DOWN && bodyEntry.getKey() != UP && bodyEntry.getValue().getY() == head.getValue().getMaxY()
                    || head.getKey() == RIGHT && bodyEntry.getKey() != LEFT && bodyEntry.getValue().getX() == head.getValue().getMaxX()
                    || head.getKey() == LEFT && bodyEntry.getKey() != RIGHT && bodyEntry.getValue().getMaxX() == head.getValue().getX()) {
                goOn = false;
                break;
            }
        }
        if ((head.getValue().getX() == food.getMaxX() && head.getValue().getY() == food.getY() && head.getKey() == LEFT)
                || (head.getValue().getMaxX() == food.getX() && head.getValue().getY() == food.getY() && head.getKey() == RIGHT)
                || (head.getValue().getY() == food.getMaxY() && head.getValue().getX() == food.getX() && head.getKey() == UP)
                || (head.getValue().getMaxY() == food.getY() && head.getValue().getX() == food.getX() && head.getKey() == DOWN)) {
            bodyList.add(0, head);
            head = new SimpleEntry<Integer, Rectangle2D>(head.getKey(), food);
            locateFood();
        }
    }

    private void locateFood() {
        int x = 0;
        int y = 0;
        boolean ok = true;
        do {
            int randomPositionX = (ANCHO_MAX_PANEL - PART_DIMENSION) / PART_DIMENSION;
            int randomPositionY = (ANCHO_MAX_PANEL - PART_DIMENSION) / PART_DIMENSION;
            x = (int) (Math.random() * randomPositionX);
            y = (int) (Math.random() * randomPositionY);
            for (SimpleEntry<Integer, Rectangle2D> bodyEntry : bodyList) {
                if (((x * PART_DIMENSION) == head.getValue().getX() && (y * PART_DIMENSION) == head.getValue().getY())
                        || ((x * PART_DIMENSION) == bodyEntry.getValue().getX() && (y * PART_DIMENSION) == bodyEntry.getValue().getY())
                        || (food != null && (x * PART_DIMENSION) == food.getX() && (y * PART_DIMENSION) == food.getY())) {
                    break;
                } else {
                    ok = false;
                }
            }
        } while (ok);
        food = new Rectangle2D.Double(x * PART_DIMENSION, y * PART_DIMENSION, PART_DIMENSION, PART_DIMENSION);
    }

    private void gameOver(Graphics2D g2) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);
        g2.setColor(Color.white);
        g2.setFont(small);
        g2.drawString(msg, (ANCHO_MAX_PANEL - metr.stringWidth(msg)) / 2, ALTO_MAX_PANEL / 2);
        timer.stop();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                if (head.getKey() != LEFT) {
                    moveTo(RIGHT);
                }
                break;
            case KeyEvent.VK_UP:
                if (head.getKey() != DOWN) {
                    moveTo(UP);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (head.getKey() != UP) {
                    moveTo(DOWN);
                }
                break;
            case KeyEvent.VK_LEFT:
                if (head.getKey() != RIGHT) {
                    moveTo(LEFT);
                }
                break;
            case KeyEvent.VK_SPACE:
                if (timer.isRunning()) {
                    timer.stop();
                } else {
                    timer.start();
                }
                break;
            default:
                break;
            }
        }
    }

    private void drawSnake(Graphics2D g2) {
        g2.setPaint(Color.WHITE);
        g2.draw(head.getValue());
        g2.fill(food);
        for (SimpleEntry<Integer, Rectangle2D> bodyEntry : bodyList) {
            g2.draw(bodyEntry.getValue());
        }
        if (!goOn) {
            gameOver(g2);
        }
        Toolkit.getDefaultToolkit().sync();
    }
}
