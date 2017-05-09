package cl.emora.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TestPanel extends JPanel implements ActionListener {

    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    private final int DELAY = 140;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    public TestPanel() {
        System.out.println("[TestPanel][INI]");
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
        System.out.println("[TestPanel][FIN]");
    }

    private void loadImages() {
        System.out.println("[loadImages][INI]");

        ImageIcon iid = new ImageIcon("green.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("green.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("red.png");
        head = iih.getImage();
        System.out.println("[loadImages][FIN]");
    }

    private void initGame() {
        System.out.println("[initGame][INI]");

        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
        System.out.println("[initGame][FIN]");
    }

    @Override
    public void paintComponent(Graphics g) {
        System.out.println("[paintComponent][INI]");
        super.paintComponent(g);

        doDrawing(g);
        System.out.println("[paintComponent][FIN]");
    }

    private void doDrawing(Graphics g) {
        System.out.println("[doDrawing][INI]");

        if (inGame) {

            g.drawImage(apple, apple_x, apple_y, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
        }
        System.out.println("[doDrawing][FIN]");
    }

    private void gameOver(Graphics g) {
        System.out.println("[gameOver][INI]");
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
        System.out.println("[gameOver][FIN]");
    }

    private void checkApple() {
        System.out.println("[checkApple][INI]");

        if ((x[0] == apple_x) && (y[0] == apple_y)) {

            dots++;
            locateApple();
        }
        System.out.println("[checkApple][FIN]");
    }

    private void move() {
        System.out.println("[move][INI]");

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
        System.out.println("[move][FIN]");
    }

    private void checkCollision() {
        System.out.println("[checkCollision][INI]");

        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= B_HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= B_WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
        System.out.println("[checkCollision][FIN]");
    }

    private void locateApple() {
        System.out.println("[locateApple][INI]");
        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
        System.out.println("[locateApple][FIN]");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("[actionPerformed][INI]");

        if (inGame) {

            checkApple();
            checkCollision();
            move();
        }

        repaint();
        System.out.println("[actionPerformed][FIN]");
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("[keyPressed][INI]");

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
            System.out.println("[keyPressed][FIN]");
        }
    }
}