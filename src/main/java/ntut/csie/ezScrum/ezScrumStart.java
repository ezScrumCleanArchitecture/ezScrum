package ntut.csie.ezScrum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import ntut.csie.ezScrum.useCase.ApplicationContext;

@SuppressWarnings("serial")
public class ezScrumStart extends HttpServlet{
	
	private static ApplicationContext context;
	
	public static void main(String[] args) throws ServletException
    {
		System.out.println("ezScrum Start!");
		context = ApplicationContext.getInstance();
    }
}
