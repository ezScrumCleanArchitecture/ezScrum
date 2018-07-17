package ntut.csie.ezScrum.unitTest.useCase;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ntut.csie.ezScrum.model.product.Product;
import ntut.csie.ezScrum.model.retrospective.Retrospective;
import ntut.csie.ezScrum.model.retrospective.RetrospectiveBuilder;
import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.unitTest.factory.TestFactory;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.retrospective.RetrospectiveManagerUseCase;
import ntut.csie.ezScrum.useCase.retrospective.io.AddRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.AddRetrospectiveOutput;
import ntut.csie.ezScrum.useCase.retrospective.io.DeleteRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.DeleteRetrospectiveOutput;
import ntut.csie.ezScrum.useCase.retrospective.io.EditRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.EditRetrospectiveOutput;
import ntut.csie.ezScrum.useCase.retrospective.io.GetRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.GetRetrospectiveOutput;

public class RetrospectiveUseCaseTest {
	private ApplicationContext context;
	private RetrospectiveManagerUseCase retrospectiveManagerUseCase;
	
	private TestFactory testFactory;
	private String productId;
	private String sprintId;
	private String editedSprintId;

	@Before
	public void setUp() {
		context = ApplicationContext.getInstance();
		testFactory = new TestFactory();
		retrospectiveManagerUseCase = new RetrospectiveManagerUseCase(context);
		
		Product product = testFactory.getNewProduct();
		productId = product.getProductId();
		
		String goal = "The goal of ezScrum.";
		int interval = 2;
		String startDate = "2018-05-05";
		String endDate = "2018-05-19";
		String demoDate = "2018-05-19";
		Sprint sprint = testFactory.getNewSprint(productId, goal, interval, startDate, endDate, demoDate);
		sprintId = sprint.getSprintId();
	
		String editedGoal = "The goal of ezScrum.";
		int editedInterval = 2;
		String editedStartDate = "2018-05-05";
		String editedEndDate = "2018-05-19";
		String editedDemoDate = "2018-05-19";
		Sprint editedSprint = testFactory.getNewSprint(productId, editedGoal, editedInterval, editedStartDate, editedEndDate, editedDemoDate);
		
		editedSprintId = editedSprint.getSprintId();
	}
	
	@After
	public void tearDown() {
		context.clearProducts();
		context.clearSprints();
		context.clearRetrospectives();
	}
	
	@Test
	public void Should_AllDataInsertIntoRetrospective_When_AddRetrospectiveWithAllParamemter() {
		String description = "The good thing is we have the unit test to test our feature.";
		
		AddRetrospectiveInput retrospectiveInput = new AddRetrospectiveInput();
		retrospectiveInput.setDescription(description);
		retrospectiveInput.setProductId(productId);
		retrospectiveInput.setSprintId(sprintId);
		
		AddRetrospectiveOutput addRetrospectiveOutput = retrospectiveManagerUseCase.addRetrospective(retrospectiveInput);
		String retrospectiveId = addRetrospectiveOutput.getRetrospectiveId();
		
		Retrospective testedRetrospective = context.getRetrospective(retrospectiveId);
		assertEquals(retrospectiveId, testedRetrospective.getRetrospectiveId());
		assertEquals(description, testedRetrospective.getDescription());
		assertEquals(sprintId, testedRetrospective.getSprintId());
	}
	
	@Test
	public void Should_ThrowExcpetion_When_AddBacklogItemWithoutDesciption() {
		try {
			RetrospectiveBuilder.newInstance().
					productId(productId).
					description(null).
					build();
		} catch (Exception e) {
			assertEquals("The description of the retrospective should not be null.", e.getMessage());
		}
	}
	
	@Test
	public void Should_ThrowExcpetion_When_AddBacklogItemWithoutSprint() {
		try {
			RetrospectiveBuilder.newInstance().
					productId(productId).
					description("The bad thing is bad smell.").
					sprintId(null).
					build();
		} catch (Exception e) {
			assertEquals("The sprint of the retrospective should not be null.", e.getMessage());
		}
	}

	@Test
	public void Should_ReturnRetrospectiveList_When_GetAllRetrospective() {
		String[] description = {"The good thing is we have unit test to test our feature.",
				"The diffcult thing is implement react UI.",
				"I think just go to view other people example could be better."
		};
		for(int i=0; i<description.length; i++) {
			AddRetrospectiveInput addRetrospectiveInput = new AddRetrospectiveInput();
			addRetrospectiveInput.setDescription(description[i]);
			addRetrospectiveInput.setProductId(productId);
			addRetrospectiveInput.setSprintId(sprintId);
			retrospectiveManagerUseCase.addRetrospective(addRetrospectiveInput);
		}
		
		GetRetrospectiveInput getRetrospectiveInput = new GetRetrospectiveInput();
		getRetrospectiveInput.setProductId(productId);
		List<GetRetrospectiveOutput> retrospectiveList = retrospectiveManagerUseCase.getRetrospectives(getRetrospectiveInput);
		int sprintOrderId = context.getSprint(sprintId).getOrderId();
		for(int i=0; i<retrospectiveList.size(); i++) {
			GetRetrospectiveOutput testedRetrospective = retrospectiveList.get(i);
			assertEquals(description[i], testedRetrospective.getDescription());
			assertEquals(sprintOrderId, testedRetrospective.getSprintOrderId());
		}
		assertEquals(description.length, retrospectiveList.size());
	}
	
