package edu.umich.med.conceptgen.datasource;

import java.sql.PreparedStatement;

public interface BatchExecSetter {

	boolean set(PreparedStatement stmt);

	boolean hasNext();

}
