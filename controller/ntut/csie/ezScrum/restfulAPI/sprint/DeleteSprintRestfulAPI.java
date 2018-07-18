package ntut.csie.ezScrum.restfulAPI.sprint;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.sprint.DeleteSprintUseCase;
import ntut.csie.ezScrum.useCase.sprint.DeleteSprintUseCaseImpl;
import ntut.csie.ezScrum.useCase.sprint.io.DeleteSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.DeleteSprintOutput;

@Path("/product/{productId}/sprint")
public class DeleteSprintRestfulAPI implements DeleteSprintOutput{
	
	private ApplicationContext context = ApplicationContext.getInstance();
	private DeleteSprintUseCase deleteSprintUseCase = new DeleteSprintUseCaseImpl(context);
	
	private boolean deleteSuccess;
	private String errorMessage;

	@DELETE
	@Path("/deleteSprint")
	public DeleteSprintOutput deleteBacklogItem(@PathParam("productId") String productId,
			String sprintInfo) {
		String sprintId = "";
		try {
			JSONObject sprintJSON = new JSONObject(sprintInfo);
			sprintId = sprintJSON.getString("sprintId");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		DeleteSprintInput input = new DeleteSprintUseCaseImpl();
		input.setSprintId(sprintId);
		
		DeleteSprintOutput output = this;
		
		deleteSprintUseCase.execute(input, output);
		
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
