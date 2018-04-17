package ntut.csie.ezScrum.useCase;

import ntut.csie.ezScrum.factory.domainModel.CommittedBacklogItemFactory;
import ntut.csie.ezScrum.model.BacklogItem;
import ntut.csie.ezScrum.model.CommittedBacklogItem;

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
