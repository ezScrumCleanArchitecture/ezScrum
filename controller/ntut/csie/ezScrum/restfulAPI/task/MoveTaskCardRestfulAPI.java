package ntut.csie.ezScrum.restfulAPI.task;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.ezScrum.ApplicationContext;
import ntut.csie.ezScrum.useCase.task.MoveTaskCardUseCase;
import ntut.csie.ezScrum.useCase.task.MoveTaskCardUseCaseImpl;
import ntut.csie.ezScrum.useCase.task.io.MoveTaskCardInput;
import ntut.csie.ezScrum.useCase.task.io.MoveTaskCardOutput;

@Path("/backlogItem/{backlogItemId}/task")
public class MoveTaskCardRestfulAPI implements MoveTaskCardOutput{
	
	private ApplicationContext applicationContext = ApplicationContext.getInstance();
	private MoveTaskCardUseCase moveTaskUseCase = applicationContext.newMoveTaskCardUseCase();
	
	private boolean moveSuccess;
	private String errorMessage;

	@POST
	@Path("/moveTaskCard")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MoveTaskCardOutput moveTaskCard(@PathParam("backlogItemId") String backlogItemId,
			String taskInfo) {
		String taskId = "";
		String status = "";
		try {
			JSONObject taskJSON = new JSONObject(taskInfo);
			taskId = taskJSON.getString("taskId");
			status = taskJSON.getString("status");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		MoveTaskCardInput input = new MoveTaskCardUseCaseImpl();
		input.setTaskId(taskId);
		input.setStatus(status);
		
		MoveTaskCardOutput output = this;
		
		moveTaskUseCase.execute(input, output);
		
		return output;
	}
	
	@Override
	public boolean isMoveSuccess() {
		return moveSuccess;
	}

	@Override
	public void setMoveSuccess(boolean moveSuccess) {
		this.moveSuccess = moveSuccess;
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
