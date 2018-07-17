package ntut.csie.ezScrum.useCase.backlogItem;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.backlogItem.io.EditBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.EditBacklogItemOutput;

public class EditBacklogItemUseCaseImpl implements EditBacklogItemUseCase, EditBacklogItemInput{

	private ApplicationContext context;
	private String backlogItemId;
	private String description;
	private int estimate;
	private int importance;
	private String notes;
	private String productId;

	public EditBacklogItemUseCaseImpl() {}
	
	public EditBacklogItemUseCaseImpl(ApplicationContext context) {
		this.context = context;
	}
	
	@Override
	public void execute(EditBacklogItemInput input, EditBacklogItemOutput output) {
		String backlogItemId = input.getBacklogItemId();
		BacklogItem backlogItem = context.getBacklogItem(backlogItemId);
		if(backlogItem == null) {
			output.setEditSuccess(false);
			output.setErrorMessage("Sorry, the backlog item is not exist.");
			return;
		}
		backlogItem.setDescription(input.getDescription());
		backlogItem.setEstimate(input.getEstimate());
		backlogItem.setImportance(input.getImportance());
		backlogItem.setNotes(input.getNotes());
		context.editBacklogItem(backlogItemId, backlogItem);
		output.setEditSuccess(true);
	}

	@Override
	public String getBacklogItemId() {
		return backlogItemId;
	}

	@Override
	public void setBacklogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
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
