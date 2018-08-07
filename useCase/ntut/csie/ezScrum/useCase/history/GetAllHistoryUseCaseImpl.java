package ntut.csie.ezScrum.useCase.history;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import ntut.csie.ezScrum.model.history.History;
import ntut.csie.ezScrum.model.history.Type;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.history.io.GetAllHistoryInput;
import ntut.csie.ezScrum.useCase.history.io.GetAllHistoryOutput;
import ntut.csie.ezScrum.useCase.history.io.HistoryModel;

public class GetAllHistoryUseCaseImpl implements GetAllHistoryUseCase, GetAllHistoryInput{
	
	private Repository<History> historyRepository;
	
	private String issueId;
	
	public GetAllHistoryUseCaseImpl() {}
	
	public GetAllHistoryUseCaseImpl(Repository<History> historyRepository) {
		this.historyRepository = historyRepository;
	}

	@Override
	public void execute(GetAllHistoryInput input, GetAllHistoryOutput output) {
		String issueId = input.getIssueId();
		List<HistoryModel> historyList = new ArrayList<>();
		
		for(History history : historyRepository.getAll()) {
			if(history.getIssueId().equals(issueId)) {
				historyList.add(convertHistoryToDTO(history));
			}
		}
		Collections.sort(historyList, new Comparator<HistoryModel>() {
			@Override
			public int compare(HistoryModel dto1, HistoryModel dto2) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date dto1CreateTime = null;
				Date dto2CreateTime = null;
				try {
					dto1CreateTime = dateFormat.parse(dto1.getCreateTime());
					dto2CreateTime = dateFormat.parse(dto2.getCreateTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return dto1CreateTime.compareTo(dto2CreateTime);
			}
			
		});
		output.setHistoryList(historyList);
	}
	
	private HistoryModel convertHistoryToDTO(History history) {
		HistoryModel dto = new HistoryModel();
		String issueType = history.getIssueType();
		String type = history.getType();
		String oldValue = history.getOldValue();
		String newValue = history.getNewValue();
		dto.setHistoryId(history.getHistoryId());
		dto.setCreateTime(history.getCreateTime());
		dto.setType(type);
		if(type.equals(Type.create)) {
			dto.setDescription("Create " + issueType);
		}else if(type.equals(Type.editDescription)) {
			dto.setDescription(oldValue + " => " + newValue);
		}else if(type.equals(Type.editEstimate)) {
			dto.setDescription(oldValue + " => " + newValue);
		}else if(type.equals(Type.editImportance)) {
			dto.setDescription(oldValue + " => " + newValue);
		}else if(type.equals(Type.editNotes)) {
			dto.setDescription(oldValue + " => " + newValue);
		}else if(type.equals(Type.assignToSprint)) {
			dto.setDescription("Assign to Sprint " + newValue);
		}else if(type.equals(Type.dropFromSprint)) {
			dto.setDescription("Drop from Sprint " + newValue);
		}else if(type.equals(Type.changeStatus)) {
			dto.setDescription(oldValue + " => " + newValue);
		}else if(type.equals(Type.addTask)) {
			dto.setDescription("Add Task " + newValue);
		}else if(type.equals(Type.removeTask)) {
			dto.setDescription("Remove Task " + newValue);
		}else if(type.equals(Type.editRemains)) {
			dto.setDescription(oldValue + " => " + newValue);
		}
		return dto;
	}

	@Override
	public String getIssueId() {
		return issueId;
	}

	@Override
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

}
