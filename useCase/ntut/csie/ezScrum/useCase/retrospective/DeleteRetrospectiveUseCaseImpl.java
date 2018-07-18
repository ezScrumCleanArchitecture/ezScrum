package ntut.csie.ezScrum.useCase.retrospective;

import ntut.csie.ezScrum.model.retrospective.Retrospective;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.retrospective.io.DeleteRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.DeleteRetrospectiveOutput;

public class DeleteRetrospectiveUseCaseImpl implements DeleteRetrospectiveUseCase, DeleteRetrospectiveInput{
	private ApplicationContext context;
	
	private String retrospectiveId;
	
	public DeleteRetrospectiveUseCaseImpl() {}
	
	public DeleteRetrospectiveUseCaseImpl(ApplicationContext context) {
		this.context = context;
	}
	
	@Override
	public void execute(DeleteRetrospectiveInput input, DeleteRetrospectiveOutput output) {
		String retrospectiveId = input.getRetrospectiveId();
		Retrospective retrospective = context.getRetrospective(retrospectiveId);
		if(retrospective == null) {
			output.setDeleteSuccess(false);
			output.setErrorMessage("Sorry, the retrospective is not exist.");
			return;
		}
		int orderId = retrospective.getOrderId();
		int numberOfRetrospectives = context.getNumberOfRetrospectives();
		Retrospective[] retrospectives = context.getRetrospectives().toArray(new Retrospective[numberOfRetrospectives]);
		for(int i = orderId; i < numberOfRetrospectives; i++) {
			retrospectives[i].setOrderId(i);
			context.editRetrospective(retrospectives[i].getRetrospectiveId(), retrospectives[i]);
		}
		context.deleteRetrospective(retrospectiveId);;
		output.setDeleteSuccess(true);
	}
	
	@Override
	public String getRetrospectiveId() {
		return retrospectiveId;
	}

	@Override
	public void setRetrospectiveId(String retrospectiveId) {
		this.retrospectiveId = retrospectiveId;
	}
}
