package fractalset;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.swing.JPanel;

public class FractalSetPanel extends JPanel {

    private static final int BITMAP_WIDTH = 512;
    private static final int BITMAP_HEIGHT = 512;
    private double scale;
    private double centerX;
    private double centerY;
    private boolean isMandelbrot = true;
    private boolean showDebug = true;
    private boolean showCenter = true;
    private boolean showAxes = false;
    private String lastAction = "";
    private int[][] colors;
    private int iterations;
    
    private BufferedImage image;
    
    public FractalSetPanel() {
        this.setBackground(Color.CYAN);
        int imageType = BufferedImage.TYPE_INT_RGB;
        int w = BITMAP_WIDTH;
        int h = BITMAP_HEIGHT;
        this.image = new BufferedImage( w, h, imageType );
        this.scale = 4.0;
        this.centerX = 0;
        this.centerY = 0;
        this.iterations = 64;
        
        this.setUpColors();
    } // FractalSetPanel()

    public void setUpColors() {
        this.colors = new int[this.iterations][3];
        
        for( int i = 0; i < this.iterations; i++ ) {
            double fraction = ((double) i) / this.iterations - 1;
//            int red = (int) ((1 - fraction) * r0 + fraction * r1);
//            int green = (int) ((1 - fraction) * g0 + fraction * g1);
//            int blue = (int) ((1 - fraction) * b0 + fraction * b1);

            Color garbage = new Color(Color.HSBtoRGB((float) fraction, 1.0f, 1.0f));
            
            this.colors[i][0] = garbage.getRed(); //red;
            this.colors[i][1] = garbage.getGreen(); //green;
            this.colors[i][2] = garbage.getBlue(); //blue;            
        } // for
    }
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
        
        Color startColor = Color.RED;
        int r0 = startColor.getRed();
        int g0 = startColor.getGreen();
        int b0 = startColor.getBlue();
        
        Color endColor = Color.BLUE;
        int r1 = endColor.getRed();
        int g1 = endColor.getGreen();
        int b1 = endColor.getBlue();
        

        
        int [] blue = { 0, 0, 255 };
        int [] black = { 0, 0, 0 };
        
        double xMin = 0;
        double xMax = BITMAP_WIDTH - 1;
        double yMin = 0;
        double yMax = BITMAP_HEIGHT - 1;
        
        double uMin =  this.centerX - this.scale / 2;
        double uMax =  this.centerX + this.scale / 2;
        double vMin =  this.centerY - this.scale / 2;
        double vMax =  this.centerY + this.scale / 2;
        
