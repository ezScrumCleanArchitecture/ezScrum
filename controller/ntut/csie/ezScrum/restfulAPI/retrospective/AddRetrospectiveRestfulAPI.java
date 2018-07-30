package ntut.csie.ezScrum.restfulAPI.retrospective;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.ezScrum.model.retrospective.Retrospective;
import ntut.csie.ezScrum.repository.retrospective.RetrospectiveRepository;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.retrospective.AddRetrospectiveUseCase;
import ntut.csie.ezScrum.useCase.retrospective.AddRetrospectiveUseCaseImpl;
import ntut.csie.ezScrum.useCase.retrospective.io.AddRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.AddRetrospectiveOutput;

@Path("/product/{productId}/retrospective")
public class AddRetrospectiveRestfulAPI implements AddRetrospectiveOutput{
	
	private Repository<Retrospective> retrospectiveRepository = new RetrospectiveRepository();
	private AddRetrospectiveUseCase addRetrospectiveUseCase = new AddRetrospectiveUseCaseImpl(retrospectiveRepository);
	
	private boolean addSuccess;
	
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
		
		AddRetrospectiveInput input = new AddRetrospectiveUseCaseImpl();
		input.setDescription(description);
		input.setProductId(productId);
		input.setSprintId(sprintId);
		
		AddRetrospectiveOutput output = this;
		
		addRetrospectiveUseCase.execute(input, output);
		
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
