package unitTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.Sprint;
import useCase.CreateSprint;

public class SprintTest {
	
	CreateSprint newSprint;

	@Before
	public void setUp() {
		newSprint = new CreateSprint();
	}

	@Test
	public void addSprintTest() {
		String goal = "Implement the function of creating sprint.";
		int interval = 2;
		int teamSize = 3;
		String startDate = "2018-04-09";
		String endDate = "2018-04-22";
		String demoDate = "2018-04-12";
		String demoPlace = "1622";
		String dailyTime = "10:00";
		String dailyPlace = "1321";
		long productId = 1;
		Sprint sprint = newSprint.addSprint(goal, interval, teamSize, startDate, demoDate, demoPlace, dailyTime, dailyPlace, productId);
		assertEquals(1, sprint.getId());
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
