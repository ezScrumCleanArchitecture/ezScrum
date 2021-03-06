package ntut.csie.ezScrum.model.backlogItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class BacklogItemBuilder {
	
	private String backlogItemId;
	private int orderId;
	private String description;
	private int estimate;
	private int importance;
	private String notes;
	private String productId; 
	private String createTime;
	
	public static BacklogItemBuilder newInstance() {
		return new BacklogItemBuilder();
	}
	
	public BacklogItemBuilder orderId(int orderId) {
		this.orderId = orderId;
		return this;
	}
	
	public BacklogItemBuilder description(String description) {
		this.description = description;
		return this;
	}
	
	public BacklogItemBuilder estimate(int estimate) {
		this.estimate = estimate;
		return this;
	}
	
	public BacklogItemBuilder importance(int importance) {
		this.importance = importance;
		return this;
	}
	
	public BacklogItemBuilder notes(String notes) {
		this.notes = notes;
		return this;
	}
	
	public BacklogItemBuilder productId(String productId) {
		this.productId = productId;
		return this;
	}
	
	public BacklogItem build() throws Exception{ 
		backlogItemId = UUID.randomUUID().toString();
		if(description == null) {
			throw new Exception("The description of the backlog item should not be null.");
		}
		Calendar calendar = Calendar.getInstance();
		createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
		BacklogItem backlogItem = new BacklogItem(productId,description,createTime);
		backlogItem.setBacklogItemId(backlogItemId);
		backlogItem.setOrderId(orderId);
		backlogItem.setEstimate(estimate);
		backlogItem.setImportance(importance);
		backlogItem.setNotes(notes);
		backlogItem.setStatus("To do");
		return backlogItem;
	}

}
