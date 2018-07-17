package ntut.csie.ezScrum.useCase.task.io;

import ntut.csie.ezScrum.useCase.Input;

public interface EditTaskInput extends Input{
	public String getTaskId();
	public void setTaskId(String taskId);
	public String getDescription();
	public void setDescription(String description);
	public int getEstimate();
	public void setEstimate(int estimate);
	public int getRemains();
	public void setRemains(int remains);
	public String getNotes();
	public void setNotes(String notes);
	public String getBacklogItemId();
	public void setBacklogItemId(String backlogItemId);
}
