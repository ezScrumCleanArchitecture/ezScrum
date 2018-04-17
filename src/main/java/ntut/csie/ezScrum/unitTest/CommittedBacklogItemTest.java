
package ntut.csie.ezScrum.unitTest;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ntut.csie.ezScrum.factory.testData.BacklogItemTestDataFactory;
import ntut.csie.ezScrum.factory.testData.SprintTestDataFactory;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.AssignBacklogItemToSprint;

public class CommittedBacklogItemTest {
	ApplicationContext context;
	String sprintId;
	String backlogItemId;

	@Before
	public void setUp() {
		context = ApplicationContext.newInstance();
		BacklogItemTestDataFactory backlogItemTestDataFactory = new BacklogItemTestDataFactory(this.context);
		backlogItemId = backlogItemTestDataFactory.createTestData();
		SprintTestDataFactory sprintTestDataFactory = new SprintTestDataFactory(this.context);
		sprintId = sprintTestDataFactory.createTestData();
	}
	@After
	public void tearDown() {
		context.getProducts().clear();
		context.getSprints().clear();
		context.getBacklogItems().clear();
	}
	@Test
	public void assignBacklogItemToSprintTest() {
		AssignBacklogItemToSprint assignBacklogItemToSprintUseCase = new AssignBacklogItemToSprint();
		String committedBacklogItemId = assignBacklogItemToSprintUseCase.execute(context, sprintId, backlogItemId);
		assertEquals(context.getBacklogItems().get(backlogItemId), context.getCommittedBacklogItems().get(committedBacklogItemId).getBacklogItem());
	}
}
