package ntut.csie.ezScrum.unitTest;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ntut.csie.ezScrum.model.Product;
import ntut.csie.ezScrum.model.Sprint;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.Product.ProductBuilder;
import ntut.csie.ezScrum.useCase.Product.ProductManagerUseCase;
import ntut.csie.ezScrum.useCase.Sprint.SprintBuilder;
import ntut.csie.ezScrum.useCase.Sprint.SprintInputDTO;
import ntut.csie.ezScrum.useCase.Sprint.SprintManagerUseCase;
import ntut.csie.ezScrum.useCase.Sprint.SprintOutputDTO;

public class SprintTest {
	
	private ApplicationContext context;
	private String productId;

	@Before
	public void setUp() {
		context = ApplicationContext.getInstance();
		ProductManagerUseCase productManagerUseCase = new ProductManagerUseCase(context);
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
	}
	
	@After
	public void tearDown() {
		context.clearProducts();
		context.clearSprints();
	}
	
	@Test
	public void Should_RequiredDataInsertIntoSprint_When_AddSprintWithRequiredParamemter() {
		SprintManagerUseCase sprintManagerUseCase = new SprintManagerUseCase(context);
		String goal = "Implement the function of creating sprint.";
		String startDate = "2018-04-09";
		String endDate = "2018-04-22";
		int interval = 2;
		String demoDate = "2018-04-22";
		
		SprintInputDTO sprintInputDTO = new SprintInputDTO();
		sprintInputDTO.setGoal(goal);
		sprintInputDTO.setInterval(interval);
		sprintInputDTO.setStartDate(startDate);
		sprintInputDTO.setEndDate(endDate);
		sprintInputDTO.setDemoDate(demoDate);
		sprintInputDTO.setProductId(productId);
		
		String sprintId = sprintManagerUseCase.addSprint(sprintInputDTO);
		Sprint testedSprint = context.getSprint(sprintId);
		assertEquals(sprintId, testedSprint.getSprintId());
		assertEquals(goal, testedSprint.getGoal());
		assertEquals(interval, testedSprint.getInterval());
		assertEquals(0, testedSprint.getTeamSize());
		assertEquals(startDate, testedSprint.getStartDate());
		assertEquals(endDate, testedSprint.getEndDate());
		assertEquals(demoDate, testedSprint.getDemoDate());
		assertEquals(null, testedSprint.getDemoPlace());
		assertEquals(null, testedSprint.getDaily());
		assertEquals(productId, testedSprint.getProductId());
	}

	@Test
	public void Should_AllDataInsertIntoSprint_When_AddSprintWithAllParamemter() {
		SprintManagerUseCase sprintManagerUseCase = new SprintManagerUseCase(context);
		String goal = "Implement the function of creating sprint.";
		int teamSize = 3;
		String startDate = "2018-04-09";
		int interval = 2;
		String endDate = "2018-04-22";
		String demoDate = "2018-04-22";
		String demoPlace = "1622";
		String daily = "10:00 1321";
		
		SprintInputDTO sprintInputDTO = new SprintInputDTO();
		sprintInputDTO.setGoal(goal);
		sprintInputDTO.setInterval(interval);
		sprintInputDTO.setTeamSize(teamSize);
		sprintInputDTO.setStartDate(startDate);
		sprintInputDTO.setEndDate(endDate);
		sprintInputDTO.setDemoDate(demoDate);
		sprintInputDTO.setDemoPlace(demoPlace);
		sprintInputDTO.setDaily(daily);
		sprintInputDTO.setProductId(productId);
		
		String sprintId = sprintManagerUseCase.addSprint(sprintInputDTO);
		Sprint testedSprint = context.getSprint(sprintId);
		assertEquals(sprintId, testedSprint.getSprintId());
		assertEquals(goal, testedSprint.getGoal());
		assertEquals(interval, testedSprint.getInterval());
		assertEquals(teamSize, testedSprint.getTeamSize());
		assertEquals(startDate, testedSprint.getStartDate());
		assertEquals(endDate, testedSprint.getEndDate());
		assertEquals(demoDate, testedSprint.getDemoDate());
		assertEquals(demoPlace, testedSprint.getDemoPlace());
		assertEquals(daily, testedSprint.getDaily());
		assertEquals(productId, testedSprint.getProductId());
	}
	
