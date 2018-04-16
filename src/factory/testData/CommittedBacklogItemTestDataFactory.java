package factory.testData;

import useCase.ApplicationContext;
import useCase.AssignBacklogItemToSprint;

public class CommittedBacklogItemTestDataFactory implements TestDataFactory{

	private ApplicationContext context;
	private String backlogItemId;
	private String sprintId;
	
	public CommittedBacklogItemTestDataFactory(ApplicationContext context) {
		this.context = context;
		BacklogItemTestDataFactory backlogItemTestDataFactory = new BacklogItemTestDataFactory(this.context);
		this.backlogItemId = backlogItemTestDataFactory.createTestData();
		SprintTestDataFactory sprintTestDataFactory = new SprintTestDataFactory(this.context);
		this.sprintId = sprintTestDataFactory.createTestData();
	}
	
	public CommittedBacklogItemTestDataFactory(ApplicationContext context, String backlogItemId, String sprintId) {
		this.context = context;
		this.backlogItemId = backlogItemId;
		this.sprintId = sprintId;
	}
	
	public String createTestData() {
		AssignBacklogItemToSprint assignBacklogItemToSprintUseCase = new AssignBacklogItemToSprint();
		String committedBacklogItemId = assignBacklogItemToSprintUseCase.execute(context, sprintId, backlogItemId);
		return committedBacklogItemId;
	}
}
