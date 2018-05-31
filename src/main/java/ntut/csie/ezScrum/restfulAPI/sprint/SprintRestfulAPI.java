package ntut.csie.ezScrum.restfulAPI.sprint;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.ezScrum.restfulAPI.sprint.viewModel.AddSprintViewModel;
import ntut.csie.ezScrum.restfulAPI.sprint.viewModel.DeleteSprintViewModel;
import ntut.csie.ezScrum.restfulAPI.sprint.viewModel.EditSprintViewModel;
import ntut.csie.ezScrum.restfulAPI.sprint.viewModel.IsSprintOverdueViewModel;
import ntut.csie.ezScrum.restfulAPI.sprint.viewModel.IsSprintOverlapViewModel;
import ntut.csie.ezScrum.restfulAPI.sprint.viewModel.SprintListViewModel;
import ntut.csie.ezScrum.restfulAPI.sprint.viewModel.SprintTableViewModel;
import ntut.csie.ezScrum.restfulAPI.sprint.viewModel.SprintViewModel;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.sprint.SprintManagerUseCase;
import ntut.csie.ezScrum.useCase.sprint.io.AddSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.AddSprintOutput;
import ntut.csie.ezScrum.useCase.sprint.io.DeleteSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.DeleteSprintOutput;
import ntut.csie.ezScrum.useCase.sprint.io.EditSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.EditSprintOutput;
import ntut.csie.ezScrum.useCase.sprint.io.GetSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.SprintDTO;
import ntut.csie.ezScrum.useCase.sprint.io.IsSprintOverdueInput;
import ntut.csie.ezScrum.useCase.sprint.io.IsSprintOverdueOutput;
import ntut.csie.ezScrum.useCase.sprint.io.IsSprintOverlapInput;
import ntut.csie.ezScrum.useCase.sprint.io.IsSprintOverlapOutput;

@Path("/product/{productId}/sprint")
@Singleton
public class SprintRestfulAPI {
	
	ApplicationContext context = ApplicationContext.getInstance();
	SprintManagerUseCase sprintManagerUseCase = new SprintManagerUseCase(context);
	
