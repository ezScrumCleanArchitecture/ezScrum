package ntut.csie.ezScrum.model.history;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class HistoryBuilder {
	
	private String historyId;
	private String issueId; //backlog item id or task id
	private String issueType; //backlog item or task
	private String type; //the behavior of the history. 
	private String oldValue;
	private String newValue;
	private String createTime;
	
	public static HistoryBuilder newInstance() {
		return new HistoryBuilder();
	}

	public HistoryBuilder issueId(String issueId) {
		this.issueId = issueId;
		return this;
	}
	
	public HistoryBuilder issueType(String issueType) {
		this.issueType = issueType;
		return this;
	}
	
	public HistoryBuilder type(String type) {
		this.type = type;
		return this;
	}
	
	public HistoryBuilder oldValue(String oldValue) {
		this.oldValue = oldValue;
		return this;
	}
	
	public HistoryBuilder newValue(String newValue) {
		this.newValue = newValue;
		return this;
	}
	
	public History build() throws Exception{
		historyId = UUID.randomUUID().toString();
		if(issueId == null) {
			throw new Exception("The issue id of the history should not be null.");
		}
		if(issueType == null) {
			throw new Exception("The issue type of the history should not be null.");
		}
		Calendar calendar = Calendar.getInstance();
		createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
		History history = new History(issueId, issueType, type, createTime);
		history.setHistoryId(historyId);
		history.setOldValue(oldValue);
		history.setNewValue(newValue);
		return history;
	}
}
