package edu.umich.med.conceptgen.statistics;

import java.util.Arrays;
import java.util.Vector;

public class FisherExactTest
{

	public double execute(int a, int b, int c, int d)
	{
		double ptot = 0;
		double prob = 0;

		ptot = calculate(a, b, c, d);
		double prevProb = ptot;

		while (!((b < 0) && (c < 0)))
		{

			a++;
			d++;
			b--;
			c--;

			prob = (prevProb * (b + 1) * (c + 1)) / (a * d);
			prevProb = prob;
			ptot += prob;

		}
		return ptot;
	}

	public double calculate(int a, int b, int c, int d)
	{

		// Variable Declaration
		// ***************************************************************************************************************

		Vector numlist = new Vector();
		Vector denlist = new Vector();
		double p3 = 1;

		int count = 0;
		int e100 = 0;
		int e = a + b;
		int f = c + d;
		int g = a + c;
		int h = b + d;
		int i = e + f;

		int[] bot =
		{ a, b, c, d };
		int[] top =
		{ e, f, g, h };

		// Sort array
		// ***************************************************************************************************************

		Arrays.sort(bot);
		Arrays.sort(top);

		// Create num array
		// ***************************************************************************************************************

		for (int x = (bot[1] + 1); x <= top[0]; x++)
		{
			numlist.add(String.valueOf(x));
		}

		for (int x = (bot[2] + 1); x <= top[1]; x++)
		{
			numlist.add(String.valueOf(x));
		}

		for (int x = (bot[3] + 1); x <= top[2]; x++)
		{
			numlist.add(String.valueOf(x));
		}

		int[] num = new int[numlist.size()];

		for (int x = 0; x < numlist.size(); x++)
		{
			num[x] = Integer.parseInt((String) numlist.get(x));
		}

		// Create den array
		// ***************************************************************************************************************

		for (int x = 1; x <= bot[0]; x++)
		{
			denlist.add(Integer.toString(x));
		}

		for (int x = (top[3] + 1); x <= i; x++)
		{
			denlist.add(Integer.toString(x));
		}

		int[] den = new int[denlist.size()];

		for (int x = 0; x < denlist.size(); x++)
		{
			den[x] = Integer.parseInt((String) denlist.get(x));

		}

		// Sort array
		// ***************************************************************************************************************

		Arrays.sort(num);
		Arrays.sort(den);

		for (int j = 0; j < num.length; j++)
		{

			int den2 = den[count];
			p3 *= (double) num[j] / den2;

			if (p3 > 1e+100)
			{
				p3 /= 1e+100;
				e100++;
			}

			if (p3 < 1e-100)
			{
				if (e100 > 0)
				{
					p3 *= 1e+100;
					e100--;
				}
			}

			if (p3 < 1e-100)
			{
				p3 = 1e-100;
				break;
			}
			count++;
		}
		return p3;
	}
}
