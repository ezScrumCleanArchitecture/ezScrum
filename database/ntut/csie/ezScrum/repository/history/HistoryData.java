package ntut.csie.ezScrum.repository.history;

public class HistoryData {
	
	private String historyId;
	private String issueId; //backlog item id or task id
	private String issueType; //backlog item or task
	private String type; //the behavior of the history. 
	private String oldValue;
	private String newValue;
	private String createTime;
	
	public String getHistoryId() {
		return historyId;
	}
	
	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}
	
	public String getIssueId() {
		return issueId;
	}
	
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	
	public String getIssueType() {
		return issueType;
	}
	
	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getOldValue() {
		return oldValue;
	}
	
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	
	public String getNewValue() {
		return newValue;
	}
	
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