	@Test
	public void Should_UpdateData_When_EditRetrospective() {
		String description = "The bad thing is we have so many things need to do. So busy~";
		
		AddRetrospectiveInput addRetrospecitveInput = new AddRetrospectiveInput();
		addRetrospecitveInput.setDescription(description);
		addRetrospecitveInput.setProductId(productId);
		addRetrospecitveInput.setSprintId(sprintId);
		
		AddRetrospectiveOutput addRetrospectiveOutput = retrospectiveManagerUseCase.addRetrospective(addRetrospecitveInput);
		String retrospectiveId = addRetrospectiveOutput.getRetrospectiveId();
		
		String editedDescription = "The good thing is we have the machine to finish our thing automatally. So funny~";
		
		EditRetrospectiveInput editRetrospectiveInput = new EditRetrospectiveInput();
		
		editRetrospectiveInput.setRetrospectiveId(retrospectiveId);
		editRetrospectiveInput.setDescription(editedDescription);
		editRetrospectiveInput.setSprintId(editedSprintId);
		
		EditRetrospectiveOutput editBacklogItemOutput = retrospectiveManagerUseCase.editRetrospective(editRetrospectiveInput);
		
		Retrospective testedRetrospective = context.getRetrospective(retrospectiveId);
		
		assertEquals(true, editBacklogItemOutput.isEditSuccess());
		assertEquals(editedDescription, testedRetrospective.getDescription());
		assertEquals(editedSprintId, testedRetrospective.getSprintId());
	}
	
	@Test
	public void Should_DeleteData_When_DeleteRetrospective() {
		String description = "The bad thing is we have so many things need to do. So busy~";
		
		AddRetrospectiveInput addRetrospectiveInput = new AddRetrospectiveInput();
		addRetrospectiveInput.setDescription(description);
		addRetrospectiveInput.setProductId(productId);
		addRetrospectiveInput.setSprintId(sprintId);
		
		AddRetrospectiveOutput addRetrospectiveOutput = retrospectiveManagerUseCase.addRetrospective(addRetrospectiveInput);
		String retrospectiveId = addRetrospectiveOutput.getRetrospectiveId();
		
		DeleteRetrospectiveInput deleteRetrospectiveInput = new DeleteRetrospectiveInput();
		deleteRetrospectiveInput.setRetrospectiveId(retrospectiveId);
		
		DeleteRetrospectiveOutput deleteRetrospectiveOutput = retrospectiveManagerUseCase.deleteRetrospective(deleteRetrospectiveInput);
		
		GetRetrospectiveInput getRetrospectiveInput = new GetRetrospectiveInput();
		getRetrospectiveInput.setProductId(productId);
		List<GetRetrospectiveOutput> retrospectives = retrospectiveManagerUseCase.getRetrospectives(getRetrospectiveInput);
		
		assertEquals(true, deleteRetrospectiveOutput.isDeleteSuccess());
		boolean isFound = false;
		for(GetRetrospectiveOutput retrospectiveDTO : retrospectives) {
			if(retrospectiveDTO.getRetrospectiveId().equals(retrospectiveId)) {
				isFound = true;
				break;
			}
		}
		assertEquals(false, isFound);
	}
	
	@Test
	public void Should_OrderIdUpdated_When_DeleteRetrospective() {
		String[] description = {"The good thing is we have unit test to test our feature.",
				"The diffcult thing is implement react UI.",
				"I think just go to view other people example could be better."
		};
		String[] retrospectiveIds = new String[description.length];
		for(int i=0; i<description.length; i++) {
			AddRetrospectiveInput addRetrospectiveInput = new AddRetrospectiveInput();
			addRetrospectiveInput.setDescription(description[i]);
			addRetrospectiveInput.setProductId(productId);
			addRetrospectiveInput.setSprintId(sprintId);
			AddRetrospectiveOutput addRetrospectiveOutput = retrospectiveManagerUseCase.addRetrospective(addRetrospectiveInput);
			retrospectiveIds[i] = addRetrospectiveOutput.getRetrospectiveId();
		}
		
		DeleteRetrospectiveInput deleteRetrospectiveInput = new DeleteRetrospectiveInput();
		deleteRetrospectiveInput.setRetrospectiveId(retrospectiveIds[1]);
		
		retrospectiveManagerUseCase.deleteRetrospective(deleteRetrospectiveInput);
		
		GetRetrospectiveInput getRetrospectivedInput = new GetRetrospectiveInput();
		getRetrospectivedInput.setProductId(productId);
		List<GetRetrospectiveOutput> retrospectiveList = retrospectiveManagerUseCase.getRetrospectives(getRetrospectivedInput);
		
		for(int i=0; i<retrospectiveList.size(); i++) {
			assertEquals(i+1, retrospectiveList.get(i).getOrderId());
		}
	}
}
