package ntut.csie.ezScrum.useCase.backlogItem.io;

import ntut.csie.ezScrum.useCase.Input;

public interface EditBacklogItemInput extends Input{
	public String getBacklogItemId();
	public void setBacklogItemId(String backlogItemId);
	public String getDescription();
	public void setDescription(String description);
	public int getEstimate();
	public void setEstimate(int estimate);
	public int getImportance();
	public void setImportance(int importance);
	public String getNotes();
	public void setNotes(String notes);
	public String getProductId();
	public void setProductId(String productId);
}
