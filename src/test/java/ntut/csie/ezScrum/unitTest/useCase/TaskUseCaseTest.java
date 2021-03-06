package ntut.csie.ezScrum.unitTest.useCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.model.history.IssueType;
import ntut.csie.ezScrum.model.history.Type;
import ntut.csie.ezScrum.model.product.Product;
import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.model.task.Task;
import ntut.csie.ezScrum.restfulAPI.history.GetAllHistoryRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.task.AddTaskRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.task.DeleteTaskRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.task.EditTaskRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.task.GetAllTaskRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.task.MoveTaskCardRestfulAPI;
import ntut.csie.ezScrum.unitTest.factory.TestFactory;
import ntut.csie.ezScrum.unitTest.repository.FakeBacklogItemRepository;
import ntut.csie.ezScrum.unitTest.repository.FakeHistoryRepository;
import ntut.csie.ezScrum.unitTest.repository.FakeProductRepository;
import ntut.csie.ezScrum.unitTest.repository.FakeSprintRepository;
import ntut.csie.ezScrum.unitTest.repository.FakeTaskRepository;
import ntut.csie.ezScrum.useCase.history.GetAllHistoryUseCase;
import ntut.csie.ezScrum.useCase.history.GetAllHistoryUseCaseImpl;
import ntut.csie.ezScrum.useCase.history.io.GetAllHistoryInput;
import ntut.csie.ezScrum.useCase.history.io.GetAllHistoryOutput;
import ntut.csie.ezScrum.useCase.history.io.HistoryModel;
import ntut.csie.ezScrum.useCase.task.AddTaskUseCase;
import ntut.csie.ezScrum.useCase.task.AddTaskUseCaseImpl;
import ntut.csie.ezScrum.useCase.task.DeleteTaskUseCase;
import ntut.csie.ezScrum.useCase.task.DeleteTaskUseCaseImpl;
import ntut.csie.ezScrum.useCase.task.EditTaskUseCase;
import ntut.csie.ezScrum.useCase.task.EditTaskUseCaseImpl;
import ntut.csie.ezScrum.useCase.task.GetAllTaskUseCase;
import ntut.csie.ezScrum.useCase.task.GetAllTaskUseCaseImpl;
import ntut.csie.ezScrum.useCase.task.MoveTaskCardUseCase;
import ntut.csie.ezScrum.useCase.task.MoveTaskCardUseCaseImpl;
import ntut.csie.ezScrum.useCase.task.io.AddTaskInput;
import ntut.csie.ezScrum.useCase.task.io.AddTaskOutput;
import ntut.csie.ezScrum.useCase.task.io.DeleteTaskInput;
import ntut.csie.ezScrum.useCase.task.io.DeleteTaskOutput;
import ntut.csie.ezScrum.useCase.task.io.EditTaskInput;
import ntut.csie.ezScrum.useCase.task.io.EditTaskOutput;
import ntut.csie.ezScrum.useCase.task.io.TaskModel;
import ntut.csie.ezScrum.useCase.task.io.GetAllTaskInput;
import ntut.csie.ezScrum.useCase.task.io.MoveTaskCardInput;
import ntut.csie.ezScrum.useCase.task.io.MoveTaskCardOutput;
import ntut.csie.ezScrum.useCase.task.io.GetAllTaskOutput;

public class TaskUseCaseTest {
	
	private FakeProductRepository fakeProductRepository;
	private FakeSprintRepository fakeSprintRepository;
	private FakeBacklogItemRepository fakeBacklogItemRepository;
	private FakeTaskRepository fakeTaskRepository;
	private FakeHistoryRepository fakeHistoryRepository;
	private TestFactory testFactory;
	private String productId;
	private String backlogItemId;
	private String sprintId;

