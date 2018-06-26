package ntut.csie.ezScrum.unitTest;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.model.backlogItem.BacklogItemBuilder;
import ntut.csie.ezScrum.model.product.Product;
import ntut.csie.ezScrum.model.product.ProductBuilder;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.backlogItem.BacklogItemManagerUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.io.AddBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.AddBacklogItemOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.AssignBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.BacklogItemDTO;
import ntut.csie.ezScrum.useCase.backlogItem.io.DeleteBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.DeleteBacklogItemOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.EditBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.EditBacklogItemOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetCommittedBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.CommittedBacklogItemDTO;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetNotYetCommittedBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.MoveStoryCardInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.MoveStoryCardOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.NotYetCommittedBacklogItemDTO;
import ntut.csie.ezScrum.useCase.product.ProductManagerUseCase;
import ntut.csie.ezScrum.useCase.sprint.SprintManagerUseCase;
import ntut.csie.ezScrum.useCase.sprint.io.AddSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.AddSprintOutput;

public class BacklogItemTest {
	private ApplicationContext context;
	private BacklogItemManagerUseCase backlogItemManagerUseCase;
	private String productId;
	private String sprintId;

	@Before
	public void setUp() {
		context = ApplicationContext.getInstance();
		backlogItemManagerUseCase = new BacklogItemManagerUseCase(context);
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
		
		AddBacklogItemInput backlogItemInputDTO = new AddBacklogItemInput();
		backlogItemInputDTO.setDescription(description);
		
		AddBacklogItemOutput backlogItemAddOutput = backlogItemManagerUseCase.addBacklogItem(backlogItemInputDTO);
		String backlogItemId = backlogItemAddOutput.getBacklogItemId();
		
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
		
		AddBacklogItemInput backlogItemInputDTO = new AddBacklogItemInput();
		backlogItemInputDTO.setDescription(description);
		backlogItemInputDTO.setEstimate(estimate);
		backlogItemInputDTO.setImportance(importance);
		backlogItemInputDTO.setNotes(notes);
		
		AddBacklogItemOutput backlogItemAddOutput = backlogItemManagerUseCase.addBacklogItem(backlogItemInputDTO);
		String backlogItemId = backlogItemAddOutput.getBacklogItemId();
		
		BacklogItem testedBacklogItem = context.getBacklogItem(backlogItemId);
		assertEquals(backlogItemId, testedBacklogItem.getBacklogItemId());
		assertEquals(description, testedBacklogItem.getDescription());
		assertEquals(estimate, testedBacklogItem.getEstimate());
		assertEquals(importance, testedBacklogItem.getImportance());
		assertEquals(notes, testedBacklogItem.getNotes());
	}
	
	@Test
	public void Should_ThrowExcpetion_When_AddBacklogItemWithEmptyParamemter() {
		try {
			BacklogItemBuilder.newInstance().
					productId(productId).
					description(null).
					build();
		} catch (Exception e) {
			assertEquals("The description of the backlog item should not be null.", e.getMessage());
		}
	}

	@Test
	public void Should_ReturnBacklogItemList_When_GetAllBacklogItem() {
		String[] description = {"As a ezScrum developer, I want to get the first backlog item.",
				"As a ezScrum developer, I want to get the second backlog item.",
				"As a ezScrum developer, I want to get the third backlog item."
		};
		for(int i=0; i<description.length; i++) {
			AddBacklogItemInput backlogItemInputDTO = new AddBacklogItemInput();
			backlogItemInputDTO.setDescription(description[i]);
			backlogItemInputDTO.setProductId(productId);
			backlogItemManagerUseCase.addBacklogItem(backlogItemInputDTO);
		}
		
		GetBacklogItemInput backlogItemGetInput = new GetBacklogItemInput();
		backlogItemGetInput.setProductId(productId);
		List<BacklogItemDTO> backlogItemList = backlogItemManagerUseCase.getBacklogItems(backlogItemGetInput);
		for(int i=0; i<backlogItemList.size(); i++) {
			assertEquals(description[i], backlogItemList.get(i).getDescription());
		}
		assertEquals(description.length, backlogItemList.size());
	}
	
