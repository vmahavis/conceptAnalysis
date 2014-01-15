package edu.umich.med.conceptgen.analysis;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;
import java.util.ArrayList;

import edu.umich.med.conceptgen.datasource.QueryExecuter;
import edu.umich.med.conceptgen.statistics.FisherExactTest;
import edu.umich.med.conceptgen.statistics.Kappa;
import edu.umich.med.conceptgen.util.TextParser;

public class PrivateConceptEngine
{
	// Variables Definition
	// ***********************************************************************************************************************************************
	private static ResourceBundle sql = ResourceBundle.getBundle("org.ncibi.conceptGen.resource.bundle.privateConceptSQL");
	private static ResourceBundle dbParam = ResourceBundle.getBundle("org.ncibi.conceptGen.resource.bundle.database");
	private static QueryExecuter db = new QueryExecuter(dbParam.getString("url"), dbParam.getString("driver"), dbParam.getString("usernamePrivate"),
			dbParam.getString("passwdPrivate"));
	private  FisherExactTest fisherExactTest = new FisherExactTest();
	private  Kappa kp = new Kappa();
	private  HashMap conceptDictionaryList = new HashMap();
	private  TextParser txtParser = new TextParser();

	public void analyze(String conceptId, ArrayList conceptTypeList)
	{
		try
		{
			System.out.println("Initalizing");
			String conceptTypeFilter = txtParser.createCommaDelimitedText(conceptTypeList);
			System.out.println("Store Data for : " + conceptId);
			storeData(conceptTypeFilter);
			System.out.println("Analyze Data");
			updateData(conceptId, conceptTypeFilter);
			System.out.println("Run Update");
			runUpdate(conceptId);		
			System.out.println("Analysis Completed");
		} 
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	

	public void deleteConcept() throws SQLException
	{
		Vector deletedConceptID = getDeletedConceptID();

		for (int i = 0; i < deletedConceptID.size(); i++)
		{
			removeConceptId((String) deletedConceptID.get(i));
		}
	}

	private  Vector getDeletedConceptID() throws SQLException
	{
		return  db.getData(sql.getString("selectDeletedConcepts"));
	}
	
	private  String getOwner(String conceptId) throws SQLException
	{
		return db.selectSingleValue(sql.getString("selectOwner").replaceFirst("\\?", conceptId));
	}

	private  void fisher(String conceptId, HashMap conceptDictionary, HashMap a, HashMap g, HashMap e, HashMap i, boolean isPrivate) throws SQLException
	{

		String compareConceptId = "";
		String compareDictionaryId = "";
		String query = sql.getString("insertFisherStats");
		
		// Local variable declaration
		// *******************************************************************************************************************************************

		int a0 = 0;
		int g0 = 0;
		int e0 = 0;
		int i0 = 0;
		int b = 0;
		int c = 0;
		int d = 0;

		int bz = 0;
		int cz = 0;

		double ax = 0;
		double dx = 0;
		double cx = 0;
		double bx = 0;

		double oddsRatio = 0;
		double pValue = 0;
		double easeScore = 0;
		double kappa = 0;
		int aEase = 0;

		double[] val = new double[8];
		Vector values = new Vector();
		BigDecimal bd;

		// *******************************************************************************************************************************************

		Set set = a.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext())
		{
			Map.Entry m = (Map.Entry) it.next();
			compareConceptId = (String)m.getKey();
			compareDictionaryId = (String)conceptDictionary.get(compareConceptId);
			a0 = (int) Double.parseDouble((String) a.get(compareConceptId));
			
			if (a0 > 1)
			{
				g0 = (int) Double.parseDouble((String) g.get(compareDictionaryId));
				e0 = (int) Double.parseDouble((String) e.get(compareConceptId));	
				i0 = (int) Double.parseDouble((String) i.get(compareDictionaryId));

				
				c = e0 - a0;
				b = g0 - a0;

				bz = b;
				cz = c;

				if (b < 1)
				{
					b = 1;
				}
				if (c < 1)
				{
					c = 1;
				}

				d = i0 - (a0 + b + c);

				if (d < 1)
				{
					d = 1;
				}

				ax = a0;
				dx = d;
				cx = c;
				bx = b;
				aEase = a0;

				oddsRatio = (ax * dx) / (bx * cx);
				bd = new BigDecimal(oddsRatio);
				bd = bd.setScale(2, BigDecimal.ROUND_UP);
				oddsRatio = bd.doubleValue();

				pValue = fisherExactTest.execute(a0, b, c, d);
				easeScore = fisherExactTest.execute((aEase - 1), b, c, d);
				kappa = kp.execute(a0, b, c, d);

				if (pValue < 1)
				{
					val = new double[10];
					val[0] = Double.parseDouble(conceptId);
					val[1] = Double.parseDouble(compareConceptId);
					val[2] = a0;
					val[3] = bz;
					val[4] = cz;
					val[5] = dx;
					val[6] = oddsRatio;
					val[7] = pValue;
					val[8] = easeScore;
					val[9] = kappa;
					values.add(val);
					

					if(isPrivate)
					{
						if(!conceptId.equals(compareConceptId))
						{
							val = new double[10];
							val[0] = Double.parseDouble(compareConceptId);
							val[1] = Double.parseDouble(conceptId);
							val[2] = a0;
							val[3] = bz;
							val[4] = cz;
							val[5] = dx;
							val[6] = oddsRatio;
							val[7] = pValue;
							val[8] = easeScore;
							val[9] = kappa;
							values.add(val);
						}
					}	
			    }	
			}
		}

		db.batchExecQuery(query, values);

	}

