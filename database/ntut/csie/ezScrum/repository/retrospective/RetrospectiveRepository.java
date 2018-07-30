package ntut.csie.ezScrum.repository.retrospective;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import ntut.csie.ezScrum.database.RetrospectiveTable;
import ntut.csie.ezScrum.database.SqlDatabaseHelper;
import ntut.csie.ezScrum.model.retrospective.Retrospective;
import ntut.csie.ezScrum.useCase.Repository;

public class RetrospectiveRepository implements Repository<Retrospective> {

	private SqlDatabaseHelper sqlDatabaseHelper = SqlDatabaseHelper.getInstance();
	private RetrospectiveMapper retrospectiveMapper;

	public RetrospectiveRepository() {
		retrospectiveMapper = new RetrospectiveMapper();
	}

	@Override
	public void add(Retrospective retrospective) {
		RetrospectiveData data = retrospectiveMapper.transformToRetrospectiveData(retrospective);
		String sql = String.format("Insert Into %s Values (?, ?, ?, ?, ?, ?, ?)", RetrospectiveTable.tableName);
		PreparedStatement preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
		try {
			preparedStatement.setString(1, data.getRetrospectiveId());
			preparedStatement.setInt(2, data.getOrderId());
			preparedStatement.setString(3, data.getDescription());
			preparedStatement.setString(4, data.getProductId());
			preparedStatement.setString(5, data.getSprintId());
			preparedStatement.setString(6, data.getCreateTime());
			preparedStatement.setString(7, data.getUpdateTime());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Retrospective retrospective) {
		RetrospectiveData data = retrospectiveMapper.transformToRetrospectiveData(retrospective);
		String sql = String.format("Update %s Set " + "%s=?, %s=?, %s=?, %s=?, %s=? Where %s=?",
				RetrospectiveTable.tableName, RetrospectiveTable.orderId, RetrospectiveTable.description,
				RetrospectiveTable.productId, RetrospectiveTable.sprintId, RetrospectiveTable.updateTime,
				RetrospectiveTable.retrospectiveId);
		PreparedStatement preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
		try {
			preparedStatement.setInt(1, data.getOrderId());
			preparedStatement.setString(2, data.getDescription());
			preparedStatement.setString(3, data.getProductId());
			preparedStatement.setString(4, data.getSprintId());
			preparedStatement.setString(5, data.getUpdateTime());
			preparedStatement.setString(6, data.getRetrospectiveId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void remove(Retrospective retrospective) {
		String sql = String.format("Delete From %s Where %s=?", RetrospectiveTable.tableName,
				RetrospectiveTable.retrospectiveId);
		PreparedStatement preparedStatementForDeleting = sqlDatabaseHelper.getPreparedStatement(sql);
		try {
			preparedStatementForDeleting.setString(1, retrospective.getRetrospectiveId());
			preparedStatementForDeleting.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Retrospective get(String retrospectiveId) {
		Retrospective retrospective = null;
		String query = String.format("Select * From %s Where %s='%s'", RetrospectiveTable.tableName,
				RetrospectiveTable.retrospectiveId, retrospectiveId);
		ResultSet resultSet = sqlDatabaseHelper.getResultSet(query);
		try {
			if (resultSet.next()) {
				int orderId = resultSet.getInt(RetrospectiveTable.orderId);
				String description = resultSet.getString(RetrospectiveTable.description);
				String productId = resultSet.getString(RetrospectiveTable.productId);
				String sprintId = resultSet.getString(RetrospectiveTable.sprintId);
				String createTime = resultSet.getString(RetrospectiveTable.createTime);
				String updateTime = resultSet.getString(RetrospectiveTable.updateTime);
				RetrospectiveData retrospectiveData = new RetrospectiveData();
				retrospectiveData.setRetrospectiveId(retrospectiveId);
				retrospectiveData.setOrderId(orderId);
				retrospectiveData.setDescription(description);
				retrospectiveData.setProductId(productId);
				retrospectiveData.setSprintId(sprintId);
				retrospectiveData.setCreateTime(createTime);
				retrospectiveData.setUpdateTime(updateTime);
				retrospective = retrospectiveMapper.transformToRetrospective(retrospectiveData);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retrospective;
	}

	@Override
	public Collection<Retrospective> getAll() {
		Map<String, Retrospective> retrospectives = Collections
				.synchronizedMap(new LinkedHashMap<String, Retrospective>());
		String query = String.format("Select * From %s", RetrospectiveTable.tableName);
		ResultSet resultSet = sqlDatabaseHelper.getResultSet(query);
		try {
			while (resultSet.next()) {
				String retrospectiveId = resultSet.getString(RetrospectiveTable.retrospectiveId);
				int orderId = resultSet.getInt(RetrospectiveTable.orderId);
				String description = resultSet.getString(RetrospectiveTable.description);
				String productId = resultSet.getString(RetrospectiveTable.productId);
				String sprintId = resultSet.getString(RetrospectiveTable.sprintId);
				String createTime = resultSet.getString(RetrospectiveTable.createTime);
				String updateTime = resultSet.getString(RetrospectiveTable.updateTime);
				RetrospectiveData retrospectiveData = new RetrospectiveData();
				retrospectiveData.setRetrospectiveId(retrospectiveId);
				retrospectiveData.setOrderId(orderId);
				retrospectiveData.setDescription(description);
				retrospectiveData.setProductId(productId);
				retrospectiveData.setSprintId(sprintId);
				retrospectiveData.setCreateTime(createTime);
				retrospectiveData.setUpdateTime(updateTime);
				Retrospective retrospective = retrospectiveMapper.transformToRetrospective(retrospectiveData);
				retrospectives.put(retrospectiveId, retrospective);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retrospectives.values();
	}

	@Override
	public int getNumberOfItems() {
		String query = String.format("Select Count(*) As numberOfRetrospectives From %s", RetrospectiveTable.tableName);
		ResultSet resultSet = sqlDatabaseHelper.getResultSet(query);
		int numberOfRetrospectives = 0;
		try {
			if (resultSet.next()) {
				numberOfRetrospectives = resultSet.getInt("numberOfRetrospectives");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return numberOfRetrospectives;
	}

}
