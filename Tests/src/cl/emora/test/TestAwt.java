package cl.emora.test;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TestAwt {

    private static final int ANCHO_MAX_FRAME = 800;
    private static final int ALTO_MAX_FRAME = 600;
    private static final int ANCHO_MAX_CANVAS = 50;
    private static final int ALTO_MAX_CANVAS = 50;
    private Frame myFrame;
    private Panel myPanel;
    private Label myLabel;
    private Button myButton;
    private Dialog myDialog;
    private Window myWindow;
    private Canvas myCanvas;

    public TestAwt() {
        myFrame = new Frame();
        myFrame.setLayout(new BorderLayout());
        myPanel = new Panel();
        myLabel = new Label("Label", Label.CENTER);
        myButton = new Button("Button");
        myWindow = new Window(myFrame);
        myDialog = new Dialog(myFrame);
        myCanvas = new Canvas();
        
        myPanel.setSize(ANCHO_MAX_FRAME, ALTO_MAX_FRAME);        
        
        myCanvas.setBackground(Color.BLACK);
        myCanvas.setLocation(new Point(0, 0));
        myCanvas.setSize(ANCHO_MAX_CANVAS, ALTO_MAX_CANVAS);

        // myDialog.add(myLabel);
        myPanel.add(myCanvas);
        myPanel.add(myLabel);
        myPanel.add(myButton);
        myFrame.add(myPanel);

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
    }

    public static void main(String[] args) {
        TestAwt testAwt = new TestAwt();
        testAwt.show();
    }

    public void show() {
        myFrame.setBackground(Color.GRAY);
        myFrame.setSize(ANCHO_MAX_FRAME, ALTO_MAX_FRAME);
        myFrame.setVisible(true);
    }

}
