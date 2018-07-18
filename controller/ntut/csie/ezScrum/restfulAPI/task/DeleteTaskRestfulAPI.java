package ntut.csie.ezScrum.restfulAPI.task;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.task.DeleteTaskUseCase;
import ntut.csie.ezScrum.useCase.task.DeleteTaskUseCaseImpl;
import ntut.csie.ezScrum.useCase.task.io.DeleteTaskInput;
import ntut.csie.ezScrum.useCase.task.io.DeleteTaskOutput;

@Path("/backlogItem/{backlogItemId}/task")
public class DeleteTaskRestfulAPI implements DeleteTaskOutput{
	
	private ApplicationContext context = ApplicationContext.getInstance();
	private DeleteTaskUseCase deleteTaskUseCase = new DeleteTaskUseCaseImpl(context);
	
	private boolean deleteSuccess;
	private String errorMessage;
	
	@DELETE
	@Path("/deleteTask")
	public DeleteTaskOutput deleteBacklogItem(@PathParam("backlogItemId") String backlogItemId,
			String taskInfo) {
		String taskId = "";
		try {
			JSONObject taskJSON = new JSONObject(taskInfo);
			taskId = taskJSON.getString("taskId");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		DeleteTaskInput input = new DeleteTaskUseCaseImpl();
		input.setTaskId(taskId);
		
		DeleteTaskOutput output = this;
		
		deleteTaskUseCase.execute(input, output);
		
		return output;
	}
	
	@Override
	public boolean isDeleteSuccess() {
		return deleteSuccess;
	}
	
	@Override
	public void setDeleteSuccess(boolean deleteSuccess) {
		this.deleteSuccess = deleteSuccess;
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
