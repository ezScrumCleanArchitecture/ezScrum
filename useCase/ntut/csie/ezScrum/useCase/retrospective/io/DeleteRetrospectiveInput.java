package ntut.csie.ezScrum.useCase.retrospective.io;

import ntut.csie.ezScrum.useCase.Input;

public interface DeleteRetrospectiveInput extends Input{
	public String getRetrospectiveId();
	public void setRetrospectiveId(String retrospectiveId);
}
