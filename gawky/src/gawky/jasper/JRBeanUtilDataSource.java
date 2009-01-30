package gawky.jasper;

import java.util.HashMap;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JRBeanUtilDataSource implements JRDataSource
{
	private static Log log = LogFactory.getLog(JRBeanUtilDataSource.class);

	Object data;

	int rows   = 0;
	int currow = 0;
	
	public JRBeanUtilDataSource(Object data, int rows) {
		this.data = data;
		this.rows = rows;
	}
	
	public JRBeanUtilDataSource(DataSource data) {
		this.data = data;
		this.rows = data.getSize();
	}
	
	public Object getValue(Object bean, String path, int row) 
	{
		if(path.equals("%LASTPAGE")) {
			if(row == rows-1)
				return Boolean.TRUE;
			else
				return Boolean.FALSE;
		}
		
		try {
			// Indexed parameters described as [x] in Report 
			path = path.replaceAll("\\[x\\]", "[" + (row) + "]");
			
			if(path.startsWith("#") && bean instanceof Hashprovider)
			{
				path = path.substring(1);
				String value =  ((Hashprovider)bean).getHashValue(path);
				
				if(value == null)
					return "$$" + path + "$$";
				else
					return value;
			}
			
			if (bean instanceof HashMap) {
				if(!((HashMap)bean).containsKey(path))
					return "$$" + path + "$$";
				else
					return ((HashMap)bean).get(path);
			}
			
		
				
			// TODO: implement cached version
			return PropertyUtils.getProperty(bean, path);  //return BeanUtils.getProperty(bean, path);
		} catch (Exception e) {
			log.info(e);
			return "$$" + path + "$$";
		} 
	}

	/**
	 * 	FOR TESTINGONLY
	 */
	public Object getFieldValue(String path) throws JRException {
		return getValue(data, path, currow-1);
	}

	
	public Object getFieldValue(JRField jrfield) throws JRException {
		return getValue(data, jrfield.getName(), currow-1);
	}

	
	public boolean next() throws JRException {
		currow++;
		return rows - currow >= 0;
	}
}
