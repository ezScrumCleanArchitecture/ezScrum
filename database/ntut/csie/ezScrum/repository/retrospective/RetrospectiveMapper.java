package ntut.csie.ezScrum.repository.retrospective;

import ntut.csie.ezScrum.model.retrospective.Retrospective;

public class RetrospectiveMapper {
	
	public Retrospective transformToRetrospective(RetrospectiveData data) {
		Retrospective retrospective = new Retrospective();
		retrospective.setRetrospectiveId(data.getRetrospectiveId());
		retrospective.setOrderId(data.getOrderId());
		retrospective.setDescription(data.getDescription());;
		retrospective.setProductId(data.getProductId());
		retrospective.setSprintId(data.getSprintId());
		retrospective.setCreateTime(data.getCreateTime());
		retrospective.setUpdateTime(data.getUpdateTime());
		return retrospective;
	}
	
	public RetrospectiveData transformToRetrospectiveData(Retrospective retrospective) {
		RetrospectiveData data = new RetrospectiveData();
		data.setRetrospectiveId(retrospective.getRetrospectiveId());
		data.setOrderId(retrospective.getOrderId());
		data.setDescription(retrospective.getDescription());;
		data.setProductId(retrospective.getProductId());
		data.setSprintId(retrospective.getSprintId());
		data.setCreateTime(retrospective.getCreateTime());
		data.setUpdateTime(retrospective.getUpdateTime());
		return data;
	}
	
}
