package gawky.database.jdbcclient;

import java.sql.ResultSet;

public interface JdbcSelectMapper {
	public Object selectmapper(ResultSet rset) throws Exception;
}
