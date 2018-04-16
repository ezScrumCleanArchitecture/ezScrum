package model;

public class Sprint {
	
	private long id;
	private String goal;
	private int interval;
	private int teamSize;
	private String startDate;
	private String endDate;
	private String demoDate;
	private String demoPlace;
	private String dailyTime;
	private String dailyPlace;
	private long productId;
	private String createTime;
	private String updateTime;
	
	public Sprint() {}
	
	public Sprint(String goal, int interval, int teamSize, String startDate,
			String demoDate, long productId, String createTime) {
		this.goal = goal;
		this.interval = interval;
		this.teamSize = teamSize;
		this.startDate = startDate;
		this.demoDate = demoDate;
		this.productId = productId;
		this.createTime = createTime;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getGoal() {
		return goal;
	}
	public void setGoal(String goal) {
		this.goal = goal;
	}
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public int getTeamSize() {
		return teamSize;
	}
	public void setTeamSize(int teamSize) {
		this.teamSize = teamSize;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getDemoDate() {
		return demoDate;
	}
	public void setDemoDate(String demoDate) {
		this.demoDate = demoDate;
	}
	public String getDemoPlace() {
		return demoPlace;
	}
	public void setDemoPlace(String demoPlace) {
		this.demoPlace = demoPlace;
	}
	public String getDailyTime() {
		return dailyTime;
	}
	public void setDailyTime(String dailyTime) {
		this.dailyTime = dailyTime;
	}
	public String getDailyPlace() {
		return dailyPlace;
	}
	public void setDailyPlace(String dailyPlace) {
		this.dailyPlace = dailyPlace;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	

}
