package ntut.csie.ezScrum;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import ntut.csie.ezScrum.database.SqlDatabaseHelper;
import ntut.csie.ezScrum.useCase.ApplicationContext;

@SuppressWarnings("serial")
public class ezScrumStart extends HttpServlet implements ServletContextListener{
	
	private static SqlDatabaseHelper sqlDatabaseHelper = SqlDatabaseHelper.getInstance();
	/*private static ApplicationContext context;
	
	public static void main(String[] args) throws ServletException
    {
		System.out.println("ezScrum Start!");
		context = ApplicationContext.getInstance();
    }*/
	
	@Override
	  public void contextDestroyed(ServletContextEvent arg0) {
		
	  }

	  @Override
	  public void contextInitialized(ServletContextEvent arg0) {
		  System.out.println("ezScrum Start!");
		  sqlDatabaseHelper.connection();
	  }

}
