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
import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.restfulAPI.sprint.AddSprintRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.sprint.DeleteSprintRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.sprint.EditSprintRestfulAPI;
import ntut.csie.ezScrum.restfulAPI.sprint.GetAllSprintRestfulAPI;
import ntut.csie.ezScrum.unitTest.factory.TestFactory;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.sprint.AddSprintUseCase;
import ntut.csie.ezScrum.useCase.sprint.AddSprintUseCaseImpl;
import ntut.csie.ezScrum.useCase.sprint.DeleteSprintUseCase;
import ntut.csie.ezScrum.useCase.sprint.DeleteSprintUseCaseImpl;
import ntut.csie.ezScrum.useCase.sprint.EditSprintUseCase;
import ntut.csie.ezScrum.useCase.sprint.EditSprintUseCaseImpl;
import ntut.csie.ezScrum.useCase.sprint.GetAllSprintUseCase;
import ntut.csie.ezScrum.useCase.sprint.GetAllSprintUseCaseImpl;
import ntut.csie.ezScrum.useCase.sprint.io.AddSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.AddSprintOutput;
import ntut.csie.ezScrum.useCase.sprint.io.DeleteSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.DeleteSprintOutput;
import ntut.csie.ezScrum.useCase.sprint.io.EditSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.EditSprintOutput;
import ntut.csie.ezScrum.useCase.sprint.io.GetAllSprintDTO;
import ntut.csie.ezScrum.useCase.sprint.io.GetAllSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.GetAllSprintOutput;