	private  void updateData(String conceptId, String conceptTypeFilter) throws SQLException
	{
		String dictionaryId = db.selectSingleValue(sql.getString("selectDictionaryId").replaceFirst("\\?", conceptId));
		String owner = getOwner(conceptId);
		HashMap conceptPrivateDictionaryList = db.hashResult(sql.getString("selectAllPrivateConceptDictionaryId").replaceFirst("\\?", owner));		
		
		// *******************************************************************************************************************************************
		// PUBLIC DATASOURCE
		
		String query = sql.getString("calculateA");
		query = query.replaceFirst("\\?", conceptId);
		query = query.replaceFirst("\\?", conceptTypeFilter);
		
		HashMap a = db.hashResult(query);
		HashMap g = db.hashResult(sql.getString("calculateG").replaceFirst("\\?", conceptId));
		HashMap e = db.hashResult(sql.getString("calculateE").replaceFirst("\\?", dictionaryId));
		HashMap i = db.hashResult(sql.getString("calculateI").replaceFirst("\\?", dictionaryId));

		fisher(conceptId, conceptDictionaryList, a, g, e, i, false);
		
		// *******************************************************************************************************************************************
		// PRIVATE DATASOURCE
		
		String query1 = sql.getString("calculatePrivateA");
		query1 = query1.replaceFirst("\\?", conceptId);
		query1 = query1.replaceFirst("\\?", owner);
		
		String query2 = sql.getString("calculatePrivateG");
		query2 = query2.replaceFirst("\\?", conceptId);
		query2 = query2.replaceFirst("\\?", owner);
		
		String query3 = sql.getString("calculatePrivateE");
		query3 = query3.replaceFirst("\\?", dictionaryId);
		query3 = query3.replaceFirst("\\?", owner);
		
		String query4 = sql.getString("calculatePrivateI");
		query4 = query4.replaceFirst("\\?", dictionaryId);
		query4 = query4.replaceFirst("\\?", owner);	
			
		HashMap _a = db.hashResult(query1);
		HashMap _g = db.hashResult(query2);
		HashMap _e = db.hashResult(query3);
		HashMap _i = db.hashResult(query4);
	
		fisher(conceptId, conceptPrivateDictionaryList, _a, _g, _e, _i, true);	
	}
	
	private  void storeData(String conceptTypeFilter) throws SQLException
	{
		String query = sql.getString("selectAllPublicConceptDictionaryId");
		query = query.replaceFirst("\\?", conceptTypeFilter);
		conceptDictionaryList = db.hashResult(query);		
	}
	
	private  void runUpdate(String conceptID) throws SQLException
	{
		String query = sql.getString("updateSAS");
		query = query.replaceAll("\\?", conceptID);
		db.execQuery(query);
		
		query = sql.getString("updateConceptElementSize");
		query = query.replaceAll("\\?", conceptID);
		db.execQuery(query);
	}
	
	private  void removeConceptId(String conceptId) throws SQLException
	{
		String query = sql.getString("deleteSAS");
		query = query.replaceAll("\\?", conceptId);
		db.execQuery(query);
		
		query = sql.getString("deleteConceptSet");
		query = query.replaceAll("\\?", conceptId);
		db.execQuery(query);
		
		query = sql.getString("deleteConcept");
		query = query.replaceAll("\\?", conceptId);
		db.execQuery(query);
	}
}
