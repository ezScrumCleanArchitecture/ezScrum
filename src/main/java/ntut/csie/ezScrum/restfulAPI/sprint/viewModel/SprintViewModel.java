package ntut.csie.ezScrum.restfulAPI.sprint.viewModel;

public abstract class SprintViewModel {
	private String sprintId;
	
	public SprintViewModel() {
		
	}

	public String getSprintId() {
		return sprintId;
	}

	public void setSprintId(String sprintId) {
		this.sprintId = sprintId;
	}
	
}
