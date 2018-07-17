package ntut.csie.ezScrum.unitTest.entity;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ntut.csie.ezScrum.model.product.Product;
import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.unitTest.factory.TestFactory;
import ntut.csie.ezScrum.useCase.ApplicationContext;

public class SprintTest {
	private ApplicationContext context;
	private TestFactory testFactory;
	
	private Sprint sprint;
	@Before
	public void setUp() {
		context = ApplicationContext.getInstance();
		testFactory = new TestFactory();
		
		Product product = testFactory.getNewProduct();
		String productId = product.getProductId();
		
		String goal = "Implement the function of creating sprint.";
		String startDate = "2018-04-09";
		String endDate = "2018-04-22";
		int interval = 2;
		String demoDate = "2018-04-22";
		sprint = testFactory.getNewSprint(productId, goal, interval, startDate, endDate, demoDate);
	}
	
	@After
	public void tearDown() {
		context.clearProducts();
		context.clearSprints();
	}
	
	@Test
	public void Should_ReturnTrue_When_SprintIsOverlap() {
		String[] startDate = {"2018-04-10", "2018-04-07", "2018-04-09", "2018-04-15",
				"2018-04-07", "2018-04-01", "2018-04-22", "2018-04-09", "2018-04-01"};
		String[] endDate = {"2018-04-23", "2018-04-20", "2018-04-22", "2018-04-20",
				"2018-04-23", "2018-04-22", "2018-04-30", "2018-04-23", "2018-04-09"};
		assertTrue(sprint.isSprintOverlap(startDate[0], endDate[0]));
		assertTrue(sprint.isSprintOverlap(startDate[1], endDate[1]));
		assertTrue(sprint.isSprintOverlap(startDate[2], endDate[2]));
		assertTrue(sprint.isSprintOverlap(startDate[3], endDate[3]));
		assertTrue(sprint.isSprintOverlap(startDate[4], endDate[4]));
		assertTrue(sprint.isSprintOverlap(startDate[5], endDate[5]));
		assertTrue(sprint.isSprintOverlap(startDate[6], endDate[6]));
		assertTrue(sprint.isSprintOverlap(startDate[7], endDate[7]));
		assertTrue(sprint.isSprintOverlap(startDate[8], endDate[8]));
	}
	
	@Test
	public void Should_ReturnTrue_When_SprintIsOverdue() {
		assertTrue(sprint.isSprintOverdue());
	}
}
