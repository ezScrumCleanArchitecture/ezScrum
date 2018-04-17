package ntut.csie.ezScrum.factory.domainModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import ntut.csie.ezScrum.model.Sprint;
import ntut.csie.ezScrum.model.ezScrumDomainModel;

public class SprintFactory implements DomainModelFactory{
	private String goal;
	private int interval;
	private int teamSize;
	private String startDate;
	private String demoDate;
	private String demoPlace;
	private String dailyTime;
	private String dailyPlace;
	private String productId;
	
	public SprintFactory() {}
	
	public SprintFactory(String goal, int interval, int teamSize, String startDate,
			String demoDate, String demoPlace, String dailyTime, String dailyPlace, String productId) {
		this.goal = goal;
		this.interval = interval;
		this.teamSize = teamSize;
		this.startDate = startDate;
		this.demoDate = demoDate;
		this.demoPlace = demoPlace;
		this.dailyTime = dailyTime;
		this.dailyPlace = dailyPlace;
		this.productId = productId;
	}
	public ezScrumDomainModel createDomainModel() {
		String sprintId = UUID.randomUUID().toString();
		Calendar calendar = Calendar.getInstance();
		String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
		Sprint sprint = new Sprint(goal, interval, teamSize, startDate, demoDate, productId, createTime);
		Date startTime = null;
		try {
			startTime = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar end = Calendar.getInstance();
		end.setTime(startTime);
		end.add(Calendar.DAY_OF_MONTH, interval * 7);
		end.add(Calendar.DAY_OF_MONTH, -1);
		String endDate = new SimpleDateFormat("yyyy-MM-dd").format(end.getTime());
		sprint.setSprintId(sprintId);
		sprint.setEndDate(endDate);
		sprint.setDemoDate(demoDate == endDate ? endDate : demoDate);
		sprint.setDemoPlace(demoPlace);
		sprint.setDailyTime(dailyTime);
		sprint.setDailyPlace(dailyPlace);
		return sprint;
	}
}
