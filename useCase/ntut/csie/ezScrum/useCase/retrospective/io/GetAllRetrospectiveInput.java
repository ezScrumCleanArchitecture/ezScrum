package ntut.csie.ezScrum.useCase.retrospective.io;

import ntut.csie.ezScrum.useCase.Input;

public interface GetAllRetrospectiveInput extends Input{
	public String getProductId();
	public void setProductId(String productId);
}
