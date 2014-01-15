package edu.umich.med.conceptgen.util;

import java.util.ArrayList;

/**
 * @author Vasudeva Mahavisno vmahavis@gmail.com
 * @version Gene Annotation Data Pipeline 1.0 Sep 21, 2009
 */

public class TextParser
{
	public static String[] parseCommaDelimitedText(String text)
	{
		return text.split(",");
	}
	
	
	public String createCommaDelimitedText(ArrayList text)
	{
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < text.size(); i++)
		{
			if (i != 0)
			{
				temp.append(", " + text.get(i).toString());
			}
			else
			{
				temp.append(text.get(i).toString());
			}
		}
		return temp.toString();
	}
	
	public String removeSpecialChars(String text, String[] regex, String replacement)
	{
		for (int i = 0; i < regex.length; i++)
		{
			text = text.replaceAll(regex[i], replacement);
		}

		return text;
	}

	public String oracleSQLPrepare(String text)
	{
		text = text.replaceAll("\'", "\''");
		return text;
	}

}
