package edu.umich.med.conceptgen.datasource;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Vector;

public class QueryExecuter
{

	public Vector distinct = new Vector();
	private String url;
	private String username;
	private String passwd;
	private String driver;
	
	public QueryExecuter()
	{
		ResourceBundle db = ResourceBundle.getBundle("org.ncibi.conceptGen.resource.bundle.database");
		this.url = db.getString("url");
		this.username = db.getString("username");
		this.passwd = db.getString("passwd");
		this.driver = db.getString("driver");
	}
	
	public QueryExecuter(String url, String driver, String username, String passwd)
	{
		this.url = url;
		this.username = username;
		this.passwd = passwd;
		this.driver = driver;
	}
	

	public String selectSingleValue(String query) throws SQLException
	{
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		String value = "";

		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, passwd);
			statement = connection.createStatement();
			result = statement.executeQuery(query);

			while (result.next())
			{
				value = result.getString(1);
			}
			result.close();
			result = null;
			statement.close();
			statement = null;

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			if (statement != null)
			{
				try
				{
					statement.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				statement = null;
			}
			if (connection != null)
			{
				try
				{
					connection.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				connection = null;
			}
		}
		return value;
	}

	
	public HashMap hashResult(String query) throws SQLException
	{

		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;

		String key = "";
		String val = "";

		HashMap hashedList = new HashMap();

		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, passwd);
			statement = connection.createStatement();
			result = statement.executeQuery(query);

			while (result.next())
			{
				key = String.valueOf(result.getInt(1));
				val = result.getString(2);
				hashedList.put(key, val);
			}

			result.close();
			result = null;
			statement.close();
			statement = null;

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			if (statement != null)
			{
				try
				{
					statement.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				statement = null;
			}
			if (connection != null)
			{
				try
				{
					connection.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				connection = null;
			}
		}

		return hashedList;
	}

	public HashMap dbHashing(String query) throws SQLException
	{

		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;

		String key = "";
		String val = "";

		HashMap hashedList = new HashMap();
		HashMap value = new HashMap();

		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, passwd);
			statement = connection.createStatement();
			result = statement.executeQuery(query);

			while (result.next())
			{
				key = result.getString(1);
				val = result.getString(2);

				if (hashedList.containsKey(key))
				{
					value = (HashMap) hashedList.get(key);
					value.put(val, val);
				}
				else
				{
					value = new HashMap();
					value.put(val, val);
					distinct.add(key);
				}
				hashedList.put(key, value);
			}

			result.close();
			result = null;
			statement.close();
			statement = null;

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			if (statement != null)
			{
				try
				{
					statement.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				statement = null;
			}
			if (connection != null)
			{
				try
				{
					connection.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				connection = null;
			}
		}

		return hashedList;
	}

	public HashMap vectorMap (String query) throws SQLException
	{

		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;

		String key = "";
		String val = "";

		HashMap hashedList = new HashMap();
		Vector value = new Vector();

		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, passwd);
			statement = connection.createStatement();
			result = statement.executeQuery(query);

			while (result.next())
			{
				key = result.getString(1);
				val = result.getString(2);

				if (hashedList.containsKey(key))
				{
					value = (Vector) hashedList.get(key);
					value.add(val);
				}
				else
				{
					value = new Vector();
					value.add(val);
					distinct.add(key);
				}
				hashedList.put(key, value);
			}

			result.close();
			result = null;
			statement.close();
			statement = null;

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			if (statement != null)
			{
				try
				{
					statement.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				statement = null;
			}
			if (connection != null)
			{
				try
				{
					connection.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				connection = null;
			}
		}

		return hashedList;
	}
	
	public HashMap dbDoubleHashing(String query) throws SQLException
	{

		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		String key = "";
		String val1 = "";
		String val2 = "";
		HashMap doubleHash = new HashMap();
		HashMap hashedList1 = new HashMap();
		HashMap hashedList2 = new HashMap();

		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, passwd);
			statement = connection.createStatement();
			result = statement.executeQuery(query);

