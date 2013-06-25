package example.incubator;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRAlignment;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextElement;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public final class JasperDyn {

	public JasperDyn() {

	}

	public final JasperDesign getPreMatureDesign(
			java.sql.ResultSetMetaData data, int ColumnWidth, int reportWidth,
			int reportHeight, int gap, boolean landSacpe) throws JRException,
			SQLException 
	{
		JasperDesign design = (JasperDesign) JRXmlLoader.load("./design.jrxml");
		System.out.println(design.getOrientation());
		if (landSacpe) {
			design.setOrientation((byte) (0));
		}
		design.setPageWidth(reportWidth);
		design.setPageHeight(reportHeight);
		Map designFieldMap = (HashMap) design.getMainDesignDataset()
				.getFieldsMap();
		JRBand bandDetail = design.getDetail();
		bandDetail.setSplitAllowed(false);
		List bandDetailList = bandDetail.getChildren();

		JRBand columnHeader = design.getColumnHeader();
		List columnHeaderList = (ArrayList) columnHeader.getChildren();
		List designFieldList = (ArrayList) design.getMainDesignDataset()
				.getFieldsList();

		JRDesignStaticText[] staticFields = this.getHeaders(data, ColumnWidth);
		JRDesignTextField[] texts = this.getDataSet(data, designFieldMap,
				bandDetailList, designFieldList, ColumnWidth);
		int x = 0;
		int xHeader = 0;
		int counter = 0;
		for (JRDesignTextField temp : texts) {
			staticFields[counter].setX(xHeader);
			temp.setX(x);
			columnHeaderList.add(staticFields[counter]);

			x += ColumnWidth;
			if (counter != 0) {
				x += gap;
			}
			xHeader += ColumnWidth;
			counter++;

		}
		return design;
	}

	private final JRDesignStaticText[] getHeaders(ResultSetMetaData data,
			int width) throws SQLException {
		int columnCount = data.getColumnCount();
		JRDesignStaticText texts[] = new JRDesignStaticText[columnCount];
		for (int counter = 1; counter <= columnCount; counter++) {
			texts[counter - 1] = new JRDesignStaticText();
			texts[counter - 1].setText(data.getColumnName(counter).replace("_",
					" "));
			texts[counter - 1]
					.setHorizontalAlignment(JRDesignTextElement.HORIZONTAL_ALIGN_CENTER);
			texts[counter - 1].setKey("staticText-" + counter);
			texts[counter - 1].setBold(true);
			texts[counter - 1].setMode(JRDesignStaticText.MODE_OPAQUE);
			texts[counter - 1].setPdfFontName("PCS NEPALI");
			texts[counter - 1].setFontName("SanSerif");
			texts[counter - 1].setFontSize(10);
			texts[counter - 1].setWidth(width);
			texts[counter - 1].setHeight(30);
			texts[counter - 1].setY(0);
		}
		return texts;

	}

	private final JRDesignTextField[] getDataSet(ResultSetMetaData data,
			Map designFieldMap, List bandDetailList, List designFieldList,
			int width) throws SQLException 
	{
		int columnCount = data.getColumnCount();
		JRDesignTextField fields[] = new JRDesignTextField[columnCount];

		for (int counter = 1; counter <= columnCount; counter++) {
			fields[counter - 1] = new JRDesignTextField();
			String field_name = data.getColumnName(counter);

			bandDetailList.add(fields[counter - 1]);

			JRDesignField designField = new JRDesignField();

			designField.setName(field_name);

			// designField.setValueClassName();
			designField.setValueClassName("java.lang.String");
			designFieldList.add(designField);
			designFieldMap.put(field_name, designField);

			JRDesignExpression exp = new JRDesignExpression();
			exp.setValueClassName("java.lang.String");
			exp.setText("$F{" + field_name + "}");

			fields[counter - 1].setExpression(exp);
			fields[counter - 1].setKey("textField-" + counter);
			fields[counter - 1].setBlankWhenNull(true);
			fields[counter - 1].setHeight(20);
			fields[counter - 1].setWidth(width);
			fields[counter - 1].setY(0);
			fields[counter - 1].setFontName("PCS NEPAL");
			if (data.getColumnClassName(counter).equalsIgnoreCase(
					"java.math.BigDecimal")) {
				fields[counter - 1]
						.setHorizontalAlignment(JRAlignment.HORIZONTAL_ALIGN_RIGHT);
			} else {
				fields[counter - 1]
						.setHorizontalAlignment(JRAlignment.HORIZONTAL_ALIGN_LEFT);
			}

		}
		return fields;

	}

}