package ntut.csie.ezScrum.unitTest;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ntut.csie.ezScrum.model.product.Product;
import ntut.csie.ezScrum.model.product.ProductBuilder;
import ntut.csie.ezScrum.model.task.Task;
import ntut.csie.ezScrum.model.task.TaskBuilder;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.backlogItem.BacklogItemManagerUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.io.AddBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.AddBacklogItemOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.AssignBacklogItemInput;
import ntut.csie.ezScrum.useCase.product.ProductManagerUseCase;
import ntut.csie.ezScrum.useCase.sprint.SprintManagerUseCase;
import ntut.csie.ezScrum.useCase.sprint.io.AddSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.AddSprintOutput;
import ntut.csie.ezScrum.useCase.task.TaskManagerUseCase;
import ntut.csie.ezScrum.useCase.task.io.AddTaskInput;
import ntut.csie.ezScrum.useCase.task.io.AddTaskOutput;
import ntut.csie.ezScrum.useCase.task.io.DeleteTaskInput;
import ntut.csie.ezScrum.useCase.task.io.DeleteTaskOutput;
import ntut.csie.ezScrum.useCase.task.io.EditTaskInput;
import ntut.csie.ezScrum.useCase.task.io.EditTaskOutput;
import ntut.csie.ezScrum.useCase.task.io.GetTaskInput;
import ntut.csie.ezScrum.useCase.task.io.TaskDTO;

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

		AddBacklogItemInput backlogItemInputDTO = new AddBacklogItemInput();
		backlogItemInputDTO.setDescription("The description of the ezScrum.");
		backlogItemInputDTO.setEstimate(3);
		backlogItemInputDTO.setImportance(70);
		backlogItemInputDTO.setNotes("This is the note for this backlog item.");
		backlogItemInputDTO.setProductId(productId);
		
		AddBacklogItemOutput addBacklogItemOutput = backlogItemManagerUseCase.addBacklogItem(backlogItemInputDTO);
		backlogItemId = addBacklogItemOutput.getBacklogItemId();
		
		AddSprintInput addSprintInput = new AddSprintInput();
		addSprintInput.setGoal("The goal of ezScrum.");
		addSprintInput.setInterval(2);
		addSprintInput.setStartDate("2018-05-05");
		addSprintInput.setDemoDate("2018-05-19");
		addSprintInput.setDemoPlace("The place for demo ezScrum");
		addSprintInput.setProductId(productId);
		
		AddSprintOutput sprintAddOutput = sprintManagerUseCase.addSprint(addSprintInput);
		sprintId = sprintAddOutput.getSprintId();
		
		AssignBacklogItemInput assignBacklogItemInput = new AssignBacklogItemInput();
		assignBacklogItemInput.setSprintId(sprintId);
		assignBacklogItemInput.setBacklogItemId(backlogItemId);
		
		backlogItemManagerUseCase.assignBacklogItemToSprint(assignBacklogItemInput);
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
			AddTaskInput taskInputDTO = new AddTaskInput();
			taskInputDTO.setDescription(description[i]);
			taskInputDTO.setBacklogItemId(backlogItemId);
			
			AddTaskOutput addTaskOutput = taskManagerUseCase.addTask(taskInputDTO);
			taskId[i] = addTaskOutput.getTaskId();
			
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
			AddTaskInput taskInputDTO = new AddTaskInput();
			taskInputDTO.setDescription(description[i]);
			taskInputDTO.setEstimate(estimate[i]);
			taskInputDTO.setNotes(notes[i]);
			taskInputDTO.setBacklogItemId(backlogItemId);
			
			AddTaskOutput addTaskOutput = taskManagerUseCase.addTask(taskInputDTO);
			taskId[i] = addTaskOutput.getTaskId();
			
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
			AddTaskInput taskInputDTO = new AddTaskInput();
			taskInputDTO.setDescription(description[i]);
			taskInputDTO.setBacklogItemId(backlogItemId);
			taskManagerUseCase.addTask(taskInputDTO);
		}
		
		GetTaskInput getTaskInput = new GetTaskInput();
		getTaskInput.setBacklogItemId(backlogItemId);
		
		List<TaskDTO> taskList = taskManagerUseCase.getTasks(getTaskInput);
		
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
			AddTaskInput taskInputDTO = new AddTaskInput();
			taskInputDTO.setDescription(description[i]);
			taskInputDTO.setEstimate(estimate[i]);
			taskInputDTO.setNotes(notes[i]);
			taskInputDTO.setBacklogItemId(backlogItemId);
			
			AddTaskOutput addTaskOutput = taskManagerUseCase.addTask(taskInputDTO);
			taskId[i] = addTaskOutput.getTaskId();
		}
		String[] editedDescription = {"Write Unit Test to test editing task.", "Update task use case.", "Fix Bug of editing task."};
		int[] editedEstimate = {8, 5, 3};
		String[] editedNotes = {"Please use factory pattern to edit task test data.", "Use task factory to edit use case.", "You can edit unit test first."};
		boolean[] isEditSuccess = new boolean[description.length];
		
		for(int i=0; i<editedDescription.length ;i++) {
			EditTaskInput editTaskInput = new EditTaskInput();
			editTaskInput.setTaskId(taskId[i]);
			editTaskInput.setDescription(editedDescription[i]);
			editTaskInput.setEstimate(editedEstimate[i]);
			editTaskInput.setNotes(editedNotes[i]);
			editTaskInput.setBacklogItemId(backlogItemId);
			
			EditTaskOutput editTaskOutput = taskManagerUseCase.editTask(editTaskInput);
			isEditSuccess[i] = editTaskOutput.isEditSuccess();
		}
		
		for(int i=0; i<editedDescription.length; i++) {
			Task testedTask = context.getTask(taskId[i]);
			assertEquals(true, isEditSuccess[i]);
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
			AddTaskInput taskInputDTO = new AddTaskInput();
			taskInputDTO.setDescription(description[i]);
			taskInputDTO.setEstimate(estimate[i]);
			taskInputDTO.setNotes(notes[i]);
			taskInputDTO.setBacklogItemId(backlogItemId);
			
			AddTaskOutput addTaskOutput = taskManagerUseCase.addTask(taskInputDTO);
			taskId[i] = addTaskOutput.getTaskId();
		}
		
		DeleteTaskInput deleteTaskInput = new DeleteTaskInput();
		deleteTaskInput.setTaskId(taskId[1]);
		
		DeleteTaskOutput deleteTaskOutput = taskManagerUseCase.deleteTask(deleteTaskInput);
		
		GetTaskInput getTaskInput = new GetTaskInput();
		getTaskInput.setBacklogItemId(backlogItemId);
		
		List<TaskDTO> taskList = taskManagerUseCase.getTasks(getTaskInput);
		
		assertEquals(true, deleteTaskOutput.isDeleteSuccess());
		boolean isFound = false;
		for(TaskDTO taskOutputDTO : taskList) {
			if(taskOutputDTO.getTaskId().equals(taskId)) {
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
			AddTaskInput taskInputDTO = new AddTaskInput();
			taskInputDTO.setDescription(description[i]);
			taskInputDTO.setEstimate(estimate[i]);
			taskInputDTO.setNotes(notes[i]);
			taskInputDTO.setBacklogItemId(backlogItemId);
			
			AddTaskOutput addTaskOutput = taskManagerUseCase.addTask(taskInputDTO);
			taskId[i] = addTaskOutput.getTaskId();
		}
		
		DeleteTaskInput deleteTaskInput = new DeleteTaskInput();
		deleteTaskInput.setTaskId(taskId[1]);
		
		taskManagerUseCase.deleteTask(deleteTaskInput);
		
		GetTaskInput getTaskInput = new GetTaskInput();
		getTaskInput.setBacklogItemId(backlogItemId);
		
		List<TaskDTO> tasks = taskManagerUseCase.getTasks(getTaskInput);
		
		for(int i=0; i<tasks.size(); i++) {
			assertEquals(i+1, tasks.get(i).getOrderId());
		}
	}
}
