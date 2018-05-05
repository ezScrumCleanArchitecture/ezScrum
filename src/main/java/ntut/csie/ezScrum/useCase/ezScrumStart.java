package ntut.csie.ezScrum.useCase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

@SuppressWarnings("serial")
public class ezScrumStart extends HttpServlet{
	
	private static ApplicationContext context;
	
	public static void main(String[] args) throws ServletException
    {
		System.out.println("ezScrum Start!");
		context = ApplicationContext.getInstance();
    }
}
