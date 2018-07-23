package ntut.csie.ezScrum.repository.backlogItem;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;

public class BacklogItemMapper {
	public BacklogItem transformToBacklogItem(BacklogItemData data) {
		BacklogItem backlogItem = new BacklogItem();
		backlogItem.setBacklogItemId(data.getBacklogItemId());
		backlogItem.setOrderId(data.getOrderId());
		backlogItem.setDescription(data.getDescription());
		backlogItem.setStatus(data.getStatus());
		backlogItem.setEstimate(data.getEstimate());
		backlogItem.setImportance(data.getImportance());
		backlogItem.setNotes(data.getNotes());
		backlogItem.setProductId(data.getProductId());
		backlogItem.setSprintId(data.getSprintId());
		backlogItem.setCreateTime(data.getCreateTime());
		backlogItem.setUpdateTime(data.getUpdateTime());
		return backlogItem;
	}
}
