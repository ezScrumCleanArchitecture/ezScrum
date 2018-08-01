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
import ntut.csie.ezScrum.model.history.History;
import ntut.csie.ezScrum.repository.backlogItem.BacklogItemRepository;
import ntut.csie.ezScrum.repository.history.HistoryRepository;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.backlogItem.MoveStoryCardUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.MoveStoryCardUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.io.MoveStoryCardInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.MoveStoryCardOutput;

@Path("/product/{productId}/backlogItem")
public class MoveStoryCardRestfulAPI implements MoveStoryCardOutput{
	
	private Repository<BacklogItem> backlogItemRepository = new BacklogItemRepository();
	private Repository<History> historyRepository = new HistoryRepository();
	private MoveStoryCardUseCase moveStoryCardUseCase = new MoveStoryCardUseCaseImpl(backlogItemRepository, historyRepository);
	
	private boolean moveSuccess;
	private String errorMessage;
	
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
	public boolean isMoveSuccess() {
		return moveSuccess;
	}

	@Override
	public void setMoveSuccess(boolean moveSuccess) {
		this.moveSuccess = moveSuccess;
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
