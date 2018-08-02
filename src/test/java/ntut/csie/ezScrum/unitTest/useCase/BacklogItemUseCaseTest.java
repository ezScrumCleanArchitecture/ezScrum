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
import ntut.csie.ezScrum.restfulAPI.backlogItem.AddBacklogItemRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.backlogItem.AssignBacklogItemRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.backlogItem.DeleteBacklogItemRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.backlogItem.EditBacklogItemRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.backlogItem.GetAllBacklogItemRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.backlogItem.GetAllCommittedBacklogItemRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.backlogItem.GetAllNotYetCommittedBacklogItemRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.backlogItem.MoveStoryCardRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.history.GetAllHistoryRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.task.AddTaskRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.task.DeleteTaskRestfulAPI;
import ntut.csie.ezScrum.unitTest.factory.TestFactory;
import ntut.csie.ezScrum.unitTest.repository.FakeBacklogItemRepository;
import ntut.csie.ezScrum.unitTest.repository.FakeHistoryRepository;
import ntut.csie.ezScrum.unitTest.repository.FakeProductRepository;
import ntut.csie.ezScrum.unitTest.repository.FakeSprintRepository;
import ntut.csie.ezScrum.unitTest.repository.FakeTaskRepository;
import ntut.csie.ezScrum.useCase.backlogItem.AddBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.AddBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.AssignBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.AssignBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.DeleteBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.DeleteBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.EditBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.EditBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.GetAllBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.GetAllBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.GetAllCommittedBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.GetAllCommittedBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.GetAllNotYetCommittedBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.GetAllNotYetCommittedBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.MoveStoryCardUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.MoveStoryCardUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.io.AddBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.AddBacklogItemOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.AssignBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.AssignBacklogItemOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.DeleteBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.DeleteBacklogItemOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.EditBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.EditBacklogItemOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.BacklogItemModel;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllBacklogItemOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.CommittedBacklogItemModel;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllCommittedBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllCommittedBacklogItemOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.NotYetCommittedBacklogItemModel;
import ntut.csie.ezScrum.useCase.history.GetAllHistoryUseCase;
import ntut.csie.ezScrum.useCase.history.GetAllHistoryUseCaseImpl;
import ntut.csie.ezScrum.useCase.history.io.GetAllHistoryInput;
import ntut.csie.ezScrum.useCase.history.io.GetAllHistoryOutput;
import ntut.csie.ezScrum.useCase.history.io.HistoryModel;
import ntut.csie.ezScrum.useCase.task.AddTaskUseCase;
import ntut.csie.ezScrum.useCase.task.AddTaskUseCaseImpl;
import ntut.csie.ezScrum.useCase.task.DeleteTaskUseCase;
import ntut.csie.ezScrum.useCase.task.DeleteTaskUseCaseImpl;
import ntut.csie.ezScrum.useCase.task.io.AddTaskInput;
import ntut.csie.ezScrum.useCase.task.io.AddTaskOutput;
import ntut.csie.ezScrum.useCase.task.io.DeleteTaskInput;
import ntut.csie.ezScrum.useCase.task.io.DeleteTaskOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllNotYetCommittedBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllNotYetCommittedBacklogItemOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.MoveStoryCardInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.MoveStoryCardOutput;

public class BacklogItemUseCaseTest {
	
	private FakeProductRepository fakeProductRepository;
	private FakeSprintRepository fakeSprintRepository;
	private FakeBacklogItemRepository fakeBacklogItemRepository;
	private FakeTaskRepository fakeTaskRepository;
	private FakeHistoryRepository fakeHistoryRepository;

