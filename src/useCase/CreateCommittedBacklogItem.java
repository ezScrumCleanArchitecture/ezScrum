package useCase;

import model.BacklogItem;
import model.CommittedBacklogItem;

public class CreateCommittedBacklogItem {
	private static long committedBacklogItemId=0;
	
	public CommittedBacklogItem addCommittedBacklogItem(long sprintId,BacklogItem backlogItem) {
		committedBacklogItemId++;
		CommittedBacklogItem committedBacklogItem = new CommittedBacklogItem(sprintId,backlogItem);
		committedBacklogItem.setCommittedBacklogItemId(committedBacklogItemId);
		backlogItem.setCommittedBacklogItemId(committedBacklogItemId);
		return committedBacklogItem;
	}
}
