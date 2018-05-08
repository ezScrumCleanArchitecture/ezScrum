package ntut.csie.ezScrum.unitTest;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ntut.csie.ezScrum.model.Product;
import ntut.csie.ezScrum.model.Sprint;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.Product.ProductBuilder;
import ntut.csie.ezScrum.useCase.Product.ProductManagerUseCase;
import ntut.csie.ezScrum.useCase.Sprint.SprintBuilder;
import ntut.csie.ezScrum.useCase.Sprint.SprintManagerUseCase;

public class SprintTest {
	
	private ApplicationContext context;
	private String productId;

	@Before
	public void setUp() {
		context = ApplicationContext.getInstance();
		ProductManagerUseCase productManagerUseCase = new ProductManagerUseCase(context);
		Product product = null;
		try {
			product = ProductBuilder.newInstance().
					name("ezScrum").
					comment("This is the comment for ezScrum Product.").
					build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		productId = productManagerUseCase.addProduct(product);
	}
	
	@After
	public void tearDown() {
		context.clearProducts();
		context.clearSprints();
	}
	
	@Test
	public void Should_RequiredDataInsertIntoSprint_When_AddSprintWithRequiredParamemter() {
		SprintManagerUseCase sprintManagerUseCase = new SprintManagerUseCase(context);
		String goal = "Implement the function of creating sprint.";
		String startDate = "2018-04-09";
		String endDate = "2018-04-22";
		int interval = 2;
		String demoDate = "2018-04-22";
		Sprint sprint = null;
		try {
			sprint = SprintBuilder.newInstance().
					goal(goal).
					interval(interval).
					startDate(startDate).
					demoDate(demoDate).
					productId(productId).
					build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String sprintId = sprintManagerUseCase.addSprint(sprint);
		Sprint testedSprint = context.getSprint(sprintId);
		assertEquals(sprintId, testedSprint.getSprintId());
		assertEquals(goal, testedSprint.getGoal());
		assertEquals(interval, testedSprint.getInterval());
		assertEquals(0, testedSprint.getTeamSize());
		assertEquals(startDate, testedSprint.getStartDate());
		assertEquals(endDate, testedSprint.getEndDate());
		assertEquals(demoDate, testedSprint.getDemoDate());
		assertEquals(null, testedSprint.getDemoPlace());
		assertEquals(null, testedSprint.getDailyTime());
		assertEquals(null, testedSprint.getDailyPlace());
		assertEquals(productId, testedSprint.getProductId());
	}

	@Test
	public void Should_AllDataInsertIntoSprint_When_AddSprintWithAllParamemter() {
		SprintManagerUseCase sprintManagerUseCase = new SprintManagerUseCase(context);
		String goal = "Implement the function of creating sprint.";
		int teamSize = 3;
		String startDate = "2018-04-09";
		int interval = 2;
		String endDate = "2018-04-22";
		String demoDate = "2018-04-22";
		String demoPlace = "1622";
		String dailyTime = "10:00";
		String dailyPlace = "1321";
		Sprint sprint = null;
		try {
			sprint = SprintBuilder.newInstance().
					goal(goal).
					interval(interval).
					teamSize(teamSize).
					startDate(startDate).
					endDate(endDate).
					demoDate(demoDate).
					demoPlace(demoPlace).
					dailyTime(dailyTime).
					dailyPlace(dailyPlace).
					productId(productId).
					build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String sprintId = sprintManagerUseCase.addSprint(sprint);
		Sprint testedSprint = context.getSprint(sprintId);
		assertEquals(sprintId, testedSprint.getSprintId());
		assertEquals(goal, testedSprint.getGoal());
		assertEquals(interval, testedSprint.getInterval());
		assertEquals(teamSize, testedSprint.getTeamSize());
		assertEquals(startDate, testedSprint.getStartDate());
		assertEquals(endDate, testedSprint.getEndDate());
		assertEquals(demoDate, testedSprint.getDemoDate());
		assertEquals(demoPlace, testedSprint.getDemoPlace());
		assertEquals(dailyTime, testedSprint.getDailyTime());
		assertEquals(dailyPlace, testedSprint.getDailyPlace());
		assertEquals(productId, testedSprint.getProductId());
	}
	
	@Test
	public void Should_ThrowException_When_AddSprintWithEmptyParamemter() {
		String exceptionMessage = "The goal of the sprint should not be null.\n" +
				"The interval of the sprint should not be zero.\n" +
				"The start date of the sprint should not be null.\n" +
				"The demo date of the sprint should not be null.\n";
		try {
			SprintBuilder.newInstance().
				goal(null).
				interval(0).
				startDate(null).
				demoDate(null).
				build();
		} catch (Exception e) {
			assertEquals(exceptionMessage, e.getMessage());
		}
	}
}
