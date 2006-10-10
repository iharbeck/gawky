package gawky.jasper;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

public class JRBeanUtilDataSource implements JRDataSource
{
	static final Logger log = Logger.getLogger(JRBeanUtilDataSource.class);
	
	Object data;

	int rows = 0;
	int currow = 0;
	
	public JRBeanUtilDataSource(Object data, int rows) {
		this.data = data;
		this.rows = rows;
	}
	
	public Object getValue(Object bean, String path, int row) 
	{
		try {
			path = path.replaceAll("\\[x\\]", "[" + (row) + "]");
			return BeanUtils.getProperty(bean, path);
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
