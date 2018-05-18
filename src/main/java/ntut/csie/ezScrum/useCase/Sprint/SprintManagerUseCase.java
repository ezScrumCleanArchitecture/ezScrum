package ntut.csie.ezScrum.useCase.Sprint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		int orderId = context.getNumberOfSprints() + 1;
		Sprint sprint = null;
		try {
			sprint = SprintBuilder.newInstance().
					orderId(orderId).
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
		sprintDTO.setOrderId(sprint.getOrderId());
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
		int orderId = context.getSprint(sprintId).getOrderId();
		int numberOfSprints = context.getNumberOfSprints();
		Sprint[] sprints = context.getSprints().toArray(new Sprint[numberOfSprints]);
		for(int i = orderId; i < numberOfSprints; i++) {
			sprints[i].setOrderId(i);
			context.editSprint(sprints[i].getSprintId(), sprints[i]);
		}
		context.deleteSprint(sprintId);
		return sprintId;
	}
	
	public boolean isSprintOverlap(String productId, String startDate, String endDate) throws ParseException {
		List<Sprint> sprintList = new ArrayList<>();
		for(Sprint sprint : context.getSprints()) {
			if(sprint.getProductId().equals(productId)) {
				sprintList.add(sprint);
			}
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		long thisSprintStartDate = simpleDateFormat.parse(startDate).getTime();
		long thisSprintEndDate = simpleDateFormat.parse(endDate).getTime();
		for(Sprint sprint : sprintList) {
			long otherSprintStartDate = simpleDateFormat.parse(sprint.getStartDate()).getTime();
			long otherSprintEndDate = simpleDateFormat.parse(sprint.getEndDate()).getTime();
			if((thisSprintStartDate >= otherSprintStartDate && thisSprintStartDate <= otherSprintEndDate) ||
				(thisSprintEndDate >= otherSprintStartDate	&& thisSprintEndDate <= otherSprintEndDate)) {
				return true;
			}
		}
		return false;
	}
}
