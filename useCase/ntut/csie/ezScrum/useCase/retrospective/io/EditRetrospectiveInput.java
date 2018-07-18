package ntut.csie.ezScrum.useCase.retrospective.io;

import ntut.csie.ezScrum.useCase.Input;

public interface EditRetrospectiveInput extends Input{
	public String getRetrospectiveId();
	public void setRetrospectiveId(String retrospectiveId); 
	public String getDescription();
	public void setDescription(String description);
	public String getProductId();
	public void setProductId(String productId);
	public String getSprintId();
	public void setSprintId(String sprintId);
}
