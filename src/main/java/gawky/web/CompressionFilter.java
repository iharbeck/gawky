package gawky.web;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 <filter>
    <filter-name>compressionFilter</filter-name>
    <filter-class>gawky.web.CompressionFilter</filter-class>
     <init-param>
      <param-name>compressionThreshold</param-name>
      <param-value>0</param-value>
     </init-param>   
	</filter>
 
    <filter-mapping>
     <filter-name>compressionFilter</filter-name>
     <url-pattern>*.do</url-pattern>
    </filter-mapping> 
 *
 */

public class CompressionFilter implements Filter {

  private FilterConfig config = null;

  protected int compressionThreshold;

  public void init(FilterConfig filterConfig) {
    config = filterConfig;
    compressionThreshold = 0;
    if (filterConfig != null) {
      String str = filterConfig.getInitParameter("compressionThreshold");
      if (str != null) {
        compressionThreshold = Integer.parseInt(str);
      }
      else {
        compressionThreshold = 0;
      }
    }
  }

  public void destroy() {
    this.config = null;
  }

  public void doFilter(ServletRequest request, ServletResponse response,
                    FilterChain chain ) throws IOException, ServletException {
    
	boolean supportCompression = false;
    
	if (request instanceof HttpServletRequest) 
    {
      Enumeration e = ((HttpServletRequest)request).getHeaders("Accept-Encoding");
      while (e.hasMoreElements()) {
        String name = (String)e.nextElement();
        if (name.indexOf("gzip") != -1) {
          supportCompression = true;
        }
      }
    }
    if (!supportCompression) {
      chain.doFilter(request, response);
    }
    else {
      if (response instanceof HttpServletResponse) {
        CompressionResponseWrapper wrappedResponse =
          new CompressionResponseWrapper((HttpServletResponse)response);
        wrappedResponse.setCompressionThreshold(compressionThreshold);
        chain.doFilter(request, wrappedResponse);
      }
    }
  }
}
