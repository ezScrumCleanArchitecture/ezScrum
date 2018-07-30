package ntut.csie.ezScrum.model.sprint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Sprint {
	
	private String sprintId;
	private int orderId;
	private String goal;
	private int interval;
	private String startDate;
	private String endDate;
	private String demoDate;
	private String demoPlace;
	private String daily;
	private String productId;
	private String createTime;
	private String updateTime;
	
	public Sprint() {}
	
	public Sprint(String goal, int interval, String startDate,
			String demoDate, String productId, String createTime) {
		this.goal = goal;
		this.interval = interval;
		this.startDate = startDate;
		this.demoDate = demoDate;
		this.productId = productId;
		this.createTime = createTime;
		this.updateTime = createTime;
	}
	
	public boolean isSprintOverlap(String otherStartDate, String otherEndDate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		long thisSprintStartDate = 0;
		long thisSprintEndDate = 0;
		long otherSprintStartDate = 0;
		long otherSprintEndDate = 0;
		try {
			thisSprintStartDate = simpleDateFormat.parse(startDate).getTime();
			thisSprintEndDate = simpleDateFormat.parse(endDate).getTime();
			otherSprintStartDate = simpleDateFormat.parse(otherStartDate).getTime();
			otherSprintEndDate = simpleDateFormat.parse(otherEndDate).getTime();
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		if((thisSprintStartDate >= otherSprintStartDate && thisSprintStartDate <= otherSprintEndDate) ||
			(thisSprintEndDate >= otherSprintStartDate	&& thisSprintEndDate <= otherSprintEndDate) ||
			(thisSprintStartDate <= otherSprintStartDate && thisSprintEndDate >= otherSprintEndDate)) {
			return true;
		}
		return false;
	}
	
	public boolean isSprintOverdue() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		long today = calendar.getTimeInMillis();
		long thisSprintEndDate = 0;
		try {
			thisSprintEndDate = simpleDateFormat.parse(endDate).getTime();
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		return thisSprintEndDate < today;
	}
	
	public String getSprintId() {
		return sprintId;
	}
	
	public void setSprintId(String sprintId) {
		this.sprintId = sprintId;
	}
	
	public int getOrderId() {
		return orderId;
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
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
	
	public String getDaily() {
		return daily;
	}
	
	public void setDaily(String daily) {
		this.daily = daily;
	}
	
	public String getProductId() {
		return productId;
	}
	
	public void setProductId(String productId) {
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
