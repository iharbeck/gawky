package example.database;


import gawky.database.DB;
import gawky.database.dialect.MySQL;
import gawky.database.generator.IDGenerator;
import gawky.database.part.Table;
import gawky.global.Option;
import gawky.message.part.Desc;
import gawky.message.part.DescV;

import java.sql.Connection;

public class DaoObject extends Table {

	//Record definition
	public Desc[] getDesc() {
		
		// ### SET ID ###
		
		//setDescID(0); // Manual set ID
		//setDescID(0, IDGenerator.ID_SEQUENCE("mymy.nextval"));  // ORACLE
		setDescID(0, IDGenerator.ID_AUTO());                    // MYSQL auto column
		
		setDialect(new MySQL());
		
		return new Desc[]  {
			new DescV("kunde_id"),
			new DescV("name")
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
		daoobject.insert(conn);
		
		daoobject.find(conn, 30);
		
		try {
			//daoobject.delete(conn);
		} catch (Exception e) {}
		
		//daoobject.setKunde_id("16");
		daoobject.setName("INGO");
		daoobject.insert(conn);
		
		daoobject.setName("INGO2");
		daoobject.update(conn);
		
		conn.close();
		
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
