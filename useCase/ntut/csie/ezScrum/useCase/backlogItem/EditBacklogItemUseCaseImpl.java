package ntut.csie.ezScrum.useCase.backlogItem;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.model.history.History;
import ntut.csie.ezScrum.model.history.HistoryBuilder;
import ntut.csie.ezScrum.model.history.IssueType;
import ntut.csie.ezScrum.model.history.Type;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.backlogItem.io.EditBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.EditBacklogItemOutput;

public class EditBacklogItemUseCaseImpl implements EditBacklogItemUseCase, EditBacklogItemInput{

	private Repository<BacklogItem> backlogItemRepository;
	private Repository<History> historyRepository;
	
	private String backlogItemId;
	private String description;
	private int estimate;
	private int importance;
	private String notes;
	private String productId;

	public EditBacklogItemUseCaseImpl() {}
	
	public EditBacklogItemUseCaseImpl(Repository<BacklogItem> backlogItemRepository, Repository<History> historyRepository) {
		this.backlogItemRepository = backlogItemRepository;
		this.historyRepository = historyRepository;
	}
	
	@Override
	public void execute(EditBacklogItemInput input, EditBacklogItemOutput output) {
		String backlogItemId = input.getBacklogItemId();
		BacklogItem backlogItem = backlogItemRepository.get(backlogItemId);
		if(backlogItem == null) {
			output.setEditSuccess(false);
			output.setErrorMessage("Sorry, the backlog item is not exist.");
			return;
		}
		String originalDescription = backlogItem.getDescription();
		int originalEstimate = backlogItem.getEstimate();
		int orignalImportance = backlogItem.getImportance();
		String originalNotes = backlogItem.getNotes();
		
		String editedDescription = input.getDescription();
		int editedEstimate = input.getEstimate();
		int editedImportance = input.getImportance();
		String editedNotes = input.getNotes();
		
		if(!originalDescription.equals(editedDescription)) {
			backlogItem.setDescription(editedDescription);
			recordHistory(backlogItem.getBacklogItemId(), Type.editDescription, "\"" + originalDescription + "\"", "\"" + editedDescription + "\"");
		}
		if(originalEstimate != editedEstimate) {
			backlogItem.setEstimate(editedEstimate);
			recordHistory(backlogItem.getBacklogItemId(), Type.editEstimate, String.valueOf(originalEstimate), String.valueOf(editedEstimate));
		}
		if(orignalImportance != editedImportance) {
			backlogItem.setImportance(input.getImportance());
			recordHistory(backlogItem.getBacklogItemId(), Type.editImportance, String.valueOf(orignalImportance), String.valueOf(editedImportance));
		}
		if(!originalNotes.equals(editedNotes)) {
			backlogItem.setNotes(input.getNotes());
			recordHistory(backlogItem.getBacklogItemId(), Type.editNotes, "\"" + originalNotes + "\"", "\"" + editedNotes + "\"");
		}
		backlogItemRepository.update(backlogItem);
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

	private void recordHistory(String backlogItemId, String type, String oldValue, String newValue) {
		History history = null;
		try {
			history = HistoryBuilder.newInstance().
					issueId(backlogItemId).
					issueType(IssueType.backlogItem).
					type(type).
					oldValue(oldValue).
					newValue(newValue).
					build();
		}catch (Exception e) {
			System.out.print(e.getMessage());
		}
		historyRepository.add(history);
	}

}
