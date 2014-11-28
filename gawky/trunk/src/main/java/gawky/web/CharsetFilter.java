package gawky.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 
<filter>
<filter-name>Charset Filter</filter-name>
<filter-class>gawky.web.CharsetFilter</filter-class>
  <init-param>
    <param-name>requestEncoding</param-name>
    <param-value>UTF-8</param-value>
  </init-param>
</filter>

<filter-mapping>
<filter-name>Charset Filter</filter-name>
<url-pattern>/*</url-pattern>
</filter-mapping>
*
**/

public class CharsetFilter implements Filter
{
	private String encoding;

	@Override
	public void init(FilterConfig config) throws ServletException
	{
		encoding = config.getInitParameter("requestEncoding");

		if(encoding == null)
		{
			encoding = "UTF-8";
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
	        FilterChain next) throws IOException, ServletException
	{
		request.setCharacterEncoding(encoding);
		next.doFilter(request, response);
	}

	@Override
	public void destroy()
	{
	}
}
