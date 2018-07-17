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
import ntut.csie.ezScrum.model.product.Product;
import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.restfulAPI.backlogItem.AddBacklogItemRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.backlogItem.AssignBacklogItemRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.backlogItem.DeleteBacklogItemRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.backlogItem.EditBacklogItemRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.backlogItem.GetAllBacklogItemRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.backlogItem.GetAllCommittedBacklogItemRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.backlogItem.GetAllNotYetCommittedBacklogItemRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.backlogItem.MoveStoryCardRestfulAPI;
import ntut.csie.ezScrum.unitTest.factory.TestFactory;
import ntut.csie.ezScrum.useCase.ApplicationContext;
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
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllBacklogItemDTO;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllBacklogItemOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllCommittedBacklogItemDTO;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllCommittedBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllCommittedBacklogItemOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllNotYetCommittedBacklogItemDTO;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllNotYetCommittedBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllNotYetCommittedBacklogItemOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.MoveStoryCardInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.MoveStoryCardOutput;

public class BacklogItemUseCaseTest {
	private ApplicationContext context;

	private TestFactory testFactory;
	private String productId;

	@Before
	public void setUp() {
		context = ApplicationContext.getInstance();
		testFactory = new TestFactory();

		Product product = testFactory.getNewProduct();
		productId = product.getProductId();
	}
	
	@After
	public void tearDown() {
		context.clearProducts();
		context.clearSprints();
		context.clearBacklogItems();
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
		
		AddBacklogItemUseCase addBacklogItemUseCase = new AddBacklogItemUseCaseImpl(context);
		addBacklogItemUseCase.execute(input, output);
		
		
		String expectedException = "The description of the backlog item should not be null.";
		assertEquals(expectedException, stream.toString());
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
		List<GetAllBacklogItemDTO> backlogItemList = output.getBacklogItemList();
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
		List<GetAllBacklogItemDTO> backlogItemList = output.getBacklogItemList();
		for(int i=0; i<backlogItemList.size(); i++) {
			assertEquals(i+1, backlogItemList.get(i).getOrderId());
		}
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
		List<GetAllNotYetCommittedBacklogItemDTO> notYetCommittedBacklogItemList = getAllNotYetCommittedBacklogItemOutput.getNotYetCommittedBacklogItemList();
		
		GetAllCommittedBacklogItemOutput getAllCommittedBacklogItemOutput = getAllCommittedBacklogItem(sprintId);
		List<GetAllCommittedBacklogItemDTO> committedBacklogItemList = getAllCommittedBacklogItemOutput.getCommittedBacklogItemList();
		
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
	
	private AddBacklogItemOutput addNewBacklogItemWithRequiredParamemter(String description) {
		AddBacklogItemInput input = new AddBacklogItemUseCaseImpl();
		input.setDescription(description);
		input.setProductId(productId);

		AddBacklogItemOutput output = new AddBacklogItemRestfulAPI();
		
		AddBacklogItemUseCase addBacklogItemUseCase = new AddBacklogItemUseCaseImpl(context);
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
		
		AddBacklogItemUseCase addBacklogItemUseCase = new AddBacklogItemUseCaseImpl(context);
		addBacklogItemUseCase.execute(input, output);
		
		return output;
	}
	
	private GetAllBacklogItemOutput getAllBacklogItem(){
		GetAllBacklogItemInput input = new GetAllBacklogItemUseCaseImpl();
		input.setProductId(productId);
		
		GetAllBacklogItemOutput output = new GetAllBacklogItemRestfulAPI();
		
		GetAllBacklogItemUseCase getAllBacklogItemUseCase = new GetAllBacklogItemUseCaseImpl(context);
		getAllBacklogItemUseCase.execute(input, output);
		
		return output;
	}
	
	private void assignBacklogItem(String backlogItemId, String sprintId) {
		AssignBacklogItemInput input = new AssignBacklogItemUseCaseImpl(context);
		input.setBacklogItemId(backlogItemId);
		input.setSprintId(sprintId);
		
		AssignBacklogItemOutput output = new AssignBacklogItemRestfulAPI();
		
		AssignBacklogItemUseCase assignBacklogItemUseCase = new AssignBacklogItemUseCaseImpl(context);
		assignBacklogItemUseCase.execute(input, output);
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
		
		EditBacklogItemUseCase editBacklogItemUseCase = new EditBacklogItemUseCaseImpl(context);
		editBacklogItemUseCase.execute(input, output);
		
		return output;
	}
	
	private DeleteBacklogItemOutput deleteBacklogItem(String backlogItemId) {
		DeleteBacklogItemInput input = new DeleteBacklogItemUseCaseImpl();
		input.setBacklogItemId(backlogItemId);
		
		DeleteBacklogItemOutput output = new DeleteBacklogItemRestfulAPI();
		
		DeleteBacklogItemUseCase deleteBacklogItemUseCase = new DeleteBacklogItemUseCaseImpl(context);
		deleteBacklogItemUseCase.execute(input, output);
		
		return output;
	}
	
	private GetAllNotYetCommittedBacklogItemOutput getAllNotYetCommittedBacklogItem(){
		GetAllNotYetCommittedBacklogItemInput input = new GetAllNotYetCommittedBacklogItemUseCaseImpl();
		input.setProductId(productId);
		
		GetAllNotYetCommittedBacklogItemOutput output = new GetAllNotYetCommittedBacklogItemRestfulAPI();
		
		GetAllNotYetCommittedBacklogItemUseCase getAllNotYetCommittedBacklogItemUseCase = new GetAllNotYetCommittedBacklogItemUseCaseImpl(context);
		getAllNotYetCommittedBacklogItemUseCase.execute(input, output);
		
		return output;
	}
	
	private GetAllCommittedBacklogItemOutput getAllCommittedBacklogItem(String sprintId){
		GetAllCommittedBacklogItemInput input = new GetAllCommittedBacklogItemUseCaseImpl();
		input.setProductId(productId);
		input.setSprintId(sprintId);
		
		GetAllCommittedBacklogItemOutput output = new GetAllCommittedBacklogItemRestfulAPI();
		
		GetAllCommittedBacklogItemUseCase getAllCommittedBacklogItemUseCase = new GetAllCommittedBacklogItemUseCaseImpl(context);
		getAllCommittedBacklogItemUseCase.execute(input, output);
		
		return output;
	}
	
	private MoveStoryCardOutput moveStoryCard(String backlogItemId, String status) {
		MoveStoryCardInput input = new MoveStoryCardUseCaseImpl();
		input.setBacklogItemId(backlogItemId);
		input.setStatus(status);
		
		MoveStoryCardOutput output = new MoveStoryCardRestfulAPI();
		
		MoveStoryCardUseCase moveStoryCardUseCase = new MoveStoryCardUseCaseImpl(context);
		moveStoryCardUseCase.execute(input, output);
		
		return output;
	}
}
