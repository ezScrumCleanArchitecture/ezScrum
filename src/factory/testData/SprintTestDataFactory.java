package factory.testData;

import useCase.ApplicationContext;
import useCase.CreateSprint;

public class SprintTestDataFactory implements TestDataFactory{

	private ApplicationContext context;
	private String goal;
	private int interval;
	private int teamSize;
	private String startDate;
	private String demoDate;
	private String demoPlace;
	private String dailyTime;
	private String dailyPlace;
	private String productId;
	
	public SprintTestDataFactory(ApplicationContext context) {
		this.context = context;
		ProductTestDataFactory productTestDataFactory = new ProductTestDataFactory(this.context);
		this.productId = productTestDataFactory.createTestData();
		this.goal = "Implement the function of creating sprthis..";
		this. interval = 2;
		this. teamSize = 3;
		this.startDate = "2018-04-09";
		this.demoDate = "2018-04-22";
		this.demoPlace = "1622";
		this.dailyTime = "10:00";
		this.dailyPlace = "1321";
	}
	
	public SprintTestDataFactory(ApplicationContext context, String goal, int interval, int teamSize, String startDate,
			String demoDate, String demoPlace, String dailyTime, String dailyPlace, String productId) {
		this.context = context;
		this.goal = goal;
		this.interval = interval;
		this.teamSize = teamSize;
		this.startDate = startDate;
		this.demoDate = demoDate;
		this.demoPlace = demoPlace;
		this.dailyTime = dailyTime;
		this.dailyPlace = dailyPlace;
		this.productId = productId;
	}
	
	public String createTestData() {
		CreateSprint createSprintUseCase = new CreateSprint();
		String sprintId = createSprintUseCase.execute(context, goal, interval, teamSize, startDate, demoDate, demoPlace, dailyTime, dailyPlace, productId);
		return sprintId;
	}
}
