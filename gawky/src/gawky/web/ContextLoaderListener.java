package gawky.web;


import gawky.global.Option;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



/**
 * Add to web.xml Initialisiert Option parameter
 *
 *  <listener>
 *     <listener-class>gawky.web.ContextLoaderListener</listener-class>
 *  </listener>
 * 
 * @author Ingo Harbeck
 *
 */


public class ContextLoaderListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent arg0) {
		try {
			Option.init();
		} catch (Exception e) {
			arg0.getServletContext().log("Init gawky failed", e);
		}
	}

	public void contextDestroyed(ServletContextEvent arg0) {
	}
	
}
