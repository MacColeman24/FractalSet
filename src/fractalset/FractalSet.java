package fractalset;

import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

public class FractalSet extends JFrame {

    private static final int FRACTAL_WIDTH = 512;
    private static final int FRACTAL_HEIGHT = 512;
    private static final String FRACTAL_TITLE = "Fractal";
    private static final ImageIcon img = new ImageIcon("/home/mac/Downloads/EA8J7U1UcAIVdhD.jpg");
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
                System.out.println("Key " + e.getKeyChar() + " pressed.");
                fractalSet.panel.repaint();
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
