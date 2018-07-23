package ntut.csie.ezScrum.useCase.retrospective.io;

import java.util.List;

import ntut.csie.ezScrum.useCase.Output;

public interface GetAllRetrospectiveOutput extends Output{
	public List<RetrospectiveModel> getRetrospectiveList();
	public void setRetrospectiveList(List<RetrospectiveModel> retrospectiveList);
}
