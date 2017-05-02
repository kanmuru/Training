package cl.emora.test;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TestSynchronized {

    private static final int ANCHO_MAX_FRAME = 800;
    private static final int ALTO_MAX_FRAME = 600;
    private static final int ANCHO_MAX_CANVAS = 500;
    private static final int ALTO_MAX_CANVAS = 500;
    private final Object keyPressMonitor = new Object();

    private boolean running = true;
    private Frame myFrame;
    private Panel myPanel;
    private MyCanvas myCanvas;

    private Runnable gameLoop = new Runnable() {
        @Override
        public void run() {
            try {
                synchronized (keyPressMonitor) {
                    while (true) {
                        while (!running) {
                            System.out.println("do nothing!");
                            keyPressMonitor.wait();
                        }
                        //System.out.println("do something!");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public TestSynchronized() {
        myFrame = new Frame();
        myFrame.setLayout(new BorderLayout());
        myPanel = new Panel();
        myCanvas = new MyCanvas();
        
        myFrame.setSize(ANCHO_MAX_FRAME, ALTO_MAX_FRAME);


        myCanvas.setSize(ANCHO_MAX_CANVAS, ALTO_MAX_CANVAS);
        myPanel.add(myCanvas);
        myFrame.add(myPanel);
        
        myCanvas.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == 39) {
                    synchronized (keyPressMonitor) {
                        running = true;
                        keyPressMonitor.notifyAll();
                    }
                }
            }
            
            public void keyReleased(KeyEvent event) {
                if (event.getKeyCode() == 39) {
                    System.out.println("keypress 39");
                    synchronized (keyPressMonitor) {
                        running = false;
                        keyPressMonitor.notifyAll();
                    }
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }
            
        });
    }
    
    public static void main(String[] args) {
        TestSynchronized testSynchronized = new TestSynchronized();
        testSynchronized.myFrame.setVisible(true);
//        testSynchronized.gameLoop.run();
    }

}
