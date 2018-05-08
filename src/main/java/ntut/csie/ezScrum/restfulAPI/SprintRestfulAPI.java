package ntut.csie.ezScrum.restfulAPI;

import java.util.ArrayList;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
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
		String dailyTime = "";
		String dailyPlace = "";
		try {
			JSONObject sprintJSON = new JSONObject(sprintInfo);
			goal = sprintJSON.getString("goal");
			interval = sprintJSON.getInt("interval");
			teamSize = sprintJSON.getInt("teamSize");
			startDate = sprintJSON.getString("startDate");
			endDate = sprintJSON.getString("endDate");
			demoDate = sprintJSON.getString("demoDate");
			demoPlace = sprintJSON.getString("demoPlace");
			dailyTime = sprintJSON.getString("dailyTime");
			dailyPlace = sprintJSON.getString("dailyPlace");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		SprintManagerUseCase sprintManagerUseCase = new SprintManagerUseCase(context);
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
					dailyTime(dailyTime).
					dailyPlace(dailyPlace).
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
	public ArrayList<SprintDTO> getAllSprint(@PathParam("productId") String productId) {
		SprintManagerUseCase sprintManagerUseCase = new SprintManagerUseCase(context);
		ArrayList<SprintDTO> sprintList = sprintManagerUseCase.getSprintsForUI(productId);
		return sprintList;
	}
	
}
