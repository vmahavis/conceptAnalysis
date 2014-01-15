package edu.umich.med.conceptgen.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.FormParam;

import edu.umich.med.conceptgen.util.TextParser;

/**
 * @author Vasudeva Mahavisno
 * @email vmahavis@gmail.com
 */

@Path("/analysis")
public class EnrichmentService
{
	
	@POST
	public void run(@FormParam("s") String species, @FormParam("ct") String ct, @FormParam("t") String threshold,
			@FormParam("ot") String outputType, @FormParam("geneid") String geneid)
	{
		
		String[] conceptType = TextParser.parseCommaDelimitedText(ct);
		String[] geneIdList= TextParser.parseCommaDelimitedText(geneid);
		
		
	
	}
	

}
