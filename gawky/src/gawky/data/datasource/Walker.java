package gawky.data.datasource;

import java.util.ArrayList;

public class Walker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ArrayList rows = new ArrayList();
		
		rows.add(new String[] {
				"1erster",
				"1zweiter",
				"1dritter"});
		rows.add(new String[] {
				"2erster",
				"2zweiter",
				"2dritter"});
		rows.add(new String[] {
				"3erster",
				"3zweiter",
				"3dritter"});
		
		
		ArrayListDatasource ds = new ArrayListDatasource(rows, new Column[]{
				
				new Column("1", Column.TYPE_STRING),
				new Column("2", Column.TYPE_STRING),
				new Column("3", Column.TYPE_STRING)
				
		});
		
		
		for(int i=0; i < ds.getColumns(); i++)
		{
			System.out.println(ds.getHead(i));
		}
		
		while(ds.nextRow())
		{
			for(int i=0; i < ds.getColumns(); i++)
			{
				System.out.print(ds.getValue(i));
			}
			System.out.print("\n");
		}
	}

}
