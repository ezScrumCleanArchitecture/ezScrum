package ntut.csie.ezScrum.restfulAPI.retrospective;

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
import ntut.csie.ezScrum.useCase.retrospective.RetrospectiveManagerUseCase;
import ntut.csie.ezScrum.useCase.retrospective.io.AddRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.AddRetrospectiveOutput;
import ntut.csie.ezScrum.useCase.retrospective.io.DeleteRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.DeleteRetrospectiveOutput;
import ntut.csie.ezScrum.useCase.retrospective.io.EditRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.EditRetrospectiveOutput;
import ntut.csie.ezScrum.useCase.retrospective.io.GetRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.GetRetrospectiveOutput;

@Path("/product/{productId}/retrospective")
@Singleton
public class RetrospectiveRestfulAPI {
	
	ApplicationContext context = ApplicationContext.getInstance();
	RetrospectiveManagerUseCase retrospectiveManagerUseCase = new RetrospectiveManagerUseCase(context);
	
	@POST
	@Path("/addRetrospective")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AddRetrospectiveOutput addRetrospective(@PathParam("productId") String productId,
			String retrospectiveInfo) {
		String description = "";
		String sprintId = "";
		try {
			JSONObject retrospectiveJSON = new JSONObject(retrospectiveInfo);
			description = retrospectiveJSON.getString("description");
			sprintId = retrospectiveJSON.getString("sprintId");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		AddRetrospectiveInput addRetrospectiveInput = new AddRetrospectiveInput();
		addRetrospectiveInput.setDescription(description);
		addRetrospectiveInput.setProductId(productId);
		addRetrospectiveInput.setSprintId(sprintId);
		
		AddRetrospectiveOutput addRetrospectiveOutput = retrospectiveManagerUseCase.addRetrospective(addRetrospectiveInput);
		
		return addRetrospectiveOutput;
	}
	
	@GET
	@Path("/getAllRetrospective")
	@Produces(MediaType.APPLICATION_JSON)
	public List<GetRetrospectiveOutput> getAllRetrospective(@PathParam("productId") String productId) {
		GetRetrospectiveInput getRetrospectiveInput = new GetRetrospectiveInput();
		getRetrospectiveInput.setProductId(productId);
		List<GetRetrospectiveOutput> retrospectiveDTOList = retrospectiveManagerUseCase.getRetrospectives(getRetrospectiveInput);
		return retrospectiveDTOList;
	}
	
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
		
		EditRetrospectiveInput editRetrospectiveInput = new EditRetrospectiveInput();
		editRetrospectiveInput.setRetrospectiveId(retrospectiveId);
		editRetrospectiveInput.setDescription(description);
		editRetrospectiveInput.setSprintId(sprintId);
		
		EditRetrospectiveOutput editRetrospectiveOutput = retrospectiveManagerUseCase.editRetrospective(editRetrospectiveInput);
		
		return editRetrospectiveOutput;
	}
	
	@DELETE
	@Path("/deleteRetrospective")
	public DeleteRetrospectiveOutput deleteRetrospective(@PathParam("productId") String productId,
			String retrospectiveInfo) {
		String retrospectiveId = "";
		try {
			JSONObject retrospectiveJSON = new JSONObject(retrospectiveInfo);
			retrospectiveId = retrospectiveJSON.getString("retrospectiveId");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		DeleteRetrospectiveInput deleteRetrospectiveInput = new DeleteRetrospectiveInput();
		deleteRetrospectiveInput.setRetrospectiveId(retrospectiveId);
		
		DeleteRetrospectiveOutput deleteRetrospectiveOutput = retrospectiveManagerUseCase.deleteRetrospective(deleteRetrospectiveInput);
		
		return deleteRetrospectiveOutput;
	}
}