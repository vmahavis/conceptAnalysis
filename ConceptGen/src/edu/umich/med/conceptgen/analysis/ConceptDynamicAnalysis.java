package edu.umich.med.conceptgen.analysis;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

import edu.umich.med.conceptgen.datasource.QueryExecuter;

public class ConceptDynamicAnalysis {

	private ResourceBundle sql = ResourceBundle.getBundle("edu.umich.edu.conceptGen.resource.bundle.sql");
	private PrivateConceptEngine engine = new PrivateConceptEngine();
	private ArrayList<String> conceptTypeFilter = new ArrayList<String>();
	private String dictionaryId = "322";
	private String conceptTypeId = "33";
	private String conceptId = "0";
	
	private QueryExecuter db = new QueryExecuter();
	
	public ConceptDynamicAnalysis (String conceptName, String dictionaryName, ArrayList<String> elementList, ArrayList<String> dictionaryList, String owner) throws SQLException
	{
		conceptTypeFilter.add("0");

	    db.execQuery(sql.getString("truncateElementTmp"));
	    db.execQuery(sql.getString("truncateDictionaryTmp"));
	    
	    insertElementList(elementList);
	    insertDictionaryList(dictionaryList);
	    	    
	    //SELECT DICTIONARYID***************************************************************************************************************
        
        String query = sql.getString("getNewPrivateDictionaryId");
    	ArrayList<String> list = db.selectSingleList(query); 
    	dictionaryId = list.get(0);
    	
    	//INSERT DICTIONARY*****************************************************************************************************************
    	
    	query = sql.getString("insertPrivateDictionary");
    	query = query.replaceFirst("\\?", dictionaryId);
    	query = query.replaceFirst("\\?", conceptName);
    	query = query.replaceFirst("\\?", owner);   
   		db.execQuery(query);
   		
   		//INSERT DICTIONARY_SET*************************************************************************************************************
   		
      	query = sql.getString("insertPrivateDictionarySetGeneId");
      	query = query.replaceFirst("\\?", dictionaryId);
   		db.execQuery(query);
   		
   		conceptId = dataSetup(conceptName, conceptTypeId, owner, String.valueOf(elementList.size()));
	    engine.analyze(conceptId, conceptTypeFilter);
	}
	
	
	private String dataSetup(String conceptName, String conceptTypeId, String owner, String elementSize) throws SQLException
	{
		//SELECT CONCEPTID******************************************************************************************************************
	    
	    String query = sql.getString("getNewConceptId");
	    ArrayList<String> list = db.selectSingleList(query); 
	    String conceptId = list.get(0);  

	    //INSERT CONCEPT********************************************************************************************************************
	    
	    query = sql.getString("insertConcept");
	    query = query.replaceFirst("\\?", conceptId);
	    query = query.replaceFirst("\\?", conceptName);
	    query = query.replaceFirst("\\?", conceptTypeId);
	    query = query.replaceFirst("\\?", owner);   
	    query = query.replaceFirst("\\?", dictionaryId);
	    query = query.replaceFirst("\\?", elementSize);
	    
	    db.execQuery(query);
	       
	    query = sql.getString("insertConceptSetGeneId");
	    query = query.replaceFirst("\\?", conceptId);	
	    db.execQuery(query);
	      
	    query = sql.getString("insertStatisticsStatus");
	    query = query.replaceFirst("\\?", conceptId);
	    db.execQuery(query);
	    
		db.execQuery(sql.getString("truncateElementTmp"));
		db.execQuery(sql.getString("truncateDictionaryTmp"));
		
		return conceptId;   
	}
	
	
	private void insertElementList(ArrayList<String> elementList) throws SQLException
	{
		Vector<String> distinctElements = new Vector<String>();
		Vector<String> queryList = new Vector<String>();
		String tmp = "";
		String query = "";
		
		for (int i = 0; i < elementList.size(); i++)
		{
			tmp = (String)elementList.get(i).trim();
			if (!(tmp == null || "".equals(tmp.trim())))
			{
				if (!distinctElements.contains(tmp))
				{
					query = sql.getString("insertElementTmp");
					query = query.replaceFirst("\\?", tmp);
					queryList.add(query);
					distinctElements.add(tmp);				
				}
			}
		}
		
		db.batchExecQuery(queryList);
	}
	
	private void insertDictionaryList(ArrayList<String> dictionaryList) throws SQLException
	{
		Vector<String> distinctElements = new Vector<String>();
		Vector<String> queryList = new Vector<String>();
		String tmp = "";
		String query = "";

		for (int i = 0; i < dictionaryList.size(); i++)
		{
			tmp = (String)dictionaryList.get(i).trim();
			if (!(tmp == null || "".equals(tmp.trim())))
			{
				if (!distinctElements.contains(tmp))
				{
					query = sql.getString("insertDictionaryTmp");
					query = query.replaceFirst("\\?", tmp);
					queryList.add(query);
					distinctElements.add(tmp);
				}
			}
		}
		db.batchExecQuery(queryList);
	}
	
}
