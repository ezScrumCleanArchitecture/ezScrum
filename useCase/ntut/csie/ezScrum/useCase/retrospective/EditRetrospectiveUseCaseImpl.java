package ntut.csie.ezScrum.useCase.retrospective;

import ntut.csie.ezScrum.model.retrospective.Retrospective;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.retrospective.io.EditRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.EditRetrospectiveOutput;

public class EditRetrospectiveUseCaseImpl implements EditRetrospectiveUseCase, EditRetrospectiveInput{
	
	private Repository<Retrospective> retrospectiveRepository;
	
	private String retrospectiveId;
	private String description;
	private String productId;
	private String sprintId;
	
	public EditRetrospectiveUseCaseImpl () {}
	
	public EditRetrospectiveUseCaseImpl (Repository<Retrospective> retrospectiveRepository) {
		this.retrospectiveRepository = retrospectiveRepository;
	}
	
	@Override
	public void execute(EditRetrospectiveInput input, EditRetrospectiveOutput output) {
		String retrospectiveId = input.getRetrospectiveId();
		Retrospective retrospective = retrospectiveRepository.get(retrospectiveId);
		if(retrospective == null) {
			output.setEditSuccess(false);
			output.setErrorMessage("Sorry, the retrospective is not exist.");
			return;
		}
		retrospective.setDescription(input.getDescription());
		retrospective.setSprintId(input.getSprintId());
		retrospectiveRepository.update(retrospective);
		output.setEditSuccess(true);
	}
	
	@Override
	public String getRetrospectiveId() {
		return retrospectiveId;
	}

	@Override
	public void setRetrospectiveId(String retrospectiveId) {
		this.retrospectiveId = retrospectiveId;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getProductId() {
		return productId;
	}

	@Override
	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Override
	public String getSprintId() {
		return sprintId;
	}

	@Override
	public void setSprintId(String sprintId) {
		this.sprintId = sprintId;
	}
}
