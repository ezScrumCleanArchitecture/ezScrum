package ntut.csie.ezScrum.useCase.Sprint;

import java.util.ArrayList;

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
	
	public ArrayList<Sprint> getSprints(String productId) {
		ArrayList<Sprint> sprintList = new ArrayList<Sprint>();
		for(Sprint sprint : context.getSprints()) {
			sprintList.add(sprint);
		}
		return sprintList;
	}
	
	public ArrayList<SprintDTO> getSprintsForUI(String productId){
		ArrayList<SprintDTO> sprintList = new ArrayList<SprintDTO>();
		for(Sprint sprint : context.getSprints()) {
			sprintList.add(covertSprintToDTO(sprint));
		}
		return sprintList;
	}
	
	private SprintDTO covertSprintToDTO(Sprint sprint) {
		SprintDTO sprintDTO = new SprintDTO();
		sprintDTO.setSerialId(sprint.getSerialId());
		sprintDTO.setGoal(sprint.getGoal());
		sprintDTO.setStartDate(sprint.getStartDate());
		sprintDTO.setInterval(sprint.getInterval());
		sprintDTO.setEndDate(sprint.getEndDate());
		sprintDTO.setDemoDate(sprint.getDemoDate());
		sprintDTO.setTeamSize(sprint.getTeamSize());
		sprintDTO.setDailyTime(sprint.getDailyTime());
		sprintDTO.setDailyPlace(sprint.getDailyPlace());
		sprintDTO.setDemoPlace(sprint.getDemoPlace());
		return sprintDTO;
	}
	
	public String editSprint(Sprint sprint) {
		context.editSprint(sprint);
		return sprint.getSprintId();
	}
	
	public String deleteSprint(String sprintId) {
		context.deleteSprint(sprintId);
		return sprintId;
	}
}
