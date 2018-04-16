package useCase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import model.BacklogItem;

public class CreateBacklogItem {
	private static long backlogItemId=0;
	
	public BacklogItem addBacklogItem(long productId,String description,int estimate,int importance,String notes) {
		backlogItemId++;
		Calendar calendar = Calendar.getInstance();
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
		BacklogItem story = new BacklogItem(productId,description,time);
		story.setBacklogItemId(backlogItemId);
		story.setEstimate(estimate);
		story.setImportance(importance);
		story.setNotes(notes);
		return story;
	}
}
