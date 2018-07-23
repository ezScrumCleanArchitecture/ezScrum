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

import ntut.csie.ezScrum.model.product.Product;
import ntut.csie.ezScrum.model.retrospective.Retrospective;
import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.restfulAPI.retrospective.AddRetrospectiveRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.retrospective.DeleteRetrospectiveRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.retrospective.EditRetrospectiveRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.retrospective.GetAllRetrospectiveRestfulAPI;
import ntut.csie.ezScrum.unitTest.factory.TestFactory;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.retrospective.AddRetrospectiveUseCase;
import ntut.csie.ezScrum.useCase.retrospective.AddRetrospectiveUseCaseImpl;
import ntut.csie.ezScrum.useCase.retrospective.DeleteRetrospectiveUseCase;
import ntut.csie.ezScrum.useCase.retrospective.DeleteRetrospectiveUseCaseImpl;
import ntut.csie.ezScrum.useCase.retrospective.EditRetrospectiveUseCase;
import ntut.csie.ezScrum.useCase.retrospective.EditRetrospectiveUseCaseImpl;
import ntut.csie.ezScrum.useCase.retrospective.GetAllRetrospectiveUseCase;
import ntut.csie.ezScrum.useCase.retrospective.GetAllRetrospectiveUseCaseImpl;
import ntut.csie.ezScrum.useCase.retrospective.io.AddRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.AddRetrospectiveOutput;
import ntut.csie.ezScrum.useCase.retrospective.io.DeleteRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.DeleteRetrospectiveOutput;
import ntut.csie.ezScrum.useCase.retrospective.io.EditRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.EditRetrospectiveOutput;
import ntut.csie.ezScrum.useCase.retrospective.io.RetrospectiveModel;
import ntut.csie.ezScrum.useCase.retrospective.io.GetAllRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.GetAllRetrospectiveOutput;

public class RetrospectiveUseCaseTest {
	private ApplicationContext context;
	
	private TestFactory testFactory;
	private String productId;
	private String sprintId;
	private String editedSprintId;

	@Before
	public void setUp() {
		context = ApplicationContext.getInstance();
		testFactory = new TestFactory();
		
		Product product = testFactory.getNewProduct();
		productId = product.getProductId();
		
		String goal = "The goal of ezScrum.";
		int interval = 2;
		String startDate = "2018-05-05";
		String endDate = "2018-05-19";
		String demoDate = "2018-05-19";
		Sprint sprint = testFactory.getNewSprint(productId, goal, interval, startDate, endDate, demoDate);
		sprintId = sprint.getSprintId();
	
		String editedGoal = "The goal of ezScrum part 2.";
		String editedStartDate = "2018-05-20";
		String editedEndDate = "2018-06-03";
		String editedDemoDate = "2018-06-03";
		Sprint editedSprint = testFactory.getNewSprint(productId, editedGoal, interval, editedStartDate, editedEndDate, editedDemoDate);
		editedSprintId = editedSprint.getSprintId();
	}
	
	@After
	public void tearDown() {
		context.clearProducts();
		context.clearSprints();
		context.clearRetrospectives();
	}
	
	@Test
	public void Should_Success_When_AddRetrospectiveWithAllParamemter() {
		String description = "The good thing is we have the unit test to test our feature.";
		
		AddRetrospectiveOutput output = addNewRetrospectiveWithAllParamemter(description);
		
		boolean isAddSuccess = output.isAddSuccess();
		assertTrue(isAddSuccess);
	}
	
	@Test(expected = Exception.class)
	public void Should_ThrowExcpetion_When_AddBacklogItemWithoutDesciption() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(stream);
		System.setOut(printStream);
		
		AddRetrospectiveInput input = new AddRetrospectiveUseCaseImpl();
		
		AddRetrospectiveOutput output = new AddRetrospectiveRestfulAPI();
		
		AddRetrospectiveUseCase addRetrospectiveUseCase = new AddRetrospectiveUseCaseImpl(context);
		addRetrospectiveUseCase.execute(input, output);

