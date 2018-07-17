package ntut.csie.ezScrum.useCase.sprint.io;

import ntut.csie.ezScrum.useCase.Input;

public interface AddSprintInput extends Input{
	public String getGoal();
	public void setGoal(String goal);
	public int getInterval();
	public void setInterval(int interval);
	public int getTeamSize();
	public void setTeamSize(int teamSize);
	public String getStartDate();
	public void setStartDate(String startDate);
	public String getEndDate();
	public void setEndDate(String endDate);
	public String getDemoDate();
	public void setDemoDate(String demoDate);
	public String getDemoPlace();
	public void setDemoPlace(String demoPlace);
	public String getDaily();
	public void setDaily(String daily);
	public String getProductId();
	public void setProductId(String productId);
}
