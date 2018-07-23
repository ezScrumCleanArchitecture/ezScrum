package ntut.csie.ezScrum.repository.backlogItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import ntut.csie.ezScrum.database.BacklogItemTable;
import ntut.csie.ezScrum.database.SqlDatabaseHelper;
import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.SqlSpecification;

public class BacklogItemRepository implements Repository<BacklogItem>{

	private SqlDatabaseHelper sqlDatabaseHelper;
	private BacklogItemMapper backlogItemMapper;
	
	public BacklogItemRepository() {
		sqlDatabaseHelper = new SqlDatabaseHelper();
		sqlDatabaseHelper.connection();
		backlogItemMapper = new BacklogItemMapper();
	}
	
	@Override
	public void add(BacklogItem item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(BacklogItem item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(BacklogItem item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<BacklogItem> query(SqlSpecification sqlSpecification) {
		Map<String, BacklogItem> backlogItems= Collections.synchronizedMap(new LinkedHashMap<String, BacklogItem>());
		ResultSet resultSet = sqlDatabaseHelper.getResultSet(sqlSpecification);
		try {
			while(resultSet.next()) {
				String backlogItemId = resultSet.getString(BacklogItemTable.backlogItemId);
				int orderId = resultSet.getInt(BacklogItemTable.orderId);
				String description = resultSet.getString(BacklogItemTable.description);
				String status = resultSet.getString(BacklogItemTable.status);
				int estimate = resultSet.getInt(BacklogItemTable.estimate);
				int importance = resultSet.getInt(BacklogItemTable.importance);
				String notes = resultSet.getString(BacklogItemTable.notes);
				String productId = resultSet.getString(BacklogItemTable.productId);
				String sprintId = resultSet.getString(BacklogItemTable.sprintId);
				String createTime = resultSet.getString(BacklogItemTable.createTime);
				String updateTime = resultSet.getString(BacklogItemTable.updateTime);
				BacklogItemData backlogItemData = new BacklogItemData();
				backlogItemData.setBacklogItemId(backlogItemId);
				backlogItemData.setOrderId(orderId);
				backlogItemData.setDescription(description);
				backlogItemData.setStatus(status);
				backlogItemData.setEstimate(estimate);
				backlogItemData.setImportance(importance);
				backlogItemData.setNotes(notes);
				backlogItemData.setProductId(productId);
				backlogItemData.setSprintId(sprintId);
				backlogItemData.setCreateTime(createTime);
				backlogItemData.setUpdateTime(updateTime);
				BacklogItem backlogItem = backlogItemMapper.transformToBacklogItem(backlogItemData);
				backlogItems.put(backlogItemId, backlogItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return backlogItems.values();
	}

}
