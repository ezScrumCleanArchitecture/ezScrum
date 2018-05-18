package ntut.csie.ezScrum.restfulAPI.Sprint;

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

import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.Sprint.SprintInputDTO;
import ntut.csie.ezScrum.useCase.Sprint.SprintOutputDTO;
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
		return sprintId;
	}
	
	@GET
	@Path("/getAllSprint")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SprintTableViewModel> getAllSprint(@PathParam("productId") String productId) {
		List<SprintOutputDTO> sprintDTOList = sprintManagerUseCase.getSprints(productId);
		List<SprintTableViewModel> sprintList = new ArrayList<>();
		for(SprintOutputDTO sprintDTO : sprintDTOList) {
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
			sprintList.add(sprintTableViewModel);
		}
		return sprintList;
	}
	
	@GET
	@Path("/getAllSprintList")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SprintListViewModel> getSprintList(@PathParam("productId") String productId) {
		List<SprintOutputDTO> sprintDTOList = sprintManagerUseCase.getSprints(productId);
		List<SprintListViewModel> sprintList = new ArrayList<>();
		for(SprintOutputDTO sprintDTO : sprintDTOList) {
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
		SprintInputDTO sprintInputDTO = new SprintInputDTO();
		sprintInputDTO.setGoal(goal);
		sprintInputDTO.setInterval(interval);
		sprintInputDTO.setTeamSize(teamSize);
		sprintInputDTO.setStartDate(startDate);
		sprintInputDTO.setEndDate(endDate);
		sprintInputDTO.setDemoDate(demoDate);
		sprintInputDTO.setDemoPlace(demoPlace);
		sprintInputDTO.setDaily(daily);
		sprintManagerUseCase.editSprint(sprintId, sprintInputDTO);
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
	
	@POST
	@Path("/checkSprintOverlap")
	public boolean checkSprintOverlap(@PathParam("productId") String productId,
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
		boolean isOverlap = false;
		try {
			isOverlap = sprintManagerUseCase.isSprintOverlap(productId, startDate, endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isOverlap;
	}
}
