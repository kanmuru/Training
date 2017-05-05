package cl.emora.test;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TestFrame extends Frame  {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public TestFrame() {
        add(new TestPanel());
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
                TestFrame testFrame = new TestFrame();
                System.out.println("Hola");
                testFrame.setVisible(true);
            }
        });
    }


}