	@Test
	public void Should_ThrowException_When_AddSprintWithEmptyParamemter() {
		String exceptionMessage = "The goal of the sprint should not be null.\n" +
				"The interval of the sprint should not be zero.\n" +
				"The start date of the sprint should not be null.\n" +
				"The demo date of the sprint should not be null.\n";
		try {
			SprintBuilder.newInstance().
				goal(null).
				interval(0).
				startDate(null).
				demoDate(null).
				build();
		} catch (Exception e) {
			assertEquals(exceptionMessage, e.getMessage());
		}
	}
	
	@Test
	public void Should_ReturnSprintList_When_GetAllSprint() {
		SprintManagerUseCase sprintManagerUseCase  = new SprintManagerUseCase(context);
		String[] goal = {
				"This is the goal of Sprint#1",
				"This is the goal of Sprint#2",
				"This is the goal of Sprint#3"
		};
		String[] startDate = {"2018-05-15", "2018-05-29", "2018-06-15"};
		String[] endDate = {"2018-05-28", "2018-06-14", "2018-06-28"};
		String[] demoDate = {"2018-05-28", "2018-06-14", "2018-06-28"};
		for(int i=0; i<goal.length; i++) {
			SprintInputDTO sprintInputDTO = new SprintInputDTO();
			sprintInputDTO.setGoal(goal[i]);
			sprintInputDTO.setStartDate(startDate[i]);
			sprintInputDTO.setEndDate(endDate[i]);
			sprintInputDTO.setInterval(2);
			sprintInputDTO.setDemoDate(demoDate[i]);
			sprintInputDTO.setProductId(productId);
			sprintManagerUseCase.addSprint(sprintInputDTO);
		}
		
		List<SprintOutputDTO> sprintList = sprintManagerUseCase.getSprints(productId);
		for(int i=0; i<sprintList.size(); i++) {
			assertEquals(goal[i], sprintList.get(i).getGoal());
			assertEquals(startDate[i], sprintList.get(i).getStartDate());
			assertEquals(endDate[i], sprintList.get(i).getEndDate());
			assertEquals(2, sprintList.get(i).getInterval());
			assertEquals(demoDate[i], sprintList.get(i).getDemoDate());
		}
		assertEquals(goal.length, sprintList.size());
	}
	
	@Test
	public void Should_UpdateData_When_EditSprint() {
		SprintManagerUseCase sprintManagerUseCase = new SprintManagerUseCase(context);
		String goal = "Implement the function of creating sprint.";
		int teamSize = 3;
		String startDate = "2018-04-09";
		int interval = 2;
		String endDate = "2018-04-22";
		String demoDate = "2018-04-22";
		String demoPlace = "1622";
		String daily = "10:00 1321";
		
		SprintInputDTO sprintInputDTO = new SprintInputDTO();
		sprintInputDTO.setGoal(goal);
		sprintInputDTO.setInterval(interval);
		sprintInputDTO.setTeamSize(teamSize);
		sprintInputDTO.setStartDate(startDate);
		sprintInputDTO.setEndDate(endDate);
		sprintInputDTO.setDemoDate(demoDate);
		sprintInputDTO.setDemoPlace(demoPlace);
		sprintInputDTO.setDaily(daily);
		sprintInputDTO.setProductId(productId);
		
		String sprintId = sprintManagerUseCase.addSprint(sprintInputDTO);
		
		String editedGoal = "Implement the function of editing sprint.";
		int editedTeamSize = 2;
		String editedStartDate = "2018-04-09";
		int editedInterval = 3;
		String editedEndDate = "2018-04-29";
		String editedDemoDate = "2018-04-29";
		String editedDemoPlace = "03F Room";
		String editedDaily = "15:00 1323";
		
		SprintInputDTO editedSprintInputDTO = new SprintInputDTO();
		
		editedSprintInputDTO.setGoal(editedGoal);
		editedSprintInputDTO.setTeamSize(editedTeamSize);
		editedSprintInputDTO.setStartDate(editedStartDate);
		editedSprintInputDTO.setInterval(editedInterval);
		editedSprintInputDTO.setEndDate(editedEndDate);
		editedSprintInputDTO.setDemoDate(editedDemoDate);
		editedSprintInputDTO.setDemoPlace(editedDemoPlace);
		editedSprintInputDTO.setDaily(editedDaily);
		
		String editedSprintId = sprintManagerUseCase.editSprint(sprintId, editedSprintInputDTO);
		
		Sprint testedSprint = context.getSprint(editedSprintId);
		
		assertEquals(editedSprintId, testedSprint.getSprintId());
		assertEquals(editedGoal, testedSprint.getGoal());
		assertEquals(editedTeamSize, testedSprint.getTeamSize());
		assertEquals(editedStartDate, testedSprint.getStartDate());
		assertEquals(editedInterval, testedSprint.getInterval());
		assertEquals(editedEndDate, testedSprint.getEndDate());
		assertEquals(editedDemoDate, testedSprint.getDemoDate());
		assertEquals(editedDemoPlace, testedSprint.getDemoPlace());
		assertEquals(editedDaily, testedSprint.getDaily());
	}
	
