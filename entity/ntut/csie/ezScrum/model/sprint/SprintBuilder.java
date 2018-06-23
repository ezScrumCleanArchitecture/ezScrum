package ntut.csie.ezScrum.model.sprint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class SprintBuilder {
	private String sprintId;
	private int orderId;
	private String goal;
	private int interval;
	private int teamSize;
	private String startDate;
	private String endDate;
	private String demoDate;
	private String demoPlace;
	private String daily;
	private String productId;
	private String createTime;
	
	public static SprintBuilder newInstance() {
		return new SprintBuilder();
	}
	
	public SprintBuilder orderId(int orderId) {
		this.orderId = orderId;
		return this;
	}
	
	public SprintBuilder goal(String goal) {
		this.goal = goal;
		return this;
	}
	
	public SprintBuilder interval(int interval) {
		this.interval = interval;
		return this;
	}
	
	public SprintBuilder teamSize(int teamSize) {
		this.teamSize = teamSize;
		return this;
	}
	
	public SprintBuilder startDate(String startDate) {
		this.startDate = startDate;
		return this;
	}
	
	public SprintBuilder endDate(String endDate) {
		this.endDate = endDate;
		return this;
	}
	
	public SprintBuilder demoDate(String demoDate) {
		this.demoDate = demoDate;
		return this;
	}
	
	public SprintBuilder demoPlace(String demoPlace) {
		this.demoPlace = demoPlace;
		return this;
	}
	
	public SprintBuilder daily(String daily) {
		this.daily = daily;
		return this;
	}
	
	public SprintBuilder productId(String productId) {
		this.productId = productId;
		return this;
	}
	
	public Sprint build() throws Exception{
		sprintId = UUID.randomUUID().toString();
		String exceptionMessage = "";
		if(goal == null) {
			exceptionMessage += "The goal of the sprint should not be null.\n";
		}
		if(interval == 0) {
			exceptionMessage += "The interval of the sprint should not be zero.\n";
		}
		if(startDate == null) {
			exceptionMessage += "The start date of the sprint should not be null.\n";
		}
		if(demoDate == null) {
			exceptionMessage += "The demo date of the sprint should not be null.\n";
		}
		if(!exceptionMessage.equals("")) {
			throw new Exception(exceptionMessage);
		}
		Calendar calendar = Calendar.getInstance();
		createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
		Sprint sprint = new Sprint(goal, interval, teamSize, startDate, demoDate, productId, createTime);
		sprint.setSprintId(sprintId);
		sprint.setOrderId(orderId);
		sprint.setEndDate(endDate);
		sprint.setDemoDate(demoDate);
		sprint.setDemoPlace(demoPlace);
		sprint.setDaily(daily);
		return sprint;
	}
}
