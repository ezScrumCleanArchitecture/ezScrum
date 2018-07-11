package ntut.csie.ezScrum.unitTest;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.model.product.Product;
import ntut.csie.ezScrum.model.product.ProductBuilder;
import ntut.csie.ezScrum.restfulAPI.backlogItem.AddBacklogItemRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.backlogItem.AssignBacklogItemRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.backlogItem.DeleteBacklogItemRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.backlogItem.EditBacklogItemRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.backlogItem.GetAllBacklogItemRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.backlogItem.GetAllCommittedBacklogItemRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.backlogItem.GetAllNotYetCommittedBacklogItemRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.backlogItem.MoveStoryCardRestfulAPI;
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
import ntut.csie.ezScrum.useCase.product.ProductManagerUseCase;
import ntut.csie.ezScrum.useCase.sprint.SprintManagerUseCase;
import ntut.csie.ezScrum.useCase.sprint.io.AddSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.AddSprintOutput;

public class BacklogItemTest {
	private ApplicationContext context;

	private String productId;
	private String sprintId;

	@Before
	public void setUp() {
		context = ApplicationContext.getInstance();
		
		ProductManagerUseCase productManagerUseCase = new ProductManagerUseCase(context);
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
		
		AddSprintInput sprintAddInput = new AddSprintInput();
		sprintAddInput.setGoal("The goal of ezScrum.");
		sprintAddInput.setInterval(2);
		sprintAddInput.setStartDate("2018-05-05");
		sprintAddInput.setDemoDate("2018-05-19");
		sprintAddInput.setDemoPlace("The place for demo ezScrum");
		sprintAddInput.setProductId(productId);
		
		AddSprintOutput sprintAddOutput = sprintManagerUseCase.addSprint(sprintAddInput);
		sprintId = sprintAddOutput.getSprintId();
	}
	
	@After
	public void tearDown() {
		context.clearProducts();
		context.clearSprints();
		context.clearBacklogItems();
	}

	@Test
	public void Should_RequiredDataInsertIntoBacklogItem_When_AddBacklogItemWithRequiredParamemter() {
		String description = "As a ezScrum developer, I want to test addBacklogItem.";
		
		AddBacklogItemInput input = new AddBacklogItemUseCaseImpl();
		input.setDescription(description);
		input.setProductId(productId);

		AddBacklogItemOutput output = new AddBacklogItemRestfulAPI();
		
		AddBacklogItemUseCase addBacklogItemUseCase = new AddBacklogItemUseCaseImpl(context);
		addBacklogItemUseCase.execute(input, output);
		
		String backlogItemId = output.getBacklogItemId();
		
		BacklogItem testedBacklogItem = context.getBacklogItem(backlogItemId);
		assertEquals(backlogItemId, testedBacklogItem.getBacklogItemId());
		assertEquals(description, testedBacklogItem.getDescription());
		assertEquals(0, testedBacklogItem.getEstimate());
		assertEquals(0, testedBacklogItem.getImportance());
		assertEquals(null, testedBacklogItem.getNotes());
	}
	
	@Test
	public void Should_AllDataInsertIntoBacklogItem_When_AddBacklogItemWithAllParamemter() {
		String description = "As a ezScrum developer, I want to test addBacklogItem.";
		int estimate = 13;
		int importance = 90;
		String notes = "This is the notes for this backlog item.";
		
		AddBacklogItemInput input = new AddBacklogItemUseCaseImpl();
		input.setDescription(description);
		input.setEstimate(estimate);
		input.setImportance(importance);
		input.setNotes(notes);
		input.setProductId(productId);

		AddBacklogItemOutput output = new AddBacklogItemRestfulAPI();
		
		AddBacklogItemUseCase addBacklogItemUseCase = new AddBacklogItemUseCaseImpl(context);
		addBacklogItemUseCase.execute(input, output);
		
		String backlogItemId = output.getBacklogItemId();
		
		BacklogItem testedBacklogItem = context.getBacklogItem(backlogItemId);
		assertEquals(backlogItemId, testedBacklogItem.getBacklogItemId());
		assertEquals(description, testedBacklogItem.getDescription());
		assertEquals(estimate, testedBacklogItem.getEstimate());
		assertEquals(importance, testedBacklogItem.getImportance());
		assertEquals(notes, testedBacklogItem.getNotes());
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
		
		
		String expected = "The description of the backlog item should not be null.";
		assertEquals(expected, stream.toString());
	}

