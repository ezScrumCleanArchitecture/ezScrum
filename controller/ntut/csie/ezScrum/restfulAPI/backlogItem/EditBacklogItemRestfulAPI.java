package ntut.csie.ezScrum.restfulAPI.backlogItem;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.repository.backlogItem.BacklogItemRepository;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.backlogItem.EditBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.EditBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.io.EditBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.EditBacklogItemOutput;

@Path("/product/{productId}/backlogItem")
public class EditBacklogItemRestfulAPI implements EditBacklogItemOutput{
	
	private Repository<BacklogItem> backlogItemRepository = new BacklogItemRepository();
	private EditBacklogItemUseCase editBacklogItemUseCase = new EditBacklogItemUseCaseImpl(backlogItemRepository);
	
	private boolean editSuccess;
	private String errorMessage;
	
	@POST
	@Path("/editBacklogItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EditBacklogItemOutput editBacklogItem(@PathParam("productId") String productId, 
			String backlogItemInfo) {
		String backlogItemId = "";
		String description = "";
		int estimate = 0;
		int importance = 0;
		String notes = "";
		try {
			JSONObject backlogItemJSON = new JSONObject(backlogItemInfo);
			backlogItemId = backlogItemJSON.getString("backlogItemId");
			description = backlogItemJSON.getString("description");
			estimate = backlogItemJSON.getInt("estimate");
			importance = backlogItemJSON.getInt("importance");
			notes = backlogItemJSON.getString("notes");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		EditBacklogItemInput input = new EditBacklogItemUseCaseImpl();
		input.setBacklogItemId(backlogItemId);
		input.setDescription(description);
		input.setEstimate(estimate);
		input.setImportance(importance);
		input.setNotes(notes);
		
		EditBacklogItemOutput output = this;
		
		editBacklogItemUseCase.execute(input, output);
		
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
