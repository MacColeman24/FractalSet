package fractalset;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.swing.JPanel;

public class FractalSetPanel extends JPanel {

    private static final int BITMAP_WIDTH = 512;
    private static final int BITMAP_HEIGHT = 512;
    private static double scale;
    
    private BufferedImage image;
    
    public FractalSetPanel() {
        this.setBackground(Color.CYAN);
        int imageType = BufferedImage.TYPE_INT_RGB;
        int w = BITMAP_WIDTH;
        int h = BITMAP_HEIGHT;
        this.image = new BufferedImage( w, h, imageType );
    } // FractalSetPanel()

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        int w = this.getWidth();
        int h = this.getHeight();

        AffineTransform scale = new AffineTransform();
        scale.setToScale( ((double) w)/BITMAP_WIDTH, 
                ((double) h)/ BITMAP_HEIGHT);

        WritableRaster raster = this.image.getRaster();
        
        int [][] palette = new int[64][3];
        
        Color startColor = Color.RED;
        int r0 = startColor.getRed();
        int g0 = startColor.getGreen();
        int b0 = startColor.getBlue();
        
        Color endColor = Color.BLUE;
        int r1 = endColor.getRed();
        int g1 = endColor.getGreen();
        int b1 = endColor.getBlue();
        
        for( int i = 0; i < 64; i++ ) {
//            double fraction = ((double) i) / 63;
//            int red = (int) ((1 - fraction) * r0 + fraction * r1);
//            int green = (int) ((1 - fraction) * g0 + fraction * g1);
//            int blue = (int) ((1 - fraction) * b0 + fraction * b1);
            
            palette[i][0] = (int) (256 * Math.random()); //red;
            palette[i][1] = (int) (256 * Math.random()); //green;
            palette[i][2] = (int) (256 * Math.random()); //blue;            
        } // for
        
        int [] blue = { 0, 0, 255 };
        int [] yellow = { 255, 255, 0 };
        
        double xMin = 0;
        double xMax = BITMAP_WIDTH - 1;
        double yMin = 0;
        double yMax = BITMAP_HEIGHT - 1;
        
        double uMin =  -2.0;
        double uMax =  2.0;
        double vMin =  -2.0;
        double vMax =  2.0;
        
        for( int row = 0; row < BITMAP_HEIGHT; row++ ) {
            double y = row;
            for( int column = 0; column < BITMAP_WIDTH; column++ ) {
                double x = column;
                
                double u = uMin + (uMax - uMin) * (x - xMin)/(xMax - xMin);
                double v = vMin + (vMax - vMin) * (y - yMin)/(yMax - yMin);
                
                Complex z = new Complex( 0.0, 0.0 );
                Complex c = new Complex( u, v );
                
                int count = 0;
               
                while( z.magnitudeSquared() < 4.0 && count < 64 ) {
                    // z = z^2 + c
                    z = z.multiply(z);
                    z = z.add(c);
                    count++;
                } // while
                
                if( count == 64 ) {
                    raster.setPixel( row, column, yellow );
                } // if
                else {
                    raster.setPixel( row, column, palette[count] );
                } // else
                
//                if( row < column ) {
//                    raster.setPixel( row, column, yellow );
//                } // if
//                else {
//                    raster.setPixel( row, column, blue);
//                } // else
            } // for
        } // for
        
        g2D.drawImage( image, scale, this);
    } // paintComponent( Graphics )

} // FractalSetPanel
