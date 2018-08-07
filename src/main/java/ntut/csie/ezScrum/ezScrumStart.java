package ntut.csie.ezScrum;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import ntut.csie.ezScrum.database.SqlDatabaseHelper;

@SuppressWarnings("serial")
public class ezScrumStart extends HttpServlet implements ServletContextListener{
	
	private static SqlDatabaseHelper sqlDatabaseHelper = SqlDatabaseHelper.getInstance();
	
	@Override
	  public void contextDestroyed(ServletContextEvent arg0) {
		
	  }

	  @Override
	  public void contextInitialized(ServletContextEvent arg0) {
		  System.out.println("ezScrum Start!");
		  sqlDatabaseHelper.connection();
		  ApplicationContext.getInstance();
	  }

}