	@Before
	public void setUp() {
		fakeProductRepository = new FakeProductRepository();
		fakeSprintRepository = new FakeSprintRepository();
		fakeBacklogItemRepository = new FakeBacklogItemRepository();
		fakeTaskRepository = new FakeTaskRepository();
		fakeHistoryRepository = new FakeHistoryRepository();
		testFactory = new TestFactory(fakeProductRepository, fakeSprintRepository, fakeBacklogItemRepository, fakeTaskRepository, null);
		
		Product product = testFactory.getNewProduct();
		productId = product.getProductId();
		
		String description = "The description of the ezScrum.";
		BacklogItem backlogItem = testFactory.getNewBacklogItem(productId, description);
		backlogItemId = backlogItem.getBacklogItemId();
		
		String goal = "The goal of ezScrum.";
		int interval = 2;
		String startDate = "2018-05-05";
		String endDate = "2018-05-19";
		String demoDate = "2018-05-19";
		Sprint sprint = testFactory.getNewSprint(productId, goal, interval, startDate, endDate, demoDate);
		sprintId = sprint.getSprintId();
		
		backlogItem.setSprintId(sprintId);
	}
	
	@After
	public void tearDown() {
		fakeProductRepository.clearAll();
		fakeSprintRepository.clearAll();
		fakeBacklogItemRepository.clearAll();
		fakeTaskRepository.clearAll();
	}
	
	@Test
	public void Should_Success_When_AddTaskWithRequiredParamemter() {
		String description = "Write Unit Test to test adding task.";
		
		AddTaskOutput output = addNewTaskWithRequiredParamemter(description);
		boolean isAddSuccess = output.isAddSuccess();
		
		assertTrue(isAddSuccess);
	}
	
	@Test
	public void Should_Success_When_AddTaskWithAllParamemter() {
		String description = "Write Unit Test to test adding task.";
		int estimate = 5;
		String notes = "Please use factory pattern to add task test data.";
		
		AddTaskOutput output = addNewTaskWithAllParamemter(description, estimate, notes);
		boolean isAddSuccess = output.isAddSuccess();
		assertTrue(isAddSuccess);
	}
	
	@Test(expected = Exception.class)
	public void Should_ThrowException_When_AddTaskWithEmptyParamemter() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(stream);
		System.setOut(printStream);
		
		AddTaskInput input = new AddTaskUseCaseImpl();
		
		AddTaskOutput output = new AddTaskRestfulAPI();
		
		AddTaskUseCase addTaskUseCase = new AddTaskUseCaseImpl(fakeTaskRepository, fakeHistoryRepository);
		addTaskUseCase.execute(input, output);
		
