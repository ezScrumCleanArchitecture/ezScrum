package ntut.csie.ezScrum.repository.history;

import ntut.csie.ezScrum.model.history.History;

public class HistoryMapper {

	public History transformToHistory(HistoryData data) {
		History history = new History();
		history.setHistoryId(data.getHistoryId());
		history.setIssueId(data.getIssueId());
		history.setIssueType(data.getIssueType());
		history.setType(data.getType());
		history.setOldValue(data.getOldValue());
		history.setNewValue(data.getNewValue());
		history.setCreateTime(data.getCreateTime());
		return history;
	}
	
	public HistoryData transformToHistoryData(History history) {
		HistoryData data = new HistoryData();
		data.setHistoryId(history.getHistoryId());
		data.setIssueId(history.getIssueId());
		data.setIssueType(history.getIssueType());
		data.setType(history.getType());
		data.setOldValue(history.getOldValue());
		data.setNewValue(history.getNewValue());
		data.setCreateTime(history.getCreateTime());
		return data;
	}
	
}
