package ntut.csie.ezScrum.unitTest;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ntut.csie.ezScrum.model.BacklogItem;
import ntut.csie.ezScrum.model.Product;
import ntut.csie.ezScrum.model.Sprint;
import ntut.csie.ezScrum.model.Task;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.BacklogItem.BacklogItemBuilder;
import ntut.csie.ezScrum.useCase.BacklogItem.BacklogItemManagerUseCase;
import ntut.csie.ezScrum.useCase.Product.ProductBuilder;
import ntut.csie.ezScrum.useCase.Product.ProductManagerUseCase;
import ntut.csie.ezScrum.useCase.Sprint.SprintBuilder;
import ntut.csie.ezScrum.useCase.Sprint.SprintManagerUseCase;
import ntut.csie.ezScrum.useCase.Task.TaskBuilder;
import ntut.csie.ezScrum.useCase.Task.TaskManagerUseCase;

public class TaskTest {
	private ApplicationContext context;
	private String productId;
	private String backlogItemId;
	private String sprintId;

	@Before
	public void setUp() {
		context = ApplicationContext.getInstance();
		ProductManagerUseCase productManagerUseCase = new ProductManagerUseCase(context);
		BacklogItemManagerUseCase backlogItemManagerUseCase = new BacklogItemManagerUseCase(context);
		SprintManagerUseCase sprintManagerUseCase = new SprintManagerUseCase(context);
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
		BacklogItem backlogItem = null;
		try {
			backlogItem = BacklogItemBuilder.newInstance().
					productId(productId).
					description("The description of the ezScrum.").
					estimate(3).
					importance(70).
					notes("This is the note for this backlog item.").
					build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		backlogItemId = backlogItemManagerUseCase.addBacklogItem(backlogItem);
		Sprint sprint = null;
		try {
			sprint = SprintBuilder.newInstance().
					goal("The goal of ezScrum.").
					interval(2).
					startDate("2018-05-05").
					demoDate("2018-05-19").
					demoPlace("The place for demo ezScrum").
					build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		sprintId = sprintManagerUseCase.addSprint(sprint);
		backlogItemManagerUseCase.assignBacklogItemToSprint(sprintId, backlogItemId);
	}
	
	@After
	public void tearDown() {
		context.clearProducts();
		context.clearBacklogItems();
		context.clearSprints();
	}
	
	@Test
	public void Should_RequiredDataInsertIntoTask_When_AddTaskWithRequiredParamemter() {
		TaskManagerUseCase taskManagerUseCase = new TaskManagerUseCase(context);
		String[] description = {"Write Unit Test to test adding task.", "Create task use case.", "Fix Bug of adding task."};
		String[] taskId = new String[description.length];
		for(int i=0; i<taskId.length ;i++) {
			Task task = null;
			try {
				task = TaskBuilder.newInstance().
						description(description[i]).
						backlogItemId(backlogItemId).
						build();
			} catch (Exception e) {
				e.printStackTrace();
			}
			taskId[i] = taskManagerUseCase.addTask(task);
			Task testedTask = context.getTask(taskId[i]);
			assertEquals(description[i], testedTask.getDescription());
			assertEquals(0, testedTask.getEstimate());
			assertEquals(null, testedTask.getNotes());
		}
		int count = 0;
		for(Task task: context.getTasks()) {
			if(task.getBacklogItemId() == backlogItemId) {
				count ++;
			}
		}
		assertEquals(taskId.length, count);
	}
	
	@Test
	public void Should_AllDataInsertIntoTask_When_AddTaskWithAllParamemter() {
		TaskManagerUseCase taskManagerUseCase = new TaskManagerUseCase(context);
		String[] description = {"Write Unit Test to test adding task.", "Create task use case.", "Fix Bug of adding task."};
		int[] estimate = {5, 3, 8};
		String[] notes = {"Please use factory pattern to add task test data.", "Use task factory to call use case.", "You can run unit test first."};
		String[] taskId = new String[description.length];
		for(int i=0; i<taskId.length ;i++) {
			Task task = null;
			try {
				task = TaskBuilder.newInstance().
						description(description[i]).
						estimate(estimate[i]).
						notes(notes[i]).
						backlogItemId(backlogItemId).
						build();
			} catch (Exception e) {
				e.printStackTrace();
			}
			taskId[i] = taskManagerUseCase.addTask(task);
			Task testedTask = context.getTask(taskId[i]);
			assertEquals(description[i], testedTask.getDescription());
			assertEquals(estimate[i], testedTask.getEstimate());
			assertEquals(notes[i], testedTask.getNotes());
		}
		int count = 0;
		for(Task task: context.getTasks()) {
			if(task.getBacklogItemId() == backlogItemId) {
				count ++;
			}
		}
		assertEquals(taskId.length, count);
	}
	
	@Test
	public void Should_ThrowException_When_AddTaskWithEmptyParamemter() {
		try {
			TaskBuilder.newInstance().
				description(null).
				backlogItemId(backlogItemId).
				build();
		} catch (Exception e) {
			assertEquals("The description of the task should not be null.", e.getMessage());
		}
	}
}
