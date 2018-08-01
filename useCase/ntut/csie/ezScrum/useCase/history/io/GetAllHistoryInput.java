package ntut.csie.ezScrum.useCase.history.io;

import ntut.csie.ezScrum.useCase.Input;

public interface GetAllHistoryInput extends Input{
	public String getIssueId();
	public void setIssueId(String issueId);
}
