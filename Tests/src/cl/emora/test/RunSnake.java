package cl.emora.test;

import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;

public class RunSnake extends JFrame {

    /**
     * Serial.
     */
    private static final long serialVersionUID = 1L;
    

    /**
     * @throws HeadlessException
     */
    public RunSnake() throws HeadlessException {
        add(new MySnake());
        setResizable(false);
        pack();
        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                RunSnake runSnake = new RunSnake();
                runSnake.setVisible(true);
            }
        });
    }
}
