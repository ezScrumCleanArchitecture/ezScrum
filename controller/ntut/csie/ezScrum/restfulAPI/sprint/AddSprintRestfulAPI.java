package ntut.csie.ezScrum.restfulAPI.sprint;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.repository.sprint.SprintRepository;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.sprint.AddSprintUseCase;
import ntut.csie.ezScrum.useCase.sprint.AddSprintUseCaseImpl;
import ntut.csie.ezScrum.useCase.sprint.io.AddSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.AddSprintOutput;

@Path("/product/{productId}/sprint")
public class AddSprintRestfulAPI implements AddSprintOutput{
	
	private Repository<Sprint> sprintRepository = new SprintRepository();
	private AddSprintUseCase addSprintUseCase = new AddSprintUseCaseImpl(sprintRepository);
	
	private boolean addSuccess;
	private String errorMessage;
	
	@POST
	@Path("/addSprint")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AddSprintOutput addSprint(@PathParam("productId") String productId,
			String sprintInfo) {
		String goal = "";
		int interval = 0;
		String startDate = "";
		String endDate = "";
		String demoDate = "";
		String demoPlace = "";
		String daily = "";
		try {
			JSONObject sprintJSON = new JSONObject(sprintInfo);
			goal = sprintJSON.getString("goal");
			interval = sprintJSON.getInt("interval");
			startDate = sprintJSON.getString("startDate");
			endDate = sprintJSON.getString("endDate");
			demoDate = sprintJSON.getString("demoDate");
			demoPlace = sprintJSON.getString("demoPlace");
			daily = sprintJSON.getString("daily");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		AddSprintInput input = new AddSprintUseCaseImpl();
		input.setGoal(goal);
		input.setInterval(interval);
		input.setStartDate(startDate);
		input.setEndDate(endDate);
		input.setDemoDate(demoDate);
		input.setDemoPlace(demoPlace);
		input.setDaily(daily);
		input.setProductId(productId);
		
		AddSprintOutput output = this;
		
		addSprintUseCase.execute(input, output);

		return output;
	}

	@Override
	public boolean isAddSuccess() {
		return addSuccess;
	}

	@Override
	public void setAddSuccess(boolean addSuccess) {
		this.addSuccess = addSuccess;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
