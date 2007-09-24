package gawky.message.parser;

import gawky.message.part.Desc;
import gawky.message.part.Part;

import java.io.StringReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

/**
 * @author Ingo Harbeck
 *
 */
public class XMLParser extends Parser
{
	private static Log log = LogFactory.getLog(XMLParser.class);

	public Object parse(String xml, Object bean) throws ParserException
	{
		SAXBuilder parser = new SAXBuilder();

		Document doc = null;

		try {
			doc = parser.build(new StringReader(xml));
		} catch (Exception e) {
			throw new ParserException(ParserException.ERROR_PARSING, null, xml);
		}

		return parse(doc.getRootElement(), bean);
	}

	public Object parse(Element xml, Object bean) throws ParserException
	{
		// Get Description Object
		descs = ((Part)bean).getCachedDesc();

		Desc   desc;
		String value = "";


		for(int i = 0; i < descs.length; i++)
		{
			desc = descs[i];

			// DISCUSS example - ID not in Importfile but later in DB
			if(desc.skipparsing)
				continue;

			try {
				value = ((Element)XPath.selectSingleNode(xml, desc.xmlpath)).getValue();
			} catch (Exception e) {
			}

			if(value == null)
				throw new ParserException(ParserException.ERROR_FIELD_TO_SHORT, desc, "");

			storeValue(bean, i, desc, value);

			// Required Field
			if(desc.code == Desc.CODE_R && value.length() == 0)
				throw new ParserException(ParserException.ERROR_FIELD_REQUIRED, desc, value);
			// Optional Field
			else if(desc.code == Desc.CODE_O && value.length() == 0)
			{
				storeValue(bean, i, desc, value);
				continue;
			}

			// Inhaltlich prüfung
			typeCheck(desc, value);

		    storeValue(bean, i, desc, value);
		}

		return bean;
	}
}
