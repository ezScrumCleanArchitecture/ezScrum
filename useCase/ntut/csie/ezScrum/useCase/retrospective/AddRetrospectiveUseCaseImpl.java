package ntut.csie.ezScrum.useCase.retrospective;

import ntut.csie.ezScrum.model.retrospective.Retrospective;
import ntut.csie.ezScrum.model.retrospective.RetrospectiveBuilder;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.retrospective.io.AddRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.AddRetrospectiveOutput;

public class AddRetrospectiveUseCaseImpl implements AddRetrospectiveUseCase, AddRetrospectiveInput{
	
	private Repository<Retrospective> retrospectiveRepository;
	
	private String description;
	private String productId;
	private String sprintId;
	
	public AddRetrospectiveUseCaseImpl() {}
	
	public AddRetrospectiveUseCaseImpl(Repository<Retrospective> retrospectiveRepository) {
		this.retrospectiveRepository = retrospectiveRepository;
	}
	
	@Override
	public void execute(AddRetrospectiveInput input, AddRetrospectiveOutput output) {
		int orderId = retrospectiveRepository.getNumberOfItems() + 1;
		Retrospective retrospective = null;
		try {
			retrospective = RetrospectiveBuilder.newInstance().
					orderId(orderId).
					productId(input.getProductId()).
					sprintId(input.getSprintId()).
					description(input.getDescription()).
					build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		retrospectiveRepository.add(retrospective);
		output.setAddSuccess(true);
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