	@Test
	public void Should_ReturnBacklogItemList_When_GetAllBacklogItem() {
		String[] description = {"As a ezScrum developer, I want to get the first backlog item.",
				"As a ezScrum developer, I want to get the second backlog item.",
				"As a ezScrum developer, I want to get the third backlog item."
		};
		AddBacklogItemUseCase addBacklogItemUseCase = new AddBacklogItemUseCaseImpl(context);
		for(int i=0; i<description.length; i++) {
			AddBacklogItemInput addBacklogItemInput = new AddBacklogItemUseCaseImpl();
			addBacklogItemInput.setDescription(description[i]);
			addBacklogItemInput.setProductId(productId);
			
			AddBacklogItemOutput addBacklogItemOutput = new AddBacklogItemRestfulAPI();
			
			addBacklogItemUseCase.execute(addBacklogItemInput, addBacklogItemOutput);
		}
		
		GetAllBacklogItemInput getAllBacklogItemInput = new GetAllBacklogItemUseCaseImpl();
		getAllBacklogItemInput.setProductId(productId);
		
		GetAllBacklogItemOutput getAllBacklogItemOutput = new GetAllBacklogItemRestfulAPI();
		
		GetAllBacklogItemUseCase getAllBacklogItemUseCase = new GetAllBacklogItemUseCaseImpl(context);
		getAllBacklogItemUseCase.execute(getAllBacklogItemInput, getAllBacklogItemOutput);
		
		List<GetAllBacklogItemDTO> backlogItemList = getAllBacklogItemOutput.getBacklogItemList();
		for(int i=0; i<backlogItemList.size(); i++) {
			assertEquals(description[i], backlogItemList.get(i).getDescription());
		}
		assertEquals(description.length, backlogItemList.size());
	}
	
	@Test
	public void Should_SetSprintIdForBacklogItem_When_AssignBacklogItemToSprint() {
		AddBacklogItemInput addBacklogItemInput = new AddBacklogItemUseCaseImpl();
		addBacklogItemInput.setDescription("The description of ezScrum.");
		addBacklogItemInput.setProductId(productId);
		
		AddBacklogItemOutput addBacklogItemOutput = new AddBacklogItemRestfulAPI();
		
		AddBacklogItemUseCase addBacklogItemUseCase = new AddBacklogItemUseCaseImpl(context);
		addBacklogItemUseCase.execute(addBacklogItemInput, addBacklogItemOutput);
		
		String backlogItemId = addBacklogItemOutput.getBacklogItemId();
		
		AssignBacklogItemInput assignBacklogItemInput = new AssignBacklogItemUseCaseImpl(context);
		assignBacklogItemInput.setBacklogItemId(backlogItemId);
		assignBacklogItemInput.setSprintId(sprintId);
		
		AssignBacklogItemOutput assignBacklogItemOutput = new AssignBacklogItemRestfulAPI();
		
		AssignBacklogItemUseCase assignBacklogItemUseCase = new AssignBacklogItemUseCaseImpl(context);
		assignBacklogItemUseCase.execute(assignBacklogItemInput, assignBacklogItemOutput);
		
		assertEquals(sprintId, context.getBacklogItem(backlogItemId).getSprintId());
	}
	
