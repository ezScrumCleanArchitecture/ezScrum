package ntut.csie.ezScrum.restfulAPI.retrospective;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.ezScrum.model.retrospective.Retrospective;
import ntut.csie.ezScrum.repository.retrospective.RetrospectiveRepository;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.retrospective.DeleteRetrospectiveUseCase;
import ntut.csie.ezScrum.useCase.retrospective.DeleteRetrospectiveUseCaseImpl;
import ntut.csie.ezScrum.useCase.retrospective.io.DeleteRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.DeleteRetrospectiveOutput;

@Path("/product/{productId}/retrospective")
public class DeleteRetrospectiveRestfulAPI implements DeleteRetrospectiveOutput{
	
	private Repository<Retrospective> retrospectiveRepository = new RetrospectiveRepository();
	private DeleteRetrospectiveUseCase deleteRetrospectiveUseCase = new DeleteRetrospectiveUseCaseImpl(retrospectiveRepository);
	
	private boolean deleteSuccess;
	private String errorMessage;
	
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
		
		DeleteRetrospectiveInput input = new DeleteRetrospectiveUseCaseImpl();
		input.setRetrospectiveId(retrospectiveId);
		
		DeleteRetrospectiveOutput output = this;
		
		deleteRetrospectiveUseCase.execute(input, output);
		
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
