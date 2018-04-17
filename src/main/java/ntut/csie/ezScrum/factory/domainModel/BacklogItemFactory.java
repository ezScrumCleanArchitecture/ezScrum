package ntut.csie.ezScrum.factory.domainModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import ntut.csie.ezScrum.model.BacklogItem;
import ntut.csie.ezScrum.model.ezScrumDomainModel;

public class BacklogItemFactory implements DomainModelFactory{
	private String description;
	private int estimate;
	private int importance;
	private String notes;
	private String productId; 
	
	public BacklogItemFactory() {}
	
	public BacklogItemFactory(String productId,String description,int estimate,int importance,String notes) {
		this.productId = productId;
		this.description = description;
		this.estimate = estimate;
		this.importance = importance;
		this.notes = notes;
	}

	public ezScrumDomainModel createDomainModel() {
		String backlogItemId = UUID.randomUUID().toString();
		Calendar calendar = Calendar.getInstance();
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
		BacklogItem backlogItem = new BacklogItem(productId,description,time);
		backlogItem.setBacklogItemId(backlogItemId);
		backlogItem.setEstimate(estimate);
		backlogItem.setImportance(importance);
		backlogItem.setNotes(notes);
		backlogItem.setStatus("Not Checked Out");
		return backlogItem;
	}
	
}
