package ntut.csie.ezScrum.useCase.sprint;

import java.util.ArrayList;
import java.util.List;

import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.sprint.io.EditSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.EditSprintOutput;

public class EditSprintUseCaseImpl implements EditSprintUseCase, EditSprintInput{
	private ApplicationContext context;
	private String sprintId;
	private String goal;
	private int interval;
	private int teamSize;
	private String startDate;
	private String endDate;
	private String demoDate;
	private String demoPlace;
	private String daily;
	private String productId;
	
	public EditSprintUseCaseImpl() {}
	
	public EditSprintUseCaseImpl(ApplicationContext context) {
		this.context = context;
	}
	
	@Override
	public void execute(EditSprintInput input, EditSprintOutput output) {
		String sprintId = input.getSprintId();
		Sprint sprint = context.getSprint(sprintId);
		if(sprint == null) {
			output.setEditSuccess(false);
			output.setOverlap(false);
			output.setErrorMessage("Sorry, the sprint is not exist.");
			return;
		}
		Sprint originalSprint = sprint;
		sprint.setGoal(input.getGoal());
		sprint.setInterval(input.getInterval());
		sprint.setTeamSize(input.getTeamSize());
		sprint.setStartDate(input.getStartDate());
		sprint.setEndDate(input.getEndDate());
		sprint.setDemoDate(input.getDemoDate());
		sprint.setDemoPlace(input.getDemoPlace());
		sprint.setDaily(input.getDaily());
		if(isSprintOverlap(originalSprint, sprint)) {
			output.setEditSuccess(false);
			output.setOverlap(true);
			output.setErrorMessage("Sorry, the start date or end date is overlap with the other sprint.");
			return;
		}
		context.editSprint(sprintId, sprint);
		output.setOverlap(false);
		output.setEditSuccess(true);
	}
	
	private boolean isSprintOverlap(Sprint originalSprint, Sprint editedSprint) {
		List<Sprint> sprintList = new ArrayList<>();
		String productId = editedSprint.getProductId();
		for(Sprint otherSprint : context.getSprints()) {
			if((otherSprint.getProductId().equals(productId)) && (!originalSprint.equals(otherSprint))) {
				sprintList.add(otherSprint);
			}
		}
		for(Sprint otherSprint : sprintList) {
			String otherStartDate = otherSprint.getStartDate();
			String otherEndDate = otherSprint.getEndDate();
			if(editedSprint.isSprintOverlap(otherStartDate, otherEndDate)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String getSprintId() {
		return sprintId;
	}
	
	@Override
	public void setSprintId(String sprintId) {
		this.sprintId = sprintId;
	}
	
	@Override
	public String getGoal() {
		return goal;
	}
	
	@Override
	public void setGoal(String goal) {
		this.goal = goal;
	}
	
	@Override
	public int getInterval() {
		return interval;
	}
	
	@Override
	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	@Override
	public int getTeamSize() {
		return teamSize;
	}
	
	@Override
	public void setTeamSize(int teamSize) {
		this.teamSize = teamSize;
	}
	
	@Override
	public String getStartDate() {
		return startDate;
	}
	
	@Override
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	@Override
	public String getEndDate() {
		return endDate;
	}
	
	@Override
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	@Override
	public String getDemoDate() {
		return demoDate;
	}
	
	@Override
	public void setDemoDate(String demoDate) {
		this.demoDate = demoDate;
	}
	
	@Override
	public String getDemoPlace() {
		return demoPlace;
	}
	
	@Override
	public void setDemoPlace(String demoPlace) {
		this.demoPlace = demoPlace;
	}
	
	@Override
	public String getDaily() {
		return daily;
	}
	
	@Override
	public void setDaily(String daily) {
		this.daily = daily;
	}
	
	@Override
	public String getProductId() {
		return productId;
	}
	
	@Override
	public void setProductId(String productId) {
		this.productId = productId;
	}
}
