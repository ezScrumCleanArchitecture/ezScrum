package ntut.csie.ezScrum.restfulAPI.backlogItem;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.backlogItem.AddBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.AddBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.io.AddBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.AddBacklogItemOutput;

@Path("/product/{productId}/backlogItem")
public class AddBacklogItemRestfulAPI implements AddBacklogItemOutput{
	
	private ApplicationContext context = ApplicationContext.getInstance();
	private AddBacklogItemUseCase addBacklogItemUseCase = new AddBacklogItemUseCaseImpl(context);
	
	private String backlogItemId;
	private boolean addSuccess;
	
	@POST
	@Path("/addBacklogItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AddBacklogItemOutput addBacklogItem(@PathParam("productId") String productId,
			String backlogItemInfo) {
		String description = "";
		int estimate = 0;
		int importance = 0;
		String notes = "";
		try {
			JSONObject backlogItemJSON = new JSONObject(backlogItemInfo);
			description = backlogItemJSON.getString("description");
			estimate = backlogItemJSON.getInt("estimate");
			importance = backlogItemJSON.getInt("importance");
			notes = backlogItemJSON.getString("notes");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		AddBacklogItemInput input = new AddBacklogItemUseCaseImpl();
		input.setDescription(description);
		input.setEstimate(estimate);
		input.setImportance(importance);
		input.setNotes(notes);
		input.setProductId(productId);
		
		AddBacklogItemOutput output = this;
		
		addBacklogItemUseCase.execute(input, output);
		return output;
	}

	@Override
	public String getBacklogItemId() {
		return backlogItemId;
	}

	@Override
	public void setBacklogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
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
