package ntut.csie.ezScrum.restfulAPI.task;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.ezScrum.model.task.Task;
import ntut.csie.ezScrum.repository.task.TaskRepository;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.task.EditTaskUseCase;
import ntut.csie.ezScrum.useCase.task.EditTaskUseCaseImpl;
import ntut.csie.ezScrum.useCase.task.io.EditTaskInput;
import ntut.csie.ezScrum.useCase.task.io.EditTaskOutput;

@Path("/backlogItem/{backlogItemId}/task")
public class EditTaskRestfulAPI implements EditTaskOutput {
	
	private Repository<Task> taskRepository = new TaskRepository();
	private EditTaskUseCase editTaskUseCase = new EditTaskUseCaseImpl(taskRepository);
	
	private boolean editSuccess;
	private String errorMessage;
	
	@POST
	@Path("/editTask")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EditTaskOutput editTask(@PathParam("backlogItemId") String backlogItemId,
			String taskInfo) {
		String taskId = "";
		String description = "";
		int estimate = 0;
		int remains = 0;
		String notes = "";
		try {
			JSONObject taskJSON = new JSONObject(taskInfo);
			taskId = taskJSON.getString("taskId");
			description = taskJSON.getString("description");
			estimate = taskJSON.getInt("estimate");
			remains = taskJSON.getInt("remains");
			notes = taskJSON.getString("notes");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		EditTaskInput input = new EditTaskUseCaseImpl();
		input.setTaskId(taskId);
		input.setDescription(description);
		input.setEstimate(estimate);
		input.setRemains(remains);
		input.setNotes(notes);
		input.setBacklogItemId(backlogItemId);
		
		EditTaskOutput output = this;
		
		editTaskUseCase.execute(input, output);
		
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

}
