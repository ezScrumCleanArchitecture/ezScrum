
package unitTest;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import factory.testData.BacklogItemTestDataFactory;
import factory.testData.SprintTestDataFactory;
import useCase.ApplicationContext;
import useCase.AssignBacklogItemToSprint;

public class CommittedBacklogItemTest {
	ApplicationContext context;
	String sprintId;
	String backlogItemId;

	@Before
	public void setUp() {
		context = ApplicationContext.newInstance();
		//BacklogItemTestDataFactory, SprintTestDataFactory裡面就讓 ProductTestDataFactory 幫我們建立好Product了
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
