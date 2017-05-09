package cl.emora.test;

import java.awt.EventQueue;

import javax.swing.JFrame;


public class TestFrame extends JFrame {

    public TestFrame() {
        System.out.println("[TestFrame][INI]");

        add(new TestPanel());
        
        setResizable(false);
        pack();
        
        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println("[TestFrame][FIN]");
    }

    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {             
                System.out.println("[run][INI]");
                JFrame ex = new TestFrame();
                ex.setVisible(true);                
                System.out.println("[run][FIN]");
            }
        });
    }
}