			while (result.next())
			{
				key = result.getString(1);
				val1 = result.getString(2);
				val2 = result.getString(3);

				hashedList1.put(key, val1);
				hashedList2.put(key, val2);
			}

			result.close();
			result = null;
			statement.close();
			statement = null;

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			if (statement != null)
			{
				try
				{
					statement.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				statement = null;
			}
			if (connection != null)
			{
				try
				{
					connection.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				connection = null;
			}
		}
		doubleHash.put("1", hashedList1);
		doubleHash.put("2", hashedList2);

		return doubleHash;
	}

	public Vector getData(String query) throws SQLException
	{

		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		String val = "";
		Vector value = new Vector();

		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, passwd);
			statement = connection.createStatement();
			result = statement.executeQuery(query);

			while (result.next())
			{
				val = result.getString(1);
				value.add(val);
			}

			result.close();
			result = null;
			statement.close();
			statement = null;

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			if (statement != null)
			{
				try
				{
					statement.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				statement = null;
			}
			if (connection != null)
			{
				try
				{
					connection.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				connection = null;
			}
		}

		return value;
	}
	
	public Iterator<ResultSet> getIteratorFromQuery(String query)
	{
		Iterator<ResultSet> ret = null;
		Connection connection = null;
		Statement statement = null;

		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, passwd);
			statement = connection.createStatement();
			final ResultSet result = statement.executeQuery(query);

			ret = new Iterator(){
				
				Object nextObject = null;


				public boolean hasNext() {
					try {
						if (nextObject == null) nextObject = result.next();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					return nextObject != null;
				}

	
				public Object next() {
					if ((nextObject == null) && (!hasNext()))
						throw new NoSuchElementException();
					Object ret = nextObject;
					nextObject = null;
					return ret;					
				}

				public void remove() {
					throw new UnsupportedOperationException("This iteractor has no remove() method.");
				}
				
			};
			
			statement.close();
			statement = null;

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			if (statement != null)
			{
				try
				{
					statement.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				statement = null;
			}
			if (connection != null)
			{
				try
				{
					connection.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				connection = null;
			}
		}
		return ret;
	}

	public HashMap getValues(String query) throws SQLException
	{

		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		String val1 = "";
		String val2 = "";
		HashMap value = new HashMap();

		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, passwd);
			statement = connection.createStatement();
			result = statement.executeQuery(query);

			while (result.next())
			{

				val1 = result.getString(1);
				val2 = result.getString(2);
				value.put("1", val1);
				value.put("2", val2);

			}

			result.close();
			result = null;
			statement.close();
			statement = null;

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			if (statement != null)
			{
				try
				{
					statement.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				statement = null;
			}
			if (connection != null)
			{
				try
				{
					connection.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				connection = null;
			}
		}

		return value;
	}

	public boolean execQuery(String command) throws SQLException
	{
		boolean value = false;
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;

		try
		{

			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, passwd);
			statement = connection.createStatement();
			result = statement.executeQuery(command);
			value = true;

			result.close();
			result = null;
			statement.close();
			statement = null;
			connection.close();
			connection = null;
		}

		catch (Exception e)
		{
			System.out.println(e);
			value = false;
		}
		finally
		{

			if (result != null)
			{
				try
				{
					result.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				result = null;
			}
			if (statement != null)
			{
				try
				{
					statement.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				statement = null;
			}
			if (connection != null)
			{
				try
				{
					connection.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				connection = null;
			}
		}

		return value;
	}

	public boolean batchExecQuery(String command, Vector values) throws SQLException
	{

		boolean value = false;
		Connection connection = null;
		PreparedStatement stmt = null;
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, passwd);
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement(command);

			for (int i = 0; i < values.size(); i++)
			{
				double[] val = (double[]) values.get(i);
				for (int j = 0; j < val.length; j++)
				{
					stmt.setDouble((j + 1), val[j]);
				}

				stmt.addBatch();

			}

			stmt.executeBatch();
			value = true;

			connection.commit();
			stmt.close();
			stmt = null;
			connection.close();
			connection = null;
		}
		catch (BatchUpdateException b)
		{
			//b.printStackTrace();
			System.err.println("SQLException: " + b.getMessage());
			System.err.println("SQLState:  " + b.getSQLState());
			System.err.println("Message:  " + b.getMessage());
			System.err.println("Vendor:  " + b.getErrorCode());
			System.err.println("Update counts:  ");
			value = false;
			connection.rollback();
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			System.err.println(e);
			value = false;
			connection.rollback();
		}

		finally
		{

			if (stmt != null)
			{
				try
				{
					stmt.close();
				}
				catch (SQLException e)
				{
				}
				stmt = null;
			}
			if (connection != null)
			{
				try
				{
					connection.close();
				}
				catch (SQLException e)
				{
				}
				connection = null;
			}
		}

		return value;
	}
	
	public boolean batchExecQuery(Vector query) throws SQLException
	{

		boolean value = false;
		Connection connection = null;
		Statement stmt = null;
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, passwd);
			stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			connection.setAutoCommit(false);

			for (int i = 0; i < query.size(); i++)
			{
				stmt.addBatch((String) query.get(i));
			}

			stmt.executeBatch();
			value = true;

			connection.commit();
			stmt.close();
			stmt = null;
			connection.close();
			connection = null;
		}
		catch (BatchUpdateException b)
		{

			System.err.println("SQLException: " + b.getMessage());
			System.err.println("SQLState:  " + b.getSQLState());
			System.err.println("Message:  " + b.getMessage());
			System.err.println("Vendor:  " + b.getErrorCode());
			System.err.println("Update counts:  ");
			value = false;
			connection.rollback();
		}
		catch (Exception e)
		{
			System.out.println(e);
			value = false;
			connection.rollback();
		}

