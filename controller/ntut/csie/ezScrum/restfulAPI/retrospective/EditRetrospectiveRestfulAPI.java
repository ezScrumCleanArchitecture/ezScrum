package ntut.csie.ezScrum.restfulAPI.retrospective;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.retrospective.EditRetrospectiveUseCase;
import ntut.csie.ezScrum.useCase.retrospective.EditRetrospectiveUseCaseImpl;
import ntut.csie.ezScrum.useCase.retrospective.io.EditRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.EditRetrospectiveOutput;

@Path("/product/{productId}/retrospective")
public class EditRetrospectiveRestfulAPI implements EditRetrospectiveOutput{
	
	private ApplicationContext context = ApplicationContext.getInstance();
	private EditRetrospectiveUseCase editRetrospectiveUseCase = new EditRetrospectiveUseCaseImpl(context);
	
	private boolean editSuccess;
	private String errorMessage;
	
	@POST
	@Path("/editRetrospective")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EditRetrospectiveOutput editBacklogItem(@PathParam("productId") String productId, 
			String retrospectiveInfo) {
		String retrospectiveId = "";
		String description = "";
		String sprintId = "";
		try {
			JSONObject backlogItemJSON = new JSONObject(retrospectiveInfo);
			retrospectiveId = backlogItemJSON.getString("retrospectiveId");
			description = backlogItemJSON.getString("description");
			sprintId = backlogItemJSON.getString("sprintId");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		EditRetrospectiveInput input = new EditRetrospectiveUseCaseImpl();
		input.setRetrospectiveId(retrospectiveId);
		input.setDescription(description);
		input.setSprintId(sprintId);
		
		EditRetrospectiveOutput output = this;
		
		editRetrospectiveUseCase.execute(input, output);
		
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
