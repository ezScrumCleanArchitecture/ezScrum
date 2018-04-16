package unitTest;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import factory.testData.ProductTestDataFactory;
import model.Sprint;
import useCase.ApplicationContext;
import useCase.CreateSprint;

public class SprintTest {
	
	ApplicationContext context;
	String productId;

	@Before
	public void setUp() {
		context = ApplicationContext.newInstance();
		ProductTestDataFactory productTestDataFactory = new ProductTestDataFactory(context);
		productId = productTestDataFactory.createTestData();
	}
	
	@After
	public void tearDown() {
		context.getProducts().clear();
		context.getSprints().clear();
	}

	@Test
	public void addSprintTest() {
		CreateSprint createSprintUseCase = new CreateSprint();
		String goal = "Implement the function of creating sprint.";
		int interval = 2;
		int teamSize = 3;
		String startDate = "2018-04-09";
		String endDate = "2018-04-22";
		String demoDate = "2018-04-22";
		String demoPlace = "1622";
		String dailyTime = "10:00";
		String dailyPlace = "1321";
		String sprintId = createSprintUseCase.execute(context, goal, interval, teamSize, startDate, demoDate, demoPlace, dailyTime, dailyPlace, productId);
		Sprint sprint = context.getSprints().get(sprintId);
		assertEquals(sprintId, sprint.getSprintId());
		assertEquals(goal, sprint.getGoal());
		assertEquals(interval, sprint.getInterval());
		assertEquals(teamSize, sprint.getTeamSize());
		assertEquals(startDate, sprint.getStartDate());
		assertEquals(endDate, sprint.getEndDate());
		assertEquals(demoDate, sprint.getDemoDate());
		assertEquals(demoPlace, sprint.getDemoPlace());
		assertEquals(dailyTime, sprint.getDailyTime());
		assertEquals(dailyPlace, sprint.getDailyPlace());
		assertEquals(productId, sprint.getProductId());
	}
}
