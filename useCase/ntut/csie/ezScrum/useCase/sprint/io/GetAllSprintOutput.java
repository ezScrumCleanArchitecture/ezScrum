package ntut.csie.ezScrum.useCase.sprint.io;

import java.util.List;

import ntut.csie.ezScrum.useCase.Output;

public interface GetAllSprintOutput extends Output{
	public List<SprintModel> getSprintList();
	public void setSprintList(List<SprintModel> sprintList);
}
