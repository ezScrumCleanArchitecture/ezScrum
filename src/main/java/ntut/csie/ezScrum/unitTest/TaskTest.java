package ntut.csie.ezScrum.unitTest;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ntut.csie.ezScrum.factory.testData.BacklogItemTestDataFactory;
import ntut.csie.ezScrum.factory.testData.SprintTestDataFactory;
import ntut.csie.ezScrum.model.Task;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.AssignBacklogItemToSprint;
import ntut.csie.ezScrum.useCase.CreateTask;

public class TaskTest {
	ApplicationContext context;
	String backlogItemId;
	String sprintId;

	@Before
	public void setUp() {
		context = ApplicationContext.newInstance();
		BacklogItemTestDataFactory backlogItemTestDataFactory = new BacklogItemTestDataFactory(this.context);
		this.backlogItemId = backlogItemTestDataFactory.createTestData();
		SprintTestDataFactory sprintTestDataFactory = new SprintTestDataFactory(this.context);
		this.sprintId = sprintTestDataFactory.createTestData();
		AssignBacklogItemToSprint assignBacklogItemToSprintUseCase = new AssignBacklogItemToSprint();
		assignBacklogItemToSprintUseCase.execute(context, sprintId, backlogItemId);
	}
	
	@After
	public void tearDown() {
		context.getProducts().clear();
		context.getBacklogItems().clear();
		context.getSprints().clear();
	}
	
	@Test
	public void AddTaskToBacklogItemTest() {
		CreateTask createTaskUseCase = new CreateTask();
		String[] description = {"Write Unit Test to test adding task.", "Create task use case.", "Fix Bug of adding task."};
		int[] estimate = {5, 3, 8};
		String[] notes = {"Please use factory pattern to add task test data.", "Use task factory to call use case.", "You can run unit test first."};
		String[] taskId = new String[description.length];
		for(int i=0; i<taskId.length ;i++) {
			taskId[i] = createTaskUseCase.execute(context, description[i], estimate[i], notes[i], backlogItemId);
			assertEquals(description[i], context.getTasks().get(taskId[i]).getDescription());
			assertEquals(estimate[i], context.getTasks().get(taskId[i]).getEstimate());
			assertEquals(notes[i], context.getTasks().get(taskId[i]).getNotes());
		}
		int count = 0;
		for(Task task: context.getTasks().values()) {
			if(task.getBacklogItemId() == backlogItemId) {
				count ++;
			}
		}
		assertEquals(taskId.length, count);
	}
}