	@Test
	public void Should_UpdateData_When_EditBacklogItem() {
		String description = "As a ezScrum developer, I want to test addBacklogItem.";
		int estimate = 13;
		int importance = 90;
		String notes = "This is the notes for this backlog item.";
		
		AddBacklogItemInput addBacklogItemInput = new AddBacklogItemUseCaseImpl();
		addBacklogItemInput.setDescription(description);
		addBacklogItemInput.setEstimate(estimate);
		addBacklogItemInput.setImportance(importance);
		addBacklogItemInput.setNotes(notes);
		addBacklogItemInput.setProductId(productId);
		
		AddBacklogItemOutput addBacklogItemOutput = new AddBacklogItemRestfulAPI();
		
		AddBacklogItemUseCase addBacklogItemUseCase = new AddBacklogItemUseCaseImpl(context);
		addBacklogItemUseCase.execute(addBacklogItemInput, addBacklogItemOutput);
		
		String backlogItemId = addBacklogItemOutput.getBacklogItemId();
		
		String editedDescription = "As a user, I want to edit backlog item.";
		int editedEstimate = 8;
		int editedImportance = 95;
		String editedNotes = "This is the notes about editing backlog item.";
		
		EditBacklogItemInput editBacklogItemInput = new EditBacklogItemUseCaseImpl();
		editBacklogItemInput.setBacklogItemId(backlogItemId);
		editBacklogItemInput.setDescription(editedDescription);
		editBacklogItemInput.setEstimate(editedEstimate);
		editBacklogItemInput.setImportance(editedImportance);
		editBacklogItemInput.setNotes(editedNotes);
		
		EditBacklogItemOutput editBacklogItemOutput = new EditBacklogItemRestfulAPI();
		
		EditBacklogItemUseCase editBacklogItemUseCase = new EditBacklogItemUseCaseImpl(context);
		editBacklogItemUseCase.execute(editBacklogItemInput, editBacklogItemOutput);
		
		BacklogItem testedBacklogItem = context.getBacklogItem(backlogItemId);
		
		assertEquals(true, editBacklogItemOutput.isEditSuccess());
		assertEquals(editedDescription, testedBacklogItem.getDescription());
		assertEquals(editedEstimate, testedBacklogItem.getEstimate());
		assertEquals(editedImportance, testedBacklogItem.getImportance());
		assertEquals(editedNotes, testedBacklogItem.getNotes());
	}
	
	@Test
	public void Should_DeleteData_When_DeleteBacklogItem() {
		String description = "As a ezScrum developer, I want to test addBacklogItem.";
		int estimate = 13;
		int importance = 90;
		String notes = "This is the notes for this backlog item.";
		
		AddBacklogItemInput addBacklogItemInput = new AddBacklogItemUseCaseImpl();
		addBacklogItemInput.setDescription(description);
		addBacklogItemInput.setEstimate(estimate);
		addBacklogItemInput.setImportance(importance);
		addBacklogItemInput.setNotes(notes);
		addBacklogItemInput.setProductId(productId);
		
		AddBacklogItemOutput addBacklogItemOutput = new AddBacklogItemRestfulAPI();
		
		AddBacklogItemUseCase addBacklogItemUseCase = new AddBacklogItemUseCaseImpl(context);
		addBacklogItemUseCase.execute(addBacklogItemInput, addBacklogItemOutput);
		
		String backlogItemId = addBacklogItemOutput.getBacklogItemId();
		
		DeleteBacklogItemInput deleteBacklogItemInput = new DeleteBacklogItemUseCaseImpl();
		deleteBacklogItemInput.setBacklogItemId(backlogItemId);
		
		DeleteBacklogItemOutput deleteBacklogItemOutput = new DeleteBacklogItemRestfulAPI();
		
		DeleteBacklogItemUseCase deleteBacklogItemUseCase = new DeleteBacklogItemUseCaseImpl(context);
		deleteBacklogItemUseCase.execute(deleteBacklogItemInput, deleteBacklogItemOutput);
		
		GetAllBacklogItemInput getAllBacklogItemInput = new GetAllBacklogItemUseCaseImpl();
		getAllBacklogItemInput.setProductId(productId);
		
		GetAllBacklogItemOutput getAllBacklogItemOutput = new GetAllBacklogItemRestfulAPI();
		
		GetAllBacklogItemUseCase getAllBacklogItemUseCase = new GetAllBacklogItemUseCaseImpl(context);
		getAllBacklogItemUseCase.execute(getAllBacklogItemInput, getAllBacklogItemOutput);
		
		List<GetAllBacklogItemDTO> backlogItemList = getAllBacklogItemOutput.getBacklogItemList();
		
		assertEquals(true, deleteBacklogItemOutput.isDeleteSuccess());
		boolean isFound = false;
		for(GetAllBacklogItemDTO dto : backlogItemList) {
			if(dto.getBacklogItemId().equals(backlogItemId)) {
				isFound = true;
				break;
			}
		}
		assertEquals(false, isFound);
	}
	
