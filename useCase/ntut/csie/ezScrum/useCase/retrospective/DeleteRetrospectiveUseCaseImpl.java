package ntut.csie.ezScrum.useCase.retrospective;

import ntut.csie.ezScrum.model.retrospective.Retrospective;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.retrospective.io.DeleteRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.DeleteRetrospectiveOutput;

public class DeleteRetrospectiveUseCaseImpl implements DeleteRetrospectiveUseCase, DeleteRetrospectiveInput{
	
	private Repository<Retrospective> retrospectiveRepository;
	
	private String retrospectiveId;
	
	public DeleteRetrospectiveUseCaseImpl() {}
	
	public DeleteRetrospectiveUseCaseImpl(Repository<Retrospective> retrospectiveRepository) {
		this.retrospectiveRepository = retrospectiveRepository;
	}
	
	@Override
	public void execute(DeleteRetrospectiveInput input, DeleteRetrospectiveOutput output) {
		String retrospectiveId = input.getRetrospectiveId();
		Retrospective retrospective = retrospectiveRepository.get(retrospectiveId);
		if(retrospective == null) {
			output.setDeleteSuccess(false);
			output.setErrorMessage("Sorry, the retrospective is not exist.");
			return;
		}
		int orderId = retrospective.getOrderId();
		int numberOfRetrospectives = retrospectiveRepository.getNumberOfItems();
		Retrospective[] retrospectives = retrospectiveRepository.getAll().toArray(new Retrospective[numberOfRetrospectives]);
		for(int i = orderId; i < numberOfRetrospectives; i++) {
			retrospectives[i].setOrderId(i);
			retrospectiveRepository.update(retrospectives[i]);
		}
		retrospectiveRepository.remove(retrospective);
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
