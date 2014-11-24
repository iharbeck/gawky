package example.database.part;

import gawky.database.DB;
import gawky.database.generator.Generator;
import gawky.database.part.Column;
import gawky.database.part.Table;
import gawky.global.Init;
import gawky.global.Option;
import gawky.message.part.Desc;

import java.sql.Connection;
import java.util.ArrayList;

public class KundeDAO extends Table {

	//Record definition
	public Desc[] getDesc()
	{
		return new Desc[]  {
			new Column("kunde_id").setPrimary(),
			new Column("name")
		};
	}

	public String getTableName() {
		return "kunde";
	}

	
	public static void main(String[] args) throws Exception 
	{
		long start = System.currentTimeMillis();
		
		Init.initLib();
		Option.init();
		Connection conn = DB.getConnection();
	
		conn.setAutoCommit(false);

		KundeDAO daoobject = new KundeDAO();
		daoobject.loop_init(conn);
		
		for(int i=1; i < 10000; i++)
		{
			
			daoobject.find(i);
			//daoobject.setKunde_id("1");
			//daoobject.insert(conn);
		
			System.out.println(daoobject.getName());
		}
		
		System.out.println("ins");
		
		
		conn.commit();
		
		System.out.println("done");
		
//		daoobject.queryToStream(conn, "WHERE 1=1", System.out);
		
		DB.doClose(conn);
		
		System.out.println(System.currentTimeMillis() - start);
		
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
