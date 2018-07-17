package ntut.csie.ezScrum.useCase.sprint.io;

import ntut.csie.ezScrum.useCase.Input;

public interface GetAllSprintInput extends Input{
	public String getProductId();
	public void setProductId(String productId);
}
