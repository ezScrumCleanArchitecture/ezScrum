package ntut.csie.ezScrum.restfulAPI.sprint;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.sprint.EditSprintUseCase;
import ntut.csie.ezScrum.useCase.sprint.EditSprintUseCaseImpl;
import ntut.csie.ezScrum.useCase.sprint.io.EditSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.EditSprintOutput;

@Path("/product/{productId}/sprint")
public class EditSprintRestfulAPI implements EditSprintOutput{
	
	private ApplicationContext context = ApplicationContext.getInstance();
	private EditSprintUseCase editSprintUseCase = new EditSprintUseCaseImpl(context);
	
	private boolean editSuccess;
	private boolean overlap;
	private String errorMessage;

	@POST
	@Path("/editSprint")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EditSprintOutput editSprint(@PathParam("productId") String productId,
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
		
		EditSprintInput input = new EditSprintUseCaseImpl();
		input.setSprintId(sprintId);
		input.setGoal(goal);
		input.setInterval(interval);
		input.setTeamSize(teamSize);
		input.setStartDate(startDate);
		input.setEndDate(endDate);
		input.setDemoDate(demoDate);
		input.setDemoPlace(demoPlace);
		input.setDaily(daily);
		
		EditSprintOutput output = this;
		
		editSprintUseCase.execute(input, output);
		
		return output;
	}

	@Override
	public boolean isEditSuccess() {
		return editSuccess;
	}

	@Override
	public void setEditSuccess(boolean editSuccess) {
		this.editSuccess = editSuccess;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public boolean isOverlap() {
		return overlap;
	}

	@Override
	public void setOverlap(boolean overlap) {
		this.overlap = overlap;
		
	}

}
