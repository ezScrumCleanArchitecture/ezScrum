package ntut.csie.ezScrum.useCase;

import ntut.csie.ezScrum.model.BacklogItem;

public class AssignBacklogItemToSprint {
	
	public void execute(ApplicationContext context, String sprintId, String backlogItemId) {
		BacklogItem backlogItem = context.getBacklogItems().get(backlogItemId);
		backlogItem.setSprintId(sprintId);
	}
}
