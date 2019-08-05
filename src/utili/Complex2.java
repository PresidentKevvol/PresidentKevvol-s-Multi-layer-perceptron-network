package utili;

public class Complex2
{
	double re;
	double im;
	
	public Complex2(double r, double i)
	{
		re = r;
		im = i;
	}
	
	public Complex2 squareNew()
	{
		return new Complex2(this.re*this.re-this.im*this.im, 2*this.re*this.im);
	}
	
	public double mag()
	{
		return Math.hypot(re, im);
	}
	
	public void add(Complex2 c)
	{
		this.re += c.re;
		this.im += c.im;
	}
	
	public Complex2 timesReal(double r)
	{
		return new Complex2(this.re*r, this.im*r);
	}
	
	public double getReal() {return re;}
	public double getImag() {return im;}
}
