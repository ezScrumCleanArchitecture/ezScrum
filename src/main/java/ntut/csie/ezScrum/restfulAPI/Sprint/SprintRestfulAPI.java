package ntut.csie.ezScrum.restfulAPI.Sprint;

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

import ntut.csie.ezScrum.model.Sprint;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.Sprint.SprintBuilder;
import ntut.csie.ezScrum.useCase.Sprint.SprintDTO;
import ntut.csie.ezScrum.useCase.Sprint.SprintManagerUseCase;

@Path("/product/{productId}/sprint")
@Singleton
public class SprintRestfulAPI {
	
	ApplicationContext context = ApplicationContext.getInstance();
	SprintManagerUseCase sprintManagerUseCase = new SprintManagerUseCase(context);
	
	@POST
	@Path("/addSprint")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addSprint(@PathParam("productId") String productId,
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
		Sprint sprint = null;
		try {
			sprint = SprintBuilder.newInstance().
					goal(goal).
					interval(interval).
					teamSize(teamSize).
					startDate(startDate).
					endDate(endDate).
					demoDate(demoDate).
					demoPlace(demoPlace).
					daily(daily).
					productId(productId).
					build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sprintId = sprintManagerUseCase.addSprint(sprint);
		return sprintId;
	}
	
	@GET
	@Path("/getAllSprint")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SprintTableViewModel> getAllSprint(@PathParam("productId") String productId) {
		List<SprintDTO> sprintDTOList = sprintManagerUseCase.getSprints(productId);
		List<SprintTableViewModel> sprintList = new ArrayList<>();
		for(SprintDTO sprintDTO : sprintDTOList) {
			SprintTableViewModel sprintTableViewModel = new SprintTableViewModel();
			sprintTableViewModel.setSprintId(sprintDTO.getSprintId());
			sprintTableViewModel.setSerialId(sprintDTO.getSerialId());
			sprintTableViewModel.setGoal(sprintDTO.getGoal());
			sprintTableViewModel.setStartDate(sprintDTO.getStartDate());
			sprintTableViewModel.setInterval(sprintDTO.getInterval());
			sprintTableViewModel.setEndDate(sprintDTO.getEndDate());
			sprintTableViewModel.setDemoDate(sprintDTO.getDemoDate());
			sprintTableViewModel.setDemoPlace(sprintDTO.getDemoPlace());
			sprintTableViewModel.setTeamSize(sprintDTO.getTeamSize());
			sprintTableViewModel.setDaily(sprintDTO.getDaily());
			sprintList.add(sprintTableViewModel);
		}
		return sprintList;
	}
	
	@GET
	@Path("/getAllSprintList")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SprintListViewModel> getSprintList(@PathParam("productId") String productId) {
		List<SprintDTO> sprintDTOList = sprintManagerUseCase.getSprints(productId);
		List<SprintListViewModel> sprintList = new ArrayList<>();
		for(SprintDTO sprintDTO : sprintDTOList) {
			SprintListViewModel sprintViewModel = new SprintListViewModel();
			sprintViewModel.setSprintId(sprintDTO.getSprintId());
			sprintViewModel.setDisplayName("Sprint#" + sprintDTO.getSerialId());
			sprintList.add(sprintViewModel);
		}
		return sprintList;
	}
	
	@POST
	@Path("/editSprint")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String editSprint(@PathParam("productId") String productId,
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
		SprintDTO sprintDTO = new SprintDTO();
		sprintDTO.setGoal(goal);
		sprintDTO.setInterval(interval);
		sprintDTO.setTeamSize(teamSize);
		sprintDTO.setStartDate(startDate);
		sprintDTO.setEndDate(endDate);
		sprintDTO.setDemoDate(demoDate);
		sprintDTO.setDemoPlace(demoPlace);
		sprintDTO.setDemoPlace(demoPlace);
		sprintDTO.setDaily(daily);
		sprintManagerUseCase.editSprint(sprintId, sprintDTO);
		return sprintId;
	}
	
	@DELETE
	@Path("/deleteSprint")
	public String deleteBacklogItem(@PathParam("productId") String productId,
			String sprintInfo) {
		String sprintId = "";
		try {
			JSONObject sprintJSON = new JSONObject(sprintInfo);
			sprintId = sprintJSON.getString("sprintId");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		sprintManagerUseCase.deleteSprint(sprintId);
		return sprintId;
	}
}