	@POST
	@Path("/addSprint")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SprintViewModel addSprint(@PathParam("productId") String productId,
			String sprintInfo) {
		String goal = "";
		int interval = 0;
		int teamSize = 0;
		String startDate = "";
		String endDate = "";
		String demoDate = "";
		String demoPlace = "";
		String daily = "";
		try {
			JSONObject sprintJSON = new JSONObject(sprintInfo);
			goal = sprintJSON.getString("goal");
			interval = sprintJSON.getInt("interval");
			teamSize = sprintJSON.getInt("teamSize");
			startDate = sprintJSON.getString("startDate");
			endDate = sprintJSON.getString("endDate");
			demoDate = sprintJSON.getString("demoDate");
			demoPlace = sprintJSON.getString("demoPlace");
			daily = sprintJSON.getString("daily");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		AddSprintInput addSprintInput = new AddSprintInput();
		addSprintInput.setGoal(goal);
		addSprintInput.setInterval(interval);
		addSprintInput.setTeamSize(teamSize);
		addSprintInput.setStartDate(startDate);
		addSprintInput.setEndDate(endDate);
		addSprintInput.setDemoDate(demoDate);
		addSprintInput.setDemoPlace(demoPlace);
		addSprintInput.setDaily(daily);
		addSprintInput.setProductId(productId);
		
		AddSprintOutput addSprintOutput = sprintManagerUseCase.addSprint(addSprintInput);
		String sprintId = addSprintOutput.getSprintId();
		
		SprintViewModel sprintViewModel = new AddSprintViewModel();
		sprintViewModel.setSprintId(sprintId);
		return sprintViewModel;
	}
	
	@GET
	@Path("/getAllSprint")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SprintTableViewModel> getAllSprint(@PathParam("productId") String productId) {
		GetSprintInput getSprintInput = new GetSprintInput();
		getSprintInput.setProductId(productId);
		
		List<SprintDTO> sprintDTOList = sprintManagerUseCase.getSprints(getSprintInput);
		
		List<SprintTableViewModel> sprintTableViewModelList = new ArrayList<>();
		for(SprintDTO sprintDTO : sprintDTOList) {
			SprintTableViewModel sprintTableViewModel = new SprintTableViewModel();
			sprintTableViewModel.setSprintId(sprintDTO.getSprintId());
			sprintTableViewModel.setOrderId(sprintDTO.getOrderId());
			sprintTableViewModel.setGoal(sprintDTO.getGoal());
			sprintTableViewModel.setStartDate(sprintDTO.getStartDate());
			sprintTableViewModel.setInterval(sprintDTO.getInterval());
			sprintTableViewModel.setEndDate(sprintDTO.getEndDate());
			sprintTableViewModel.setDemoDate(sprintDTO.getDemoDate());
			sprintTableViewModel.setDemoPlace(sprintDTO.getDemoPlace());
			sprintTableViewModel.setTeamSize(sprintDTO.getTeamSize());
			sprintTableViewModel.setDaily(sprintDTO.getDaily());
			sprintTableViewModelList.add(sprintTableViewModel);
		}
		return sprintTableViewModelList;
	}
	
	@GET
	@Path("/getAllSprintList")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SprintListViewModel> getSprintList(@PathParam("productId") String productId) {
		GetSprintInput getSprintInput = new GetSprintInput();
		getSprintInput.setProductId(productId);
		
		List<SprintDTO> sprintDTOList = sprintManagerUseCase.getSprints(getSprintInput);
		
		List<SprintListViewModel> sprintList = new ArrayList<>();
		for(SprintDTO sprintDTO : sprintDTOList) {
			SprintListViewModel sprintViewModel = new SprintListViewModel();
			sprintViewModel.setSprintId(sprintDTO.getSprintId());
			sprintViewModel.setDisplayName("Sprint#" + sprintDTO.getOrderId());
			sprintList.add(sprintViewModel);
		}
		return sprintList;
	}
	
	@POST
	@Path("/editSprint")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EditSprintViewModel editSprint(@PathParam("productId") String productId,
			String sprintInfo) {
		String sprintId = "";
		String goal = "";
		int interval = 0;
		int teamSize = 0;
		String startDate = "";
		String endDate = "";
		String demoDate = "";
		String demoPlace = "";
		String daily = "";
		try {
			JSONObject sprintJSON = new JSONObject(sprintInfo);
			sprintId = sprintJSON.getString("sprintId");
			goal = sprintJSON.getString("goal");
			interval = sprintJSON.getInt("interval");
			teamSize = sprintJSON.getInt("teamSize");
			startDate = sprintJSON.getString("startDate");
			endDate = sprintJSON.getString("endDate");
			demoDate = sprintJSON.getString("demoDate");
			demoPlace = sprintJSON.getString("demoPlace");
			daily = sprintJSON.getString("daily");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		EditSprintInput editSprintInput = new EditSprintInput();
		editSprintInput.setSprintId(sprintId);
		editSprintInput.setGoal(goal);
		editSprintInput.setInterval(interval);
		editSprintInput.setTeamSize(teamSize);
		editSprintInput.setStartDate(startDate);
		editSprintInput.setEndDate(endDate);
		editSprintInput.setDemoDate(demoDate);
		editSprintInput.setDemoPlace(demoPlace);
		editSprintInput.setDaily(daily);
		
		EditSprintOutput editSprintOutput = sprintManagerUseCase.editSprint(editSprintInput);
		boolean isSuccess = editSprintOutput.isEditSuccess();
		
		EditSprintViewModel editSprintViewModel = new EditSprintViewModel();
		if(isSuccess) {
			editSprintViewModel.setMessage("Edit sprint success!");
		}else {
			editSprintViewModel.setMessage("Edit sprint fail!");
		}
		
		return editSprintViewModel;
	}
	
	@DELETE
	@Path("/deleteSprint")
	public DeleteSprintViewModel deleteBacklogItem(@PathParam("productId") String productId,
			String sprintInfo) {
		String sprintId = "";
		try {
			JSONObject sprintJSON = new JSONObject(sprintInfo);
			sprintId = sprintJSON.getString("sprintId");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		DeleteSprintInput deleteSprintInput = new DeleteSprintInput();
		deleteSprintInput.setSprintId(sprintId);
		
		DeleteSprintOutput deleteSprintOutput = sprintManagerUseCase.deleteSprint(deleteSprintInput);
		boolean isSuccess = deleteSprintOutput.isDeleteSuccess();
		
		DeleteSprintViewModel deleteSprintViewModel = new DeleteSprintViewModel();
		if(isSuccess) {
			deleteSprintViewModel.setMessage("Delete sprint success!");
		}else {
			deleteSprintViewModel.setMessage("Delete sprint fail!");
		}
		
		return deleteSprintViewModel;
	}
	
	@POST
	@Path("/isSprintOverlap")
	public IsSprintOverlapViewModel isSprintOverlap(@PathParam("productId") String productId,
			String sprintInfo) {
		String startDate = "";
		String endDate = "";
		try {
			JSONObject sprintJSON = new JSONObject(sprintInfo);
			startDate = sprintJSON.getString("startDate");
			endDate = sprintJSON.getString("endDate");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		IsSprintOverlapInput isSprintOverlapInput = new IsSprintOverlapInput();
		isSprintOverlapInput.setProductId(productId);
		isSprintOverlapInput.setStartDate(startDate);
		isSprintOverlapInput.setEndDate(endDate);
		
		boolean isOverlap = false;
		try {
			IsSprintOverlapOutput isSprintOverlapOutput = sprintManagerUseCase.isSprintOverlap(isSprintOverlapInput);
			isOverlap = isSprintOverlapOutput.isSprintOverlap();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		IsSprintOverlapViewModel isSprintOverlapViewModel = new IsSprintOverlapViewModel();
		isSprintOverlapViewModel.setSprintOverlap(isOverlap);
		
		return isSprintOverlapViewModel;
	}
	
	@POST
	@Path("/isSprintOverdue")
	public IsSprintOverdueViewModel isSprintOverdue(String sprintInfo) {
		String sprintId = "";
		try {
			JSONObject sprintJSON = new JSONObject(sprintInfo);
			sprintId = sprintJSON.getString("sprintId");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		IsSprintOverdueInput isSprintOverdueInput = new IsSprintOverdueInput();
		isSprintOverdueInput.setSprintId(sprintId);
		
		boolean isOverdue = false;
		try {
			IsSprintOverdueOutput isSprintOverdueOutput = sprintManagerUseCase.isSprintOverdue(isSprintOverdueInput);
			isOverdue = isSprintOverdueOutput.isSprintOverdue();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		IsSprintOverdueViewModel isSprintOverdueViewModel = new IsSprintOverdueViewModel();
		isSprintOverdueViewModel.setSprintOverdue(isOverdue);
		
		return isSprintOverdueViewModel;
	}
}
