package ntut.csie.ezScrum.useCase.retrospective;

import java.util.ArrayList;
import java.util.List;

import ntut.csie.ezScrum.model.retrospective.Retrospective;
import ntut.csie.ezScrum.model.retrospective.RetrospectiveBuilder;
import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.retrospective.io.AddRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.AddRetrospectiveOutput;
import ntut.csie.ezScrum.useCase.retrospective.io.DeleteRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.DeleteRetrospectiveOutput;
import ntut.csie.ezScrum.useCase.retrospective.io.EditRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.EditRetrospectiveOutput;
import ntut.csie.ezScrum.useCase.retrospective.io.GetRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.GetRetrospectiveOutput;

public class RetrospectiveManagerUseCase {
	private ApplicationContext context;

	public RetrospectiveManagerUseCase(ApplicationContext context) {
		this.context = context;
	}

	public AddRetrospectiveOutput addRetrospective(AddRetrospectiveInput addRetrospectiveInput) {
		int orderId = context.getNumberOfRetrospectives() + 1;
		Retrospective retrospective = null;
		try {
			retrospective = RetrospectiveBuilder.newInstance().
					orderId(orderId).
					productId(addRetrospectiveInput.getProductId()).
					sprintId(addRetrospectiveInput.getSprintId()).
					description(addRetrospectiveInput.getDescription()).
					build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		context.addRetrospective(retrospective);
		AddRetrospectiveOutput addRetrospectiveOutput = new AddRetrospectiveOutput();
		addRetrospectiveOutput.setRetrospectiveId(retrospective.getRetrospectiveId());
		return addRetrospectiveOutput;
	}
	
	public List<GetRetrospectiveOutput> getRetrospectives(GetRetrospectiveInput getRetrospectiveInput) {
		String productId = getRetrospectiveInput.getProductId();
		List<GetRetrospectiveOutput> retrospectiveList = new ArrayList<>();
		for(Retrospective retrospective : context.getRetrospectives()) {
			if(retrospective.getProductId().equals(productId)) {
				retrospectiveList.add(convertRetrospectiveToGetOutput(retrospective));
			}
		}
		return retrospectiveList;
	}
	
	private GetRetrospectiveOutput convertRetrospectiveToGetOutput(Retrospective retrospective) {
		GetRetrospectiveOutput getRetrospectiveOutput = new GetRetrospectiveOutput();
		getRetrospectiveOutput.setRetrospectiveId(retrospective.getRetrospectiveId());
		getRetrospectiveOutput.setOrderId(retrospective.getOrderId());
		getRetrospectiveOutput.setDescription(retrospective.getDescription());
		getRetrospectiveOutput.setProductId(retrospective.getProductId());
		System.out.println("Retrospctive's sprint Id: " + retrospective.getSprintId());
		for(Sprint sprint : context.getSprints()) {
			System.out.println(sprint.getSprintId());
		}
		Sprint sprint = context.getSprint(retrospective.getSprintId());
		getRetrospectiveOutput.setSprintOrderId(sprint.getOrderId());
		getRetrospectiveOutput.setCreateTime(retrospective.getCreateTime());
		getRetrospectiveOutput.setUpdateTime(retrospective.getUpdateTime());
		return getRetrospectiveOutput;
	}
	
	public EditRetrospectiveOutput editRetrospective(EditRetrospectiveInput editRetrospectiveInput) {
		String retrospectiveId = editRetrospectiveInput.getRetrospectiveId();
		Retrospective retrospective = context.getRetrospective(retrospectiveId);
		retrospective.setDescription(editRetrospectiveInput.getDescription());
		retrospective.setSprintId(editRetrospectiveInput.getSprintId());
		context.editRetrospective(retrospectiveId, retrospective);
		EditRetrospectiveOutput editRetrospectiveOutput = new EditRetrospectiveOutput();
		editRetrospectiveOutput.setEditSuccess(true);
		return editRetrospectiveOutput;
	}
	
	public DeleteRetrospectiveOutput deleteRetrospective(DeleteRetrospectiveInput deleteRetrospectiveInput) {
		String retrospectiveId = deleteRetrospectiveInput.getRetrospectiveId();
		int orderId = context.getRetrospective(retrospectiveId).getOrderId();
		int numberOfRetrospectives = context.getNumberOfRetrospectives();
		Retrospective[] retrospectives = context.getRetrospectives().toArray(new Retrospective[numberOfRetrospectives]);
		for(int i = orderId; i < numberOfRetrospectives; i++) {
			retrospectives[i].setOrderId(i);
			context.editRetrospective(retrospectives[i].getRetrospectiveId(), retrospectives[i]);
		}
		context.deleteRetrospective(retrospectiveId);
		DeleteRetrospectiveOutput deleteRetrospectiveOutput = new DeleteRetrospectiveOutput();
		deleteRetrospectiveOutput.setDeleteSuccess(true);
		return deleteRetrospectiveOutput;
	}
}
