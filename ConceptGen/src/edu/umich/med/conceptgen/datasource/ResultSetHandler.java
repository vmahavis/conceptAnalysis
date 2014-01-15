package edu.umich.med.conceptgen.datasource;

import java.sql.ResultSet;

public interface ResultSetHandler {

	/**
	 * Used by QueryExecuter method
	 * @param index
	 * @param rs
	 * @return
	 * @see QueryExecuter
	 */
	public boolean processRow(int index, ResultSet rs);
}
