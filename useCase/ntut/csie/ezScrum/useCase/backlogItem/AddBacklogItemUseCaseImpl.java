package ntut.csie.ezScrum.useCase.backlogItem;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.model.backlogItem.BacklogItemBuilder;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.backlogItem.io.AddBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.AddBacklogItemOutput;

public class AddBacklogItemUseCaseImpl implements AddBacklogItemUseCase, AddBacklogItemInput{
	
	private ApplicationContext context;
	private String description;
	private int estimate;
	private int importance;
	private String notes;
	private String productId;

	public AddBacklogItemUseCaseImpl() {}
	
	public AddBacklogItemUseCaseImpl(ApplicationContext context) {
		this.context = context;
	}

	@Override
	public void execute(AddBacklogItemInput input, AddBacklogItemOutput output) {
		int orderId =context.getNumberOfBacklogItems()+1;
		BacklogItem backlogItem = null;
		try {
			backlogItem = BacklogItemBuilder.newInstance().
					orderId(orderId).
					productId(input.getProductId()).
					description(input.getDescription()).
					estimate(input.getEstimate()).
					importance(input.getImportance()).
					notes(input.getNotes()).
					build();
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		context.addBacklogItem(backlogItem);
		String backlogItemId = backlogItem.getBacklogItemId();
		output.setBacklogItemId(backlogItemId);
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
	public int getEstimate() {
		return estimate;
	}

	@Override
	public void setEstimate(int estimate) {
		this.estimate = estimate;
	}

	@Override
	public int getImportance() {
		return importance;
	}

	@Override
	public void setImportance(int importance) {
		this.importance = importance;
	}

	@Override
	public String getNotes() {
		return notes;
	}

	@Override
	public void setNotes(String notes) {
		this.notes = notes;
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
