package ntut.csie.ezScrum.unitTest;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ntut.csie.ezScrum.model.product.Product;
import ntut.csie.ezScrum.model.product.ProductBuilder;
import ntut.csie.ezScrum.model.retrospective.Retrospective;
import ntut.csie.ezScrum.model.retrospective.RetrospectiveBuilder;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.product.ProductManagerUseCase;
import ntut.csie.ezScrum.useCase.retrospective.RetrospectiveManagerUseCase;
import ntut.csie.ezScrum.useCase.retrospective.io.AddRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.AddRetrospectiveOutput;
import ntut.csie.ezScrum.useCase.retrospective.io.DeleteRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.DeleteRetrospectiveOutput;
import ntut.csie.ezScrum.useCase.retrospective.io.EditRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.EditRetrospectiveOutput;
import ntut.csie.ezScrum.useCase.retrospective.io.GetRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.GetRetrospectiveOutput;
import ntut.csie.ezScrum.useCase.sprint.SprintManagerUseCase;
import ntut.csie.ezScrum.useCase.sprint.io.AddSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.AddSprintOutput;

public class RetrospectiveTest {
	private ApplicationContext context;
	private RetrospectiveManagerUseCase retrospectiveManagerUseCase;
	private String productId;
	private String sprintId;
	private String editedSprintId;

	@Before
	public void setUp() {
		context = ApplicationContext.getInstance();
		retrospectiveManagerUseCase = new RetrospectiveManagerUseCase(context);
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
		
		AddSprintInput sprintAddInput2 = new AddSprintInput();
		sprintAddInput2.setGoal("The goal of ezScrum Part 2.");
		sprintAddInput2.setInterval(2);
		sprintAddInput2.setStartDate("2018-06-26");
		sprintAddInput2.setDemoDate("2018-07-10");
		sprintAddInput2.setDemoPlace("The place for demo ezScrum. But this is other way...");
		sprintAddInput2.setProductId(productId);
		
		AddSprintOutput sprintAddOutput2 = sprintManagerUseCase.addSprint(sprintAddInput2);
		editedSprintId = sprintAddOutput2.getSprintId();
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
