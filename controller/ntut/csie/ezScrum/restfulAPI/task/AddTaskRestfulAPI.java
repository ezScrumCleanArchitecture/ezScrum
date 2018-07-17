package ntut.csie.ezScrum.restfulAPI.task;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.task.AddTaskUseCase;
import ntut.csie.ezScrum.useCase.task.AddTaskUseCaseImpl;
import ntut.csie.ezScrum.useCase.task.io.AddTaskInput;
import ntut.csie.ezScrum.useCase.task.io.AddTaskOutput;

@Path("/backlogItem/{backlogItemId}/task")
@Singleton
public class AddTaskRestfulAPI implements AddTaskOutput{
	private ApplicationContext context = ApplicationContext.getInstance();
	private AddTaskUseCase addTaskUseCase = new AddTaskUseCaseImpl(context);
	
	private boolean addSuccess;
	
	@POST
	@Path("/addTask")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AddTaskOutput addTask(@PathParam("backlogItemId") String backlogItemId,
			String taskInfo) {
		String description = "";
		int estimate = 0;
		String notes = "";
		try {
			JSONObject taskJSON = new JSONObject(taskInfo);
			description = taskJSON.getString("description");
			estimate = taskJSON.getInt("estimate");
			notes = taskJSON.getString("notes");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		AddTaskInput input = new AddTaskUseCaseImpl();
		input.setDescription(description);
		input.setEstimate(estimate);
		input.setNotes(notes);
		input.setBacklogItemId(backlogItemId);
		
		AddTaskOutput output = this;
		
		addTaskUseCase.execute(input, output);
		
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
}
