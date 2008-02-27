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
	
	public Object getValue(Object bean, String path, int row) 
	{
		try {
			// Indexed parameters described as [x] in Report 
			path = path.replaceAll("\\[x\\]", "[" + (row) + "]");
			
			if (bean instanceof HashMap) {
				if(!((HashMap)bean).containsKey(path))
					return "$$" + path + "$$";
				else
					return ((HashMap)bean).get(path);
			}
			
			if(path.startsWith("$") && bean instanceof Hashprovider)
			{
				HashMap hash =  ((Hashprovider)bean).getHash();
				
				path = path.substring(1);
				if(!(hash).containsKey(path))
					return "$$" + path + "$$";
				else
					return ((HashMap)hash).get(path);
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
		return rows - currow++ > 0;
	}
}
