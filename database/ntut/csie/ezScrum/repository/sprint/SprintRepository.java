package ntut.csie.ezScrum.repository.sprint;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import ntut.csie.ezScrum.database.BacklogItemTable;
import ntut.csie.ezScrum.database.SprintTable;
import ntut.csie.ezScrum.database.SqlDatabaseHelper;
import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.useCase.Repository;

public class SprintRepository implements Repository<Sprint> {

	private SqlDatabaseHelper sqlDatabaseHelper = SqlDatabaseHelper.getInstance();
	private SprintMapper sprintMapper;

	public SprintRepository() {
		sprintMapper = new SprintMapper();
	}

	@Override
	public void add(Sprint sprint) {
		SprintData data = sprintMapper.transformToSprintData(sprint);
		String sql = String.format("Insert Into %s Values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", SprintTable.tableName);
		PreparedStatement preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
		try {
			preparedStatement.setString(1, data.getSprintId());
			preparedStatement.setInt(2, data.getOrderId());
			preparedStatement.setString(3, data.getGoal());
			preparedStatement.setInt(4, data.getInterval());
			preparedStatement.setString(5, data.getStartDate());
			preparedStatement.setString(6, data.getEndDate());
			preparedStatement.setString(7, data.getDemoDate());
			preparedStatement.setString(8, data.getDemoPlace());
			preparedStatement.setString(9, data.getDaily());
			preparedStatement.setString(10, data.getProductId());
			preparedStatement.setString(11, data.getCreateTime());
			preparedStatement.setString(12, data.getUpdateTime());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Sprint sprint) {
		SprintData data = sprintMapper.transformToSprintData(sprint);
		String sql = String.format(
				"Update %s Set " + "%s=?, %s=?, %s.%s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? Where %s=?",
				SprintTable.tableName, SprintTable.orderId, SprintTable.goal, SprintTable.tableName,
				SprintTable.interval, SprintTable.startDate, SprintTable.endDate, SprintTable.demoDate,
				SprintTable.demoPlace, SprintTable.daily, SprintTable.productId, SprintTable.updateTime,
				SprintTable.sprintId);
		PreparedStatement preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
		try {
			preparedStatement.setInt(1, data.getOrderId());
			preparedStatement.setString(2, data.getGoal());
			preparedStatement.setInt(3, data.getInterval());
			preparedStatement.setString(4, data.getStartDate());
			preparedStatement.setString(5, data.getEndDate());
			preparedStatement.setString(6, data.getDemoDate());
			preparedStatement.setString(7, data.getDemoPlace());
			preparedStatement.setString(8, data.getDaily());
			preparedStatement.setString(9, data.getProductId());
			preparedStatement.setString(10, data.getUpdateTime());
			preparedStatement.setString(11, data.getSprintId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void remove(Sprint sprint) {
		String sql = String.format("Delete From %s Where %s=?", SprintTable.tableName, SprintTable.sprintId);
		PreparedStatement preparedStatementForDeleting = sqlDatabaseHelper.getPreparedStatement(sql);
		try {
			preparedStatementForDeleting.setString(1, sprint.getSprintId());
			preparedStatementForDeleting.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Sprint get(String sprintId) {
		Sprint sprint = null;
		String query = String.format("Select * From %s Where %s='%s'", SprintTable.tableName, SprintTable.sprintId,
				sprintId);
		ResultSet resultSet = sqlDatabaseHelper.getResultSet(query);
		try {
			if (resultSet.next()) {
				int orderId = resultSet.getInt(SprintTable.orderId);
				String goal = resultSet.getString(SprintTable.goal);
				int interval = resultSet.getInt(SprintTable.interval);
				String startDate = resultSet.getString(SprintTable.startDate);
				String endDate = resultSet.getString(SprintTable.endDate);
				String demoDate = resultSet.getString(SprintTable.demoDate);
				String demoPlace = resultSet.getString(SprintTable.demoPlace);
				String daily = resultSet.getString(SprintTable.daily);
				String productId = resultSet.getString(BacklogItemTable.productId);
				String createTime = resultSet.getString(BacklogItemTable.createTime);
				String updateTime = resultSet.getString(BacklogItemTable.updateTime);
				SprintData sprintData = new SprintData();
				sprintData.setSprintId(sprintId);
				sprintData.setOrderId(orderId);
				sprintData.setGoal(goal);
				sprintData.setInterval(interval);
				sprintData.setStartDate(startDate);
				sprintData.setEndDate(endDate);
				sprintData.setDemoDate(demoDate);
				sprintData.setDemoPlace(demoPlace);
				sprintData.setDaily(daily);
				sprintData.setProductId(productId);
				sprintData.setCreateTime(createTime);
				sprintData.setUpdateTime(updateTime);
				sprint = sprintMapper.transformToSprint(sprintData);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sprint;
	}

	@Override
	public Collection<Sprint> getAll() {
		Map<String, Sprint> sprints = Collections.synchronizedMap(new LinkedHashMap<String, Sprint>());
		String query = String.format("Select * From %s", SprintTable.tableName);
		ResultSet resultSet = sqlDatabaseHelper.getResultSet(query);
		try {
			while (resultSet.next()) {
				String sprintId = resultSet.getString(SprintTable.sprintId);
				int orderId = resultSet.getInt(SprintTable.orderId);
				String goal = resultSet.getString(SprintTable.goal);
				int interval = resultSet.getInt(SprintTable.interval);
				String startDate = resultSet.getString(SprintTable.startDate);
				String endDate = resultSet.getString(SprintTable.endDate);
				String demoDate = resultSet.getString(SprintTable.demoDate);
				String demoPlace = resultSet.getString(SprintTable.demoPlace);
				String daily = resultSet.getString(SprintTable.daily);
				String productId = resultSet.getString(BacklogItemTable.productId);
				String createTime = resultSet.getString(BacklogItemTable.createTime);
				String updateTime = resultSet.getString(BacklogItemTable.updateTime);
				SprintData sprintData = new SprintData();
				sprintData.setSprintId(sprintId);
				sprintData.setOrderId(orderId);
				sprintData.setGoal(goal);
				sprintData.setInterval(interval);
				sprintData.setStartDate(startDate);
				sprintData.setEndDate(endDate);
				sprintData.setDemoDate(demoDate);
				sprintData.setDemoPlace(demoPlace);
				sprintData.setDaily(daily);
				sprintData.setProductId(productId);
				sprintData.setCreateTime(createTime);
				sprintData.setUpdateTime(updateTime);
				Sprint sprint = sprintMapper.transformToSprint(sprintData);
				sprints.put(sprintId, sprint);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sprints.values();
	}

	@Override
	public int getNumberOfItems() {
		String query = String.format("Select Count(*) As numberOfSprints From %s", SprintTable.tableName);
		ResultSet resultSet = sqlDatabaseHelper.getResultSet(query);
		int numberOfSprints = 0;
		try {
			if (resultSet.next()) {
				numberOfSprints = resultSet.getInt("numberOfSprints");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return numberOfSprints;
	}

}
