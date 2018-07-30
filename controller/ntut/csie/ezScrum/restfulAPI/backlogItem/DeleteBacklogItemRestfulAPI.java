package ntut.csie.ezScrum.restfulAPI.backlogItem;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.repository.backlogItem.BacklogItemRepository;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.backlogItem.DeleteBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.DeleteBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.io.DeleteBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.DeleteBacklogItemOutput;

@Path("/product/{productId}/backlogItem")
public class DeleteBacklogItemRestfulAPI implements DeleteBacklogItemOutput{
	
	private Repository<BacklogItem> backlogItemRepository = new BacklogItemRepository();
	private DeleteBacklogItemUseCase deleteBacklogItemUseCase = new DeleteBacklogItemUseCaseImpl(backlogItemRepository);
	
	private boolean deleteSuccess;
	private String errorMessage;
	
	@DELETE
	@Path("/deleteBacklogItem")
	public DeleteBacklogItemOutput deleteBacklogItem(@PathParam("productId") String productId,
			String backlogItemInfo) {
		String backlogItemId = "";
		try {
			JSONObject backlogItemJSON = new JSONObject(backlogItemInfo);
			backlogItemId = backlogItemJSON.getString("backlogItemId");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		DeleteBacklogItemInput input = new DeleteBacklogItemUseCaseImpl();
		input.setBacklogItemId(backlogItemId);
		
		DeleteBacklogItemOutput output = this;
		
		deleteBacklogItemUseCase.execute(input, output);
		
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
