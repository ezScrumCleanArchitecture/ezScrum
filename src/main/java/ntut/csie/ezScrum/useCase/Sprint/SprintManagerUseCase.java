package ntut.csie.ezScrum.useCase.Sprint;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<SprintDTO> getSprints(String productId) {
		List<SprintDTO> sprintList = new ArrayList<>();
		for(Sprint sprint : context.getSprints()) {
			if(sprint.getProductId().equals(productId)) {
				sprintList.add(covertSprintToDTO(sprint));
			}
		}
		return sprintList;
	}
	
	private SprintDTO covertSprintToDTO(Sprint sprint) {
		SprintDTO sprintDTO = new SprintDTO();
		sprintDTO.setSprintId(sprint.getSprintId());
		sprintDTO.setSerialId(sprint.getSerialId());
		sprintDTO.setGoal(sprint.getGoal());
		sprintDTO.setStartDate(sprint.getStartDate());
		sprintDTO.setInterval(sprint.getInterval());
		sprintDTO.setEndDate(sprint.getEndDate());
		sprintDTO.setDemoDate(sprint.getDemoDate());
		sprintDTO.setDemoPlace(sprint.getDemoPlace());
		sprintDTO.setTeamSize(sprint.getTeamSize());
		sprintDTO.setDaily(sprint.getDaily());
		sprintDTO.setCreateTime(sprint.getCreateTime());
		sprintDTO.setUpdateTime(sprint.getUpdateTime());
		return sprintDTO;
	}
	
	public String editSprint(String sprintId, SprintDTO sprintDTO) {
		Sprint sprint = context.getSprint(sprintId);
		sprint.setGoal(sprintDTO.getGoal());
		sprint.setInterval(sprintDTO.getInterval());
		sprint.setTeamSize(sprintDTO.getTeamSize());
		sprint.setStartDate(sprintDTO.getStartDate());
		sprint.setEndDate(sprintDTO.getEndDate());
		sprint.setDemoDate(sprintDTO.getDemoDate());
		sprint.setDemoPlace(sprintDTO.getDemoPlace());
		sprint.setDaily(sprintDTO.getDaily());
		context.editSprint(sprintId, sprint);
		return sprint.getSprintId();
	}
	
	public String deleteSprint(String sprintId) {
		context.deleteSprint(sprintId);
		return sprintId;
	}
}
