package utili;

public class Utili
{
	public static int bytevalue(byte b)
	{
		if(b >= 0)
		{
			return b;
		}
		else
		{
			return b + 256;
		}
	}
	
	public static int littleEndian(byte[] b, int start, int fin)
	{
		int result = 0;
		for(int ct = start, radix = 1; ct < fin; ct++, radix*=256)
		{
			result += bytevalue(b[ct]) * radix;
		}
		return result;
	}
	
	public static int[] fourier(int[] a)
	{
		return a;
	}
	
	public static Complex[] fft(Complex[] x) {
        int n = x.length;

        // base case
        if (n == 1) return new Complex[] { x[0] };

        // radix 2 Cooley-Tukey FFT
        if (n % 2 != 0) { throw new RuntimeException("n is not a power of 2"); }

        // fft of even terms
        Complex[] even = new Complex[n/2];
        for (int k = 0; k < n/2; k++) {
            even[k] = x[2*k];
        }
        Complex[] q = fft(even);

        // fft of odd terms
        Complex[] odd  = even;  // reuse the array
        for (int k = 0; k < n/2; k++) {
            odd[k] = x[2*k + 1];
        }
        Complex[] r = fft(odd);

        // combine
        Complex[] y = new Complex[n];
        for (int k = 0; k < n/2; k++) {
            double kth = -2 * k * Math.PI / n;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k]       = q[k].plus(wk.times(r[k]));
            y[k + n/2] = q[k].minus(wk.times(r[k]));
        }
        return y;
    }
}