	@Test
	public void Should_OrderIdUpdated_When_DeleteBacklogItem() {
		String[] description = {"As a ezScrum developer, I want to get the first backlog item.",
				"As a ezScrum developer, I want to get the second backlog item.",
				"As a ezScrum developer, I want to get the third backlog item."
		};
		String[] backlogItemIds = new String[description.length];
		AddBacklogItemUseCase addBacklogItemUseCase = new AddBacklogItemUseCaseImpl(context);
		for(int i=0; i<description.length; i++) {
			AddBacklogItemInput addBacklogItemInput = new AddBacklogItemUseCaseImpl();
			addBacklogItemInput.setDescription(description[i]);
			addBacklogItemInput.setProductId(productId);
			
			AddBacklogItemOutput addBacklogItemOutput = new AddBacklogItemRestfulAPI();
			
			addBacklogItemUseCase.execute(addBacklogItemInput, addBacklogItemOutput);
			
			backlogItemIds[i] = addBacklogItemOutput.getBacklogItemId();
		}
		
		DeleteBacklogItemInput deleteBacklogItemInput = new DeleteBacklogItemUseCaseImpl();
		deleteBacklogItemInput.setBacklogItemId(backlogItemIds[1]);
		
		DeleteBacklogItemOutput deleteBacklogItemOutput = new DeleteBacklogItemRestfulAPI();
		
		DeleteBacklogItemUseCase deleteBacklogItemUseCase = new DeleteBacklogItemUseCaseImpl(context);
		deleteBacklogItemUseCase.execute(deleteBacklogItemInput, deleteBacklogItemOutput);
		
		GetAllBacklogItemInput getAllBacklogItemInput = new GetAllBacklogItemUseCaseImpl();
		getAllBacklogItemInput.setProductId(productId);
		
		GetAllBacklogItemOutput getAllBacklogItemOutput = new GetAllBacklogItemRestfulAPI();
		
		GetAllBacklogItemUseCase getAllBacklogItemUseCase = new GetAllBacklogItemUseCaseImpl(context);
		getAllBacklogItemUseCase.execute(getAllBacklogItemInput, getAllBacklogItemOutput);
		
		List<GetAllBacklogItemDTO> backlogItemList = getAllBacklogItemOutput.getBacklogItemList();
		for(int i=0; i<backlogItemList.size(); i++) {
			assertEquals(i+1, backlogItemList.get(i).getOrderId());
		}
	}
	
	@Test
	public void Should_IdentifyBacklogItemCommittedOrNot_When_GetBacklogItems() {
		String[] description = {"As a ezScrum developer, I want to get the first backlog item.",
				"As a ezScrum developer, I want to get the second backlog item.",
				"As a ezScrum developer, I want to get the third backlog item."
		};
		String[] backlogItemIds = new String[description.length];
		AddBacklogItemUseCase addBacklogItemUseCase = new AddBacklogItemUseCaseImpl(context);
		for(int i=0; i<description.length; i++) {
			AddBacklogItemInput addBacklogItemInput = new AddBacklogItemUseCaseImpl();
			addBacklogItemInput.setDescription(description[i]);
			addBacklogItemInput.setProductId(productId);
			
			AddBacklogItemOutput addBacklogItemOutput = new AddBacklogItemRestfulAPI();
			
			addBacklogItemUseCase.execute(addBacklogItemInput, addBacklogItemOutput);
			
			backlogItemIds[i] = addBacklogItemOutput.getBacklogItemId();
		}
		
		AssignBacklogItemInput assignBacklogItemInput = new AssignBacklogItemUseCaseImpl();
		assignBacklogItemInput.setBacklogItemId(backlogItemIds[1]);
		assignBacklogItemInput.setSprintId(sprintId);
		
		AssignBacklogItemOutput assignBacklogItemOutput = new AssignBacklogItemRestfulAPI();
		
		AssignBacklogItemUseCase assignBacklogItemUseCase = new AssignBacklogItemUseCaseImpl(context);
		assignBacklogItemUseCase.execute(assignBacklogItemInput, assignBacklogItemOutput);
		
		AssignBacklogItemInput assignBacklogItemInput2 = new AssignBacklogItemUseCaseImpl();
		assignBacklogItemInput2.setBacklogItemId(backlogItemIds[2]);
		assignBacklogItemInput2.setSprintId(sprintId);
		
		AssignBacklogItemOutput assignBacklogItemOutput2 = new AssignBacklogItemRestfulAPI();
		
		assignBacklogItemUseCase.execute(assignBacklogItemInput2, assignBacklogItemOutput2);
		
		GetAllNotYetCommittedBacklogItemInput getAllNotYetCommittedBacklogItemInput = new GetAllNotYetCommittedBacklogItemUseCaseImpl();
		getAllNotYetCommittedBacklogItemInput.setProductId(productId);
		
		GetAllNotYetCommittedBacklogItemOutput getAllNotYetCommittedBacklogItemOutput = new GetAllNotYetCommittedBacklogItemRestfulAPI();
		
		GetAllNotYetCommittedBacklogItemUseCase getAllNotYetCommittedBacklogItemUseCase = new GetAllNotYetCommittedBacklogItemUseCaseImpl(context);
		getAllNotYetCommittedBacklogItemUseCase.execute(getAllNotYetCommittedBacklogItemInput, getAllNotYetCommittedBacklogItemOutput);
		
		List<GetAllNotYetCommittedBacklogItemDTO> notYetCommittedBacklogItemList = getAllNotYetCommittedBacklogItemOutput.getNotYetCommittedBacklogItemList();
		
		GetAllCommittedBacklogItemInput getCommittedBacklogItemInput = new GetAllCommittedBacklogItemUseCaseImpl();
		getCommittedBacklogItemInput.setProductId(productId);
		getCommittedBacklogItemInput.setSprintId(sprintId);
		
		GetAllCommittedBacklogItemOutput getAllCommittedBacklogItemOutput = new GetAllCommittedBacklogItemRestfulAPI();
		
		GetAllCommittedBacklogItemUseCase getAllCommittedBacklogItemUseCase = new GetAllCommittedBacklogItemUseCaseImpl(context);
		getAllCommittedBacklogItemUseCase.execute(getCommittedBacklogItemInput, getAllCommittedBacklogItemOutput);
		
		List<GetAllCommittedBacklogItemDTO> committedBacklogItemList = getAllCommittedBacklogItemOutput.getCommittedBacklogItemList();
		
		for(int i=0; i<notYetCommittedBacklogItemList.size(); i++) {
			assertEquals(description[i], notYetCommittedBacklogItemList.get(i).getDescription());
		}
		assertEquals(1, notYetCommittedBacklogItemList.size());
		for(int i=0; i<committedBacklogItemList.size(); i++) {
			assertEquals(description[i+1], committedBacklogItemList.get(i).getDescription());
		}
		assertEquals(2, committedBacklogItemList.size());
	}
	
