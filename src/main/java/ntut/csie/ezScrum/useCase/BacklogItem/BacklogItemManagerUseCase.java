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

	public String addBacklogItem(BacklogItemInputDTO backlogItemInputDTO) {
		int orderId =context.getNumberOfBacklogItems()+1;
		BacklogItem backlogItem = null;
		try {
			backlogItem = BacklogItemBuilder.newInstance().
					orderId(orderId).
					productId(backlogItemInputDTO.getProductId()).
					description(backlogItemInputDTO.getDescription()).
					estimate(backlogItemInputDTO.getEstimate()).
					importance(backlogItemInputDTO.getImportance()).
					notes(backlogItemInputDTO.getNotes()).
					build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		context.addBacklogItem(backlogItem);
		return backlogItem.getBacklogItemId();
	}
	
	public List<BacklogItemOutputDTO> getBacklogItems(String productId) {
		List<BacklogItemOutputDTO> backlogItemList = new ArrayList<>();
		for(BacklogItem backlogItem : context.getBacklogItems()) {
			if(backlogItem.getProductId().equals(productId)) {
				backlogItemList.add(covertBacklogItemToDTO(backlogItem));
			}
		}
		return backlogItemList;
	}
	
	private BacklogItemOutputDTO covertBacklogItemToDTO(BacklogItem backlogItem) {
		BacklogItemOutputDTO backlogItemDTO = new BacklogItemOutputDTO();
		backlogItemDTO.setBacklogItemId(backlogItem.getBacklogItemId());
		backlogItemDTO.setOrderId(backlogItem.getOrderId());
		backlogItemDTO.setDescription(backlogItem.getDescription());
		backlogItemDTO.setEstimate(backlogItem.getEstimate());
		backlogItemDTO.setImportance(backlogItem.getImportance());
		backlogItemDTO.setSprintId(backlogItem.getSprintId());
		backlogItemDTO.setStatus(backlogItem.getStatus());
		backlogItemDTO.setNotes(backlogItem.getNotes());
		backlogItemDTO.setCreateTime(backlogItem.getCreateTime());
		backlogItemDTO.setUpdateTime(backlogItem.getUpdateTime());
		return backlogItemDTO;
	}
	
	public String editBacklogItem(String backlogItemId, BacklogItemInputDTO backlogItemInputDTO) {
		BacklogItem backlogItem = context.getBacklogItem(backlogItemId);
		backlogItem.setDescription(backlogItemInputDTO.getDescription());
		backlogItem.setEstimate(backlogItemInputDTO.getEstimate());
		backlogItem.setImportance(backlogItemInputDTO.getImportance());
		backlogItem.setNotes(backlogItemInputDTO.getNotes());
		context.editBacklogItem(backlogItemId, backlogItem);
		return backlogItemId;
	}
	
	public String deleteBacklogItem(String backlogItemId) {
		int orderId = context.getBacklogItem(backlogItemId).getOrderId();
		int numberOfBacklogItems = context.getNumberOfBacklogItems();
		BacklogItem[] backlogItems = context.getBacklogItems().toArray(new BacklogItem[numberOfBacklogItems]);
		for(int i = orderId; i < numberOfBacklogItems; i++) {
			backlogItems[i].setOrderId(i);
			context.editBacklogItem(backlogItems[i].getBacklogItemId(), backlogItems[i]);
		}
		context.deleteBacklogItem(backlogItemId);
		return backlogItemId;
	}
	
	public void assignBacklogItemToSprint(String sprintId, String backlogItemId) {
		BacklogItem backlogItem = context.getBacklogItem(backlogItemId);
		backlogItem.setSprintId(sprintId);
		context.editBacklogItem(backlogItemId, backlogItem);
	}
	
	public List<BacklogItemOutputDTO> getCommittedBacklogItems(String productId, String sprintId){
		List<BacklogItemOutputDTO> committedBacklogItemList = new ArrayList<>();
		for(BacklogItemOutputDTO backlogItemDTO : getBacklogItems(productId)) {
			if(backlogItemDTO.getSprintId() != null) {
				if(backlogItemDTO.getSprintId().equals(sprintId)) {
					committedBacklogItemList.add(backlogItemDTO);
				}
			}
		}
		return committedBacklogItemList;
	}
	
	public List<BacklogItemOutputDTO> getNotYetCommittedBacklogItems(String productId){
		List<BacklogItemOutputDTO> notYetCommittedBacklogItemList = new ArrayList<>();
		for(BacklogItemOutputDTO backlogItemDTO : getBacklogItems(productId)) {
			if(backlogItemDTO.getSprintId() == null) {
				notYetCommittedBacklogItemList.add(backlogItemDTO);
			}
		}
		return notYetCommittedBacklogItemList;
	}
	
}
