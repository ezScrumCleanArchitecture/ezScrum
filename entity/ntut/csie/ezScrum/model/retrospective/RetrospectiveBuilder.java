package ntut.csie.ezScrum.model.retrospective;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class RetrospectiveBuilder {
	private String retrospectiveId;
	private int orderId;
	private String description;
	private String productId;
	private String sprintId;
	private String createTime;

	public static RetrospectiveBuilder newInstance() {
		return new RetrospectiveBuilder();
	}
	
	public RetrospectiveBuilder orderId(int orderId) {
		this.orderId = orderId;
		return this;
	}
	
	public RetrospectiveBuilder description(String description) {
		this.description = description;
		return this;
	}
	
	public RetrospectiveBuilder productId(String productId) {
		this.productId = productId;
		return this;
	}
	
	public RetrospectiveBuilder sprintId(String sprintId) {
		this.sprintId = sprintId;
		return this;
	}
	
	public Retrospective build() throws Exception{
		retrospectiveId = UUID.randomUUID().toString();
		if(description == null) {
			throw new Exception("The description of the retrospective should not be null.");
		}
		if(sprintId == null) {
			throw new Exception("The sprint of the retrospective should not be null.");
		}
		Calendar calendar = Calendar.getInstance();
		createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
		Retrospective retrospective = new Retrospective(description, productId, sprintId, createTime);
		retrospective.setRetrospectiveId(retrospectiveId);
		retrospective.setOrderId(orderId);
		return retrospective;
	}
}