		finally
		{

			if (stmt != null)
			{
				try
				{
					stmt.close();
				}
				catch (SQLException e)
				{
				}
				stmt = null;
			}
			if (connection != null)
			{
				try
				{
					connection.close();
				}
				catch (SQLException e)
				{
				}
				connection = null;
			}
		}

		return value;
	}
	
	public boolean batchExecIntQuery(String command, Vector<int[]> values) throws SQLException
	{

		boolean value = false;
		Connection connection = null;
		PreparedStatement stmt = null;
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, passwd);
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement(command);

			for (int i = 0; i < values.size(); i++)
			{
				int[] val = values.get(i);
				for (int j = 0; j < val.length; j++)
				{
					stmt.setInt((j + 1), val[j]);
				}

				stmt.addBatch();

			}

			stmt.executeBatch();
			value = true;

			connection.commit();
			stmt.close();
			stmt = null;
			connection.close();
			connection = null;
		}
		catch (BatchUpdateException b)
		{

			System.err.println("SQLException: " + b.getMessage());
			System.err.println("SQLState:  " + b.getSQLState());
			System.err.println("Message:  " + b.getMessage());
			System.err.println("Vendor:  " + b.getErrorCode());
			System.err.println("Update counts:  ");
			value = false;
			connection.rollback();
		}
		catch (Exception e)
		{
			System.out.println(e);
			value = false;
			connection.rollback();
		}

		finally
		{

			if (stmt != null)
			{
				try
				{
					stmt.close();
				}
				catch (SQLException e)
				{
				}
				stmt = null;
			}
			if (connection != null)
			{
				try
				{
					connection.close();
				}
				catch (SQLException e)
				{
				}
				connection = null;
			}
		}

		return value;
	}
	
	public boolean batchExecStringQuery(String command, Vector<String[]> values) throws SQLException
	{

		boolean value = false;
		Connection connection = null;
		PreparedStatement stmt = null;
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, passwd);
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement(command);

			for (int i = 0; i < values.size(); i++)
			{
				String[] val = values.get(i);
				for (int j = 0; j < val.length; j++)
				{
					stmt.setString((j + 1), val[j]);
				}

				stmt.addBatch();
			}

			stmt.executeBatch();
			value = true;

			connection.commit();
			stmt.close();
			stmt = null;
			connection.close();
			connection = null;
		}
		catch (BatchUpdateException b)
		{

			System.err.println("SQLException: " + b.getMessage());
			System.err.println("SQLState:  " + b.getSQLState());
			System.err.println("Message:  " + b.getMessage());
			System.err.println("Vendor:  " + b.getErrorCode());
			System.err.println("Update counts:  ");
			value = false;
			connection.rollback();
		}
		catch (Exception e)
		{
			System.out.println(e);
			value = false;
			connection.rollback();
		}

		finally
		{

			if (stmt != null)
			{
				try
				{
					stmt.close();
				}
				catch (SQLException e)
				{
				}
				stmt = null;
			}
			if (connection != null)
			{
				try
				{
					connection.close();
				}
				catch (SQLException e)
				{
				}
				connection = null;
			}
		}

		return value;
	}
	
	
	public boolean batchExecGenericQuery(String command, BatchExecSetter setter) throws SQLException
	{

		boolean value = false;
		Connection connection = null;
		PreparedStatement stmt = null;
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, passwd);
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement(command);

			while (setter.hasNext()){
				if (!setter.set(stmt))
					throw new SQLException("Generic Batch Expression Setter failed.");
				stmt.addBatch();
			}

			stmt.executeBatch();
			value = true;

			connection.commit();
			stmt.close();
			stmt = null;
			connection.close();
			connection = null;
		}
		catch (BatchUpdateException b)
		{

			System.err.println("SQLException: " + b.getMessage());
			System.err.println("SQLState:  " + b.getSQLState());
			System.err.println("Message:  " + b.getMessage());
			System.err.println("Vendor:  " + b.getErrorCode());
			System.err.println("Update counts:  ");
			value = false;
			connection.rollback();
		}
		catch (Exception e)
		{
			System.out.println(e);
			value = false;
			connection.rollback();
		}

		finally
		{

			if (stmt != null)
			{
				try
				{
					stmt.close();
				}
				catch (SQLException e)
				{
				}
				stmt = null;
			}
			if (connection != null)
			{
				try
				{
					connection.close();
				}
				catch (SQLException e)
				{
				}
				connection = null;
			}
		}

		return value;
	}
	
	public boolean iterateOverReadOnly(String query, ResultSetHandler rsh) throws SQLException
	{
		boolean good = true;
		Connection connection = null;
		Statement statement = null;
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, passwd);
			statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);

			int index = 0;
			boolean more=true;
			while (good && (more = result.next()))
			{
				good &= rsh.processRow(++index, result);
			}
			if (!more) good = true;
			result.close();
			result = null;
			statement.close();
			statement = null;
		}
		catch (Exception e)
		{
			System.out.println(e);
			good = false;
		}
		finally
		{
			if (statement != null)
			{
				try
				{
					statement.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				statement = null;
			}
			if (connection != null)
			{
				try
				{
					connection.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				connection = null;
			}
		}

		return good;
	}
	
	public ArrayList selectSingleList(String query) throws SQLException
	{

		ArrayList resultList = new ArrayList();
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		String value = "";

		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, passwd);
			statement = connection.createStatement();
			result = statement.executeQuery(query);

			while (result.next())
			{
				value = result.getString(1);
				resultList.add(value);
			}
			result.close();
			result = null;
			statement.close();
			statement = null;
			connection.close();
			connection = null;

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{

			if (result != null)
			{
				try
				{
					result.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				result = null;
			}
			if (statement != null)
			{
				try
				{
					statement.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				statement = null;
			}
			if (connection != null)
			{
				try
				{
					connection.close();
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
				connection = null;
			}

		}
		return resultList;
	}


	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(url, username, passwd);
		return connection;
	}
}
