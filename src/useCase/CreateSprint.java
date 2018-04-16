package useCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import model.Sprint;

public class CreateSprint {
	private static long sprintId = 0;
	
	public Sprint addSprint(String goal, int interval, int teamSize, String startDate,
			String demoDate, String demoPlace, String dailyTime, String dailyPlace, long productId) {
		sprintId++;
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
		sprint.setId(sprintId);
		sprint.setEndDate(endDate);
		sprint.setDemoDate(demoDate == endDate ? endDate : demoDate);
		sprint.setDemoPlace(demoPlace);
		sprint.setDailyTime(dailyTime);
		sprint.setDailyPlace(dailyPlace);
		return sprint;
	}
}