        for( int row = 0; row < BITMAP_HEIGHT; row++ ) {
            double y = row;
            for( int column = 0; column < BITMAP_WIDTH; column++ ) {
                double x = column;
                
                double u = uMin + (uMax - uMin) * (x - xMin)/(xMax - xMin);
                double v = vMin + (vMax - vMin) * (y - yMin)/(yMax - yMin);
                
                Complex z = new Complex( 0.0, 0.0 );
                Complex c = new Complex( u, v );
                
                int count = 0;
                
                if (this.isMandelbrot) {
                    //Implementation of Mandelbrot set
                    while( z.magnitudeSquared() < 4.0 && count < this.iterations ) {
                        // z = z^2 + c
                        z = z.multiply(z);
                        z = z.add(c);
                        count++;
                    } // while
                } else {
                   // Implementation of Burning ship fractal
                    while (z.magnitudeSquared() < 4.0 && count < this.iterations) {
                       // next z = (|Re(z)| + |Im(z)|i)^2 + c
                       Complex newZ = new Complex();
                       newZ.setReal(z.getReal()*z.getReal() - z.getImaginary()*z.getImaginary() - c.getReal());
                       newZ.setImaginary(2*Math.abs(z.getReal()*z.getImaginary()) - c.getImaginary());
                       z = newZ;
                       count++;
                    }
                }
                
                if( count == this.iterations ) {
                    raster.setPixel(row, column, black );
                } // if
                else {
                    raster.setPixel(row, column, this.colors[count] );
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
        
        g2D.setColor(Color.WHITE);
        
        //unused, too lazy to implement this
        if (this.showAxes) {
            double realZero = 15.0;
            double imaginaryZero = 15.0;
            
            g2D.draw(new Line2D.Double(0.0, imaginaryZero, 720.0, imaginaryZero));
            g2D.draw(new Line2D.Double(realZero, 0.0, realZero, 691.0));
        }
        
        
        if (this.showCenter) {
            g2D.draw(new Line2D.Double(0.0, 691.0 / 2, 720.0, 691.0 / 2));
            g2D.draw(new Line2D.Double(720.0 / 2, 0.0, 720.0 / 2, 720.0));
        }
        
        if (this.showDebug) {
            g2D.drawString(this.isMandelbrot ? "Mandelbrot Set" : "Burning Ship", 5, 15);
            g2D.drawString("Center: " + new Complex(this.centerX, this.centerY), 5, 30);
            g2D.drawString("Scale: " + this.scale, 5, 45);
            g2D.drawString(this.iterations + " iteration" + (this.iterations == 1 ? "" : "s"), 5, 60);
            g2D.drawString(this.lastAction, 5, 75);
            g2D.drawString("Press (m) for help (See System.out)", 5, 691-10);
        }

    } // paintComponent( Graphics )
    
    public void zoomIn() {
        double newScale = this.scale * 0.95;
        this.scale = newScale <= 0.0 ? 0.1 : newScale; 
        this.lastAction = "Zoom in";
        this.repaint();
    }
    
    public void zoomOut() {
        this.scale *= 1/0.95;
        this.lastAction = "Zoom out";
        this.repaint();
    }
    
    public void panUp() {
        this.centerX -= this.scale / 50;
        this.lastAction = "Pan down real axis";
        this.repaint();
    }
    
    public void panDown() {
        this.centerX += this.scale / 50;
        this.lastAction = "Pan up real axis";
        this.repaint();
    }
    
    public void panLeft() {
        this.centerY -= this.scale / 50;
        this.lastAction = "Pan down complex axis";
        this.repaint();
    }
    
    public void panRight() {
        this.centerY += this.scale / 50;
        this.lastAction = "Pan up complex axis";
        this.repaint();
    }
    
    public void incrementIterations() {
        this.iterations += 1;
        this.setUpColors();
        this.lastAction = "Iterations increased to " + this.iterations;
        this.repaint();
    }
    
    public void decrementIterations() {
        this.iterations = Math.max(this.iterations - 1, 1);
        this.setUpColors();
        this.lastAction = "Decreased iterations to " + this.iterations;
        this.repaint();
    }
    
    public void toggleMandelbrot() {
        this.isMandelbrot = !this.isMandelbrot;
        this.lastAction = "Switched to " + (this.isMandelbrot ? "Mandelbrot" : "Burning Ship") + " set";
        this.repaint();
    }
    
    public void toggleDebug() {
        this.showDebug = !this.showDebug;
        this.lastAction = "Debug toggled " + (this.showDebug ? "on" : "off");
        this.repaint();
    }
    
    public void toggleCenter() {
        this.showCenter = !this.showCenter;
        this.lastAction = "Centerlines toggled " + (this.showCenter ? "on" : "off");
        this.repaint();
    }
    
    public void goHome() {
        this.centerX = 0.0;
        this.centerY = 0.0;
        this.scale = 4.0;
        this.iterations = 64;
        this.lastAction = "Rehomed";
        this.repaint();
    }
    
    public void printCenter() {
        this.lastAction = "Current centerpoint printed to System.out";
        System.out.println("Current center: \n  " + new Complex(this.centerX, this.centerY));
        this.repaint();
    }
    
    public void showHelp() {
        this.lastAction = "Help printed to System.out";
        
        System.out.println("""
            FractalSet Version 1.0
                Authors: Leon Tabak and Mac Coleman
                Date: 12/8/2021
                           
            ┄┄┄┄┄┄┄┄┄┄┄┄      
            
            HELP:
                    This program displays two fractals, the Mandelbrot set and
                    the Burning ship set. Both fractals are drawn by iterating
                    the complex function 64 times by default.
                           
            ┄┄┄┄┄┄┄┄┄┄┄┄
                              
            KEYBINDINGS:
                        
                M
                    Display this help dialog
                           
                W, K, Up Arrow
                    Pan the camera up (Along the negative real numbers)
                           
                S, J, Down Arrow
                    Pan the camera down (Along the positive real numbers)
                           
                A, H, Left Arrow
                    Pan the camera left (Along the negative imaginary numbers)
                           
                           
                D, L, Right Arrow 
                    Pan the camera right (Along the positive imaginary numbers)
                           
                + (Plus key)
                    Zoom in
                           
                - (Minus key)
                    Zoom out
                           
                { (Opening brace)
                    Decreases the amount of iterations done when drawing the
                    fractal
                           
                } (Closing brace)
                    Increases the amount of iterations done when drawing the
                    fractal
                           
                Z
                    Switch between drawing the Mandelbrot and Burning Ship set.
            
                X
                    Toggle debug information display
                          
                C
                    Toggle centerline display
                           
                B
                    Return home (Resets scaling, centerpoint of drawing, and
                    iterations made)
                
                Q
                    Print the centerpoint to System.out
                          
            """);
    }
} // FractalSetPanel
