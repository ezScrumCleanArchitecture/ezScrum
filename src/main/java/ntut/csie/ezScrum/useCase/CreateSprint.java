package ntut.csie.ezScrum.useCase;

import ntut.csie.ezScrum.factory.domainModel.SprintFactory;
import ntut.csie.ezScrum.model.Sprint;

public class CreateSprint {
	
	public String execute(ApplicationContext context, String goal, int interval, int teamSize, String startDate,
			String demoDate, String demoPlace, String dailyTime, String dailyPlace, String productId) {
		SprintFactory sprintFactory = new SprintFactory(goal, interval, teamSize, startDate, demoDate, demoPlace, dailyTime, dailyPlace, productId);
		Sprint sprint = (Sprint) sprintFactory.createDomainModel();
		context.getSprints().put(sprint.getSprintId(), sprint);
		return sprint.getSprintId();
	}
}
