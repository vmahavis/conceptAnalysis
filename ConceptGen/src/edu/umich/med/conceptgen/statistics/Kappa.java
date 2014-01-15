package edu.umich.med.conceptgen.statistics;

public class Kappa
{

	public double execute(int a, int b, int c, int d)
	{
		
		double t = a + b + c + d;
		
		double s= ((a+c)*(a+b)+(b+d)*(c+d))/(Math.pow(t, 2));

		double kappa = (((a+d)/t)-s)/(1-s);
		
		return kappa;
	}
	
}
