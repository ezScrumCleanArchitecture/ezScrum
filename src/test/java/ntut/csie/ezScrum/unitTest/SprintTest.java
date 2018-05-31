package ntut.csie.ezScrum.unitTest;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ntut.csie.ezScrum.model.product.Product;
import ntut.csie.ezScrum.model.product.ProductBuilder;
import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.model.sprint.SprintBuilder;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.product.ProductManagerUseCase;
import ntut.csie.ezScrum.useCase.sprint.SprintManagerUseCase;
import ntut.csie.ezScrum.useCase.sprint.io.AddSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.AddSprintOutput;
import ntut.csie.ezScrum.useCase.sprint.io.DeleteSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.DeleteSprintOutput;
import ntut.csie.ezScrum.useCase.sprint.io.EditSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.EditSprintOutput;
import ntut.csie.ezScrum.useCase.sprint.io.GetSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.SprintDTO;
import ntut.csie.ezScrum.useCase.sprint.io.IsSprintOverlapInput;
import ntut.csie.ezScrum.useCase.sprint.io.IsSprintOverlapOutput;

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
		
		AddSprintInput sprintAddInput = new AddSprintInput();
		sprintAddInput.setGoal(goal);
		sprintAddInput.setInterval(interval);
		sprintAddInput.setStartDate(startDate);
		sprintAddInput.setEndDate(endDate);
		sprintAddInput.setDemoDate(demoDate);
		sprintAddInput.setProductId(productId);
		
		AddSprintOutput sprintAddOutput = sprintManagerUseCase.addSprint(sprintAddInput);
		String sprintId = sprintAddOutput.getSprintId();
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
		
		AddSprintInput sprintAddInput = new AddSprintInput();
		sprintAddInput.setGoal(goal);
		sprintAddInput.setInterval(interval);
		sprintAddInput.setTeamSize(teamSize);
		sprintAddInput.setStartDate(startDate);
		sprintAddInput.setEndDate(endDate);
		sprintAddInput.setDemoDate(demoDate);
		sprintAddInput.setDemoPlace(demoPlace);
		sprintAddInput.setDaily(daily);
		sprintAddInput.setProductId(productId);
		
		AddSprintOutput sprintAddOutput = sprintManagerUseCase.addSprint(sprintAddInput);
		String sprintId = sprintAddOutput.getSprintId();
		
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
			AddSprintInput sprintInputDTO = new AddSprintInput();
			sprintInputDTO.setGoal(goal[i]);
			sprintInputDTO.setStartDate(startDate[i]);
			sprintInputDTO.setEndDate(endDate[i]);
			sprintInputDTO.setInterval(2);
			sprintInputDTO.setDemoDate(demoDate[i]);
			sprintInputDTO.setProductId(productId);
			sprintManagerUseCase.addSprint(sprintInputDTO);
		}
		
		GetSprintInput sprintGetInput = new GetSprintInput();
		sprintGetInput.setProductId(productId);
		
		List<SprintDTO> sprintList = sprintManagerUseCase.getSprints(sprintGetInput);
		
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
		
		AddSprintInput sprintAddInput = new AddSprintInput();
		sprintAddInput.setGoal(goal);
		sprintAddInput.setInterval(interval);
		sprintAddInput.setTeamSize(teamSize);
		sprintAddInput.setStartDate(startDate);
		sprintAddInput.setEndDate(endDate);
		sprintAddInput.setDemoDate(demoDate);
		sprintAddInput.setDemoPlace(demoPlace);
		sprintAddInput.setDaily(daily);
		sprintAddInput.setProductId(productId);
		
		AddSprintOutput sprintAddOutput = sprintManagerUseCase.addSprint(sprintAddInput);
		String sprintId = sprintAddOutput.getSprintId();
		
		String editedGoal = "Implement the function of editing sprint.";
		int editedTeamSize = 2;
		String editedStartDate = "2018-04-09";
		int editedInterval = 3;
		String editedEndDate = "2018-04-29";
		String editedDemoDate = "2018-04-29";
		String editedDemoPlace = "03F Room";
		String editedDaily = "15:00 1323";
		
		EditSprintInput sprintEditInput = new EditSprintInput();
		
		sprintEditInput.setSprintId(sprintId);
		sprintEditInput.setGoal(editedGoal);
		sprintEditInput.setTeamSize(editedTeamSize);
		sprintEditInput.setStartDate(editedStartDate);
		sprintEditInput.setInterval(editedInterval);
		sprintEditInput.setEndDate(editedEndDate);
		sprintEditInput.setDemoDate(editedDemoDate);
		sprintEditInput.setDemoPlace(editedDemoPlace);
		sprintEditInput.setDaily(editedDaily);
		
		EditSprintOutput editedSprintOutput = sprintManagerUseCase.editSprint(sprintEditInput);
		
		Sprint testedSprint = context.getSprint(sprintId);
		
		assertEquals(true, editedSprintOutput.isEditSuccess());
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
		
		AddSprintInput sprintAddInput = new AddSprintInput();
		sprintAddInput.setGoal(goal);
		sprintAddInput.setInterval(interval);
		sprintAddInput.setTeamSize(teamSize);
		sprintAddInput.setStartDate(startDate);
		sprintAddInput.setEndDate(endDate);
		sprintAddInput.setDemoDate(demoDate);
		sprintAddInput.setDemoPlace(demoPlace);
		sprintAddInput.setDaily(daily);
		sprintAddInput.setProductId(productId);
		
		AddSprintOutput sprintAddOutput = sprintManagerUseCase.addSprint(sprintAddInput);
		String sprintId = sprintAddOutput.getSprintId();
		
		DeleteSprintInput sprintDeleteInput = new DeleteSprintInput();
		sprintDeleteInput.setSprintId(sprintId);
		DeleteSprintOutput sprintDeleteOutput = sprintManagerUseCase.deleteSprint(sprintDeleteInput);
		
		GetSprintInput sprintGetInput = new GetSprintInput();
		sprintGetInput.setProductId(productId);
		
		List<SprintDTO> sprintList = sprintManagerUseCase.getSprints(sprintGetInput);
		
		assertEquals(true, sprintDeleteOutput.isDeleteSuccess());
		boolean isFound = false;
		for(SprintDTO sprintOutputDTO : sprintList) {
			if(sprintOutputDTO.getSprintId().equals(sprintId)) {
				isFound = true;
				break;
			}
		}
		assertEquals(false, isFound);
	}
	
	@Test
	public void Should_OrderIdUpdated_When_DeleteSpint() {
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
			AddSprintInput sprintAddInput = new AddSprintInput();
			sprintAddInput.setGoal(goal[i]);
			sprintAddInput.setStartDate(startDate[i]);
			sprintAddInput.setEndDate(endDate[i]);
			sprintAddInput.setInterval(2);
			sprintAddInput.setDemoDate(demoDate[i]);
			sprintAddInput.setProductId(productId);
			AddSprintOutput sprintAddOutput = sprintManagerUseCase.addSprint(sprintAddInput);
			sprintIds[i] = sprintAddOutput.getSprintId();
		}
		
		DeleteSprintInput sprintDeleteInput = new DeleteSprintInput();
		sprintDeleteInput.setSprintId(sprintIds[1]);
		sprintManagerUseCase.deleteSprint(sprintDeleteInput);
		
		GetSprintInput sprintGetInput = new GetSprintInput();
		sprintGetInput.setProductId(productId);
		
		List<SprintDTO> sprintList = sprintManagerUseCase.getSprints(sprintGetInput);
		
		for(int i=0; i<sprintList.size(); i++) {
			assertEquals(i+1, sprintList.get(i).getOrderId());
		}
	
	}
	
	@Test
	public void Should_ReturnTrue_When_SprintIsOverlap() {
		SprintManagerUseCase sprintManagerUseCase = new SprintManagerUseCase(context);
		String goal = "Implement the function of creating sprint.";
		String startDate = "2018-04-09";
		String endDate = "2018-04-22";
		int interval = 2;
		String demoDate = "2018-04-22";
		
		AddSprintInput sprintInputDTO = new AddSprintInput();
		sprintInputDTO.setGoal(goal);
		sprintInputDTO.setInterval(interval);
		sprintInputDTO.setStartDate(startDate);
		sprintInputDTO.setEndDate(endDate);
		sprintInputDTO.setDemoDate(demoDate);
		sprintInputDTO.setProductId(productId);
		
		sprintManagerUseCase.addSprint(sprintInputDTO);
		
		IsSprintOverlapInput[] isSprintOverlapInputs = new IsSprintOverlapInput[4];
		for(int i = 0; i < isSprintOverlapInputs.length; i++) {
			isSprintOverlapInputs[i] = new IsSprintOverlapInput();
			isSprintOverlapInputs[i].setProductId(productId);
		}
		
		isSprintOverlapInputs[0].setStartDate("2018-04-10");
		isSprintOverlapInputs[0].setEndDate("2018-04-23");
		
		isSprintOverlapInputs[1].setStartDate("2018-04-07");
		isSprintOverlapInputs[1].setEndDate("2018-04-20");
		
		isSprintOverlapInputs[2].setStartDate("2018-04-09");
		isSprintOverlapInputs[2].setEndDate("2018-04-22");
		
		isSprintOverlapInputs[3].setStartDate("2018-04-15");
		isSprintOverlapInputs[3].setEndDate("2018-04-20");
		
		try {
			for(int i = 0; i < isSprintOverlapInputs.length; i++) {
				IsSprintOverlapOutput isSprintOverlapOutput = sprintManagerUseCase.isSprintOverlap(isSprintOverlapInputs[i]);
				boolean isSprintOverlap = isSprintOverlapOutput.isSprintOverlap();
				assertEquals(true, isSprintOverlap);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void Should_ReturnFalse_When_SprintIsNotOverlap() {
		SprintManagerUseCase sprintManagerUseCase = new SprintManagerUseCase(context);
		String goal = "Implement the function of creating sprint.";
		String startDate = "2018-04-09";
		String endDate = "2018-04-22";
		int interval = 2;
		String demoDate = "2018-04-22";
		
		AddSprintInput sprintInputDTO = new AddSprintInput();
		sprintInputDTO.setGoal(goal);
		sprintInputDTO.setInterval(interval);
		sprintInputDTO.setStartDate(startDate);
		sprintInputDTO.setEndDate(endDate);
		sprintInputDTO.setDemoDate(demoDate);
		sprintInputDTO.setProductId(productId);
		
		sprintManagerUseCase.addSprint(sprintInputDTO);
		
		IsSprintOverlapInput[] isSprintOverlapInputs = new IsSprintOverlapInput[2];
		for(int i = 0; i < isSprintOverlapInputs.length; i++) {
			isSprintOverlapInputs[i] = new IsSprintOverlapInput();
			isSprintOverlapInputs[i].setProductId(productId);
		}
		
		isSprintOverlapInputs[0].setStartDate("2018-04-01");
		isSprintOverlapInputs[0].setEndDate("2018-04-08");
		
		isSprintOverlapInputs[1].setStartDate("2018-04-23");
		isSprintOverlapInputs[1].setEndDate("2018-04-30");
		
		try {
			for(int i = 0; i < isSprintOverlapInputs.length; i++) {
				IsSprintOverlapOutput isSprintOverlapOutput = sprintManagerUseCase.isSprintOverlap(isSprintOverlapInputs[i]);
				boolean isSprintOverlap = isSprintOverlapOutput.isSprintOverlap();
				assertEquals(false, isSprintOverlap);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
