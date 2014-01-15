package edu.umich.med.conceptgen.analysis;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author Vasudeva Mahavisno
 * @email vmahavis@gmail.com
 * @version 1.0 Jan 15, 2014
 */

public class DataValidation
{
	private String species;
	private String threshold; 
	private String outputType; 
	private String[] conceptType;
	private String[] geneIdList;
	
	public DataValidation (String species, String[] conceptType, String threshold, String outputType, String[] geneIdList)
	{
		this.species = species;
		this.threshold = threshold;
		this.outputType = outputType;
		this.conceptType = conceptType;
		this.geneIdList = geneIdList;
		
		boolean valid = true;
		
		// START VALIDATION --------------------------------------------------------------------------------------------------------------//
		
		if(!validateSpecies())
		{
			valid = false;
		}
		
		if(!validateThreshold())
		{
			valid = false;
		}
		
		
	}
	
	private boolean validateSpecies()
	{
		if(species.equals("Human") || species.equals("Mouse") || species.equals("Rat"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private boolean validateThreshold()
	{
		return NumberUtils.isNumber(threshold);
	}
	
	private boolean validateOutputType()
	{
		if(outputType.equals("Human") || outputType.equals("Mouse"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
}
