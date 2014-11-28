package gawky.database.part;

import java.sql.SQLException;

public class NoPrimaryColumnException extends SQLException
{
	private static final long serialVersionUID = 1L;

	public NoPrimaryColumnException(Table table)
	{
		super("No primary key defined for table [" + table.getTableName() + "]");
	}
}
