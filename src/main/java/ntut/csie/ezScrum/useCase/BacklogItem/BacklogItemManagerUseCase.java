package ntut.csie.ezScrum.useCase.BacklogItem;

import java.util.ArrayList;

import ntut.csie.ezScrum.model.BacklogItem;
import ntut.csie.ezScrum.useCase.ApplicationContext;

public class BacklogItemManagerUseCase {

	private ApplicationContext context;

	public BacklogItemManagerUseCase(ApplicationContext context) {
		this.context = context;
	}

	public String addBacklogItem(BacklogItem backlogItem) {
		context.addBacklogItem(backlogItem);
		return backlogItem.getBacklogItemId();
	}
	
	public ArrayList<BacklogItem> getBacklogItems(String productId) {
		ArrayList<BacklogItem> backlogItemList = new ArrayList<BacklogItem>();
		for(BacklogItem backlogItem : context.getBacklogItems()) {
			if(backlogItem.getProductId().equals(productId)) {
				backlogItemList.add(backlogItem);
			}
		}
		return backlogItemList;
	}
	
	public ArrayList<BacklogItemDTO> getBacklogItemsForUI(String productId) {
		ArrayList<BacklogItemDTO> backlogItemList = new ArrayList<BacklogItemDTO>();
		for(BacklogItem backlogItem : context.getBacklogItems()) {
			if(backlogItem.getProductId().equals(productId)) {
				backlogItemList.add(covertBacklogItemToDTO(backlogItem));
			}
		}
		return backlogItemList;
	}
	
	private BacklogItemDTO covertBacklogItemToDTO(BacklogItem backlogItem) {
		BacklogItemDTO backlogItemDTO = new BacklogItemDTO();
		backlogItemDTO.setBacklogItemId(backlogItem.getBacklogItemId());
		backlogItemDTO.setSerialId(backlogItem.getSerialId());
		backlogItemDTO.setDescription(backlogItem.getDescription());
		backlogItemDTO.setEstimate(backlogItem.getEstimate());
		backlogItemDTO.setImportance(backlogItem.getImportance());
		String sprintId = backlogItem.getSprintId();
		if(sprintId == null) {
			backlogItemDTO.setSprintSerialId(0);
		}else {
			backlogItemDTO.setSprintSerialId(context.getSprint(sprintId).getSerialId());
		}
		backlogItemDTO.setStatus(backlogItem.getStatus());
		backlogItemDTO.setNotes(backlogItem.getNotes());
		return backlogItemDTO;
	}
	
	public void assignBacklogItemToSprint(String sprintId, String backlogItemId) {
		BacklogItem backlogItem = context.getBacklogItem(backlogItemId);
		backlogItem.setSprintId(sprintId);
	}
	
	public String editBacklogItem(String backlogItemId, BacklogItemDTO backlogItemDTO) {
		BacklogItem backlogItem = context.getBacklogItem(backlogItemId);
		backlogItem.setDescription(backlogItemDTO.getDescription());
		backlogItem.setEstimate(backlogItemDTO.getEstimate());
		backlogItem.setImportance(backlogItemDTO.getImportance());
		backlogItem.setNotes(backlogItemDTO.getNotes());
		context.editBacklogItem(backlogItemId, backlogItem);
		return backlogItemId;
	}
	
	public String deleteBacklogItem(String backlogItemId) {
		context.deleteBacklogItem(backlogItemId);
		return backlogItemId;
	}
}
