package ntut.csie.ezScrum.useCase.sprint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.model.sprint.SprintBuilder;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.sprint.io.AddSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.AddSprintOutput;
import ntut.csie.ezScrum.useCase.sprint.io.DeleteSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.DeleteSprintOutput;
import ntut.csie.ezScrum.useCase.sprint.io.EditSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.EditSprintOutput;
import ntut.csie.ezScrum.useCase.sprint.io.GetSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.GetSprintOutput;
import ntut.csie.ezScrum.useCase.sprint.io.IsSprintOverdueInput;
import ntut.csie.ezScrum.useCase.sprint.io.IsSprintOverdueOutput;
import ntut.csie.ezScrum.useCase.sprint.io.IsSprintOverlapInput;
import ntut.csie.ezScrum.useCase.sprint.io.IsSprintOverlapOutput;

public class SprintManagerUseCase {
	
	private ApplicationContext context;
	
	public SprintManagerUseCase(ApplicationContext context) {
		this.context = context;
	}
	
	public AddSprintOutput addSprint(AddSprintInput addSprintInput) {
		int orderId = context.getNumberOfSprints() + 1;
		Sprint sprint = null;
		try {
			sprint = SprintBuilder.newInstance().
					orderId(orderId).
					goal(addSprintInput.getGoal()).
					interval(addSprintInput.getInterval()).
					teamSize(addSprintInput.getTeamSize()).
					startDate(addSprintInput.getStartDate()).
					endDate(addSprintInput.getEndDate()).
					demoDate(addSprintInput.getDemoDate()).
					demoPlace(addSprintInput.getDemoPlace()).
					daily(addSprintInput.getDaily()).
					productId(addSprintInput.getProductId()).
					build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		context.addSprint(sprint);
		AddSprintOutput addSprintOutput = new AddSprintOutput();
		addSprintOutput.setSprintId(sprint.getSprintId());
		return addSprintOutput;
	}
	
	public List<GetSprintOutput> getSprints(GetSprintInput getSprintInput) {
		String productId = getSprintInput.getProductId();
		List<GetSprintOutput> sprintList = new ArrayList<>();
		for(Sprint sprint : context.getSprints()) {
			if(sprint.getProductId().equals(productId)) {
				sprintList.add(convertSprintToGetOutput(sprint));
			}
		}
		return sprintList;
	}
	
	private GetSprintOutput convertSprintToGetOutput(Sprint sprint) {
		GetSprintOutput getSprintOutput = new GetSprintOutput();
		getSprintOutput.setSprintId(sprint.getSprintId());
		getSprintOutput.setOrderId(sprint.getOrderId());
		getSprintOutput.setGoal(sprint.getGoal());
		getSprintOutput.setStartDate(sprint.getStartDate());
		getSprintOutput.setInterval(sprint.getInterval());
		getSprintOutput.setEndDate(sprint.getEndDate());
		getSprintOutput.setDemoDate(sprint.getDemoDate());
		getSprintOutput.setDemoPlace(sprint.getDemoPlace());
		getSprintOutput.setTeamSize(sprint.getTeamSize());
		getSprintOutput.setDaily(sprint.getDaily());
		getSprintOutput.setProductId(sprint.getProductId());
		getSprintOutput.setCreateTime(sprint.getCreateTime());
		getSprintOutput.setUpdateTime(sprint.getUpdateTime());
		return getSprintOutput;
	}
	
	public EditSprintOutput editSprint(EditSprintInput editSprintInput) {
		String sprintId = editSprintInput.getSprintId();
		Sprint sprint = context.getSprint(sprintId);
		sprint.setGoal(editSprintInput.getGoal());
		sprint.setInterval(editSprintInput.getInterval());
		sprint.setTeamSize(editSprintInput.getTeamSize());
		sprint.setStartDate(editSprintInput.getStartDate());
		sprint.setEndDate(editSprintInput.getEndDate());
		sprint.setDemoDate(editSprintInput.getDemoDate());
		sprint.setDemoPlace(editSprintInput.getDemoPlace());
		sprint.setDaily(editSprintInput.getDaily());
		context.editSprint(sprintId, sprint);
		EditSprintOutput editSprintOutput = new EditSprintOutput();
		editSprintOutput.setEditSuccess(true);
		return editSprintOutput;
	}
	
	public DeleteSprintOutput deleteSprint(DeleteSprintInput deleteSprintInput) {
		String sprintId = deleteSprintInput.getSprintId();
		int orderId = context.getSprint(sprintId).getOrderId();
		int numberOfSprints = context.getNumberOfSprints();
		Sprint[] sprints = context.getSprints().toArray(new Sprint[numberOfSprints]);
		for(int i = orderId; i < numberOfSprints; i++) {
			sprints[i].setOrderId(i);
			context.editSprint(sprints[i].getSprintId(), sprints[i]);
		}
		context.deleteSprint(sprintId);
		DeleteSprintOutput deleteSprintOutput = new DeleteSprintOutput();
		deleteSprintOutput.setDeleteSuccess(true);
		return deleteSprintOutput;
	}
	
	public IsSprintOverlapOutput isSprintOverlap(IsSprintOverlapInput isSprintOverlapInput) throws ParseException {
		String productId = isSprintOverlapInput.getProductId();
		String startDate = isSprintOverlapInput.getStartDate();
		String endDate = isSprintOverlapInput.getEndDate();
		List<Sprint> sprintList = new ArrayList<>();
		for(Sprint sprint : context.getSprints()) {
			if(sprint.getProductId().equals(productId)) {
				sprintList.add(sprint);
			}
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		long thisSprintStartDate = simpleDateFormat.parse(startDate).getTime();
		long thisSprintEndDate = simpleDateFormat.parse(endDate).getTime();
		IsSprintOverlapOutput isSprintOverlapOutput = new IsSprintOverlapOutput();
		for(Sprint sprint : sprintList) {
			long otherSprintStartDate = simpleDateFormat.parse(sprint.getStartDate()).getTime();
			long otherSprintEndDate = simpleDateFormat.parse(sprint.getEndDate()).getTime();
			if((thisSprintStartDate >= otherSprintStartDate && thisSprintStartDate <= otherSprintEndDate) ||
				(thisSprintEndDate >= otherSprintStartDate	&& thisSprintEndDate <= otherSprintEndDate)) {
				isSprintOverlapOutput.setSprintOverlap(true);
				return isSprintOverlapOutput;
			}
		}
		isSprintOverlapOutput.setSprintOverlap(false);
		return isSprintOverlapOutput;
	}
	
	public IsSprintOverdueOutput isSprintOverdue(IsSprintOverdueInput isSprintOverdueInput) throws ParseException {
		String sprintId = isSprintOverdueInput.getSprintId();
		Sprint thisSprint = context.getSprint(sprintId);
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		long today = calendar.getTimeInMillis();
		long thisSprintEndDate = simpleDateFormat.parse(thisSprint.getEndDate()).getTime();
		IsSprintOverdueOutput isSprintOverdueOutput = new IsSprintOverdueOutput();
		isSprintOverdueOutput.setSprintOverdue(thisSprintEndDate < today);
		return isSprintOverdueOutput;
	}
}
