package ntut.csie.ezScrum.useCase.history.io;

public class HistoryModel {
	
	private String historyId;
	private String createTime;
	private String type;
	private String description;
	
	public String getHistoryId() {
		return historyId;
	}
	
	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}
