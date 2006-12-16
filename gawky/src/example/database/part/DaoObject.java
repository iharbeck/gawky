package example.database.part;

import gawky.database.DB;
import gawky.database.part.Column;
import gawky.database.part.Table;
import gawky.global.Option;
import gawky.message.part.Desc;

import java.sql.Connection;

public class DaoObject extends Table {

	//Record definition
	public Desc[] getDesc()
	{
//		setDialect(new MySQL());
//		setDescID(0, IDGenerator.ID_AUTO());                    // MYSQL auto column
		
	  //setDescID(0);  // Manual set ID default first Column!
      //setDescID(Table.NO_ID); // no ID
	  //setDescID(0, IDGenerator.ID_SEQUENCE("mymy.nextval"));  // ORACLE
		
		setDescID(0);
		
		return new Desc[]  {
		
			new Column("kunde_id"),
			new Column("name")
		};
	}

	public String getTableName() {
		return "kunde";
	}
	
	public static void main(String[] args) throws Exception 
	{
		Option.init();
		Connection conn = DB.getConnection();
		
		DaoObject daoobject = new DaoObject();
		
		daoobject.setName("HELO");
		daoobject.setKunde_id("45");
	//	daoobject.insert(conn);
		
		daoobject.find(conn, "45");
		
		try {
			//daoobject.delete(conn);
		} catch (Exception e) {}
		
		//daoobject.setKunde_id("16");
//		daoobject.setName("INGO");
//		daoobject.insert(conn);
		
		daoobject.setName("INGO2");
		daoobject.update(conn);
		
		daoobject.queryToStream(conn, "WHERE 1=1", System.out);
		
		DB.doClose(conn);
		
		System.exit(0);
	}
	
	
	String kunde_id;
	String name;
	
	public String getKunde_id() {
		return kunde_id;
	}

	public void setKunde_id(String kunde_id) {
		this.kunde_id = kunde_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