public class SprintUseCaseTest {
	
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
	}
	
	@Test
	public void Should_Success_When_AddSprintWithRequiredParamemter() {
		String goal = "Implement the function of creating sprint.";
		String startDate = "2018-04-09";
		String endDate = "2018-04-22";
		int interval = 2;
		String demoDate = "2018-04-22";
		
		AddSprintOutput output = addNewSprintWithRequiredParamemter(goal, interval,
				startDate, endDate, demoDate);
		
		boolean isAddSuccess = output.isAddSuccess();
		assertTrue(isAddSuccess);
	}

	@Test
	public void Should_Success_When_AddSprintWithAllParamemter() {
		String goal = "Implement the function of creating sprint.";
		int teamSize = 3;
		String startDate = "2018-04-09";
		int interval = 2;
		String endDate = "2018-04-22";
		String demoDate = "2018-04-22";
		String demoPlace = "1622";
		String daily = "10:00 1321";
		
		AddSprintOutput output = addNewSprintWithAllParamemter(goal, interval, teamSize,
				startDate, endDate, demoDate, demoPlace, daily);
		
		boolean isAddSuccess = output.isAddSuccess();
		assertTrue(isAddSuccess);
	}
	
	@Test(expected = Exception.class)
	public void Should_ThrowException_When_AddSprintWithEmptyParamemter() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(stream);
		System.setOut(printStream);
		
		AddSprintInput input = new AddSprintUseCaseImpl();
		
		AddSprintOutput output = new AddSprintRestfulAPI();
		
		AddSprintUseCase addSprintUseCase = new AddSprintUseCaseImpl(context);
		addSprintUseCase.execute(input, output);
		
		String expectedException = "The goal of the sprint should not be null.\n" +
				"The interval of the sprint should not be zero.\n" +
				"The start date of the sprint should not be null.\n" +
				"The demo date of the sprint should not be null.\n";
		assertEquals(expectedException, stream.toString());
	}
	
	@Test
	public void Should_ReturnSprintList_When_GetAllSprint() {
		String[] goal = {
				"This is the goal of Sprint#1",
				"This is the goal of Sprint#2",
				"This is the goal of Sprint#3"
		};
		int interval = 2;
		String[] startDate = {"2018-05-15", "2018-05-29", "2018-06-15"};
		String[] endDate = {"2018-05-28", "2018-06-14", "2018-06-28"};
		String[] demoDate = {"2018-05-28", "2018-06-14", "2018-06-28"};
		
		int numberOfSprints = goal.length;
		
		for(int i=0; i<numberOfSprints; i++) {
			addNewSprintWithRequiredParamemter(goal[i], interval, startDate[i], endDate[i], demoDate[i]);
		}
		
		GetAllSprintOutput output = getAllSprint();
		List<GetAllSprintDTO> sprintList = output.getSprintList();
		
		for(int i=0; i<sprintList.size(); i++) {
			assertEquals(goal[i], sprintList.get(i).getGoal());
			assertEquals(startDate[i], sprintList.get(i).getStartDate());
			assertEquals(endDate[i], sprintList.get(i).getEndDate());
			assertEquals(interval, sprintList.get(i).getInterval());
			assertEquals(demoDate[i], sprintList.get(i).getDemoDate());
		}
		assertEquals(numberOfSprints, sprintList.size());
	}
	
	@Test
	public void Should_Success_When_EditSprint() {
		String goal = "This is the goal of this sprint.";
		int interval = 2;
		String startDate = "2018-05-05";
		String endDate = "2018-05-19";
		String demoDate = "2018-05-19";
		Sprint sprint = testFactory.getNewSprint(productId, goal, interval, startDate, endDate, demoDate);
		String sprintId = sprint.getSprintId();
		
		String editedGoal = "Implement the function of editing sprint.";
		int editedTeamSize = 2;
		String editedStartDate = "2018-04-09";
		int editedInterval = 3;
		String editedEndDate = "2018-04-29";
		String editedDemoDate = "2018-04-29";
		String editedDemoPlace = "03F Room";
		String editedDaily = "15:00 1323";
		
		EditSprintOutput output = editSprint(sprintId, editedGoal, editedInterval, editedTeamSize,
			editedStartDate, editedEndDate, editedDemoDate, editedDemoPlace, editedDaily);
		
		boolean isEditSuccess = output.isEditSuccess();
		assertTrue(isEditSuccess);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_EditSprint() {
		String editedGoal = "Implement the function of editing sprint.";
		int editedTeamSize = 2;
		String editedStartDate = "2018-04-09";
		int editedInterval = 3;
		String editedEndDate = "2018-04-29";
		String editedDemoDate = "2018-04-29";
		String editedDemoPlace = "03F Room";
		String editedDaily = "15:00 1323";
		
		EditSprintOutput output = editSprint(null, editedGoal, editedInterval, editedTeamSize,
				editedStartDate, editedEndDate, editedDemoDate, editedDemoPlace, editedDaily);
		
		
		boolean isEditSuccess = output.isEditSuccess();
		String expectedErrorMessage = "Sorry, the sprint is not exist.";
		assertFalse(isEditSuccess);
		assertEquals(expectedErrorMessage, output.getErrorMessage());
	}
	
	@Test
	public void Should_Success_When_DeleteSprint() {
		String goal = "This is the goal of this sprint.";
		int interval = 2;
		String startDate = "2018-05-05";
		String endDate = "2018-05-19";
		String demoDate = "2018-05-19";
		Sprint sprint = testFactory.getNewSprint(productId, goal, interval, startDate, endDate, demoDate);
		String sprintId = sprint.getSprintId();
		
		DeleteSprintOutput output = deleteSprint(sprintId);
		
		boolean isDeleteSuccess = output.isDeleteSuccess();
		String expectedErrorMessage = "Delete sprint success.";
		assertTrue(isDeleteSuccess);
		assertEquals(expectedErrorMessage, output.getErrorMessage());
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_DeleteSprint() {
		DeleteSprintOutput output = deleteSprint(null);
		
		boolean isDeleteSuccess = output.isDeleteSuccess();
		String expectedErrorMessage = "Sorry, the sprint is not exist.";
		assertFalse(isDeleteSuccess);
		assertEquals(expectedErrorMessage, output.getErrorMessage());
	}
	
	@Test
	public void Should_OrderIdUpdated_When_DeleteSpint() {
		String[] goal = {
				"This is the goal of Sprint#1",
				"This is the goal of Sprint#2",
				"This is the goal of Sprint#3"
		};
		int interval = 2;
		String[] startDate = {"2018-05-15", "2018-05-29", "2018-06-15"};
		String[] endDate = {"2018-05-28", "2018-06-14", "2018-06-28"};
		String[] demoDate = {"2018-05-28", "2018-06-14", "2018-06-28"};
		int numberOfSprints = goal.length;
		Sprint[] sprints = new Sprint[numberOfSprints];
		
		for(int i=0; i<numberOfSprints; i++) {
			sprints[i] = testFactory.getNewSprint(productId, goal[i], interval, startDate[i], endDate[i], demoDate[i]);
		}
		
		String deleteSprintId = sprints[1].getSprintId();
		deleteSprint(deleteSprintId);
		
		GetAllSprintOutput output = getAllSprint();
		List<GetAllSprintDTO> sprintList = output.getSprintList();
		
		for(int i=0; i<sprintList.size(); i++) {
			assertEquals(i+1, sprintList.get(i).getOrderId());
		}
	
	}
	
	@Test
	public void Should_ReturnOverlapMessage_When_SprintIsOverlap() {
		String goal = "Implement the function of creating sprint.";
		String startDate = "2018-04-09";
		String endDate = "2018-04-22";
		int interval = 2;
		String demoDate = "2018-04-22";
		
		addNewSprintWithRequiredParamemter(goal, interval,
				startDate, endDate, demoDate);
		
		String overlapGoal = "I hope overlap with other sprint.";
		String overlapStartDate = "2018-04-09";
		String overlapEndDate = "2018-04-22";
		int overlapInterval = 2;
		String overlapDemoDate = "2018-04-22";
		
		
		AddSprintOutput output = addNewSprintWithRequiredParamemter(overlapGoal, overlapInterval,
				overlapStartDate, overlapEndDate, overlapDemoDate);
		boolean isAddSuccess = output.isAddSuccess();
		String errorMessage = output.getErrorMessage();
		
		String expectedErrorMessage = "Sorry, the Start Date or End Date is overlap with the other Sprint.";
		assertFalse(isAddSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_Success_When_SprintIsNotOverlap() {
		String goal = "Implement the function of creating sprint.";
		String startDate = "2018-04-09";
		String endDate = "2018-04-22";
		int interval = 2;
		String demoDate = "2018-04-22";
		
		addNewSprintWithRequiredParamemter(goal, interval,
				startDate, endDate, demoDate);
		
		String overlapGoal = "I hope overlap with other sprint.";
		String overlapStartDate = "2018-04-23";
		String overlapEndDate = "2018-05-06";
		int overlapInterval = 2;
		String overlapDemoDate = "2018-05-06";
		
		AddSprintOutput output = addNewSprintWithRequiredParamemter(overlapGoal, overlapInterval,
				overlapStartDate, overlapEndDate, overlapDemoDate);
		boolean isAddSuccess = output.isAddSuccess();
		
		assertTrue(isAddSuccess);
	}
	
	private AddSprintOutput addNewSprintWithRequiredParamemter(String goal, int interval,
			String startDate, String endDate, String demoDate) {
		AddSprintInput input = new AddSprintUseCaseImpl();
		input.setGoal(goal);
		input.setInterval(interval);
		input.setStartDate(startDate);
		input.setEndDate(endDate);
		input.setDemoDate(demoDate);
		input.setProductId(productId);
		
		AddSprintOutput output = new AddSprintRestfulAPI();
		
		AddSprintUseCase addSprintUseCase = new AddSprintUseCaseImpl(context);
		addSprintUseCase.execute(input, output);
		
		return output;
	}
	
	private AddSprintOutput addNewSprintWithAllParamemter(String goal, int interval, int teamSize,
			String startDate, String endDate, String demoDate, String demoPlace, String daily) {
		AddSprintInput input = new AddSprintUseCaseImpl();
		input.setGoal(goal);
		input.setInterval(interval);
		input.setTeamSize(teamSize);
		input.setStartDate(startDate);
		input.setEndDate(endDate);
		input.setDemoDate(demoDate);
		input.setDemoPlace(demoPlace);
		input.setDaily(daily);
		input.setProductId(productId);
		
		AddSprintOutput output = new AddSprintRestfulAPI();
		
		AddSprintUseCase addSprintUseCase = new AddSprintUseCaseImpl(context);
		addSprintUseCase.execute(input, output);
		
		return output;
	}
	
	private GetAllSprintOutput getAllSprint(){
		GetAllSprintInput input = new GetAllSprintUseCaseImpl();
		input.setProductId(productId);
		
		GetAllSprintOutput output = new GetAllSprintRestfulAPI();
		
		GetAllSprintUseCase getAllSprintUseCase = new GetAllSprintUseCaseImpl(context);
		getAllSprintUseCase.execute(input, output);
		
		return output;
	}
	
	private EditSprintOutput editSprint(String sprintId, String editedGoal, int editedInterval, int editedTeamSize,
			String editedStartDate, String editedEndDate, String editedDemoDate, String editedDemoPlace, String editedDaily) {
		EditSprintInput input = new EditSprintUseCaseImpl();
		input.setSprintId(sprintId);
		input.setGoal(editedGoal);
		input.setInterval(editedInterval);
		input.setTeamSize(editedTeamSize);
		input.setStartDate(editedStartDate);
		input.setEndDate(editedEndDate);
		input.setDemoDate(editedDemoDate);
		input.setDemoPlace(editedDemoPlace);
		input.setDaily(editedDaily);
		input.setProductId(productId);
		
		EditSprintOutput output = new EditSprintRestfulAPI();
		
		EditSprintUseCase editSprintUseCase = new EditSprintUseCaseImpl(context);
		editSprintUseCase.execute(input, output);
		
		return output;
	}
	
	private DeleteSprintOutput deleteSprint(String sprintId) {
		DeleteSprintInput input = new DeleteSprintUseCaseImpl();
		input.setSprintId(sprintId);
		
		DeleteSprintOutput output = new DeleteSprintRestfulAPI();
		
		DeleteSprintUseCase deleteSprintUseCase = new DeleteSprintUseCaseImpl(context);
		deleteSprintUseCase.execute(input, output);
		
		return output;
	}
}
