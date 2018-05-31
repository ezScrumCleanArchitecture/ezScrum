package ntut.csie.ezScrum.useCase.backlogItem;

import java.util.ArrayList;
import java.util.List;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.model.backlogItem.BacklogItemBuilder;
import ntut.csie.ezScrum.useCase.ApplicationContext;
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
import ntut.csie.ezScrum.useCase.backlogItem.io.NotYetCommittedBacklogItemDTO;

public class BacklogItemManagerUseCase {

	private ApplicationContext context;

	public BacklogItemManagerUseCase(ApplicationContext context) {
		this.context = context;
	}

	public AddBacklogItemOutput addBacklogItem(AddBacklogItemInput addBacklogItemInput) {
		int orderId =context.getNumberOfBacklogItems()+1;
		BacklogItem backlogItem = null;
		try {
			backlogItem = BacklogItemBuilder.newInstance().
					orderId(orderId).
					productId(addBacklogItemInput.getProductId()).
					description(addBacklogItemInput.getDescription()).
					estimate(addBacklogItemInput.getEstimate()).
					importance(addBacklogItemInput.getImportance()).
					notes(addBacklogItemInput.getNotes()).
					build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		context.addBacklogItem(backlogItem);
		AddBacklogItemOutput addBacklogItemOutput = new AddBacklogItemOutput();
		addBacklogItemOutput.setBacklogItemId(backlogItem.getBacklogItemId());
		return addBacklogItemOutput;
	}
	
	public List<BacklogItemDTO> getBacklogItems(GetBacklogItemInput getBacklogItemInput) {
		String productId = getBacklogItemInput.getProductId();
		List<BacklogItemDTO> backlogItemList = new ArrayList<>();
		for(BacklogItem backlogItem : context.getBacklogItems()) {
			if(backlogItem.getProductId().equals(productId)) {
				backlogItemList.add(convertBacklogItemToGetOutput(backlogItem));
			}
		}
		return backlogItemList;
	}
	
	private BacklogItemDTO convertBacklogItemToGetOutput(BacklogItem backlogItem) {
		BacklogItemDTO getBacklogItemOutput = new BacklogItemDTO();
		getBacklogItemOutput.setBacklogItemId(backlogItem.getBacklogItemId());
		getBacklogItemOutput.setOrderId(backlogItem.getOrderId());
		getBacklogItemOutput.setDescription(backlogItem.getDescription());
		getBacklogItemOutput.setEstimate(backlogItem.getEstimate());
		getBacklogItemOutput.setImportance(backlogItem.getImportance());
		getBacklogItemOutput.setProductId(backlogItem.getProductId());
		getBacklogItemOutput.setSprintId(backlogItem.getSprintId());
		getBacklogItemOutput.setStatus(backlogItem.getStatus());
		getBacklogItemOutput.setNotes(backlogItem.getNotes());
		getBacklogItemOutput.setCreateTime(backlogItem.getCreateTime());
		getBacklogItemOutput.setUpdateTime(backlogItem.getUpdateTime());
		return getBacklogItemOutput;
	}
	
	public EditBacklogItemOutput editBacklogItem(EditBacklogItemInput editBacklogItemInput) {
		String backlogItemId = editBacklogItemInput.getBacklogItemId();
		BacklogItem backlogItem = context.getBacklogItem(backlogItemId);
		backlogItem.setDescription(editBacklogItemInput.getDescription());
		backlogItem.setEstimate(editBacklogItemInput.getEstimate());
		backlogItem.setImportance(editBacklogItemInput.getImportance());
		backlogItem.setNotes(editBacklogItemInput.getNotes());
		context.editBacklogItem(backlogItemId, backlogItem);
		EditBacklogItemOutput editBacklogItemOutput = new EditBacklogItemOutput();
		editBacklogItemOutput.setEditSuccess(true);
		return editBacklogItemOutput;
	}
	
	public DeleteBacklogItemOutput deleteBacklogItem(DeleteBacklogItemInput deleteBacklogItemInput) {
		String backlogItemId = deleteBacklogItemInput.getBacklogItemId();
		int orderId = context.getBacklogItem(backlogItemId).getOrderId();
		int numberOfBacklogItems = context.getNumberOfBacklogItems();
		BacklogItem[] backlogItems = context.getBacklogItems().toArray(new BacklogItem[numberOfBacklogItems]);
		for(int i = orderId; i < numberOfBacklogItems; i++) {
			backlogItems[i].setOrderId(i);
			context.editBacklogItem(backlogItems[i].getBacklogItemId(), backlogItems[i]);
		}
		context.deleteBacklogItem(backlogItemId);
		DeleteBacklogItemOutput deleteBacklogItemOutput = new DeleteBacklogItemOutput();
		deleteBacklogItemOutput.setDeleteSuccess(true);
		return deleteBacklogItemOutput;
	}
	
	public void assignBacklogItemToSprint(AssignBacklogItemInput assignBacklogItemInput) {
		String sprintId = assignBacklogItemInput.getSprintId();
		String backlogItemId = assignBacklogItemInput.getBacklogItemId();
		BacklogItem backlogItem = context.getBacklogItem(backlogItemId);
		backlogItem.setSprintId(sprintId);
		context.editBacklogItem(backlogItemId, backlogItem);
	}
	
	public List<CommittedBacklogItemDTO> getCommittedBacklogItems(GetCommittedBacklogItemInput getCommittedBacklogItemInput){
		String productId = getCommittedBacklogItemInput.getProductId();
		String sprintId = getCommittedBacklogItemInput.getSprintId();
		List<CommittedBacklogItemDTO> committedBacklogItemList = new ArrayList<>();
		for(BacklogItem backlogItem : context.getBacklogItems()) {
			if(backlogItem.getProductId().equals(productId)) {
				if(backlogItem.getSprintId() != null) {
					if(backlogItem.getSprintId().equals(sprintId)) {
						committedBacklogItemList.add(convertBacklogItemToGetCommittedBacklogItemOutput(backlogItem));
					}
				}
			}
		}
		return committedBacklogItemList;
	}
	
	private CommittedBacklogItemDTO convertBacklogItemToGetCommittedBacklogItemOutput(BacklogItem backlogItem) {
		CommittedBacklogItemDTO getCommittedBacklogItemOutput = new CommittedBacklogItemDTO();
		getCommittedBacklogItemOutput.setBacklogItemId(backlogItem.getBacklogItemId());
		getCommittedBacklogItemOutput.setOrderId(backlogItem.getOrderId());
		getCommittedBacklogItemOutput.setDescription(backlogItem.getDescription());
		getCommittedBacklogItemOutput.setEstimate(backlogItem.getEstimate());
		getCommittedBacklogItemOutput.setImportance(backlogItem.getImportance());
		getCommittedBacklogItemOutput.setSprintId(backlogItem.getSprintId());
		getCommittedBacklogItemOutput.setStatus(backlogItem.getStatus());
		getCommittedBacklogItemOutput.setNotes(backlogItem.getNotes());
		getCommittedBacklogItemOutput.setCreateTime(backlogItem.getCreateTime());
		getCommittedBacklogItemOutput.setUpdateTime(backlogItem.getUpdateTime());
		return getCommittedBacklogItemOutput;
	}
	
	public List<NotYetCommittedBacklogItemDTO> getNotYetCommittedBacklogItems(GetNotYetCommittedBacklogItemInput getNotYetCommittedBacklogItemInput){
		String productId = getNotYetCommittedBacklogItemInput.getProductId();
		List<NotYetCommittedBacklogItemDTO> notYetCommittedBacklogItemList = new ArrayList<>();
		for(BacklogItem backlogItem : context.getBacklogItems()) {
			if(backlogItem.getProductId().equals(productId)) {
				if(backlogItem.getSprintId() == null) {
					notYetCommittedBacklogItemList.add(convertBacklogItemToGetNotYetCommittedBacklogItemOutput(backlogItem));
				}
			}
		}
		return notYetCommittedBacklogItemList;
	}
	
	private NotYetCommittedBacklogItemDTO convertBacklogItemToGetNotYetCommittedBacklogItemOutput(BacklogItem backlogItem) {
		NotYetCommittedBacklogItemDTO getNotYetCommittedBacklogItemOutput = new NotYetCommittedBacklogItemDTO();
		getNotYetCommittedBacklogItemOutput.setBacklogItemId(backlogItem.getBacklogItemId());
		getNotYetCommittedBacklogItemOutput.setOrderId(backlogItem.getOrderId());
		getNotYetCommittedBacklogItemOutput.setDescription(backlogItem.getDescription());
		getNotYetCommittedBacklogItemOutput.setEstimate(backlogItem.getEstimate());
		getNotYetCommittedBacklogItemOutput.setImportance(backlogItem.getImportance());
		getNotYetCommittedBacklogItemOutput.setSprintId(backlogItem.getSprintId());
		getNotYetCommittedBacklogItemOutput.setStatus(backlogItem.getStatus());
		getNotYetCommittedBacklogItemOutput.setNotes(backlogItem.getNotes());
		getNotYetCommittedBacklogItemOutput.setCreateTime(backlogItem.getCreateTime());
		getNotYetCommittedBacklogItemOutput.setUpdateTime(backlogItem.getUpdateTime());
		return getNotYetCommittedBacklogItemOutput;
	}
	
}
