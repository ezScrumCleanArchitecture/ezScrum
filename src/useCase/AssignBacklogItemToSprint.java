package useCase;

import factory.domainModel.CommittedBacklogItemFactory;
import model.BacklogItem;
import model.CommittedBacklogItem;

public class AssignBacklogItemToSprint {
	
	public String execute(ApplicationContext context, String sprintId, String backlogItemId) {
		BacklogItem backlogItem = context.getBacklogItems().get(backlogItemId);
		CommittedBacklogItemFactory committedBacklogItemFactory = new CommittedBacklogItemFactory(sprintId, backlogItem);
		CommittedBacklogItem committedBacklogItem = (CommittedBacklogItem) committedBacklogItemFactory.createDomainModel();
		String committedBacklogItemId = committedBacklogItem.getCommittedBacklogItemId();
		context.getCommittedBacklogItems().put(committedBacklogItemId, committedBacklogItem);
		backlogItem.setCommittedBacklogItemId(committedBacklogItemId);
		return committedBacklogItemId;
	}
}
