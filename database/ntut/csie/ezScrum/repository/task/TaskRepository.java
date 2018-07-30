package ntut.csie.ezScrum.repository.task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import ntut.csie.ezScrum.database.SqlDatabaseHelper;
import ntut.csie.ezScrum.database.TaskTable;
import ntut.csie.ezScrum.model.task.Task;
import ntut.csie.ezScrum.useCase.Repository;

public class TaskRepository implements Repository<Task> {

	private SqlDatabaseHelper sqlDatabaseHelper = SqlDatabaseHelper.getInstance();
	private TaskMapper taskMapper;

	public TaskRepository() {
		taskMapper = new TaskMapper();
	}

	@Override
	public void add(Task task) {
		TaskData data = taskMapper.transformToTaskData(task);
		String sql = String.format("Insert Into %s Values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", TaskTable.tableName);
		PreparedStatement preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
		try {
			preparedStatement.setString(1, data.getTaskId());
			preparedStatement.setInt(2, data.getOrderId());
			preparedStatement.setString(3, data.getDescription());
			preparedStatement.setString(4, data.getHandlerId());
			preparedStatement.setString(5, data.getStatus());
			preparedStatement.setInt(6, data.getEstimate());
			preparedStatement.setInt(7, data.getRemains());
			preparedStatement.setString(8, data.getNotes());
			preparedStatement.setString(9, data.getBacklogItemId());
			preparedStatement.setString(10, data.getCreateTime());
			preparedStatement.setString(11, data.getUpdateTime());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Task task) {
		TaskData data = taskMapper.transformToTaskData(task);
		String sql = String.format("Update %s Set " + "%s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? Where %s=?",
				TaskTable.tableName, TaskTable.orderId, TaskTable.description, TaskTable.handlerId, TaskTable.status,
				TaskTable.estimate, TaskTable.remains, TaskTable.notes, TaskTable.backlogItemId, TaskTable.updateTime,
				TaskTable.taskId);
		PreparedStatement preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
		try {
			preparedStatement.setInt(1, data.getOrderId());
			preparedStatement.setString(2, data.getDescription());
			preparedStatement.setString(3, data.getHandlerId());
			preparedStatement.setString(4, data.getStatus());
			preparedStatement.setInt(5, data.getEstimate());
			preparedStatement.setInt(6, data.getRemains());
			preparedStatement.setString(7, data.getNotes());
			preparedStatement.setString(8, data.getBacklogItemId());
			preparedStatement.setString(9, data.getUpdateTime());
			preparedStatement.setString(10, data.getTaskId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void remove(Task task) {
		String sql = String.format("Delete From %s Where %s=?", TaskTable.tableName, TaskTable.taskId);
		PreparedStatement preparedStatementForDeleting = sqlDatabaseHelper.getPreparedStatement(sql);
		try {
			preparedStatementForDeleting.setString(1, task.getTaskId());
			preparedStatementForDeleting.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Task get(String taskId) {
		Task task = null;
		String query = String.format("Select * From %s Where %s='%s'", TaskTable.tableName, TaskTable.taskId, taskId);
		ResultSet resultSet = sqlDatabaseHelper.getResultSet(query);
		try {
			if (resultSet.next()) {
				int orderId = resultSet.getInt(TaskTable.orderId);
				String description = resultSet.getString(TaskTable.description);
				String handlerId = resultSet.getString(TaskTable.handlerId);
				String status = resultSet.getString(TaskTable.status);
				int estimate = resultSet.getInt(TaskTable.estimate);
				int remains = resultSet.getInt(TaskTable.remains);
				String notes = resultSet.getString(TaskTable.notes);
				String backlogItemId = resultSet.getString(TaskTable.backlogItemId);
				String createTime = resultSet.getString(TaskTable.createTime);
				String updateTime = resultSet.getString(TaskTable.updateTime);
				TaskData taskData = new TaskData();
				taskData.setTaskId(taskId);
				taskData.setOrderId(orderId);
				taskData.setDescription(description);
				taskData.setHandlerId(handlerId);
				taskData.setStatus(status);
				taskData.setEstimate(estimate);
				taskData.setRemains(remains);
				taskData.setNotes(notes);
				taskData.setBacklogItemId(backlogItemId);
				taskData.setCreateTime(createTime);
				taskData.setUpdateTime(updateTime);
				task = taskMapper.transformToTask(taskData);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return task;
	}

	@Override
	public Collection<Task> getAll() {
		Map<String, Task> tasks = Collections.synchronizedMap(new LinkedHashMap<String, Task>());
		String query = String.format("Select * From %s", TaskTable.tableName);
		ResultSet resultSet = sqlDatabaseHelper.getResultSet(query);
		try {
			while (resultSet.next()) {
				String taskId = resultSet.getString(TaskTable.taskId);
				int orderId = resultSet.getInt(TaskTable.orderId);
				String description = resultSet.getString(TaskTable.description);
				String handlerId = resultSet.getString(TaskTable.handlerId);
				String status = resultSet.getString(TaskTable.status);
				int estimate = resultSet.getInt(TaskTable.estimate);
				int remains = resultSet.getInt(TaskTable.remains);
				String notes = resultSet.getString(TaskTable.notes);
				String backlogItemId = resultSet.getString(TaskTable.backlogItemId);
				String createTime = resultSet.getString(TaskTable.createTime);
				String updateTime = resultSet.getString(TaskTable.updateTime);
				TaskData taskData = new TaskData();
				taskData.setTaskId(taskId);
				taskData.setOrderId(orderId);
				taskData.setDescription(description);
				taskData.setHandlerId(handlerId);
				taskData.setStatus(status);
				taskData.setEstimate(estimate);
				taskData.setRemains(remains);
				taskData.setNotes(notes);
				taskData.setBacklogItemId(backlogItemId);
				taskData.setCreateTime(createTime);
				taskData.setUpdateTime(updateTime);
				Task task = taskMapper.transformToTask(taskData);
				tasks.put(taskId, task);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tasks.values();
	}

	@Override
	public int getNumberOfItems() {
		String query = String.format("Select Count(*) As numberOfTasks From %s", TaskTable.tableName);
		ResultSet resultSet = sqlDatabaseHelper.getResultSet(query);
		int numberOfTasks = 0;
		try {
			if (resultSet.next()) {
				numberOfTasks = resultSet.getInt("numberOfTasks");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return numberOfTasks;
	}

}
