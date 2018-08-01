package ntut.csie.ezScrum.useCase.history.io;

import java.util.List;

import ntut.csie.ezScrum.useCase.Output;

public interface GetAllHistoryOutput extends Output{
	public List<HistoryModel> getHistoryList();
	public void setHistoryList(List<HistoryModel> historyList);
}