		String expectedException = "The description of the retrospective should not be null.";
		assertEquals(expectedException, stream.toString());
	}
	
	@Test(expected = Exception.class)
	public void Should_ThrowExcpetion_When_AddBacklogItemWithoutSprint() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(stream);
		System.setOut(printStream);
		
		AddRetrospectiveInput input = new AddRetrospectiveUseCaseImpl();
		input.setDescription("The bad thing is bad smell.");
		
		AddRetrospectiveOutput output = new AddRetrospectiveRestfulAPI();
		
		AddRetrospectiveUseCase addRetrospectiveUseCase = new AddRetrospectiveUseCaseImpl(context);
		addRetrospectiveUseCase.execute(input, output);

		String expectedException = "The sprint of the retrospective should not be null.";
		assertEquals(expectedException, stream.toString());
	}

	@Test
	public void Should_ReturnRetrospectiveList_When_GetAllRetrospective() {
		String[] description = {"The good thing is we have unit test to test our feature.",
				"The diffcult thing is implement react UI.",
				"I think just go to view other people example could be better."
		};
		int numberOfRetrospectives = description.length;
		for(int i=0; i<numberOfRetrospectives; i++) {
			addNewRetrospectiveWithAllParamemter(description[i]);
		}
		
		GetAllRetrospectiveOutput output = getAllRetrospective();
		List<RetrospectiveModel> retrospectiveList = output.getRetrospectiveList();
		for(int i=0; i<retrospectiveList.size(); i++) {
			assertEquals(description[i], retrospectiveList.get(i).getDescription());
		}
		assertEquals(numberOfRetrospectives, retrospectiveList.size());
	}
	
	@Test
	public void Should_Success_When_EditRetrospective() {
		String description = "The bad thing is we have so many things need to do. So busy~";
		
		Retrospective retrospective = testFactory.getNewRetrospective(productId, sprintId, description);
		String retrospectiveId = retrospective.getRetrospectiveId();
		
		String editedDescription = "The good thing is we have the machine to finish our thing automatally. So funny~";
		
		EditRetrospectiveOutput output = editRetrospective(retrospectiveId, editedDescription, editedSprintId);
		
		boolean isEditSuccess = output.isEditSuccess();
		assertTrue(isEditSuccess);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_EditNotExistRetrospective() {
		String editedDescription = "The good thing is we have the machine to finish our thing automatally. So funny~";
		
		EditRetrospectiveOutput output = editRetrospective(null, editedDescription, editedSprintId);
		
		boolean isEditSuccess = output.isEditSuccess();
		String expectedErrorMessage = "Sorry, the retrospective is not exist.";
		assertFalse(isEditSuccess);
		assertEquals(expectedErrorMessage, output.getErrorMessage());
	}
	
	@Test
	public void Should_Success_When_DeleteRetrospective() {
		String description = "The bad thing is we have so many things need to do. So busy~";
		
		Retrospective retrospective = testFactory.getNewRetrospective(productId, sprintId, description);
		String retrospectiveId = retrospective.getRetrospectiveId();
		
		DeleteRetrospectiveOutput output = deleteRetrospective(retrospectiveId);
		
		boolean isDeleteSuccess = output.isDeleteSuccess();
		assertTrue(isDeleteSuccess);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_DeleteNotExistRetrospective() {
		DeleteRetrospectiveOutput output = deleteRetrospective(null);
		
		boolean isDeleteSuccess = output.isDeleteSuccess();
		String expectedDeleteMessage = "Sorry, the retrospective is not exist.";
		assertFalse(isDeleteSuccess);
		assertEquals(expectedDeleteMessage, output.getErrorMessage());
	}
	
	@Test
	public void Should_OrderIdUpdated_When_DeleteRetrospective() {
		String[] description = {"The good thing is we have unit test to test our feature.",
				"The diffcult thing is implement react UI.",
				"I think just go to view other people example could be better."
		};
		int numberOfRetrospectives = description.length;
		Retrospective[] retrospectives = new Retrospective[numberOfRetrospectives];
		for(int i=0; i<numberOfRetrospectives; i++) {
			retrospectives[i] = testFactory.getNewRetrospective(productId, sprintId, description[i]);
		}
		
		String deleteRetrospectiveId = retrospectives[1].getRetrospectiveId();
		deleteRetrospective(deleteRetrospectiveId);
		
		GetAllRetrospectiveOutput output = getAllRetrospective();
		List<RetrospectiveModel> retrospectiveList = output.getRetrospectiveList();
		
		for(int i=0; i<retrospectiveList.size(); i++) {
			assertEquals(i+1, retrospectiveList.get(i).getOrderId());
		}
	}
	
	private AddRetrospectiveOutput addNewRetrospectiveWithAllParamemter(String description) {
		AddRetrospectiveInput input = new AddRetrospectiveUseCaseImpl();
		input.setDescription(description);
		input.setProductId(productId);
		input.setSprintId(sprintId);
		
		AddRetrospectiveOutput output = new AddRetrospectiveRestfulAPI();
		
		AddRetrospectiveUseCase addRetrospectiveUseCase = new AddRetrospectiveUseCaseImpl(context);
		addRetrospectiveUseCase.execute(input, output);
		
		return output;
	}
	
	private GetAllRetrospectiveOutput getAllRetrospective() {
		GetAllRetrospectiveInput input = new GetAllRetrospectiveUseCaseImpl();
		input.setProductId(productId);
		
		GetAllRetrospectiveOutput output = new GetAllRetrospectiveRestfulAPI();
		
		GetAllRetrospectiveUseCase getAllRetrospectiveUseCase = new GetAllRetrospectiveUseCaseImpl(context);
		getAllRetrospectiveUseCase.execute(input, output);
		
		return output;
	}
	
	private EditRetrospectiveOutput editRetrospective(String retrospectiveId, String editedDescription, String editedSprintId) {
		EditRetrospectiveInput input = new EditRetrospectiveUseCaseImpl();
		input.setRetrospectiveId(retrospectiveId);
		input.setDescription(editedDescription);
		input.setSprintId(editedSprintId);
		
		EditRetrospectiveOutput output = new EditRetrospectiveRestfulAPI();
		
		EditRetrospectiveUseCase editRetrospectiveUseCase = new EditRetrospectiveUseCaseImpl(context);
		editRetrospectiveUseCase.execute(input, output);
		
		return output;
	}
	
	private DeleteRetrospectiveOutput deleteRetrospective(String retrospectiveId) {
		DeleteRetrospectiveInput input = new DeleteRetrospectiveUseCaseImpl();
		input.setRetrospectiveId(retrospectiveId);
		
		DeleteRetrospectiveOutput output = new DeleteRetrospectiveRestfulAPI();
		
		DeleteRetrospectiveUseCase deleteRetrospectiveUseCase = new DeleteRetrospectiveUseCaseImpl(context);
		deleteRetrospectiveUseCase.execute(input, output);
		
		return output;
	}
}
