package ntut.csie.ezScrum.unitTest;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ntut.csie.ezScrum.model.Product;
import ntut.csie.ezScrum.model.Task;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.BacklogItem.BacklogItemInputDTO;
import ntut.csie.ezScrum.useCase.BacklogItem.BacklogItemManagerUseCase;
import ntut.csie.ezScrum.useCase.Product.ProductBuilder;
import ntut.csie.ezScrum.useCase.Product.ProductManagerUseCase;
import ntut.csie.ezScrum.useCase.Sprint.SprintInputDTO;
import ntut.csie.ezScrum.useCase.Sprint.SprintManagerUseCase;
import ntut.csie.ezScrum.useCase.Task.TaskBuilder;
import ntut.csie.ezScrum.useCase.Task.TaskInputDTO;
import ntut.csie.ezScrum.useCase.Task.TaskManagerUseCase;
import ntut.csie.ezScrum.useCase.Task.TaskOutputDTO;

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

		BacklogItemInputDTO backlogItemInputDTO = new BacklogItemInputDTO();
		backlogItemInputDTO.setDescription("The description of the ezScrum.");
		backlogItemInputDTO.setEstimate(3);
		backlogItemInputDTO.setImportance(70);
		backlogItemInputDTO.setNotes("This is the note for this backlog item.");
		backlogItemInputDTO.setProductId(productId);
		
		backlogItemId = backlogItemManagerUseCase.addBacklogItem(backlogItemInputDTO);
		
		SprintInputDTO sprintInputDTO = new SprintInputDTO();
		sprintInputDTO.setGoal("The goal of ezScrum.");
		sprintInputDTO.setInterval(2);
		sprintInputDTO.setStartDate("2018-05-05");
		sprintInputDTO.setDemoDate("2018-05-19");
		sprintInputDTO.setDemoPlace("The place for demo ezScrum");
		sprintInputDTO.setProductId(productId);
		
		sprintId = sprintManagerUseCase.addSprint(sprintInputDTO);
		backlogItemManagerUseCase.assignBacklogItemToSprint(sprintId, backlogItemId);
	}
	
	@After
	public void tearDown() {
		context.clearProducts();
		context.clearBacklogItems();
		context.clearSprints();
		context.clearTasks();
	}
	
	@Test
	public void Should_RequiredDataInsertIntoTask_When_AddTaskWithRequiredParamemter() {
		TaskManagerUseCase taskManagerUseCase = new TaskManagerUseCase(context);
		String[] description = {"Write Unit Test to test adding task.", "Create task use case.", "Fix Bug of adding task."};
		String[] taskId = new String[description.length];
		
		for(int i=0; i<taskId.length ;i++) {
			TaskInputDTO taskInputDTO = new TaskInputDTO();
			taskInputDTO.setDescription(description[i]);
			taskInputDTO.setBacklogItemId(backlogItemId);
			taskId[i] = taskManagerUseCase.addTask(taskInputDTO);
			
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
			TaskInputDTO taskInputDTO = new TaskInputDTO();
			taskInputDTO.setDescription(description[i]);
			taskInputDTO.setEstimate(estimate[i]);
			taskInputDTO.setNotes(notes[i]);
			taskInputDTO.setBacklogItemId(backlogItemId);
			taskId[i] = taskManagerUseCase.addTask(taskInputDTO);
			
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
	
	@Test
	public void Should_ReturnTaskList_When_GetAllTask() {
		TaskManagerUseCase taskManagerUseCase  = new TaskManagerUseCase(context);
		String[] description = {"Write Unit Test to test adding task.", "Create task use case.", "Fix Bug of adding task."};
		
		for(int i=0; i<description.length; i++) {
			TaskInputDTO taskInputDTO = new TaskInputDTO();
			taskInputDTO.setDescription(description[i]);
			taskInputDTO.setBacklogItemId(backlogItemId);
			taskManagerUseCase.addTask(taskInputDTO);
		}
		
		List<TaskOutputDTO> taskList = taskManagerUseCase.getTasks(backlogItemId);
		for(int i=0; i<taskList.size(); i++) {
			assertEquals(description[i], taskList.get(i).getDescription());
		}
		assertEquals(description.length, taskList.size());
	}
	
	@Test
	public void Should_UpdateData_When_EditTask() {
		TaskManagerUseCase taskManagerUseCase = new TaskManagerUseCase(context);
		String[] description = {"Write Unit Test to test adding task.", "Create task use case.", "Fix Bug of adding task."};
		int[] estimate = {5, 3, 8};
		String[] notes = {"Please use factory pattern to add task test data.", "Use task factory to call use case.", "You can run unit test first."};
		String[] taskId = new String[description.length];
		
		for(int i=0; i<description.length ;i++) {
			TaskInputDTO taskInputDTO = new TaskInputDTO();
			taskInputDTO.setDescription(description[i]);
			taskInputDTO.setEstimate(estimate[i]);
			taskInputDTO.setNotes(notes[i]);
			taskInputDTO.setBacklogItemId(backlogItemId);
			taskId[i] = taskManagerUseCase.addTask(taskInputDTO);
		}
		String[] editedDescription = {"Write Unit Test to test editing task.", "Update task use case.", "Fix Bug of editing task."};
		int[] editedEstimate = {8, 5, 3};
		String[] editedNotes = {"Please use factory pattern to edit task test data.", "Use task factory to edit use case.", "You can edit unit test first."};
		String[] editedTaskId = new String[description.length];
		
		for(int i=0; i<editedDescription.length ;i++) {
			TaskInputDTO editedTaskInputDTO = new TaskInputDTO();
			editedTaskInputDTO.setDescription(editedDescription[i]);
			editedTaskInputDTO.setEstimate(editedEstimate[i]);
			editedTaskInputDTO.setNotes(editedNotes[i]);
			editedTaskInputDTO.setBacklogItemId(backlogItemId);
			editedTaskId[i] = taskManagerUseCase.editTask(taskId[i], editedTaskInputDTO);
		}
		
		for(int i=0; i<editedTaskId.length; i++) {
			Task testedTask = context.getTask(editedTaskId[i]);
			assertEquals(editedTaskId[i], testedTask.getTaskId());
			assertEquals(editedDescription[i], testedTask.getDescription());
			assertEquals(editedEstimate[i], testedTask.getEstimate());
			assertEquals(editedNotes[i], testedTask.getNotes());
		}
	}
	
	@Test
	public void Should_DeleteData_When_DeleteTask() {
		TaskManagerUseCase taskManagerUseCase = new TaskManagerUseCase(context);
		String[] description = {"Write Unit Test to test adding task.", "Create task use case.", "Fix Bug of adding task."};
		int[] estimate = {5, 3, 8};
		String[] notes = {"Please use factory pattern to add task test data.", "Use task factory to call use case.", "You can run unit test first."};
		String[] taskId = new String[description.length];
		
		for(int i=0; i<taskId.length ;i++) {
			TaskInputDTO taskInputDTO = new TaskInputDTO();
			taskInputDTO.setDescription(description[i]);
			taskInputDTO.setEstimate(estimate[i]);
			taskInputDTO.setNotes(notes[i]);
			taskInputDTO.setBacklogItemId(backlogItemId);
			taskId[i] = taskManagerUseCase.addTask(taskInputDTO);
		}
		
		String deletedTaskId = taskManagerUseCase.deleteTask(taskId[1]);
		
		List<TaskOutputDTO> taskList = taskManagerUseCase.getTasks(backlogItemId);
		
		boolean isFound = false;
		for(TaskOutputDTO taskOutputDTO : taskList) {
			if(taskOutputDTO.getTaskId().equals(deletedTaskId)) {
				isFound = true;
				break;
			}
		}
		assertEquals(false, isFound);
	}
	
	@Test
	public void Should_OrderIdUpdated_When_DeleteTask() {
		TaskManagerUseCase taskManagerUseCase = new TaskManagerUseCase(context);
		String[] description = {"Write Unit Test to test adding task.", "Create task use case.", "Fix Bug of adding task."};
		int[] estimate = {5, 3, 8};
		String[] notes = {"Please use factory pattern to add task test data.", "Use task factory to call use case.", "You can run unit test first."};
		String[] taskId = new String[description.length];
		
		for(int i=0; i<taskId.length ;i++) {
			TaskInputDTO taskInputDTO = new TaskInputDTO();
			taskInputDTO.setDescription(description[i]);
			taskInputDTO.setEstimate(estimate[i]);
			taskInputDTO.setNotes(notes[i]);
			taskInputDTO.setBacklogItemId(backlogItemId);
			taskId[i] = taskManagerUseCase.addTask(taskInputDTO);
		}
		
		taskManagerUseCase.deleteTask(taskId[1]);
		
		List<TaskOutputDTO> tasks = taskManagerUseCase.getTasks(backlogItemId);
		
		for(int i=0; i<tasks.size(); i++) {
			assertEquals(i+1, tasks.get(i).getOrderId());
		}
	}
}
