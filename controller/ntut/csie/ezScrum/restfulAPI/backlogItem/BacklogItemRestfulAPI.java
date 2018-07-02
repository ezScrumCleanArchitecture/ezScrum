package ntut.csie.ezScrum.restfulAPI.backlogItem;

import java.util.ArrayList;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.backlogItem.BacklogItemManagerUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.io.AddBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.AddBacklogItemOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.AssignBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.BacklogItemDTO;
import ntut.csie.ezScrum.useCase.backlogItem.io.DeleteBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.DeleteBacklogItemOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.EditBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.EditBacklogItemOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetCommittedBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.CommittedBacklogItemDTO;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetNotYetCommittedBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.MoveStoryCardInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.MoveStoryCardOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.NotYetCommittedBacklogItemDTO;

@Path("/product/{productId}/backlogItem")
@Singleton
public class BacklogItemRestfulAPI {
	
	ApplicationContext context = ApplicationContext.getInstance();
	BacklogItemManagerUseCase backlogItemManagerUseCase = new BacklogItemManagerUseCase(context);
	
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
		
		AddBacklogItemInput addBacklogItemInput = new AddBacklogItemInput();
		addBacklogItemInput.setDescription(description);
		addBacklogItemInput.setEstimate(estimate);
		addBacklogItemInput.setImportance(importance);
		addBacklogItemInput.setNotes(notes);
		addBacklogItemInput.setProductId(productId);
		
		AddBacklogItemOutput addBacklogItemOutput = backlogItemManagerUseCase.addBacklogItem(addBacklogItemInput);
		
		return addBacklogItemOutput;
	}
	
	@GET
	@Path("/sprint/{sprintId}/getCommittedBacklogItems")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CommittedBacklogItemDTO> getCommittedBacklogItems(@PathParam("productId") String productId, 
			@PathParam("sprintId") String sprintId) {
		GetCommittedBacklogItemInput getCommittedBacklogItemInput = new GetCommittedBacklogItemInput();
		getCommittedBacklogItemInput.setProductId(productId);
		getCommittedBacklogItemInput.setSprintId(sprintId);
		
		List<CommittedBacklogItemDTO> committedBacklogItemDTOList = backlogItemManagerUseCase.getCommittedBacklogItems(getCommittedBacklogItemInput);
		
		return committedBacklogItemDTOList;
	}
	
	@GET
	@Path("/getNotYetCommittedBacklogItems")
	@Produces(MediaType.APPLICATION_JSON)
	public List<NotYetCommittedBacklogItemDTO> getNotYetCommittedBacklogItems(@PathParam("productId") String productId) {
		GetNotYetCommittedBacklogItemInput getNotYetCommittedBacklogItemInput = new GetNotYetCommittedBacklogItemInput();
		getNotYetCommittedBacklogItemInput.setProductId(productId);
		
		List<NotYetCommittedBacklogItemDTO> notYetCommittedBacklogItemDTOList = backlogItemManagerUseCase.getNotYetCommittedBacklogItems(getNotYetCommittedBacklogItemInput);
		
		return notYetCommittedBacklogItemDTOList;
	}
	
	@GET
	@Path("/getAllBacklogItem")
	@Produces(MediaType.APPLICATION_JSON)
	public List<BacklogItemDTO> getAllBacklogItem(@PathParam("productId") String productId) {
		GetBacklogItemInput getBacklogItemInput = new GetBacklogItemInput();
		getBacklogItemInput.setProductId(productId);
		
		List<BacklogItemDTO> backlogItemDTOList = backlogItemManagerUseCase.getBacklogItems(getBacklogItemInput);
		
		return backlogItemDTOList;
	}
	
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
		
		EditBacklogItemInput editBacklogItemInput = new EditBacklogItemInput();
		editBacklogItemInput.setBacklogItemId(backlogItemId);
		editBacklogItemInput.setDescription(description);
		editBacklogItemInput.setEstimate(estimate);
		editBacklogItemInput.setImportance(importance);
		editBacklogItemInput.setNotes(notes);
		
		EditBacklogItemOutput editBacklogItemOutput = backlogItemManagerUseCase.editBacklogItem(editBacklogItemInput);
		
		return editBacklogItemOutput;
	}
	
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
		
		DeleteBacklogItemInput deleteBacklogItemInput = new DeleteBacklogItemInput();
		deleteBacklogItemInput.setBacklogItemId(backlogItemId);
		
		DeleteBacklogItemOutput deleteBacklogItemOutput = backlogItemManagerUseCase.deleteBacklogItem(deleteBacklogItemInput);
		
		return deleteBacklogItemOutput;
	}
	
	@POST
	@Path("/assignBacklogItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void assignBacklogItem(@PathParam("productId") String productId,
			String backlogItemInfo) {
		String sprintId = "";
		String backlogItemIds = "";
		List<String> backlogItemIdList = new ArrayList<>();
		try {
			JSONObject backlogItemJSON = new JSONObject(backlogItemInfo);
			sprintId = backlogItemJSON.getString("sprintId");
			backlogItemIds = backlogItemJSON.getString("backlogItemIds");
			JSONArray backlogItemIdsJSON = new JSONArray(backlogItemIds);
			for(int i = 0; i < backlogItemIdsJSON.length(); i++) {
				backlogItemIdList.add(backlogItemIdsJSON.getString(i));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		AssignBacklogItemInput assignBacklogItemInput = new AssignBacklogItemInput();
		for(String backlogItemId : backlogItemIdList) {
			assignBacklogItemInput.setSprintId(sprintId);
			assignBacklogItemInput.setBacklogItemId(backlogItemId);
			backlogItemManagerUseCase.assignBacklogItemToSprint(assignBacklogItemInput);
		}
	}
	
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
		
		MoveStoryCardInput moveStoryCardInput = new MoveStoryCardInput();
		moveStoryCardInput.setBacklogItemId(backlogItemId);
		moveStoryCardInput.setStatus(status);
		
		MoveStoryCardOutput moveStoryCardOutput = backlogItemManagerUseCase.moveStoryCard(moveStoryCardInput);
		
		return moveStoryCardOutput;
	}
}