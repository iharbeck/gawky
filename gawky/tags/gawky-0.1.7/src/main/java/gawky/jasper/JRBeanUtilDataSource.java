package gawky.jasper;

import gawky.regex.Replacer;

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
	
	Boolean footed = Boolean.FALSE;
	 
	//Replacer for Rowindicator 
	Replacer replacer = new Replacer("\\[x\\]");
	
	public JRBeanUtilDataSource(Object data, int rows) {
		this.data = data;
		this.rows = rows;
		footed = Boolean.FALSE;
	}
	
	public JRBeanUtilDataSource(DataSource data) {
		this.data = data;
		this.rows = data.getSize();
		footed = Boolean.FALSE;
	}
	
	public Object getValue(Object bean, String path, int row) 
	{
		if(log.isDebugEnabled())
			log.debug("PATH: " + path + " ROW: " + (row+1) + "/" + rows);
		
		// Expression handler
		if(path.equals("%LASTPAGE")) {
			if(row == rows-1)
				return Boolean.TRUE;
			else
				return Boolean.FALSE;
		}
		
		
//		if(path.equals("%LASTPAGE_FOOTER")) {
//			if(row == rows-1) {
//				footed = Boolean.TRUE;
//			}
//			return "";
//		}
//		
//		
//		if(path.equals("%LASTPAGE_FOOTED")) {
//			return footed;
//		}
		
		if(path.equals("%FIRSTPAGE")) {
			if(row == 0)
				return Boolean.TRUE;
			else
				return Boolean.FALSE;
		}
		
		try {
			// Indexed parameters described as [x] in Report 
			path = replacer.replaceFirst(path, "[" + (row) + "]");
			
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
				if(((HashMap)bean).containsKey(path))
					return ((HashMap)bean).get(path);
				else
					return "$$" + path + "$$";
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