	@Test
	public void Should_ChangeBacklogItemStatus_When_MoveStoryCard() {
		String description = "As a ezScrum developer, I want to test moving story card.";
		String[] status = {"To do", "Done"};
		
		AddBacklogItemInput addBacklogItemInput = new AddBacklogItemUseCaseImpl();
		addBacklogItemInput.setDescription(description);
		addBacklogItemInput.setProductId(productId);
		
		AddBacklogItemOutput addBacklogItemOutput = new AddBacklogItemRestfulAPI();
		
		AddBacklogItemUseCase addBacklogItemUseCase = new AddBacklogItemUseCaseImpl(context);
		addBacklogItemUseCase.execute(addBacklogItemInput, addBacklogItemOutput);
		
		String backlogItemId = addBacklogItemOutput.getBacklogItemId();
		
		MoveStoryCardInput moveStoryCardInput = new MoveStoryCardUseCaseImpl();
		moveStoryCardInput.setBacklogItemId(backlogItemId);
		moveStoryCardInput.setStatus(status[1]);
		
		MoveStoryCardOutput moveStoryCardOutput = new MoveStoryCardRestfulAPI();
		
		MoveStoryCardUseCase moveStoryCardUseCase = new MoveStoryCardUseCaseImpl(context);
		moveStoryCardUseCase.execute(moveStoryCardInput, moveStoryCardOutput);
		
		boolean isSuccess = moveStoryCardOutput.isMoveStoryCardSuccess();
		
		BacklogItem testedBacklogItem = context.getBacklogItem(backlogItemId);
		
		assertEquals(true, isSuccess);
		assertEquals(status[1], testedBacklogItem.getStatus());
		
		MoveStoryCardInput moveStoryCardInput2 = new MoveStoryCardUseCaseImpl();
		moveStoryCardInput2.setBacklogItemId(backlogItemId);
		moveStoryCardInput2.setStatus(status[0]);
		
		MoveStoryCardOutput moveStoryCardOutput2 = new MoveStoryCardRestfulAPI();
		
		moveStoryCardUseCase.execute(moveStoryCardInput2, moveStoryCardOutput2);
		
		boolean isSuccess2 = moveStoryCardOutput2.isMoveStoryCardSuccess();
		
		BacklogItem testedBacklogItem2 = context.getBacklogItem(backlogItemId);
		assertEquals(true, isSuccess2);
		assertEquals(status[0], testedBacklogItem2.getStatus());
	}
}