	private TestFactory testFactory;
	private String productId;

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
	}
	
	@After
	public void tearDown() {
		fakeProductRepository.clearAll();
		fakeSprintRepository.clearAll();
		fakeBacklogItemRepository.clearAll();
		fakeHistoryRepository.clearAll();
	}

	@Test
	public void Should_Success_When_AddBacklogItemWithRequiredParamemter() {
		String description = "As a ezScrum developer, I want to test addBacklogItem.";
		
		AddBacklogItemOutput output = addNewBacklogItemWithRequiredParamemter(description);

		boolean isAddSuccess = output.isAddSuccess();
		assertTrue(isAddSuccess);
	}
	
	@Test
	public void Should_Success_When_AddBacklogItemWithAllParamemter() {
		String description = "As a ezScrum developer, I want to test addBacklogItem.";
		int estimate = 13;
		int importance = 90;
		String notes = "This is the notes for this backlog item.";
		
		AddBacklogItemOutput output = addNewBacklogItemWithAllParamemter(description, estimate, importance, notes);
		
		boolean isAddSuccess = output.isAddSuccess();
		assertTrue(isAddSuccess);
	}
	
	@Test(expected = Exception.class)
	public void Should_ThrowExcpetion_When_AddBacklogItemWithEmptyParamemter() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(stream);
		System.setOut(printStream);
		
		AddBacklogItemInput input = new AddBacklogItemUseCaseImpl();
		input.setProductId(productId);
		
		AddBacklogItemOutput output = new AddBacklogItemRestfulAPI();
		
		AddBacklogItemUseCase addBacklogItemUseCase = new AddBacklogItemUseCaseImpl(fakeBacklogItemRepository, fakeHistoryRepository);
		addBacklogItemUseCase.execute(input, output);
		
		
		String expectedException = "The description of the backlog item should not be null.";
		assertEquals(expectedException, stream.toString());
	}
	
	@Test
	public void Should_HasCreateHistory_When_AddBacklogItem() {
		String description = "As a ezScrum developer, I want to test addBacklogItem.";
		
		AddBacklogItemOutput addBacklogItemOutput = addNewBacklogItemWithRequiredParamemter(description);
		String backlogItemId = addBacklogItemOutput.getBacklogItemId();
		
		GetAllHistoryOutput getAllHistoryOutput = getAllHistory(backlogItemId);
		List<HistoryModel> historyList = getAllHistoryOutput.getHistoryList();
		
		String expectedDescription = "Create " + IssueType.backlogItem;
		int numberOfHistories = 1;
		assertEquals(Type.create, historyList.get(0).getType());
		assertEquals(expectedDescription, historyList.get(0).getDescription());
		assertEquals(numberOfHistories, historyList.size());
	}

	@Test
	public void Should_ReturnBacklogItemList_When_GetAllBacklogItem() {
		String[] description = {"As a ezScrum developer, I want to get the first backlog item.",
				"As a ezScrum developer, I want to get the second backlog item.",
				"As a ezScrum developer, I want to get the third backlog item."
		};
		
		int numberOfBacklogItems = description.length;
		
		for(int i=0; i<numberOfBacklogItems; i++) {
			addNewBacklogItemWithRequiredParamemter(description[i]);
		}
		
		GetAllBacklogItemOutput output = getAllBacklogItem();
		List<BacklogItemModel> backlogItemList = output.getBacklogItemList();
		
		for(int i=0; i<backlogItemList.size(); i++) {
			assertEquals(description[i], backlogItemList.get(i).getDescription());
		}
		assertEquals(numberOfBacklogItems, backlogItemList.size());
	}
	
	@Test
	public void Should_SetSprintIdForBacklogItem_When_AssignBacklogItemToSprint() {
		String description = "As a ezScrum developer, I want to test addBacklogItem.";
		BacklogItem backlogItem = testFactory.getNewBacklogItem(productId, description);
		String backlogItemId = backlogItem.getBacklogItemId();
		
		String goal = "This is the goal of this sprint.";
		int interval = 2;
		String startDate = "2018-05-05";
		String endDate = "2018-05-19";
		String demoDate = "2018-05-19";
		Sprint sprint = testFactory.getNewSprint(productId, goal, interval, startDate, endDate, demoDate);
		String sprintId = sprint.getSprintId();
		
		assignBacklogItem(backlogItemId, sprintId);
		
		assertEquals(sprintId, backlogItem.getSprintId());
	}
	
	@Test
	public void Should_HasAssignToSprintHistory_When_AssignBacklogItemToSprint() {
		String description = "As a ezScrum developer, I want to test addBacklogItem.";
		BacklogItem backlogItem = testFactory.getNewBacklogItem(productId, description);
		String backlogItemId = backlogItem.getBacklogItemId();
		
		String goal = "This is the goal of this sprint.";
		int interval = 2;
		String startDate = "2018-05-05";
		String endDate = "2018-05-19";
		String demoDate = "2018-05-19";
		Sprint sprint = testFactory.getNewSprint(productId, goal, interval, startDate, endDate, demoDate);
		String sprintId = sprint.getSprintId();
		
		assignBacklogItem(backlogItemId, sprintId);
		
		GetAllHistoryOutput getAllHistoryOutput = getAllHistory(backlogItemId);
		List<HistoryModel> historyList = getAllHistoryOutput.getHistoryList();
		
		String expectedDescription = "Assign to Sprint \"" + goal + "\"";
		int numberOfHistories = 1;
		assertEquals(Type.assignToSprint, historyList.get(0).getType());
		assertEquals(expectedDescription, historyList.get(0).getDescription());
		assertEquals(numberOfHistories, historyList.size());
	}
	
	@Test
	public void Should_Success_When_EditBacklogItem() {
		String description = "As a ezScrum developer, I want to test addBacklogItem.";
		BacklogItem backlogItem = testFactory.getNewBacklogItem(productId, description);
		String backlogItemId = backlogItem.getBacklogItemId();
		
		String editedDescription = "As a user, I want to edit backlog item.";
		int editedEstimate = 8;
		int editedImportance = 95;
		String editedNotes = "This is the notes about editing backlog item.";
		
		EditBacklogItemOutput output = editBacklogItem(backlogItemId, editedDescription, editedEstimate, editedImportance, editedNotes);
		
		boolean isEditSuccess = output.isEditSuccess();
		assertTrue(isEditSuccess);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_EditNotExistBacklogItem() {
		String editedDescription = "As a user, I want to edit backlog item.";
		int editedEstimate = 8;
		int editedImportance = 95;
		String editedNotes = "This is the notes about editing backlog item.";
		
		EditBacklogItemOutput output = editBacklogItem(null, editedDescription, editedEstimate, editedImportance, editedNotes);
		
		boolean isEditSuccess = output.isEditSuccess();
		String expectedErrorMessage = "Sorry, the backlog item is not exist.";
		assertFalse(isEditSuccess);
		assertEquals(expectedErrorMessage, output.getErrorMessage());
	}
	
	@Test
	public void Should_HasEditHistory_When_EditBacklogItem() {
		String description = "As a ezScrum developer, I want to test addBacklogItem.";
		BacklogItem backlogItem = testFactory.getNewBacklogItem(productId, description);
		String backlogItemId = backlogItem.getBacklogItemId();
		
		String editedDescription = "As a user, I want to edit backlog item.";
		int editedEstimate = 3;
		int editedImportance = 95;
		String editedNotes = "This is the notes about editing backlog item.";
		
		editBacklogItem(backlogItemId, editedDescription, editedEstimate, editedImportance, editedNotes);
		
		GetAllHistoryOutput getAllHistoryOutput = getAllHistory(backlogItemId);
		List<HistoryModel> historyList = getAllHistoryOutput.getHistoryList();
		
		String expectedEditDescription = "\"" + description + "\" => \"" + editedDescription + "\"";
		String expectedEditEstimate = "8 => " + editedEstimate;
		String expectedEditImportance = "90 => " + editedImportance;
		String expectedEditNotes = "\"The note of backlog item.\" => \"" + editedNotes + "\"";
		int numberOfHistories = 4;
		assertEquals(Type.editDescription, historyList.get(0).getType());
		assertEquals(Type.editEstimate, historyList.get(1).getType());
		assertEquals(Type.editImportance, historyList.get(2).getType());
		assertEquals(Type.editNotes, historyList.get(3).getType());
		assertEquals(expectedEditDescription, historyList.get(0).getDescription());
		assertEquals(expectedEditEstimate, historyList.get(1).getDescription());
		assertEquals(expectedEditImportance, historyList.get(2).getDescription());
		assertEquals(expectedEditNotes, historyList.get(3).getDescription());
		assertEquals(numberOfHistories, historyList.size());
	}
	
	@Test
	public void Should_Success_When_DeleteBacklogItem() {
		String description = "As a ezScrum developer, I want to test addBacklogItem.";
		BacklogItem backlogItem = testFactory.getNewBacklogItem(productId, description);
		String backlogItemId = backlogItem.getBacklogItemId();
		
		DeleteBacklogItemOutput output = deleteBacklogItem(backlogItemId);
		
		boolean isDeleteSuccess = output.isDeleteSuccess();
		assertTrue(isDeleteSuccess);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_DeleteNotExistBacklogItem() {
		DeleteBacklogItemOutput output = deleteBacklogItem(null);
		
		boolean isDeleteSuccess = output.isDeleteSuccess();
		String expectedDeleteMessage = "Sorry, the backlog item is not exist.";
		assertFalse(isDeleteSuccess);
		assertEquals(expectedDeleteMessage, output.getErrorMessage());
	}
	
	@Test
	public void Should_OrderIdUpdated_When_DeleteBacklogItem() {
		String[] description = {"As a ezScrum developer, I want to get the first backlog item.",
				"As a ezScrum developer, I want to get the second backlog item.",
				"As a ezScrum developer, I want to get the third backlog item."
		};
		int numberOfBacklogItems = description.length;
		BacklogItem[] backlogItems = new BacklogItem[numberOfBacklogItems];
		for(int i=0; i<numberOfBacklogItems; i++) {
			backlogItems[i] = testFactory.getNewBacklogItem(productId, description[i]);
		}
		
		String deleteBacklogItemId = backlogItems[1].getBacklogItemId();
		deleteBacklogItem(deleteBacklogItemId);
		
		GetAllBacklogItemOutput output = getAllBacklogItem();
		List<BacklogItemModel> backlogItemList = output.getBacklogItemList();
		
		for(int i=0; i<backlogItemList.size(); i++) {
			assertEquals(i+1, backlogItemList.get(i).getOrderId());
		}
	}
	
	@Test
	public void Should_HasNotHistory_When_DeleteBacklogItem() {
		String description = "As a ezScrum developer, I want to test addBacklogItem.";
		
		AddBacklogItemOutput addBacklogItemOutput = addNewBacklogItemWithRequiredParamemter(description);
		
		String backlogItemId = addBacklogItemOutput.getBacklogItemId();
		
		deleteBacklogItem(backlogItemId);
		
		GetAllHistoryOutput getAllHistoryOutput = getAllHistory(backlogItemId);
		List<HistoryModel> historyList = getAllHistoryOutput.getHistoryList();
		
		int numberOfHistories = 0;
		assertEquals(numberOfHistories, historyList.size());
	}
	
	@Test
	public void Should_IdentifyBacklogItemCommittedOrNot_When_GetBacklogItems() {
		String description = "As a ezScrum developer, I want to test addBacklogItem.";
		BacklogItem[] backlogItems = new BacklogItem[3];
		for(int i=0; i<backlogItems.length; i++) {
			backlogItems[i] = testFactory.getNewBacklogItem(productId, description);
		}
		
		String goal = "This is the goal of this sprint.";
		int interval = 2;
		String startDate = "2018-05-05";
		String endDate = "2018-05-19";
		String demoDate = "2018-05-19";
		Sprint sprint = testFactory.getNewSprint(productId, goal, interval, startDate, endDate, demoDate);
		String sprintId = sprint.getSprintId();
		
		assignBacklogItem(backlogItems[1].getBacklogItemId(), sprintId);
		assignBacklogItem(backlogItems[2].getBacklogItemId(), sprintId);
		
		GetAllNotYetCommittedBacklogItemOutput getAllNotYetCommittedBacklogItemOutput = getAllNotYetCommittedBacklogItem();
		List<NotYetCommittedBacklogItemModel> notYetCommittedBacklogItemList = getAllNotYetCommittedBacklogItemOutput.getNotYetCommittedBacklogItemList();
		
		GetAllCommittedBacklogItemOutput getAllCommittedBacklogItemOutput = getAllCommittedBacklogItem(sprintId);
		List<CommittedBacklogItemModel> committedBacklogItemList = getAllCommittedBacklogItemOutput.getCommittedBacklogItemList();
		
		for(int i=0; i<notYetCommittedBacklogItemList.size(); i++) {
			String expectedNotYetCommittedBacklogItemId = backlogItems[i].getBacklogItemId();
			assertEquals(expectedNotYetCommittedBacklogItemId, notYetCommittedBacklogItemList.get(i).getBacklogItemId());
		}
		assertEquals(1, notYetCommittedBacklogItemList.size());
		for(int i=0; i<committedBacklogItemList.size(); i++) {
			String expectedCommittedBacklogItemId = backlogItems[i + 1].getBacklogItemId();
			assertEquals(expectedCommittedBacklogItemId, committedBacklogItemList.get(i).getBacklogItemId());
		}
		assertEquals(2, committedBacklogItemList.size());
	}
	
	@Test
	public void Should_OrderByImportance_When_GetAllCommittedBacklogItem() {
		String[] description = {"As a ezScrum developer, I want to get the first backlog item.",
				"As a ezScrum developer, I want to get the second backlog item.",
				"As a ezScrum developer, I want to get the third backlog item."
		};
		int[] estimate = {3, 8, 5};
		int[] importance = {85, 80, 90};
		int numberOfBacklogItems = description.length;
		String[] backlogItemIds = new String[numberOfBacklogItems];
		for(int i=0; i<numberOfBacklogItems; i++) {
			AddBacklogItemOutput addBacklogItemOutput = addNewBacklogItemWithAllParamemter(description[i], estimate[i], importance[i], "");
			backlogItemIds[i] = addBacklogItemOutput.getBacklogItemId();
		}
		
		String goal = "This is the goal of this sprint.";
		int interval = 2;
		String startDate = "2018-05-05";
		String endDate = "2018-05-19";
		String demoDate = "2018-05-19";
		Sprint sprint = testFactory.getNewSprint(productId, goal, interval, startDate, endDate, demoDate);
		String sprintId = sprint.getSprintId();
		for(int i=0; i<numberOfBacklogItems; i++) {
			assignBacklogItem(backlogItemIds[i], sprintId);
		}
		
		GetAllCommittedBacklogItemOutput getAllCommittedBacklogItemOutput = getAllCommittedBacklogItem(sprintId);
		List<CommittedBacklogItemModel> committedBacklogItemList = getAllCommittedBacklogItemOutput.getCommittedBacklogItemList();
		
		assertEquals(backlogItemIds[2], committedBacklogItemList.get(0).getBacklogItemId());
		assertEquals(backlogItemIds[0], committedBacklogItemList.get(1).getBacklogItemId());
		assertEquals(backlogItemIds[1], committedBacklogItemList.get(2).getBacklogItemId());
	}
	
	@Test
	public void Should_Success_When_MoveStoryCard() {
		String description = "As a ezScrum developer, I want to test addBacklogItem.";
		String[] status = {"To do", "Done"};
		
		BacklogItem backlogItem = testFactory.getNewBacklogItem(productId, description);
		String backlogItemId = backlogItem.getBacklogItemId();
		
		MoveStoryCardOutput output = moveStoryCard(backlogItemId, status[1]);
		
		boolean isMoveSuccess = output.isMoveSuccess();
		assertTrue(isMoveSuccess);

		MoveStoryCardOutput output2 = moveStoryCard(backlogItemId, status[0]);
		
		boolean isMoveSuccess2 = output2.isMoveSuccess();
		assertTrue(isMoveSuccess2);
	}
	
	@Test
	public void Should_HasChangeStatusHistory_When_MoveStoryCard() {
		String description = "As a ezScrum developer, I want to test addBacklogItem.";
		String[] status = {"To do", "Done"};
		
		BacklogItem backlogItem = testFactory.getNewBacklogItem(productId, description);
		String backlogItemId = backlogItem.getBacklogItemId();
		
		moveStoryCard(backlogItemId, status[1]);
		moveStoryCard(backlogItemId, status[0]);
		
		GetAllHistoryOutput getAllHistoryOutput = getAllHistory(backlogItemId);
		List<HistoryModel> historyList = getAllHistoryOutput.getHistoryList();
		
		String expectedDoneDescription = status[0] + " => " + status[1];
		String expectedToDoDescription = status[1] + " => " + status[0];
		int numberOfHistories = 2;
		assertEquals(Type.changeStatus, historyList.get(0).getType());
		assertEquals(Type.changeStatus, historyList.get(1).getType());
		assertEquals(expectedDoneDescription, historyList.get(0).getDescription());
		assertEquals(expectedToDoDescription, historyList.get(1).getDescription());
		assertEquals(numberOfHistories, historyList.size());
	}
	
	@Test
	public void Should_HasAddTaskHistory_When_AddTask() {
		String backlogItemDescription = "As a ezScrum developer, I want to test addBacklogItem.";
		String taskDescription = "Write Unit Test to test adding task.";
		
		BacklogItem backlogItem = testFactory.getNewBacklogItem(productId, backlogItemDescription);
		String backlogItemId = backlogItem.getBacklogItemId();
		
		addTask(backlogItemId, taskDescription);
		
		GetAllHistoryOutput getAllHistoryOutput = getAllHistory(backlogItemId);
		List<HistoryModel> historyList = getAllHistoryOutput.getHistoryList();
		
		String expectedDescription = "Add Task \"" + taskDescription + "\"";
		int numberOfHistories = 1;
		assertEquals(Type.addTask, historyList.get(0).getType());
		assertEquals(expectedDescription, historyList.get(0).getDescription());
		assertEquals(numberOfHistories, historyList.size());
	}
	
	@Test
	public void Should_HasRemoveTaskHistory_When_DeleteTask() {
		String backlogItemDescription = "As a ezScrum developer, I want to test addBacklogItem.";
		String taskDescription = "Write Unit Test to test adding task.";
		
		BacklogItem backlogItem = testFactory.getNewBacklogItem(productId, backlogItemDescription);
		String backlogItemId = backlogItem.getBacklogItemId();
		
		Task task = testFactory.getNewTask(backlogItemId, taskDescription);
		String taskId = task.getTaskId();
		
		deleteTask(taskId);
		
		GetAllHistoryOutput getAllHistoryOutput = getAllHistory(backlogItemId);
		List<HistoryModel> historyList = getAllHistoryOutput.getHistoryList();
		
		String expectedDescription = "Remove Task \"" + taskDescription + "\"";
		int numberOfHistories = 1;
		assertEquals(Type.removeTask, historyList.get(0).getType());
		assertEquals(expectedDescription, historyList.get(0).getDescription());
		assertEquals(numberOfHistories, historyList.size());
	}
	
	private AddBacklogItemOutput addNewBacklogItemWithRequiredParamemter(String description) {
		AddBacklogItemInput input = new AddBacklogItemUseCaseImpl();
		input.setDescription(description);
		input.setProductId(productId);

		AddBacklogItemOutput output = new AddBacklogItemRestfulAPI();
		
		AddBacklogItemUseCase addBacklogItemUseCase = new AddBacklogItemUseCaseImpl(fakeBacklogItemRepository, fakeHistoryRepository);
		addBacklogItemUseCase.execute(input, output);
		
		return output;
	}
	
	private AddBacklogItemOutput addNewBacklogItemWithAllParamemter(String description, int estimate,
			int importance, String notes) {
		AddBacklogItemInput input = new AddBacklogItemUseCaseImpl();
		input.setDescription(description);
		input.setEstimate(estimate);
		input.setImportance(importance);
		input.setNotes(notes);
		input.setProductId(productId);

		AddBacklogItemOutput output = new AddBacklogItemRestfulAPI();
		
		AddBacklogItemUseCase addBacklogItemUseCase = new AddBacklogItemUseCaseImpl(fakeBacklogItemRepository, fakeHistoryRepository);
		addBacklogItemUseCase.execute(input, output);
		
		return output;
	}
	
	private GetAllBacklogItemOutput getAllBacklogItem(){
		GetAllBacklogItemInput input = new GetAllBacklogItemUseCaseImpl();
		input.setProductId(productId);
		
		GetAllBacklogItemOutput output = new GetAllBacklogItemRestfulAPI();
		
		GetAllBacklogItemUseCase getAllBacklogItemUseCase = new GetAllBacklogItemUseCaseImpl(fakeBacklogItemRepository, fakeSprintRepository);
		getAllBacklogItemUseCase.execute(input, output);
		
		return output;
	}
	
	private AssignBacklogItemOutput assignBacklogItem(String backlogItemId, String sprintId) {
		AssignBacklogItemInput input = new AssignBacklogItemUseCaseImpl(fakeBacklogItemRepository, fakeSprintRepository, fakeHistoryRepository);
		input.setBacklogItemId(backlogItemId);
		input.setSprintId(sprintId);
		
		AssignBacklogItemOutput output = new AssignBacklogItemRestfulAPI();
		
		AssignBacklogItemUseCase assignBacklogItemUseCase = new AssignBacklogItemUseCaseImpl(fakeBacklogItemRepository, fakeSprintRepository, fakeHistoryRepository);
		assignBacklogItemUseCase.execute(input, output);
		
		return output;
	}
	
	private EditBacklogItemOutput editBacklogItem(String backlogItemId, String editedDescription, int editedEstimate,
			int editedImportance, String editedNotes) {
		EditBacklogItemInput input = new EditBacklogItemUseCaseImpl();
		input.setBacklogItemId(backlogItemId);
		input.setDescription(editedDescription);
		input.setEstimate(editedEstimate);
		input.setImportance(editedImportance);
		input.setNotes(editedNotes);
		
		EditBacklogItemOutput output = new EditBacklogItemRestfulAPI();
		
		EditBacklogItemUseCase editBacklogItemUseCase = new EditBacklogItemUseCaseImpl(fakeBacklogItemRepository, fakeHistoryRepository);
		editBacklogItemUseCase.execute(input, output);
		
		return output;
	}
	
	private DeleteBacklogItemOutput deleteBacklogItem(String backlogItemId) {
		DeleteBacklogItemInput input = new DeleteBacklogItemUseCaseImpl();
		input.setBacklogItemId(backlogItemId);
		
		DeleteBacklogItemOutput output = new DeleteBacklogItemRestfulAPI();
		
		DeleteBacklogItemUseCase deleteBacklogItemUseCase = new DeleteBacklogItemUseCaseImpl(fakeBacklogItemRepository, fakeTaskRepository, fakeHistoryRepository);
		deleteBacklogItemUseCase.execute(input, output);
		
		return output;
	}
	
	private GetAllNotYetCommittedBacklogItemOutput getAllNotYetCommittedBacklogItem(){
		GetAllNotYetCommittedBacklogItemInput input = new GetAllNotYetCommittedBacklogItemUseCaseImpl();
		input.setProductId(productId);
		
		GetAllNotYetCommittedBacklogItemOutput output = new GetAllNotYetCommittedBacklogItemRestfulAPI();
		
		GetAllNotYetCommittedBacklogItemUseCase getAllNotYetCommittedBacklogItemUseCase = new GetAllNotYetCommittedBacklogItemUseCaseImpl(fakeBacklogItemRepository);
		getAllNotYetCommittedBacklogItemUseCase.execute(input, output);
		
		return output;
	}
	
	private GetAllCommittedBacklogItemOutput getAllCommittedBacklogItem(String sprintId){
		GetAllCommittedBacklogItemInput input = new GetAllCommittedBacklogItemUseCaseImpl();
		input.setProductId(productId);
		input.setSprintId(sprintId);
		
		GetAllCommittedBacklogItemOutput output = new GetAllCommittedBacklogItemRestfulAPI();
		
		GetAllCommittedBacklogItemUseCase getAllCommittedBacklogItemUseCase = new GetAllCommittedBacklogItemUseCaseImpl(fakeBacklogItemRepository, fakeSprintRepository);
		getAllCommittedBacklogItemUseCase.execute(input, output);
		
		return output;
	}
	
	private MoveStoryCardOutput moveStoryCard(String backlogItemId, String status) {
		MoveStoryCardInput input = new MoveStoryCardUseCaseImpl();
		input.setBacklogItemId(backlogItemId);
		input.setStatus(status);
		
		MoveStoryCardOutput output = new MoveStoryCardRestfulAPI();
		
		MoveStoryCardUseCase moveStoryCardUseCase = new MoveStoryCardUseCaseImpl(fakeBacklogItemRepository, fakeHistoryRepository);
		moveStoryCardUseCase.execute(input, output);
		
		return output;
	}
	
	private AddTaskOutput addTask(String backlogItemId, String description) {
		AddTaskInput input = new AddTaskUseCaseImpl();
		input.setDescription(description);
		input.setBacklogItemId(backlogItemId);

		AddTaskOutput output = new AddTaskRestfulAPI();
		
		AddTaskUseCase addTaskUseCase = new AddTaskUseCaseImpl(fakeTaskRepository, fakeHistoryRepository);
		addTaskUseCase.execute(input, output);
		
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
	
	private GetAllHistoryOutput getAllHistory(String issueId) {
		GetAllHistoryInput input = new GetAllHistoryUseCaseImpl();
		input.setIssueId(issueId);
		
		GetAllHistoryOutput output = new GetAllHistoryRestfulAPI();
		
		GetAllHistoryUseCase getAllHistoryUseCase = new GetAllHistoryUseCaseImpl(fakeHistoryRepository);
		getAllHistoryUseCase.execute(input, output);
		
		return output;
	}
	
}
