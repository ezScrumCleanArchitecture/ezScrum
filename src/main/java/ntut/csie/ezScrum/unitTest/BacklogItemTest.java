package ntut.csie.ezScrum.unitTest;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ntut.csie.ezScrum.model.BacklogItem;
import ntut.csie.ezScrum.model.Product;
import ntut.csie.ezScrum.model.Sprint;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.BacklogItem.BacklogItemBuilder;
import ntut.csie.ezScrum.useCase.BacklogItem.BacklogItemDTO;
import ntut.csie.ezScrum.useCase.BacklogItem.BacklogItemManagerUseCase;
import ntut.csie.ezScrum.useCase.Product.ProductBuilder;
import ntut.csie.ezScrum.useCase.Product.ProductManagerUseCase;
import ntut.csie.ezScrum.useCase.Sprint.SprintBuilder;
import ntut.csie.ezScrum.useCase.Sprint.SprintManagerUseCase;

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
		Sprint sprint = null;
		try {
			sprint = SprintBuilder.newInstance().
					goal("The goal of ezScrum.").
					interval(2).
					startDate("2018-05-05").
					demoDate("2018-05-19").
					demoPlace("The place for demo ezScrum").
					productId(productId).
					build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		sprintId = sprintManagerUseCase.addSprint(sprint);
	}
	
	@After
	public void tearDown() {
		context.clearProducts();
		context.clearSprints();
		context.clearBacklogItems();
	}

	@Test
	public void Should_RequiredDataInsertIntoBacklogItem_When_AddBacklogItemWithRequiredParamemter() {
		BacklogItemManagerUseCase backlogItemManagerUseCase = new BacklogItemManagerUseCase(context);
		String description = "As a ezScrum developer, I want to test addBacklogItem.";
		
		BacklogItem backlogItem = null;
		try {
			backlogItem = BacklogItemBuilder.newInstance().
					productId(productId).
					description(description).
					build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String backlogItemId = backlogItemManagerUseCase.addBacklogItem(backlogItem);
		BacklogItem testedBacklogItem = context.getBacklogItem(backlogItemId);
		assertEquals(backlogItemId, testedBacklogItem.getBacklogItemId());
		assertEquals(description, testedBacklogItem.getDescription());
		assertEquals(0, testedBacklogItem.getEstimate());
		assertEquals(0, testedBacklogItem.getImportance());
		assertEquals(null, testedBacklogItem.getNotes());
	}
	
	@Test
	public void Should_AllDataInsertIntoBacklogItem_When_AddBacklogItemWithAllParamemter() {
		BacklogItemManagerUseCase backlogItemManagerUseCase = new BacklogItemManagerUseCase(context);
		String description = "As a ezScrum developer, I want to test addBacklogItem.";
		int estimate = 13;
		int importance = 90;
		String notes = "This is the notes for this backlog item.";
		
		BacklogItem backlogItem = null;
		try {
			backlogItem = BacklogItemBuilder.newInstance().
					productId(productId).
					description(description).
					estimate(estimate).
					importance(importance).
					notes(notes).
					build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String backlogItemId = backlogItemManagerUseCase.addBacklogItem(backlogItem);
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
		BacklogItemManagerUseCase backlogItemManagerUseCase  = new BacklogItemManagerUseCase(context);
		String[] description = {"As a ezScrum developer, I want to get the first backlog item.",
				"As a ezScrum developer, I want to get the second backlog item.",
				"As a ezScrum developer, I want to get the third backlog item."
		};
		for(int i=0; i<description.length; i++) {
			BacklogItem backlogItem = null;
			try {
				backlogItem = BacklogItemBuilder.newInstance().
						productId(productId).
						description(description[i]).
						build();
			} catch (Exception e) {
				e.printStackTrace();
			}
			backlogItemManagerUseCase.addBacklogItem(backlogItem);
		}
		
		List<BacklogItemDTO> backlogItemList = backlogItemManagerUseCase.getBacklogItems(productId);
		for(int i=0; i<backlogItemList.size(); i++) {
			assertEquals(description[i], backlogItemList.get(i).getDescription());
		}
		assertEquals(description.length, backlogItemList.size());
	}
	
	@Test
	public void Should_SetSprintIdForBacklogItem_When_AssignBacklogItemToSprint() {
		BacklogItem backlogItem = null;
		try {
			backlogItem = BacklogItemBuilder.newInstance().
					productId(productId).
					description("The description of ezScrum.").
					build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BacklogItemManagerUseCase backlogItemManagerUseCase = new BacklogItemManagerUseCase(context);
		backlogItemManagerUseCase.addBacklogItem(backlogItem);
		String backlogItemId = backlogItem.getBacklogItemId();
		backlogItemManagerUseCase.assignBacklogItemToSprint(sprintId, backlogItemId);
		assertEquals(sprintId, context.getBacklogItem(backlogItemId).getSprintId());
	}
	
	@Test
	public void Should_UpdateData_When_EditBacklogItem() {
		BacklogItemManagerUseCase backlogItemManagerUseCase = new BacklogItemManagerUseCase(context);
		String description = "As a ezScrum developer, I want to test addBacklogItem.";
		int estimate = 13;
		int importance = 90;
		String notes = "This is the notes for this backlog item.";
		
		BacklogItem backlogItem = null;
		try {
			backlogItem = BacklogItemBuilder.newInstance().
					productId(productId).
					description(description).
					estimate(estimate).
					importance(importance).
					notes(notes).
					build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String backlogItemId = backlogItemManagerUseCase.addBacklogItem(backlogItem);
		
		String editedDescription = "As a user, I want to edit backlog item.";
		int editedEstimate = 8;
		int editedImportance = 95;
		String editedNotes = "This is the notes about editing backlog item.";
		
		BacklogItemDTO backlogItemDTO = new BacklogItemDTO();
		
		backlogItemDTO.setDescription(editedDescription);
		backlogItemDTO.setEstimate(editedEstimate);
		backlogItemDTO.setImportance(editedImportance);
		backlogItemDTO.setNotes(editedNotes);
		
		String editedBacklogItemId = backlogItemManagerUseCase.editBacklogItem(backlogItemId, backlogItemDTO);
		
		BacklogItem testedBacklogItem = context.getBacklogItem(editedBacklogItemId);
		
		assertEquals(editedBacklogItemId, testedBacklogItem.getBacklogItemId());
		assertEquals(editedDescription, testedBacklogItem.getDescription());
		assertEquals(editedEstimate, testedBacklogItem.getEstimate());
		assertEquals(editedImportance, testedBacklogItem.getImportance());
		assertEquals(editedNotes, testedBacklogItem.getNotes());
	}
	
	@Test
	public void Should_DeleteData_When_DeleteBacklogItem() {
		BacklogItemManagerUseCase backlogItemManagerUseCase = new BacklogItemManagerUseCase(context);
		String description = "As a ezScrum developer, I want to test addBacklogItem.";
		int estimate = 13;
		int importance = 90;
		String notes = "This is the notes for this backlog item.";
		
		BacklogItem backlogItem = null;
		try {
			backlogItem = BacklogItemBuilder.newInstance().
					productId(productId).
					description(description).
					estimate(estimate).
					importance(importance).
					notes(notes).
					build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String backlogItemId = backlogItemManagerUseCase.addBacklogItem(backlogItem);
		
		String deletedBacklogItemId = backlogItemManagerUseCase.deleteBacklogItem(backlogItemId);
		
		List<BacklogItemDTO> backlogItems = backlogItemManagerUseCase.getBacklogItems(productId);
		
		boolean isFound = false;
		for(BacklogItemDTO backlogItemDTO : backlogItems) {
			if(backlogItemDTO.getBacklogItemId().equals(deletedBacklogItemId)) {
				isFound = true;
				break;
			}
		}
		assertEquals(false, isFound);
	}
	
	@Test
	public void Should_IdentifyBacklogItemCommittedOrNot_When_GetBacklogItems() {
		BacklogItemManagerUseCase backlogItemManagerUseCase  = new BacklogItemManagerUseCase(context);
		String[] description = {"As a ezScrum developer, I want to get the first backlog item.",
				"As a ezScrum developer, I want to get the second backlog item.",
				"As a ezScrum developer, I want to get the third backlog item."
		};
		String[] backlogItemId = new String[description.length];
		for(int i=0; i<description.length; i++) {
			BacklogItem backlogItem = null;
			try {
				backlogItem = BacklogItemBuilder.newInstance().
						productId(productId).
						description(description[i]).
						build();
			} catch (Exception e) {
				e.printStackTrace();
			}
			backlogItemId[i] = backlogItemManagerUseCase.addBacklogItem(backlogItem);
		}
		
		for(int i=1; i<description.length; i++) {
			backlogItemManagerUseCase.assignBacklogItemToSprint(sprintId, backlogItemId[i]);
		}
		
		List<BacklogItemDTO> notYetCommittedBacklogItemList = backlogItemManagerUseCase.getNotYetCommittedBacklogItems(productId);
		List<BacklogItemDTO> committedBacklogItemList = backlogItemManagerUseCase.getCommittedBacklogItems(productId, sprintId);
		for(int i=0; i<notYetCommittedBacklogItemList.size(); i++) {
			assertEquals(description[i], notYetCommittedBacklogItemList.get(i).getDescription());
		}
		assertEquals(1, notYetCommittedBacklogItemList.size());
		for(int i=0; i<committedBacklogItemList.size(); i++) {
			assertEquals(description[i+1], committedBacklogItemList.get(i).getDescription());
		}
		assertEquals(2, committedBacklogItemList.size());
	}
}
