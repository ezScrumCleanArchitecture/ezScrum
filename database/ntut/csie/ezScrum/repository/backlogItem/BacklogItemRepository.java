package ntut.csie.ezScrum.repository.backlogItem;

import java.sql.PreparedStatement;
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

public class BacklogItemRepository implements Repository<BacklogItem> {

	private SqlDatabaseHelper sqlDatabaseHelper = SqlDatabaseHelper.getInstance();
	private BacklogItemMapper backlogItemMapper;

	public BacklogItemRepository() {
		backlogItemMapper = new BacklogItemMapper();
	}

	@Override
	public void add(BacklogItem backlogItem) {
		BacklogItemData data = backlogItemMapper.transformToBacklogItemData(backlogItem);
		String sql = String.format("Insert Into %s Values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				BacklogItemTable.tableName);
		PreparedStatement preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
		try {
			preparedStatement.setString(1, data.getBacklogItemId());
			preparedStatement.setInt(2, data.getOrderId());
			preparedStatement.setString(3, data.getDescription());
			preparedStatement.setString(4, data.getStatus());
			preparedStatement.setInt(5, data.getEstimate());
			preparedStatement.setInt(6, data.getImportance());
			preparedStatement.setString(7, data.getNotes());
			preparedStatement.setString(8, data.getProductId());
			preparedStatement.setString(9, data.getSprintId());
			preparedStatement.setString(10, data.getCreateTime());
			preparedStatement.setString(11, data.getUpdateTime());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(BacklogItem backlogItem) {
		BacklogItemData data = backlogItemMapper.transformToBacklogItemData(backlogItem);
		String sql = String.format(
				"Update %s Set " + "%s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? Where %s=?",
				BacklogItemTable.tableName, BacklogItemTable.orderId, BacklogItemTable.description,
				BacklogItemTable.status, BacklogItemTable.estimate, BacklogItemTable.importance, BacklogItemTable.notes,
				BacklogItemTable.productId, BacklogItemTable.sprintId, BacklogItemTable.updateTime,
				BacklogItemTable.backlogItemId);
		PreparedStatement preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
		try {
			preparedStatement.setInt(1, data.getOrderId());
			preparedStatement.setString(2, data.getDescription());
			preparedStatement.setString(3, data.getStatus());
			preparedStatement.setInt(4, data.getEstimate());
			preparedStatement.setInt(5, data.getImportance());
			preparedStatement.setString(6, data.getNotes());
			preparedStatement.setString(7, data.getProductId());
			preparedStatement.setString(8, data.getSprintId());
			preparedStatement.setString(9, data.getUpdateTime());
			preparedStatement.setString(10, data.getBacklogItemId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void remove(BacklogItem backlogItem) {
		String sql = String.format("Delete From %s Where %s=?", BacklogItemTable.tableName,
				BacklogItemTable.backlogItemId);
		PreparedStatement preparedStatementForDeleting = sqlDatabaseHelper.getPreparedStatement(sql);
		try {
			preparedStatementForDeleting.setString(1, backlogItem.getBacklogItemId());
			preparedStatementForDeleting.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public BacklogItem get(String backlogItemId) {
		BacklogItem backlogItem = null;
		String query = String.format("Select * From %s Where %s='%s'", BacklogItemTable.tableName,
				BacklogItemTable.backlogItemId, backlogItemId);
		ResultSet resultSet = sqlDatabaseHelper.getResultSet(query);
		try {
			if (resultSet.next()) {
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
				backlogItem = backlogItemMapper.transformToBacklogItem(backlogItemData);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return backlogItem;
	}

	@Override
	public Collection<BacklogItem> getAll() {
		Map<String, BacklogItem> backlogItems = Collections.synchronizedMap(new LinkedHashMap<String, BacklogItem>());
		String query = String.format("Select * From %s", BacklogItemTable.tableName);
		ResultSet resultSet = sqlDatabaseHelper.getResultSet(query);
		try {
			while (resultSet.next()) {
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
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return backlogItems.values();
	}

	@Override
	public int getNumberOfItems() {
		String query = String.format("Select Count(*) As numberOfBacklogItems From %s", BacklogItemTable.tableName);
		ResultSet resultSet = sqlDatabaseHelper.getResultSet(query);
		int numberOfBacklogItems = 0;
		try {
			if (resultSet.next()) {
				numberOfBacklogItems = resultSet.getInt("numberOfBacklogItems");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return numberOfBacklogItems;
	}

}
