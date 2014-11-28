package example;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.beanutils.PropertyUtils;

public class TEST
{

	ArrayList<HashMap> list = new ArrayList<HashMap>();

	public static void main(String[] args) throws Exception
	{

		TEST nnn = new TEST();

		HashMap val = new HashMap<String, String>();

		val.put("ERSTER", "2");

		nnn.getList().add(val);

		System.out.println(PropertyUtils.getProperty(nnn, "list[0].ERSTER"));
	}

	public ArrayList<HashMap> getList()
	{
		return list;
	}

	public void setList(ArrayList<HashMap> list)
	{
		this.list = list;
	}
}
