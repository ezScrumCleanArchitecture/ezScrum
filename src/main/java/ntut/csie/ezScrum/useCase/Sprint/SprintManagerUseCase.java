package ntut.csie.ezScrum.useCase.Sprint;

import ntut.csie.ezScrum.model.Sprint;
import ntut.csie.ezScrum.useCase.ApplicationContext;

public class SprintManagerUseCase {
	
	private ApplicationContext context;
	
	public SprintManagerUseCase(ApplicationContext context) {
		this.context = context;
	}
	
	public String addSprint(Sprint sprint) {
		context.addSprint(sprint);
		return sprint.getSprintId();
	}
}
