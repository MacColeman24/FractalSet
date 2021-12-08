package fractalset;


public class Complex {
    /*
     TO-DO: Define a class that models
     complex numbers.
    
     The class will have:
       instance variables
       a single constructor
       a method named add() for adding the complex number to
         another complex number 
       a method named multiply() for multiplying the
         complex number times another complex number
       a method named magnitudeSquared() for computing
         the square of the complex number's magnitude
    
     Here is the mathematics that you need to know:
    
       Let i be the square root of -1.
       What follows is mathematical notation.
       In your program there will be no variable
       named i and no variable that holds the square
       root of -1!
       The instance variables of the Complex class
       will both hold floating point values and
       will both have the type double.
    
       Let u be a complex number with a real part
       equal to a and an imaginary part equal to b.
       Both a and b are floating point values.
       u = (a + bi)
    
       Let v be a complex number with a real part
       equal to c and an imaginary part equal to d.
       Both c and d are floating point values.
       v = (c + di)
    
       Then the sum of the two complex numbers is:
    
       u + v = (a + c) + (b + d)i
    
       The sum is a complex number whose real part
       is (a + c) and whose imaginary part is (b + d).
    
       And the product of the two complex numbers is:
    
       u * v = (a + bi)(c + di)
             = ac + bi * di + adi + cbi
             = (ac - bd) + (ad + cb)i
    
       The product is a complex number whose real part
       is (ac - bd) and whose imaginary part is (ad + cb).
    
       The square of the magnitude of the complex number
       u = a + bi is a * a + b * b.
    */
    
    private double real;
    private double imaginary;
    
    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }
    
    public Complex() {
        this.real = 0;
        this.imaginary = 0;
    }
    
    public Complex add(Complex other) {
        return new Complex (
                this.real + other.real,
                this.imaginary + other.imaginary
            );
    }
    
    public Complex multiply(Complex other) {
        return new Complex (
                this.real * other.real - this.imaginary * other.imaginary,
                this.real * other.imaginary + other.real * this.imaginary
            );
    }
    
    public Complex multiply(double scalar) {
        return new Complex(
                this.real * scalar,
                this.imaginary * scalar
            );
    }
    
    public double getReal() {
        return this.real;
    }
    
    public double getImaginary() {
        return this.imaginary;
    }
    
    public double magnitudeSquared() {
        return this.real * this.real + this.imaginary * this.imaginary;
    }
    
    @Override
    public String toString() {
        return this.real + " + " + this.imaginary + "i";
    }
    
} // Complex
