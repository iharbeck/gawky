package gawky.jasper;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.CommentNode;
import org.htmlcleaner.ContentNode;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.HtmlNode;
import org.htmlcleaner.SimpleXmlSerializer;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.TagNodeVisitor;
import org.htmlcleaner.Utils;
import org.xhtmlrenderer.pdf.ITextRenderer;

public class Html2PDF
{
	public static void main(String[] args) throws Exception
	{
		//renderURL(new URL("http://www.google.de"), new FileOutputStream("e:/users/test.pdf"), "UTF-8", "http://www.google.de");

		//renderString("<table border=1><tr><td><div style='font-weight:bold; text-decoration:underline'>hello</div></td></tr></table>", "testxml.pdf", "UTF-8", "http://www.google.de");

		String ddd = "<html>\n" +
		        "  <head>\n" +
		        "    <title>Some title</title>\n" +
		        "  </head>\n" +
		        "  <body>\n" +
		        "    <div>\n" +
		        "<table cellpadding=\"0\" cellspacing=\"0\">\n" +
		        "<tr>\n" +
		        "<td>\n" +
		        "   <img src=\"data:image/png;base64,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\" width=\"185\" height=\"83\"/>\n" +
		        "</td>\n" +
		        "</tr>\n" +
		        "<tr>\n" +
		        "<td>\n" +
		        "   <img src=\"data:image/png;base64,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\" width=\"185\" height=\"84\"/>\n" +
		        "</td>\n" +
		        "</tr>\n" +
		        "<tr>\n" +
		        "<td>\n" +
		        "   <img src=\"data:image/png;base64,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\" width=\"185\" height=\"83\"/>\n" +
		        "   <img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAF0AAAB8CAIAAACE4ysHAAAFQUlEQVR4nO2cMXKzMBCFORQnyRU4QO6RGa7gynU6yj9lSpcuVMRlWm6gv0AS2tVbAUmIiee9SRELIaQvKwkeGzeeQmru3YGDilywyAWLXLDIBYtcsMgFi1ywyAWLXLDIBYtcsBo/dI1SN6TDQ9e0vZuru75Nn9PveWG9WlOWhgNRqqG7ScaL6Lr3rm+7vm8zTt/lkqBjWn7oxJ/lfsq56E4NXdMN3uVgvsml7ftOnqMD0g/dIUImcVGh4iMWO+RT1G/h4gLmUFqeehAFLmX85j2OhH4gXlyICMAlLXRHmEiNR6Hiy9W4RPBVLnHZsubRQbjApU73Lo5pJZdsuK5vQ1NZtaHLSedkjrLwNnLBmOJY7kGT0iJsbTRqm08BZ27NchcCNwn3FO/rsMgFi1ywyAWLXLDIBYtcsMgFi1ywyAWLXLDIBYtcsBrv9QNxfM7NStNDLnx0dn2rqyCjzzQlgoDHXnlM39kkj1z0JYQRMJsiomYciOvbtm0zRy982MSl7rGna/2WbWVw0SXI602lk6HdT+VDl4a3hcuCx56u9dtcVGyu8etEvPRD30XzdtjOxfDYYbz8yrsmI14qXMr5PR0cum7wru+S5b+ei+mxl9c6/DzKVslQmGbQVi6LHnt2rTtzWbPuJkc7vfPo2vhuKG0eNffbp9bXeOxTpXtz8eY+rcz7+V1QPqB4AnS/xe67zmNP15KxtR8i3tdhkQsWuWCRCxa5YJELFrlgkQsWuWCRCxa5YJELFrlgNZXUMGBll7azK7K4hzI1LRSCBqOQ6Y2dO1A5Nake3nFvV43L5gKt7NL+cDCLG35ADaajpeltNAsqg3Q9u7frxlXhgqxsfKUii9uABBqMA4GmN2wWVLaSxq3erhhXjQuwskvb2YEsbosLaNBnnSp7ApoFlc1XUkZv14yrxgVY2cZfwMksbosLaLBieqNmYWVVOFt5Vm9XjKvKpbSybS5x2le5lA0umt6iWaOylTRu9nZ5XE3VnS6s7AoXkcVtcVlsEJjec7NWZbXupoXX7O3yuBrxZygCR1vZpe2sXfs6F9WgW2V6h2btyjO+rGPV3i6Oi/d1WOSCRS5Y5IJFLljkgkUuWOSCRS5Y5IJFLljkgkUuWOp7TmwLzPCQ9TOsKETJY5XUcYcMc514aHRvFy6iryB/OXTNdl5ULq8TZW45hVEOXF8cuuK/ykWmcG/hgk5MHq1+yWGnjoOGl1PB95Dmkgyt2jzCLr0RL5tSx0WNdKieCr6LtnCBb47USlCuL5tSx0UN/Tv+upV99CPzqMz9htVkQ/B0fcaKr1vZRz+07lZOXLPuzoa7PLJqDu4ie59Wm2jd8S5yv6WMfbo8XY/edsV1b35YvK/DIhcscsEiFyxywSIXrOapf+NP+cN4wSIXLHLBIhcscsEiFyzwPSeGosm040PsgSTiZcFUyl0Slen3cNrI5ZFRCG3hMk+jx55D3m/lEjXxeWQ6X+OycOgBtIXL7Fl7bVU/nLbFiwP//PSY4n0dFrlgkQsWuWCRCxa5YNH3NnzvkUIiFyxywSIXLHLBIhes5ul0vY7jeLnku9TLZRxv1+d863r9SOf8ew2Fz2+fYzz35TId/Dyf0lnv59s4jqGpUOFyeerfz7e8mmztGGrCkEJ3syN6MGpg4/XtPZwyH/08n94C6DGrALjEq5TXPYaabGw2l9v1WUSHrJm4WIP8k1ymCSLm0eXfaMfL+PGiZlY8OgeIkpqSf4LL8zRCI16syR+XGDGP6lz+WLycTzUuaQFGi+LHy1Qu5lEKK9TU+Ge4/AfCiI9MyS3hWwAAAABJRU5ErkJggg==\" alt='' /> \n" +
		        "</td>\n" +
		        "</tr>\n" +
		        "</table>\n" +
		        "    </div>\n" +
		        "  </body>\n" +
		        "</html>";

		ddd = "<?xml version='1.0' encoding='UTF-8'?><html><head><style type='text/css'><![CDATA[ " +

		        " @page {" +

		        "   size: 21cm 29.7cm;" +
		        "   background: url(https://online-inte.bfs-finance.de:8443/oak_i1/images/bfs_logo.gif) no-repeat 2.55cm 0.9cm; " +
		        "   margin-top: 4.0cm; " +
		        "   margin-left: 2.5cm;" +
		        "   margin-right: 1.5cm;" +
		        " 		  margin-bottom: 2.5cm;" +
		        " 		  padding-right: 0cm;" +
		        " 			  @top-right {" +
		        " 		   white-space: pre;" +
		        "  		   font-size: 12pt;" +
		        "  		   font_weight: bold;" +
		        "   color: #000000; " +
		        " content: 'ss' }" +
		        " 		  @bottom-left { " +
		        " 		      font-size: 9pt;" +
		        " 		      content: 'Seite: ' counter(page) ' / ' counter(pages);" +
		        " 		     }" +
		        " 		 }" +

		        " 		  td {" +
		        " 		   text-align: left;" +
		        " 		   vertical-align: top;" +
		        " 		  }" +

		        " 		 .einpixeltablelight  {" +
		        " 		  margin: 0px;" +
		        " 		  border-collapse: collapse;" +
		        " 		  empty-cells: show;" +
		        " 		  width: 100%;" +
		        " 		  table-layout: fixed;" +
		        " 		  border: 0.5px solid black;" +
		        " 		 }" +

		        " 		 .einpixeltablelight  td {" +
		        " 		  padding-left: 3px; " +
		        " 		  padding-right: 3px; " +
		        " 		  overflow: hidden; " +
		        " 		  white-space: nowrap; " +
		        " 		  cursor: pointer;" +
		        " 		  border: 0.5px solid silver;" +
		        " 		    border-top: none;" +
		        " 		 }" +

		        " 		 .einpixeltablelight th {" +
		        " 		  background-color: silver;" +
		        " 		  color: black;" +
		        " 		  text-align: left;" +
		        " 		  font-weight: bold;" +
		        " 		  border: 0.5px solid silver;" +
		        " 		 }" +

		        " 		 table {" +
		        " 		        border-collapse: collapse;" +
		        " 		        empty-cells:show;   " +
		        " 		        border: 0.5px solid black;" +
		        " 		    }" +
		        " 		    table td {" +
		        " 		       border: 0.5px solid black;" +
		        " 		       border-top: none;" +
		        " 		    }" +
		        " 		    table th {" +
		        " 		       border: 0.5px solid black;" +
		        " 		       background-color: silver;" +
		        " 	       color: black;" +
		        " 	       text-align: left;" +
		        " 	       font-weight: bold;" +
		        " 	       font-size: 12px;" +
		        " 	    }" +

		        " 	    div.section {" +
		        " 	           page-break-inside:avoid; " +
		        " 	           margin-top:40px;" +
		        " }" +

		        "]]   " +
		        "<title>CR Dokument - Ticket ID 101588</title></head><body border='0' style='margin:0px; padding:0px'> " +

		        " <table class='einpixeltablelight'><tbody><tr><th class='table_header_blue' width='210'>Punkt</th><th class='table_header_blue' colspan='2'>Beschreibung</th></tr><tr><td>Datum erstellt / gespeichert</td><td>25.06.2012</td><td>12.07.2012</td></tr><tr><td>Ticket ID</td><td colspan='2'>101588</td></tr><tr><td>Ticket Bezeichnung</td><td colspan='2'>TESt CR</td></tr><tr><td>Auftragsnummer</td><td colspan='2' /></tr><tr><td>Fachbereich</td><td colspan='2'>Dennis Laumeier</td></tr><tr><td>Verantwortlich Fachbereich</td><td colspan='2'>Dennis Laumeier</td></tr></tbody></table> " +

		        "  <div class='section'> " +
		        "  <h3>1. Gesch�ftliche Hintergrundinformation / Kunden Spezifikation ABCDEF</h3>" +
		        "  Test CR<br />Dokument sdf dsf sdf sdf sd INGO22<br /><br /><img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAF0AAAB8CAIAAACE4ysHAAAFQUlEQVR4nO2cMXKzMBCFORQnyRU4QO6RGa7gynU6yj9lSpcuVMRlWm6gv0AS2tVbAUmIiee9SRELIaQvKwkeGzeeQmru3YGDilywyAWLXLDIBYtcsMgFi1ywyAWLXLDIBYtcsBo/dI1SN6TDQ9e0vZuru75Nn9PveWG9WlOWhgNRqqG7ScaL6Lr3rm+7vm8zTt/lkqBjWn7oxJ/lfsq56E4NXdMN3uVgvsml7ftOnqMD0g/dIUImcVGh4iMWO+RT1G/h4gLmUFqeehAFLmX85j2OhH4gXlyICMAlLXRHmEiNR6Hiy9W4RPBVLnHZsubRQbjApU73Lo5pJZdsuK5vQ1NZtaHLSedkjrLwNnLBmOJY7kGT0iJsbTRqm08BZ27NchcCNwn3FO/rsMgFi1ywyAWLXLDIBYtcsMgFi1ywyAWLXLDIBYtcsBrv9QNxfM7NStNDLnx0dn2rqyCjzzQlgoDHXnlM39kkj1z0JYQRMJsiomYciOvbtm0zRy982MSl7rGna/2WbWVw0SXI602lk6HdT+VDl4a3hcuCx56u9dtcVGyu8etEvPRD30XzdtjOxfDYYbz8yrsmI14qXMr5PR0cum7wru+S5b+ei+mxl9c6/DzKVslQmGbQVi6LHnt2rTtzWbPuJkc7vfPo2vhuKG0eNffbp9bXeOxTpXtz8eY+rcz7+V1QPqB4AnS/xe67zmNP15KxtR8i3tdhkQsWuWCRCxa5YJELFrlgkQsWuWCRCxa5YJELFrlgNZXUMGBll7azK7K4hzI1LRSCBqOQ6Y2dO1A5Nake3nFvV43L5gKt7NL+cDCLG35ADaajpeltNAsqg3Q9u7frxlXhgqxsfKUii9uABBqMA4GmN2wWVLaSxq3erhhXjQuwskvb2YEsbosLaNBnnSp7ApoFlc1XUkZv14yrxgVY2cZfwMksbosLaLBieqNmYWVVOFt5Vm9XjKvKpbSybS5x2le5lA0umt6iWaOylTRu9nZ5XE3VnS6s7AoXkcVtcVlsEJjec7NWZbXupoXX7O3yuBrxZygCR1vZpe2sXfs6F9WgW2V6h2btyjO+rGPV3i6Oi/d1WOSCRS5Y5IJFLljkgkUuWOSCRS5Y5IJFLljkgkUuWOp7TmwLzPCQ9TOsKETJY5XUcYcMc514aHRvFy6iryB/OXTNdl5ULq8TZW45hVEOXF8cuuK/ykWmcG/hgk5MHq1+yWGnjoOGl1PB95Dmkgyt2jzCLr0RL5tSx0WNdKieCr6LtnCBb47USlCuL5tSx0UN/Tv+upV99CPzqMz9htVkQ/B0fcaKr1vZRz+07lZOXLPuzoa7PLJqDu4ie59Wm2jd8S5yv6WMfbo8XY/edsV1b35YvK/DIhcscsEiFyxywSIXrOapf+NP+cN4wSIXLHLBIhcscsEiFyzwPSeGosm040PsgSTiZcFUyl0Slen3cNrI5ZFRCG3hMk+jx55D3m/lEjXxeWQ6X+OycOgBtIXL7Fl7bVU/nLbFiwP//PSY4n0dFrlgkQsWuWCRCxa5YNH3NnzvkUIiFyxywSIXLHLBIhes5ul0vY7jeLnku9TLZRxv1+d863r9SOf8ew2Fz2+fYzz35TId/Dyf0lnv59s4jqGpUOFyeerfz7e8mmztGGrCkEJ3syN6MGpg4/XtPZwyH/08n94C6DGrALjEq5TXPYaabGw2l9v1WUSHrJm4WIP8k1ymCSLm0eXfaMfL+PGiZlY8OgeIkpqSf4LL8zRCI16syR+XGDGP6lz+WLycTzUuaQFGi+LHy1Qu5lEKK9TU+Ge4/AfCiI9MyS3hWwAAAABJRU5ErkJggg==' alt='' />ssss sadas dasddd " +
		        "  </div> " +

		        "   <div class='section'>" +
		        " <h3>2. Technische Spezifikation</h3>" +
		        "Tech. CR" +
		        " </div>" +

		        "<div class='section'>" +
		        "<h3>3. Kalkulation</h3>" +

		        "<table class='einpixeltablelight' style='width: 100%;' border='0'><tbody><tr><th class='table_header_blue'>Aufgabe</th><th class='table_header_blue' width='50'>MT</th></tr><tr><td>�dsds</td><td>�0,5</td></tr><tr><td>�asasa</td><td>�1</td></tr><tr><td>�1212</td><td>�2</td></tr></tbody></table>" +
		        "</div>" +
		        "</body></html>";

		ddd = "<img src='http://www.iharbeck.de/meshcms/generated/images/apache_tomcat_logo.jpg/org.meshcms.core.ResizedThumbnail/resized-792748241.jpg'> <img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAF0AAAB8CAIAAACE4ysHAAAFQUlEQVR4nO2cMXKzMBCFORQnyRU4QO6RGa7gynU6yj9lSpcuVMRlWm6gv0AS2tVbAUmIiee9SRELIaQvKwkeGzeeQmru3YGDilywyAWLXLDIBYtcsMgFi1ywyAWLXLDIBYtcsBo/dI1SN6TDQ9e0vZuru75Nn9PveWG9WlOWhgNRqqG7ScaL6Lr3rm+7vm8zTt/lkqBjWn7oxJ/lfsq56E4NXdMN3uVgvsml7ftOnqMD0g/dIUImcVGh4iMWO+RT1G/h4gLmUFqeehAFLmX85j2OhH4gXlyICMAlLXRHmEiNR6Hiy9W4RPBVLnHZsubRQbjApU73Lo5pJZdsuK5vQ1NZtaHLSedkjrLwNnLBmOJY7kGT0iJsbTRqm08BZ27NchcCNwn3FO/rsMgFi1ywyAWLXLDIBYtcsMgFi1ywyAWLXLDIBYtcsBrv9QNxfM7NStNDLnx0dn2rqyCjzzQlgoDHXnlM39kkj1z0JYQRMJsiomYciOvbtm0zRy982MSl7rGna/2WbWVw0SXI602lk6HdT+VDl4a3hcuCx56u9dtcVGyu8etEvPRD30XzdtjOxfDYYbz8yrsmI14qXMr5PR0cum7wru+S5b+ei+mxl9c6/DzKVslQmGbQVi6LHnt2rTtzWbPuJkc7vfPo2vhuKG0eNffbp9bXeOxTpXtz8eY+rcz7+V1QPqB4AnS/xe67zmNP15KxtR8i3tdhkQsWuWCRCxa5YJELFrlgkQsWuWCRCxa5YJELFrlgNZXUMGBll7azK7K4hzI1LRSCBqOQ6Y2dO1A5Nake3nFvV43L5gKt7NL+cDCLG35ADaajpeltNAsqg3Q9u7frxlXhgqxsfKUii9uABBqMA4GmN2wWVLaSxq3erhhXjQuwskvb2YEsbosLaNBnnSp7ApoFlc1XUkZv14yrxgVY2cZfwMksbosLaLBieqNmYWVVOFt5Vm9XjKvKpbSybS5x2le5lA0umt6iWaOylTRu9nZ5XE3VnS6s7AoXkcVtcVlsEJjec7NWZbXupoXX7O3yuBrxZygCR1vZpe2sXfs6F9WgW2V6h2btyjO+rGPV3i6Oi/d1WOSCRS5Y5IJFLljkgkUuWOSCRS5Y5IJFLljkgkUuWOp7TmwLzPCQ9TOsKETJY5XUcYcMc514aHRvFy6iryB/OXTNdl5ULq8TZW45hVEOXF8cuuK/ykWmcG/hgk5MHq1+yWGnjoOGl1PB95Dmkgyt2jzCLr0RL5tSx0WNdKieCr6LtnCBb47USlCuL5tSx0UN/Tv+upV99CPzqMz9htVkQ/B0fcaKr1vZRz+07lZOXLPuzoa7PLJqDu4ie59Wm2jd8S5yv6WMfbo8XY/edsV1b35YvK/DIhcscsEiFyxywSIXrOapf+NP+cN4wSIXLHLBIhcscsEiFyzwPSeGosm040PsgSTiZcFUyl0Slen3cNrI5ZFRCG3hMk+jx55D3m/lEjXxeWQ6X+OycOgBtIXL7Fl7bVU/nLbFiwP//PSY4n0dFrlgkQsWuWCRCxa5YNH3NnzvkUIiFyxywSIXLHLBIhes5ul0vY7jeLnku9TLZRxv1+d863r9SOf8ew2Fz2+fYzz35TId/Dyf0lnv59s4jqGpUOFyeerfz7e8mmztGGrCkEJ3syN6MGpg4/XtPZwyH/08n94C6DGrALjEq5TXPYaabGw2l9v1WUSHrJm4WIP8k1ymCSLm0eXfaMfL+PGiZlY8OgeIkpqSf4LL8zRCI16syR+XGDGP6lz+WLycTzUuaQFGi+LHy1Qu5lEKK9TU+Ge4/AfCiI9MyS3hWwAAAABJRU5ErkJggg==' alt='' />";
		renderString(ddd, "c:/testxml.pdf", "UTF-8", "http://www.google.de");
	}

