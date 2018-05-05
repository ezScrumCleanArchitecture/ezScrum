package ntut.csie.ezScrum.useCase.Sprint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import ntut.csie.ezScrum.model.Sprint;

public class SprintBuilder {
	private String sprintId;
	private long serialId;
	private String goal;
	private int interval;
	private int teamSize;
	private String startDate;
	private String endDate;
	private String demoDate;
	private String demoPlace;
	private String dailyTime;
	private String dailyPlace;
	private String productId;
	private String createTime;
	
	public static SprintBuilder newIntance() {
		return new SprintBuilder();
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
	
	public SprintBuilder dailyTime(String dailyTime) {
		this.dailyTime = dailyTime;
		return this;
	}
	
	public SprintBuilder dailyPlace(String dailyPlace) {
		this.dailyPlace = dailyPlace;
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
		Date startTime = null;
		try {
			startTime = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar end = Calendar.getInstance();
		end.setTime(startTime);
		end.add(Calendar.DAY_OF_MONTH, interval * 7);
		end.add(Calendar.DAY_OF_MONTH, -1);
		endDate = new SimpleDateFormat("yyyy-MM-dd").format(end.getTime());
		sprint.setSprintId(sprintId);
		sprint.setSerialId(++serialId);
		sprint.setEndDate(endDate);
		sprint.setDemoDate(demoDate == endDate ? endDate : demoDate);
		sprint.setDemoPlace(demoPlace);
		sprint.setDailyTime(dailyTime);
		sprint.setDailyPlace(dailyPlace);
		return sprint;
	}
}
