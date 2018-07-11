package ntut.csie.ezScrum.restfulAPI.backlogItem;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.backlogItem.MoveStoryCardUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.MoveStoryCardUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.io.MoveStoryCardInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.MoveStoryCardOutput;

@Path("/product/{productId}/backlogItem")
@Singleton
public class MoveStoryCardRestfulAPI implements MoveStoryCardOutput{
	
	private ApplicationContext context = ApplicationContext.getInstance();
	private MoveStoryCardUseCase moveStoryCardUseCase = new MoveStoryCardUseCaseImpl(context);
	
	private boolean moveStoryCardSuccess;
	
	@POST
	@Path("/moveStoryCard")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MoveStoryCardOutput moveStoryCard(@PathParam("productId") String productId, 
			String backlogItemInfo) {
		String backlogItemId = "";
		String status = "";
		try {
			JSONObject backlogItemJSON = new JSONObject(backlogItemInfo);
			backlogItemId = backlogItemJSON.getString("backlogItemId");
			status = backlogItemJSON.getString("status");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		MoveStoryCardInput input = new MoveStoryCardUseCaseImpl();
		input.setBacklogItemId(backlogItemId);
		input.setStatus(status);
		
		MoveStoryCardOutput output = this;
		
		moveStoryCardUseCase.execute(input, output);
		
		return output;
	}

	@Override
	public boolean isMoveStoryCardSuccess() {
		return moveStoryCardSuccess;
	}

	@Override
	public void setMoveStoryCardSuccess(boolean moveStoryCardSuccess) {
		this.moveStoryCardSuccess = moveStoryCardSuccess;
	}

}