	public static void renderString(String xml, OutputStream os, String encoding, String base) throws Exception
	{
		byte[] byteArray = xml.getBytes(encoding);
		ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);

		render(bais, os, encoding, base);
	}

	public static void renderString(String xml, String filename_pdf, String encoding, String base) throws Exception
	{
		OutputStream os = new FileOutputStream(filename_pdf);

		renderString(xml, os, encoding, base);
	}

	public static void renderStream(InputStream in, OutputStream os, String encoding, String base)
	{
		render(in, os, encoding, base);
	}

	public static void renderURL(URL in, OutputStream os, String encoding, String base)
	{
		render(in, os, encoding, base);
	}

	public static void render(Object indata, OutputStream os, String encoding, final String base)
	{
		//System.setProperty("java.protocol.handler.pkgs", "org.xhtmlrenderer.protocols");

		//String prop = System.getProperty("java.protocol.handler.pkgs");

		//		if(prop == null)
		//		{
		//			System.setProperty("java.protocol.handler.pkgs", "org.xhtmlrenderer.protocols");
		//		}
		//		else if(!prop.contains("org.xhtmlrenderer.protocols"))
		//		{
		//			System.setProperty("java.protocol.handler.pkgs", prop + "|org.xhtmlrenderer.protocols");
		//		}

		try
		{
			CleanerProperties props = new CleanerProperties();

			props.setTranslateSpecialEntities(true);
			props.setTransResCharsToNCR(true);
			props.setOmitComments(true);

			HtmlCleaner cleaner = new HtmlCleaner(props);

			TagNode tagNode;
			if(indata instanceof URL)
			{
				tagNode = cleaner.clean((URL)indata);
			}
			else
			{
				tagNode = cleaner.clean((InputStream)indata, encoding);
			}

			tagNode.traverse(new TagNodeVisitor()
			{

				@Override
				public boolean visit(TagNode tagNode, HtmlNode htmlNode)
				{
					if(htmlNode instanceof TagNode)
					{
						TagNode tag = (TagNode)htmlNode;
						String tagName = tag.getName();

						if("select".equals(tagName))
						{
							tag.setName("span");

							if(tag.getChildren().size() == 0)
							{
								return true;
							}

							String text = ((TagNode)tag.getChildren().get(0)).getText().toString();

							List<TagNode> li = tag.getChildren();

							for(TagNode dd : li)
							{
								if(dd.getAttributeByName("selected") != null)
								{
									text = dd.getText().toString();
								}
							}

							tag.removeAllChildren();

							ContentNode cont = new ContentNode(text);
							tag.addChild(cont);
						}
						else if("input".equals(tagName))
						{
							String type = tag.getAttributeByName("type");

							tag.setName("span");

							if(type == null)
							{
								return true;
							}

							if(type.equals("text"))
							{
								tag.setName("div");
								tag.setAttribute("class", tag.getAttributeByName("class ") + " ufo_input");
								String text = tag.getAttributeByName("value");
								if(text != null)
								{
									ContentNode cont = new ContentNode(text);
									tag.addChild(cont);
								}
							}
							else if(type.equals("checkbox"))
							{
								tag.setName("span");
								if(tag.getAttributeByName("checked") != null)
								{
									tag.addChild(new ContentNode("[x]"));
								}
								else
								{
									tag.addChild(new ContentNode("[ ]"));
								}
							}
							else if(type.equals("radio"))
							{
								tag.setName("span");
								if(tag.getAttributeByName("checked") != null)
								{
									tag.addChild(new ContentNode("(*)"));
								}
								else
								{
									tag.addChild(new ContentNode("( )"));
								}
							}

						}
						else if("textarea".equals(tagName))
						{
							tag.setName("span");
						}
						else if("img".equals(tagName))
						{
							String src = tag.getAttributeByName("src");

							if(src != null)
							{
								if(src.startsWith("data:"))
								{
									return true;
								}

								if(!src.startsWith("http"))
								{
									tag.setAttribute("src", Utils.fullUrl(base, src));
									//System.out.println(Utils.fullUrl(base, src));
									System.out.println(Utils.fullUrl(base, src));
								}
							}

							//tag.setAttribute("src", Locator.findBinROOT() + src);
						}
						else if("link".equals(tagName))
						{
							String rel = tag.getAttributeByName("rel");
							String href = tag.getAttributeByName("href");

							if(href != null && "stylesheet".equals(rel))
							{
								tag.setAttribute("href", Utils.fullUrl(base, href));
								/*
								 * try { HttpClient client = new DefaultHttpClient(); String fullUrl = ""; if (href.startsWith("http")) fullUrl = href; else fullUrl = Utils.fullUrl(base, href);
								 * 
								 * HttpGet get = new HttpGet(fullUrl); HttpResponse response = client.execute(get); HttpEntity entity = response.getEntity(); if (entity != null) { InputStream is = entity.getContent(); href = "css" + cssCounter + ".css"; cssCounter++; OutputStream os = new FileOutputStream(href); IOUtils.copy(is, os); } tag.setAttribute("href", href); } catch (IOException ex) { //Logger.getLogger(HTML2PDF.class.getName()).log(Level.SEVERE, null, ex); }
								 */
							}
						}
					}
					else if(htmlNode instanceof CommentNode)
					{
						//CommentNode comment = ((CommentNode) htmlNode);
						//comment.getContent().append(" -- By HtmlCleaner");
					}

					return true;
				}
			});

			// serialize to xml file

			String content = new SimpleXmlSerializer/* PrettyXmlSerializer */(props).getAsString(tagNode, encoding);

			// FlyingSaucer and iText part

			ITextRenderer renderer = new ITextRenderer();

			renderer.setDocumentFromString(content);

			//renderer.getSharedContext().setUserAgentCallback(new NaiveUserAgent());

			UniversalITextUserAgent useragent = new UniversalITextUserAgent(renderer);
			//useragent.setSharedContext(renderer.getSharedContext());

			renderer.getSharedContext().setUserAgentCallback(useragent);

			//URL.setURLStreamHandlerFactory(new ConfigurableStreamHandlerFactory("data", new Handler()));

			renderer.layout();
			renderer.createPDF(os);

			os.close();

		}
		catch(Exception ex)
		{
			ex.printStackTrace(System.out);
			//Logger.getLogger(HTML2PDF.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}