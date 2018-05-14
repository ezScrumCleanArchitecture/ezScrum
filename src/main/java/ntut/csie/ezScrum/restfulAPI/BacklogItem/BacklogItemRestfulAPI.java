package ntut.csie.ezScrum.restfulAPI.BacklogItem;

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

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.BacklogItem.BacklogItemInputDTO;
import ntut.csie.ezScrum.useCase.BacklogItem.BacklogItemOutputDTO;
import ntut.csie.ezScrum.useCase.BacklogItem.BacklogItemManagerUseCase;

@Path("/product/{productId}/backlogItem")
@Singleton
public class BacklogItemRestfulAPI {
	
	ApplicationContext context = ApplicationContext.getInstance();
	BacklogItemManagerUseCase backlogItemManagerUseCase = new BacklogItemManagerUseCase(context);
	
	@POST
	@Path("/addBacklogItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addBacklogItem(@PathParam("productId") String productId,
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
		BacklogItemInputDTO backlogItemInputDTO = new BacklogItemInputDTO();
		backlogItemInputDTO.setDescription(description);
		backlogItemInputDTO.setEstimate(estimate);
		backlogItemInputDTO.setImportance(importance);
		backlogItemInputDTO.setNotes(notes);
		backlogItemInputDTO.setProductId(productId);
		String backlogItemId = backlogItemManagerUseCase.addBacklogItem(backlogItemInputDTO);
		return backlogItemId;
	}
	
	@GET
	@Path("/sprint/{sprintId}/getCommittedBacklogItems")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CommittedBacklogItemTableViewModel> getCommittedBacklogItems(@PathParam("productId") String productId, 
			@PathParam("sprintId") String sprintId) {
		List<BacklogItemOutputDTO> backlogItemDTOList = backlogItemManagerUseCase.getCommittedBacklogItems(productId, sprintId);
		List<CommittedBacklogItemTableViewModel> backlogItemList = new ArrayList<>();
		for(BacklogItemOutputDTO backlogItemDTO : backlogItemDTOList) {
			CommittedBacklogItemTableViewModel committedBacklogItemTableViewModel = new CommittedBacklogItemTableViewModel();
			committedBacklogItemTableViewModel.setBacklogItemId(backlogItemDTO.getBacklogItemId());
			committedBacklogItemTableViewModel.setSerialId(backlogItemDTO.getSerialId());
			committedBacklogItemTableViewModel.setDescription(backlogItemDTO.getDescription());
			committedBacklogItemTableViewModel.setStatus(backlogItemDTO.getStatus());
			committedBacklogItemTableViewModel.setEstimate(backlogItemDTO.getEstimate());
			committedBacklogItemTableViewModel.setImportance(backlogItemDTO.getImportance());
			backlogItemList.add(committedBacklogItemTableViewModel);
		}
		return backlogItemList;
	}
	
	@GET
	@Path("/getNotYetCommittedBacklogItems")
	@Produces(MediaType.APPLICATION_JSON)
	public List<NotYetCommittedBacklogItemTableViewModel> getNotYetCommittedBacklogItems(@PathParam("productId") String productId) {
		List<BacklogItemOutputDTO> backlogItemDTOList = backlogItemManagerUseCase.getNotYetCommittedBacklogItems(productId);
		List<NotYetCommittedBacklogItemTableViewModel> backlogItemList = new ArrayList<>();
		for(BacklogItemOutputDTO backlogItemDTO : backlogItemDTOList) {
			NotYetCommittedBacklogItemTableViewModel notYetCommittedBacklogItemTableViewModel = new NotYetCommittedBacklogItemTableViewModel();
			notYetCommittedBacklogItemTableViewModel.setBacklogItemId(backlogItemDTO.getBacklogItemId());
			notYetCommittedBacklogItemTableViewModel.setSerialId(backlogItemDTO.getSerialId());
			notYetCommittedBacklogItemTableViewModel.setDescription(backlogItemDTO.getDescription());
			notYetCommittedBacklogItemTableViewModel.setEstimate(backlogItemDTO.getEstimate());
			notYetCommittedBacklogItemTableViewModel.setImportance(backlogItemDTO.getImportance());
			backlogItemList.add(notYetCommittedBacklogItemTableViewModel);
		}
		return backlogItemList;
	}
	
	@GET
	@Path("/getAllBacklogItem")
	@Produces(MediaType.APPLICATION_JSON)
	public List<BacklogItemTableViewModel> getAllBacklogItem(@PathParam("productId") String productId) {
		List<BacklogItemOutputDTO> backlogItemDTOList = backlogItemManagerUseCase.getBacklogItems(productId);
		List<BacklogItemTableViewModel> backlogItemList = new ArrayList<>();
		for(BacklogItemOutputDTO backlogItemDTO : backlogItemDTOList) {
			BacklogItemTableViewModel backlogItemTableViewModel = new BacklogItemTableViewModel();
			backlogItemTableViewModel.setBacklogItemId(backlogItemDTO.getBacklogItemId());
			backlogItemTableViewModel.setSerialId(backlogItemDTO.getSerialId());
			backlogItemTableViewModel.setDescription(backlogItemDTO.getDescription());
			backlogItemTableViewModel.setEstimate(backlogItemDTO.getEstimate());
			backlogItemTableViewModel.setImportance(backlogItemDTO.getImportance());
			String sprintId = backlogItemDTO.getSprintId();
			if(sprintId == null) {
				backlogItemTableViewModel.setSprintSerialId(0);
			}else {
				backlogItemTableViewModel.setSprintSerialId(context.getSprint(sprintId).getSerialId());
			}
			backlogItemTableViewModel.setStatus(backlogItemDTO.getStatus());
			backlogItemTableViewModel.setNotes(backlogItemDTO.getNotes());
			backlogItemList.add(backlogItemTableViewModel);
		}
		return backlogItemList;
	}
	
	@POST
	@Path("/editBacklogItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String editBacklogItem(@PathParam("productId") String productId, 
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
		BacklogItemInputDTO backlogItemInputDTO = new BacklogItemInputDTO();
		backlogItemInputDTO.setDescription(description);
		backlogItemInputDTO.setEstimate(estimate);
		backlogItemInputDTO.setImportance(importance);
		backlogItemInputDTO.setNotes(notes);
		backlogItemManagerUseCase.editBacklogItem(backlogItemId, backlogItemInputDTO);
		return backlogItemId;
	}
	
	@DELETE
	@Path("/deleteBacklogItem")
	public String deleteBacklogItem(@PathParam("productId") String productId,
			String backlogItemInfo) {
		String backlogItemId = "";
		try {
			JSONObject backlogItemJSON = new JSONObject(backlogItemInfo);
			backlogItemId = backlogItemJSON.getString("backlogItemId");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		backlogItemManagerUseCase.deleteBacklogItem(backlogItemId);
		return backlogItemId;
	}
	
	@POST
	@Path("/assignBacklogItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void assignBacklogItem(@PathParam("productId") String productId,
			String backlogItemInfo) {
		String sprintId = "";
		String backlogItemId = "";
		try {
			JSONObject backlogItemJSON = new JSONObject(backlogItemInfo);
			sprintId = backlogItemJSON.getString("sprintId");
			backlogItemId = backlogItemJSON.getString("backlogItemId");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		backlogItemManagerUseCase.assignBacklogItemToSprint(sprintId, backlogItemId);
	}
}