		String expectedException = "The description of the task should not be null.";
		assertEquals(expectedException, stream.toString());
	}
	
	@Test
	public void Should_HasCreateHistory_When_AddTask() {
		String description = "Write Unit Test to test adding task.";
		
		addNewTaskWithRequiredParamemter(description);
		
		GetAllTaskOutput output = getAllTask();
		List<TaskModel> taskList = output.getTaskList();
		String taskId = taskList.get(0).getTaskId();
		
		GetAllHistoryOutput getAllHistoryOutput = getAllHistory(taskId);
		List<HistoryModel> historyList = getAllHistoryOutput.getHistoryList();
		
		String expectedDescription = "Create " + IssueType.task;
		int numberOfHistories = 1;
		assertEquals(Type.create, historyList.get(0).getType());
		assertEquals(expectedDescription, historyList.get(0).getDescription());
		assertEquals(numberOfHistories, historyList.size());
	}
	
	@Test
	public void Should_ReturnTaskList_When_GetAllTask() {
		String[] description = {"Write Unit Test to test adding task.", "Create task use case.", "Fix Bug of adding task."};
		
		int numberOfTasks = description.length;
		for(int i=0; i<numberOfTasks; i++) {
			addNewTaskWithRequiredParamemter(description[i]);
		}
		
		GetAllTaskOutput output = getAllTask();
		
		List<TaskModel> taskList = output.getTaskList();
		
		for(int i=0; i<taskList.size(); i++) {
			assertEquals(description[i], taskList.get(i).getDescription());
		}
		assertEquals(numberOfTasks, taskList.size());
	}
	
	@Test
	public void Should_Success_When_EditTask() {
		String description = "Write Unit Test to test adding task.";
		
		Task task = testFactory.getNewTask(backlogItemId, description);
		String taskId = task.getTaskId();
		
		String editedDescription = "Write Unit Test to test editing task.";
		int editedEstimate = 8;
		int editedRemains = 5;
		String editedNotes = "his is the notes about editing backlog item.";
		
		EditTaskOutput output = editTask(taskId, editedDescription, editedEstimate, editedRemains, editedNotes);
		
		boolean isEditSuccess = output.isEditSuccess();
		assertTrue(isEditSuccess);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_EditNotExistTask() {
		String editedDescription = "As a user, I want to edit backlog item.";
		int editedEstimate = 8;
		int editedRemains = 5;
		String editedNotes = "This is the notes about editing backlog item.";
		
		EditTaskOutput output = editTask(null, editedDescription, editedEstimate, editedRemains, editedNotes);
		
		boolean isEditSuccess = output.isEditSuccess();
		String expectedErrorMessage = "Sorry, the task is not exist.";
		assertFalse(isEditSuccess);
		assertEquals(expectedErrorMessage, output.getErrorMessage());
	}
	
	@Test
	public void Should_HasEditHistory_When_EditTask() {
		String description = "Write Unit Test to test adding task.";
		Task task = testFactory.getNewTask(backlogItemId, description);
		String taskId = task.getTaskId();
		
		String editedDescription = "Write Unit Test to test editing task.";
		int editedEstimate = 8;
		int editedRemains = 13;
		String editedNotes = "his is the notes about editing backlog item.";
		
		editTask(taskId, editedDescription, editedEstimate, editedRemains, editedNotes);
		
		GetAllHistoryOutput getAllHistoryOutput = getAllHistory(taskId);
		List<HistoryModel> historyList = getAllHistoryOutput.getHistoryList();
		
		String expectedEditDescription = "\"" + description + "\" => \"" + editedDescription + "\"";
		String expectedEditEstimate = "5 => " + editedEstimate;
		String expectedEditRemains = "5 => " + editedRemains;
		String expectedEditNotes = "\"Please use factory pattern to add task test data.\" => \"" + editedNotes + "\"";
		int numberOfHistories = 4;
		assertEquals(Type.editDescription, historyList.get(0).getType());
		assertEquals(Type.editEstimate, historyList.get(1).getType());
		assertEquals(Type.editRemains, historyList.get(2).getType());
		assertEquals(Type.editNotes, historyList.get(3).getType());
		assertEquals(expectedEditDescription, historyList.get(0).getDescription());
		assertEquals(expectedEditEstimate, historyList.get(1).getDescription());
		assertEquals(expectedEditRemains, historyList.get(2).getDescription());
		assertEquals(expectedEditNotes, historyList.get(3).getDescription());
		assertEquals(numberOfHistories, historyList.size());
	}
	
	@Test
	public void Should_Success_When_DeleteTask() {
		String description = "Write Unit Test to test adding task.";
		
		Task task = testFactory.getNewTask(backlogItemId, description);
		String taskId = task.getTaskId();
		
		DeleteTaskOutput output = deleteTask(taskId);
		
		boolean isDeleteSuccess = output.isDeleteSuccess();
		assertTrue(isDeleteSuccess);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_DeleteNotExistTask() {
		DeleteTaskOutput output = deleteTask(null);
		
		boolean isDeleteSuccess = output.isDeleteSuccess();
		String expectedDeleteMessage = "Sorry, the task is not exist.";
		assertFalse(isDeleteSuccess);
		assertEquals(expectedDeleteMessage, output.getErrorMessage());
	}
	
	@Test
	public void Should_OrderIdUpdated_When_DeleteTask() {
		String[] description = {"Write Unit Test to test adding task.", "Create task use case.", "Fix Bug of adding task."};
		int numberOfTasks = description.length;
		Task[] tasks = new Task[numberOfTasks];
		
		for(int i=0; i<numberOfTasks ;i++) {
			tasks[i] = testFactory.getNewTask(backlogItemId, description[i]);
		}
		
		String deleteTaskId = tasks[1].getTaskId();
		deleteTask(deleteTaskId);
		
		GetAllTaskOutput output = getAllTask();
		List<TaskModel> taskList = output.getTaskList();
		
		for(int i=0; i<taskList.size(); i++) {
			assertEquals(i+1, taskList.get(i).getOrderId());
		}
	}
	
	@Test
	public void Should_HasNotHistory_When_DeleteTask() {
		String description = "Write Unit Test to test adding task.";
		
		addNewTaskWithRequiredParamemter(description);
		
		GetAllTaskOutput output = getAllTask();
		List<TaskModel> taskList = output.getTaskList();
		String taskId = taskList.get(0).getTaskId();
		
		deleteTask(taskId);
		
		GetAllHistoryOutput getAllHistoryOutput = getAllHistory(taskId);
		List<HistoryModel> historyList = getAllHistoryOutput.getHistoryList();
		
		int numberOfHistories = 0;
		assertEquals(numberOfHistories, historyList.size());
	}
	
	@Test
	public void Should_ChangeTaskStatus_When_MoveTaskCard() {
		String description = "Write Unit Test to test adding task.";
		String[] status = {"To do", "Doing", "Done"};
		Task task = testFactory.getNewTask(backlogItemId, description);
		String taskId = task.getTaskId();
		
		MoveTaskCardOutput output = moveTaskCard(taskId, status[1]);
		boolean isMoveSuccess = output.isMoveSuccess();
		assertTrue(isMoveSuccess);
		
		MoveTaskCardOutput output2 = moveTaskCard(taskId, status[2]);
		boolean isMoveSuccess2 = output2.isMoveSuccess();
		assertTrue(isMoveSuccess2);
		
		MoveTaskCardOutput output3 = moveTaskCard(taskId, status[1]);
		boolean isMoveSuccess3 = output3.isMoveSuccess();
		assertTrue(isMoveSuccess3);
		
		MoveTaskCardOutput output4 = moveTaskCard(taskId, status[0]);
		boolean isMoveSuccess4 = output4.isMoveSuccess();
		assertTrue(isMoveSuccess4);
	}
	
	@Test
	public void Should_HasChangeStatusHistory_When_MoveTaskCard() {
		String description = "Write Unit Test to test adding task.";
		String[] status = {"To do", "Doing", "Done"};
		Task task = testFactory.getNewTask(backlogItemId, description);
		String taskId = task.getTaskId();
		
		moveTaskCard(taskId, status[1]);
		moveTaskCard(taskId, status[2]);
		moveTaskCard(taskId, status[1]);
		moveTaskCard(taskId, status[0]);
		
		GetAllHistoryOutput getAllHistoryOutput = getAllHistory(taskId);
		List<HistoryModel> historyList = getAllHistoryOutput.getHistoryList();
		
		String expectedToDo_DoingDescription = status[0] + " => " + status[1];
		String expectedDoneDescription = status[1] + " => " + status[2];
		String expectedDone_DoingDescription = status[2] + " => " + status[1];
		String expectedToDoDescription = status[1] + " => " + status[0];
		int numberOfHistories = 4;
		assertEquals(Type.changeStatus, historyList.get(0).getType());
		assertEquals(Type.changeStatus, historyList.get(1).getType());
		assertEquals(Type.changeStatus, historyList.get(2).getType());
		assertEquals(Type.changeStatus, historyList.get(3).getType());
		assertEquals(expectedToDo_DoingDescription, historyList.get(0).getDescription());
		assertEquals(expectedDoneDescription, historyList.get(1).getDescription());
		assertEquals(expectedDone_DoingDescription, historyList.get(2).getDescription());
		assertEquals(expectedToDoDescription, historyList.get(3).getDescription());
		assertEquals(numberOfHistories, historyList.size());
	}
	
	private AddTaskOutput addNewTaskWithRequiredParamemter(String description) {
		AddTaskInput input = new AddTaskUseCaseImpl();
		input.setDescription(description);
		input.setBacklogItemId(backlogItemId);

		AddTaskOutput output = new AddTaskRestfulAPI();
		
		AddTaskUseCase addTaskUseCase = new AddTaskUseCaseImpl(fakeTaskRepository, fakeHistoryRepository);
		addTaskUseCase.execute(input, output);
		
		return output;
	}
	
	private AddTaskOutput addNewTaskWithAllParamemter(String description, int estimate, String notes) {
		AddTaskInput input = new AddTaskUseCaseImpl();
		input.setDescription(description);
		input.setEstimate(estimate);
		input.setNotes(notes);
		input.setBacklogItemId(backlogItemId);

		AddTaskOutput output = new AddTaskRestfulAPI();
		
		AddTaskUseCase addTaskUseCase = new AddTaskUseCaseImpl(fakeTaskRepository, fakeHistoryRepository);
		addTaskUseCase.execute(input, output);
		
		return output;
	}
	
	private GetAllTaskOutput getAllTask(){
		GetAllTaskInput input = new GetAllTaskUseCaseImpl();
		input.setBacklogItemId(backlogItemId);
		
		GetAllTaskOutput output = new GetAllTaskRestfulAPI();
		
		GetAllTaskUseCase getAllTaskUseCase = new GetAllTaskUseCaseImpl(fakeTaskRepository);
		getAllTaskUseCase.execute(input, output);
		
		return output;
	}
	
	private EditTaskOutput editTask(String taskId, String editedDescription,
			int editedEstimate, int editedRemains, String editedNotes) {
		EditTaskInput input = new EditTaskUseCaseImpl();
		input.setTaskId(taskId);
		input.setDescription(editedDescription);
		input.setEstimate(editedEstimate);
		input.setRemains(editedRemains);
		input.setNotes(editedNotes);
		
		EditTaskOutput output = new EditTaskRestfulAPI();
		
		EditTaskUseCase editTaskUseCase = new EditTaskUseCaseImpl(fakeTaskRepository, fakeHistoryRepository);
		editTaskUseCase.execute(input, output);
		
		return output;
	}
	
	private DeleteTaskOutput deleteTask(String taskId) {
		DeleteTaskInput input = new DeleteTaskUseCaseImpl();
		input.setTaskId(taskId);
		
		DeleteTaskOutput output = new DeleteTaskRestfulAPI();
		
		DeleteTaskUseCase deleteTaskUseCase = new DeleteTaskUseCaseImpl(fakeTaskRepository, fakeHistoryRepository);
		deleteTaskUseCase.execute(input, output);
		
		return output;
	}
	
	private MoveTaskCardOutput moveTaskCard(String taskId, String status) {
		MoveTaskCardInput input = new MoveTaskCardUseCaseImpl();
		input.setTaskId(taskId);
		input.setStatus(status);
		
		MoveTaskCardOutput output = new MoveTaskCardRestfulAPI();
		
		MoveTaskCardUseCase moveTaskCardUseCase = new MoveTaskCardUseCaseImpl(fakeTaskRepository, fakeHistoryRepository);
		moveTaskCardUseCase.execute(input, output);
		
		return output;
	}
	
	private GetAllHistoryOutput getAllHistory(String issueId) {
		GetAllHistoryInput input = new GetAllHistoryUseCaseImpl();
		input.setIssueId(issueId);
		
		GetAllHistoryOutput output = new GetAllHistoryRestfulAPI();
		
		GetAllHistoryUseCase getAllHistoryUseCase = new GetAllHistoryUseCaseImpl(fakeHistoryRepository);
		getAllHistoryUseCase.execute(input, output);
		
		return output;
	}
	
}
