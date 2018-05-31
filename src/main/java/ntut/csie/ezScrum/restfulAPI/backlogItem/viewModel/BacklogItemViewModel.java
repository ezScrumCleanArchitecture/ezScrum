package ntut.csie.ezScrum.restfulAPI.backlogItem.viewModel;

public abstract class BacklogItemViewModel {
	private String backlogItemId;

	public String getBacklogItemId() {
		return backlogItemId;
	}

	public void setBacklogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
	}
	
}
