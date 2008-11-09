package gawky.jasper;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;


public class EchoDataSource implements JRDataSource
{
	private int index = -1;
	private int size = 10;

	public EchoDataSource(int size)
	{
		this.size = size;
	}

	public EchoDataSource()
	{
	}

	public boolean next() throws JRException
	{
		index++;

		return (index < size);
	}

	public Object getFieldValue(JRField field) throws JRException
	{
		String fieldName = field.getName();
		
		try {
			if(field.getValueClassName().equals("java.lang.String"))
				return fieldName;
			if(field.getValueClassName().equals("java.lang.Double"))
				return new Double(0);
			if(field.getValueClassName().equals("java.lang.Integer"))
				return new Integer(0);
			
			return field.getValueClass().newInstance();
		} catch (Exception e) {
			return null;
		} 
	}
}

