package cl.emora.test;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RunSnake extends Frame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    

    /**
     * @throws HeadlessException
     */
    public RunSnake() throws HeadlessException {
        add(new MySnake());
        setResizable(false);
        pack();
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });
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
