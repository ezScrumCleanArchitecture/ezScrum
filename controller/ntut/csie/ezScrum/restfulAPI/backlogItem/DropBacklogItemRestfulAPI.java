package ntut.csie.ezScrum.restfulAPI.backlogItem;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.ezScrum.ApplicationContext;
import ntut.csie.ezScrum.useCase.backlogItem.DropBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.DropBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.io.DropBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.DropBacklogItemOutput;

@Path("/product/{productId}/backlogItem")
public class DropBacklogItemRestfulAPI implements DropBacklogItemOutput{

	private ApplicationContext applicationContext = ApplicationContext.getInstance();
	private DropBacklogItemUseCase dropBacklogItemUseCase = applicationContext.newDropBacklogItemUseCase();
	
	private boolean dropSuccess;
	private String errorMessage;
	
	@POST
	@Path("/dropBacklogItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void dropBacklogItem(@PathParam("productId") String productId,
			String backlogItemInfo) {
		String backlogItemId = "";
		try {
			JSONObject backlogItemJSON = new JSONObject(backlogItemInfo);
			backlogItemId = backlogItemJSON.getString("backlogItemId");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		DropBacklogItemInput input = new DropBacklogItemUseCaseImpl();
		DropBacklogItemOutput output = this;
		input.setBacklogItemId(backlogItemId);
		dropBacklogItemUseCase.execute(input, output);
	}
	
	@Override
	public boolean isDropSuccess() {
		return dropSuccess;
	}

	@Override
	public void setDropSuccess(boolean dropSuccess) {
		this.dropSuccess = dropSuccess;
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
