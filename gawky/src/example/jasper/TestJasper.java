package example.jasper;

import gawky.jasper.JRBeanUtilDataSource;

public class TestJasper 
{
	public static void main(String[] args) throws Exception {

		// Datenbean erstellen
		Sample bean = new Sample();
	
		bean.setOwner("Meiermann");
		
		bean.getPos().add(new Sample("user1"));
		bean.getPos().add(new Sample("user2"));
		bean.getPos().add(new Sample("user3"));
		bean.getPos().add(new Sample("user4"));

		// Datasource erstellen
		JRBeanUtilDataSource ds = new JRBeanUtilDataSource(bean, bean.pos.size());

		while(ds.next())
		{
			System.out.println( ds.getFieldValue("owner") );
			System.out.println( ds.getFieldValue("pos[x].name") );
		}
	}
}