	@Test
	public void Should_SetSprintIdForBacklogItem_When_AssignBacklogItemToSprint() {
		AddBacklogItemInput backlogItemInputDTO = new AddBacklogItemInput();
		backlogItemInputDTO.setProductId(productId);
		backlogItemInputDTO.setDescription("The description of ezScrum.");
		
		AddBacklogItemOutput backlogItemAddOutput = backlogItemManagerUseCase.addBacklogItem(backlogItemInputDTO);
		String backlogItemId = backlogItemAddOutput.getBacklogItemId();
		
		AssignBacklogItemInput assignBacklogItemInput = new AssignBacklogItemInput();
		assignBacklogItemInput.setSprintId(sprintId);
		assignBacklogItemInput.setBacklogItemId(backlogItemId);
		
		backlogItemManagerUseCase.assignBacklogItemToSprint(assignBacklogItemInput);
		assertEquals(sprintId, context.getBacklogItem(backlogItemId).getSprintId());
	}
	
	@Test
	public void Should_UpdateData_When_EditBacklogItem() {
		String description = "As a ezScrum developer, I want to test addBacklogItem.";
		int estimate = 13;
		int importance = 90;
		String notes = "This is the notes for this backlog item.";
		
		AddBacklogItemInput backlogItemInputDTO = new AddBacklogItemInput();
		backlogItemInputDTO.setDescription(description);
		backlogItemInputDTO.setEstimate(estimate);
		backlogItemInputDTO.setImportance(importance);
		backlogItemInputDTO.setNotes(notes);
		backlogItemInputDTO.setProductId(productId);
		
		AddBacklogItemOutput backlogItemAddOutput = backlogItemManagerUseCase.addBacklogItem(backlogItemInputDTO);
		String backlogItemId = backlogItemAddOutput.getBacklogItemId();
		
		String editedDescription = "As a user, I want to edit backlog item.";
		int editedEstimate = 8;
		int editedImportance = 95;
		String editedNotes = "This is the notes about editing backlog item.";
		
		EditBacklogItemInput editBacklogItemInput = new EditBacklogItemInput();
		
		editBacklogItemInput.setBacklogItemId(backlogItemId);
		editBacklogItemInput.setDescription(editedDescription);
		editBacklogItemInput.setEstimate(editedEstimate);
		editBacklogItemInput.setImportance(editedImportance);
		editBacklogItemInput.setNotes(editedNotes);
		
		EditBacklogItemOutput editBacklogItemOutput = backlogItemManagerUseCase.editBacklogItem(editBacklogItemInput);
		
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
		
		AddBacklogItemInput backlogItemInputDTO = new AddBacklogItemInput();
		backlogItemInputDTO.setDescription(description);
		backlogItemInputDTO.setEstimate(estimate);
		backlogItemInputDTO.setImportance(importance);
		backlogItemInputDTO.setNotes(notes);
		backlogItemInputDTO.setProductId(productId);
		
		AddBacklogItemOutput addBacklogItemOutput = backlogItemManagerUseCase.addBacklogItem(backlogItemInputDTO);
		String backlogItemId = addBacklogItemOutput.getBacklogItemId();
		
		DeleteBacklogItemInput deleteBacklogItemInput = new DeleteBacklogItemInput();
		deleteBacklogItemInput.setBacklogItemId(backlogItemId);
		
		DeleteBacklogItemOutput deleteBacklogItemOutput = backlogItemManagerUseCase.deleteBacklogItem(deleteBacklogItemInput);
		
		GetBacklogItemInput backlogItemGetInput = new GetBacklogItemInput();
		backlogItemGetInput.setProductId(productId);
		List<BacklogItemDTO> backlogItems = backlogItemManagerUseCase.getBacklogItems(backlogItemGetInput);
		
		assertEquals(true, deleteBacklogItemOutput.isDeleteSuccess());
		boolean isFound = false;
		for(BacklogItemDTO backlogItemDTO : backlogItems) {
			if(backlogItemDTO.getBacklogItemId().equals(backlogItemId)) {
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
		for(int i=0; i<description.length; i++) {
			AddBacklogItemInput backlogItemInputDTO = new AddBacklogItemInput();
			backlogItemInputDTO.setDescription(description[i]);
			backlogItemInputDTO.setProductId(productId);
			AddBacklogItemOutput backlogItemAddOutput = backlogItemManagerUseCase.addBacklogItem(backlogItemInputDTO);
			backlogItemIds[i] = backlogItemAddOutput.getBacklogItemId();
		}
		
		DeleteBacklogItemInput deleteBacklogItemInput = new DeleteBacklogItemInput();
		deleteBacklogItemInput.setBacklogItemId(backlogItemIds[1]);
		
		backlogItemManagerUseCase.deleteBacklogItem(deleteBacklogItemInput);
		
		GetBacklogItemInput backlogItemGetInput = new GetBacklogItemInput();
		backlogItemGetInput.setProductId(productId);
		List<BacklogItemDTO> backlogItemList = backlogItemManagerUseCase.getBacklogItems(backlogItemGetInput);
		
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
		for(int i=0; i<description.length; i++) {
			AddBacklogItemInput backlogItemInputDTO = new AddBacklogItemInput();
			backlogItemInputDTO.setDescription(description[i]);
			backlogItemInputDTO.setProductId(productId);
			
			AddBacklogItemOutput backlogItemAddOutput = backlogItemManagerUseCase.addBacklogItem(backlogItemInputDTO);
			backlogItemIds[i] = backlogItemAddOutput.getBacklogItemId();
		}
		
		for(int i=1; i<description.length; i++) {
			AssignBacklogItemInput assignBacklogItemInput = new AssignBacklogItemInput();
			assignBacklogItemInput.setSprintId(sprintId);
			assignBacklogItemInput.setBacklogItemId(backlogItemIds[i]);
			backlogItemManagerUseCase.assignBacklogItemToSprint(assignBacklogItemInput);
		}
		
		GetNotYetCommittedBacklogItemInput getNotYetCommittedBacklogItemInput = new GetNotYetCommittedBacklogItemInput();
		getNotYetCommittedBacklogItemInput.setProductId(productId);
		
		GetCommittedBacklogItemInput getCommittedBacklogItemInput = new GetCommittedBacklogItemInput();
		getCommittedBacklogItemInput.setProductId(productId);
		getCommittedBacklogItemInput.setSprintId(sprintId);
		
		List<NotYetCommittedBacklogItemDTO> notYetCommittedBacklogItemList = backlogItemManagerUseCase.getNotYetCommittedBacklogItems(getNotYetCommittedBacklogItemInput);
		
		List<CommittedBacklogItemDTO> committedBacklogItemList = backlogItemManagerUseCase.getCommittedBacklogItems(getCommittedBacklogItemInput);
		
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
		
		AddBacklogItemInput backlogItemInputDTO = new AddBacklogItemInput();
		backlogItemInputDTO.setDescription(description);
		
		AddBacklogItemOutput backlogItemAddOutput = backlogItemManagerUseCase.addBacklogItem(backlogItemInputDTO);
		String backlogItemId = backlogItemAddOutput.getBacklogItemId();
		
		MoveStoryCardInput moveStoryCardInput = new MoveStoryCardInput();
		moveStoryCardInput.setBacklogItemId(backlogItemId);
		moveStoryCardInput.setStatus(status[1]);
		
		MoveStoryCardOutput moveStoryCardOutput = backlogItemManagerUseCase.moveStoryCard(moveStoryCardInput);
		boolean isSuccess = moveStoryCardOutput.isMoveStoryCardSuccess();
		
		BacklogItem testedBacklogItem = context.getBacklogItem(backlogItemId);
		
		assertEquals(true, isSuccess);
		assertEquals(status[1], testedBacklogItem.getStatus());
		
		MoveStoryCardInput moveStoryCardInput2 = new MoveStoryCardInput();
		moveStoryCardInput2.setBacklogItemId(backlogItemId);
		moveStoryCardInput2.setStatus(status[0]);
		
		MoveStoryCardOutput moveStoryCardOutput2 = backlogItemManagerUseCase.moveStoryCard(moveStoryCardInput2);
		isSuccess = moveStoryCardOutput2.isMoveStoryCardSuccess();
		
		BacklogItem testedBacklogItem2 = context.getBacklogItem(backlogItemId);
		assertEquals(true, isSuccess);
		assertEquals(status[0], testedBacklogItem2.getStatus());
	}
}
