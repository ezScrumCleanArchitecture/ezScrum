package ntut.csie.ezScrum.repository.history;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import ntut.csie.ezScrum.database.HistoryTable;
import ntut.csie.ezScrum.database.SqlDatabaseHelper;
import ntut.csie.ezScrum.model.history.History;
import ntut.csie.ezScrum.useCase.Repository;

public class HistoryRepository implements Repository<History>{
	
	private SqlDatabaseHelper sqlDatabaseHelper = SqlDatabaseHelper.getInstance();
	private HistoryMapper historyMapper;
	
	public HistoryRepository() {
		historyMapper = new HistoryMapper();
	}

	@Override
	public void add(History history) {
		HistoryData data = historyMapper.transformToHistoryData(history);
		String sql = String.format("Insert Into %s Values (?, ?, ?, ?, ?, ?, ?)", HistoryTable.tableName);
		PreparedStatement preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
		try {
			preparedStatement.setString(1, data.getHistoryId());
			preparedStatement.setString(2, data.getIssueId());
			preparedStatement.setString(3, data.getIssueType());
			preparedStatement.setString(4, data.getType());
			preparedStatement.setString(5, data.getOldValue());
			preparedStatement.setString(6, data.getNewValue());
			preparedStatement.setString(7, data.getCreateTime());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(History history) {
		
	}

	@Override
	public void remove(History history) {
		String sql = String.format("Delete From %s Where %s=?", HistoryTable.tableName,
				HistoryTable.historyId);
		PreparedStatement preparedStatementForDeleting = sqlDatabaseHelper.getPreparedStatement(sql);
		try {
			preparedStatementForDeleting.setString(1, history.getHistoryId());
			preparedStatementForDeleting.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public History get(String historyId) {
		return null;
	}

	@Override
	public Collection<History> getAll() {
		Map<String, History> histories = Collections
				.synchronizedMap(new LinkedHashMap<String, History>());
		String query = String.format("Select * From %s", HistoryTable.tableName);
		ResultSet resultSet = sqlDatabaseHelper.getResultSet(query);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			while (resultSet.next()) {
				String historyId = resultSet.getString(HistoryTable.historyId);
				String issueId = resultSet.getString(HistoryTable.issueId);
				String issueType = resultSet.getString(HistoryTable.issueType);
				String type = resultSet.getString(HistoryTable.type);
				String oldValue = resultSet.getString(HistoryTable.oldValue);
				String newValue = resultSet.getString(HistoryTable.newValue);
				Date createTime = resultSet.getTimestamp(HistoryTable.createTime);
				HistoryData historyData = new HistoryData();
				historyData.setHistoryId(historyId);
				historyData.setIssueId(issueId);
				historyData.setIssueType(issueType);
				historyData.setType(type);
				historyData.setOldValue(oldValue);
				historyData.setNewValue(newValue);
				historyData.setCreateTime(dateFormat.format(createTime));
				History history = historyMapper.transformToHistory(historyData);
				histories.put(historyId, history);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return histories.values();
	}

	@Override
	public int getNumberOfItems() {
		String query = String.format("Select Count(*) As numberOfHistories From %s", HistoryTable.tableName);
		ResultSet resultSet = sqlDatabaseHelper.getResultSet(query);
		int numberOfHistories = 0;
		try {
			if (resultSet.next()) {
				numberOfHistories = resultSet.getInt("numberOfHistories");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return numberOfHistories;
	}

}
