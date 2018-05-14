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
	
	public String addSprint(SprintInputDTO sprintInputDTO) {
		Sprint sprint = null;
		try {
			sprint = SprintBuilder.newInstance().
					goal(sprintInputDTO.getGoal()).
					interval(sprintInputDTO.getInterval()).
					teamSize(sprintInputDTO.getTeamSize()).
					startDate(sprintInputDTO.getStartDate()).
					endDate(sprintInputDTO.getEndDate()).
					demoDate(sprintInputDTO.getDemoDate()).
					demoPlace(sprintInputDTO.getDemoPlace()).
					daily(sprintInputDTO.getDaily()).
					productId(sprintInputDTO.getProductId()).
					build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		context.addSprint(sprint);
		return sprint.getSprintId();
	}
	
	public List<SprintOutputDTO> getSprints(String productId) {
		List<SprintOutputDTO> sprintList = new ArrayList<>();
		for(Sprint sprint : context.getSprints()) {
			if(sprint.getProductId().equals(productId)) {
				sprintList.add(covertSprintToDTO(sprint));
			}
		}
		return sprintList;
	}
	
	private SprintOutputDTO covertSprintToDTO(Sprint sprint) {
		SprintOutputDTO sprintDTO = new SprintOutputDTO();
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
	
	public String editSprint(String sprintId, SprintInputDTO sprintInputDTO) {
		Sprint sprint = context.getSprint(sprintId);
		sprint.setGoal(sprintInputDTO.getGoal());
		sprint.setInterval(sprintInputDTO.getInterval());
		sprint.setTeamSize(sprintInputDTO.getTeamSize());
		sprint.setStartDate(sprintInputDTO.getStartDate());
		sprint.setEndDate(sprintInputDTO.getEndDate());
		sprint.setDemoDate(sprintInputDTO.getDemoDate());
		sprint.setDemoPlace(sprintInputDTO.getDemoPlace());
		sprint.setDaily(sprintInputDTO.getDaily());
		context.editSprint(sprintId, sprint);
		return sprint.getSprintId();
	}
	
	public String deleteSprint(String sprintId) {
		context.deleteSprint(sprintId);
		return sprintId;
	}
}
