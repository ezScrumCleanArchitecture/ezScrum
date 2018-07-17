package ntut.csie.ezScrum.useCase.task.io;

import ntut.csie.ezScrum.useCase.Input;

public interface AddTaskInput extends Input{
	public String getDescription();
	public void setDescription(String description);
	public int getEstimate();
	public void setEstimate(int estimate);
	public String getNotes();
	public void setNotes(String notes);
	public String getBacklogItemId();
	public void setBacklogItemId(String backlogItemId);
}
