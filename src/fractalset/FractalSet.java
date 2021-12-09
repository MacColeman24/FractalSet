package fractalset;

import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

public class FractalSet extends JFrame {

    private static final int FRACTAL_WIDTH = 720;
    private static final int FRACTAL_HEIGHT = 720;
    private static final String FRACTAL_TITLE = "Fractal";
    private static final ImageIcon img = new ImageIcon("src/icon.png");
    private FractalSetPanel panel;

    public FractalSet() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FRACTAL_WIDTH, FRACTAL_HEIGHT);
        this.setTitle(FRACTAL_TITLE);
        this.setIconImage(img.getImage());

        Container pane = this.getContentPane();

        this.panel = new FractalSetPanel();
        pane.add(this.panel);
        this.setVisible(true);
    } // FractalSet()

    public static void main(String[] args) {
        FractalSet fractalSet = new FractalSet();
        
        fractalSet.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                
                // System.out.println("Key " + e.getKeyChar() + " pressed.");
                // System.out.println("Keycode: " + e.getKeyCode());
                
                switch (e.getKeyCode()) {
                    case 45: fractalSet.panel.zoomOut(); break;  // -
                    case 61: fractalSet.panel.zoomIn(); break;   // +
                    case 65: fractalSet.panel.panLeft(); break;  // a
                    case 72: fractalSet.panel.panLeft(); break;  // h
                    case 37: fractalSet.panel.panLeft(); break;  // Left Arrow
                    case 68: fractalSet.panel.panRight(); break; // d
                    case 76: fractalSet.panel.panRight(); break; // l
                    case 39: fractalSet.panel.panRight(); break; // Right Arrow
                    case 83: fractalSet.panel.panDown(); break;  // s
                    case 74: fractalSet.panel.panDown(); break;  // j
                    case 40: fractalSet.panel.panDown(); break;  // Down Arrow
                    case 87: fractalSet.panel.panUp(); break;    // w
                    case 75: fractalSet.panel.panUp(); break;    // k
                    case 38: fractalSet.panel.panUp(); break;    // Up Arrow
                    case 66: fractalSet.panel.goHome(); break;           // b
                    case 67: fractalSet.panel.toggleCenter(); break;     // c
                    case 77: fractalSet.panel.showHelp(); break;         // m
                    case 81: fractalSet.panel.printCenter(); break;      // q
                    case 88: fractalSet.panel.toggleDebug(); break;      // x
                    case 90: fractalSet.panel.toggleMandelbrot(); break; // z
                }
            };
            
            @Override
            public void keyReleased(KeyEvent e) {
            
            };
            
            @Override
            public void keyTyped(KeyEvent e) {
            
            };
        });
    } // main( String [] )

} // FractalSet