	@Test
	public void Should_DeleteData_When_DeleteSprint() {
		SprintManagerUseCase sprintManagerUseCase = new SprintManagerUseCase(context);
		String goal = "Implement the function of creating sprint.";
		int teamSize = 3;
		String startDate = "2018-04-09";
		int interval = 2;
		String endDate = "2018-04-22";
		String demoDate = "2018-04-22";
		String demoPlace = "1622";
		String daily = "10:00 1321";
		
		SprintInputDTO sprintInputDTO = new SprintInputDTO();
		sprintInputDTO.setGoal(goal);
		sprintInputDTO.setInterval(interval);
		sprintInputDTO.setTeamSize(teamSize);
		sprintInputDTO.setStartDate(startDate);
		sprintInputDTO.setEndDate(endDate);
		sprintInputDTO.setDemoDate(demoDate);
		sprintInputDTO.setDemoPlace(demoPlace);
		sprintInputDTO.setDaily(daily);
		sprintInputDTO.setProductId(productId);
		
		String sprintId = sprintManagerUseCase.addSprint(sprintInputDTO);
		
		String deletedBacklogItemId = sprintManagerUseCase.deleteSprint(sprintId);
		
		List<SprintOutputDTO> sprintList = sprintManagerUseCase.getSprints(productId);
		
		boolean isFound = false;
		for(SprintOutputDTO sprintOutputDTO : sprintList) {
			if(sprintOutputDTO.getSprintId().equals(deletedBacklogItemId)) {
				isFound = true;
				break;
			}
		}
		assertEquals(false, isFound);
	}
	
	@Test
	public void Should_OrderIdUpdated_When_DeleteBacklogItem() {
		SprintManagerUseCase sprintManagerUseCase  = new SprintManagerUseCase(context);
		String[] goal = {
				"This is the goal of Sprint#1",
				"This is the goal of Sprint#2",
				"This is the goal of Sprint#3"
		};
		String[] startDate = {"2018-05-15", "2018-05-29", "2018-06-15"};
		String[] endDate = {"2018-05-28", "2018-06-14", "2018-06-28"};
		String[] demoDate = {"2018-05-28", "2018-06-14", "2018-06-28"};
		String[] sprintIds = new String[goal.length];
		
		for(int i=0; i<goal.length; i++) {
			SprintInputDTO sprintInputDTO = new SprintInputDTO();
			sprintInputDTO.setGoal(goal[i]);
			sprintInputDTO.setStartDate(startDate[i]);
			sprintInputDTO.setEndDate(endDate[i]);
			sprintInputDTO.setInterval(2);
			sprintInputDTO.setDemoDate(demoDate[i]);
			sprintInputDTO.setProductId(productId);
			sprintIds[i] = sprintManagerUseCase.addSprint(sprintInputDTO);
		}
		
		sprintManagerUseCase.deleteSprint(sprintIds[1]);
		
		List<SprintOutputDTO> sprintList = sprintManagerUseCase.getSprints(productId);
		
		for(int i=0; i<sprintList.size(); i++) {
			assertEquals(i+1, sprintList.get(i).getOrderId());
		}
	
	}
}
