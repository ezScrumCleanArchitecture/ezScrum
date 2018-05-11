package ntut.csie.ezScrum.useCase.BacklogItem;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<BacklogItemDTO> getBacklogItems(String productId) {
		List<BacklogItemDTO> backlogItemList = new ArrayList<>();
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
		backlogItemDTO.setSprintId(backlogItem.getSprintId());
		backlogItemDTO.setStatus(backlogItem.getStatus());
		backlogItemDTO.setNotes(backlogItem.getNotes());
		backlogItemDTO.setCreate_time(backlogItem.getCreate_time());
		backlogItemDTO.setUpdate_time(backlogItem.getUpdate_time());
		return backlogItemDTO;
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
	
	public void assignBacklogItemToSprint(String sprintId, String backlogItemId) {
		BacklogItem backlogItem = context.getBacklogItem(backlogItemId);
		backlogItem.setSprintId(sprintId);
		context.editBacklogItem(backlogItemId, backlogItem);
	}
	
	public List<BacklogItemDTO> getCommittedBacklogItems(String productId, String sprintId){
		List<BacklogItemDTO> committedBacklogItemList = new ArrayList<>();
		for(BacklogItemDTO backlogItemDTO : getBacklogItems(productId)) {
			if(backlogItemDTO.getSprintId() != null) {
				if(backlogItemDTO.getSprintId().equals(sprintId)) {
					committedBacklogItemList.add(backlogItemDTO);
				}
			}
		}
		return committedBacklogItemList;
	}
	
	public List<BacklogItemDTO> getNotYetCommittedBacklogItems(String productId){
		List<BacklogItemDTO> notYetCommittedBacklogItemList = new ArrayList<>();
		for(BacklogItemDTO backlogItemDTO : getBacklogItems(productId)) {
			if(backlogItemDTO.getSprintId() == null) {
				notYetCommittedBacklogItemList.add(backlogItemDTO);
			}
		}
		return notYetCommittedBacklogItemList;
	}
	
}
