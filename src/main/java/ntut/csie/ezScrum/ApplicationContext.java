package ntut.csie.ezScrum;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.model.history.History;
import ntut.csie.ezScrum.model.retrospective.Retrospective;
import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.model.task.Task;
import ntut.csie.ezScrum.repository.backlogItem.BacklogItemRepository;
import ntut.csie.ezScrum.repository.history.HistoryRepository;
import ntut.csie.ezScrum.repository.retrospective.RetrospectiveRepository;
import ntut.csie.ezScrum.repository.sprint.SprintRepository;
import ntut.csie.ezScrum.repository.task.TaskRepository;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.backlogItem.AddBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.AddBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.AssignBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.AssignBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.DeleteBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.DeleteBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.DropBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.DropBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.EditBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.EditBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.GetAllBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.GetAllBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.GetAllCommittedBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.GetAllCommittedBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.GetAllNotYetCommittedBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.GetAllNotYetCommittedBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.MoveStoryCardUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.MoveStoryCardUseCaseImpl;
import ntut.csie.ezScrum.useCase.history.GetAllHistoryUseCase;
import ntut.csie.ezScrum.useCase.history.GetAllHistoryUseCaseImpl;
import ntut.csie.ezScrum.useCase.retrospective.AddRetrospectiveUseCase;
import ntut.csie.ezScrum.useCase.retrospective.AddRetrospectiveUseCaseImpl;
import ntut.csie.ezScrum.useCase.retrospective.DeleteRetrospectiveUseCase;
import ntut.csie.ezScrum.useCase.retrospective.DeleteRetrospectiveUseCaseImpl;
import ntut.csie.ezScrum.useCase.retrospective.EditRetrospectiveUseCase;
import ntut.csie.ezScrum.useCase.retrospective.EditRetrospectiveUseCaseImpl;
import ntut.csie.ezScrum.useCase.retrospective.GetAllRetrospectiveUseCase;
import ntut.csie.ezScrum.useCase.retrospective.GetAllRetrospectiveUseCaseImpl;
import ntut.csie.ezScrum.useCase.sprint.AddSprintUseCase;
import ntut.csie.ezScrum.useCase.sprint.AddSprintUseCaseImpl;
import ntut.csie.ezScrum.useCase.sprint.DeleteSprintUseCase;
import ntut.csie.ezScrum.useCase.sprint.DeleteSprintUseCaseImpl;
import ntut.csie.ezScrum.useCase.sprint.EditSprintUseCase;
import ntut.csie.ezScrum.useCase.sprint.EditSprintUseCaseImpl;
import ntut.csie.ezScrum.useCase.sprint.GetAllSprintUseCase;
import ntut.csie.ezScrum.useCase.sprint.GetAllSprintUseCaseImpl;
import ntut.csie.ezScrum.useCase.task.AddTaskUseCase;
import ntut.csie.ezScrum.useCase.task.AddTaskUseCaseImpl;
import ntut.csie.ezScrum.useCase.task.DeleteTaskUseCase;
import ntut.csie.ezScrum.useCase.task.DeleteTaskUseCaseImpl;
import ntut.csie.ezScrum.useCase.task.EditTaskUseCase;
import ntut.csie.ezScrum.useCase.task.EditTaskUseCaseImpl;
import ntut.csie.ezScrum.useCase.task.GetAllTaskUseCase;
import ntut.csie.ezScrum.useCase.task.GetAllTaskUseCaseImpl;
import ntut.csie.ezScrum.useCase.task.MoveTaskCardUseCase;
import ntut.csie.ezScrum.useCase.task.MoveTaskCardUseCaseImpl;

public class ApplicationContext {
	private static ApplicationContext instance = null;
	
	private Repository<Sprint> sprintRepository;
	private Repository<BacklogItem> backlogItemRepository;
	private Repository<Task> taskRepository;
	private Repository<Retrospective> retrospectiveRepository;
	private Repository<History> historyRepository;
	
	private AddSprintUseCase addSprintUseCase;
	private DeleteSprintUseCase deleteSprintUseCase;
	private EditSprintUseCase editSprintUseCase;
	private GetAllSprintUseCase getAllSprintUseCase;
	
	private AddBacklogItemUseCase addBacklogItemUseCase;
	private AssignBacklogItemUseCase assignBacklogItemUseCase;
	private DeleteBacklogItemUseCase deleteBacklogItemUseCase;
	private DropBacklogItemUseCase dropBacklogItemUseCase;
	private EditBacklogItemUseCase editBacklogItemUseCase;
	private GetAllBacklogItemUseCase getAllBacklogItemUseCase;
	private GetAllCommittedBacklogItemUseCase getAllCommittedBacklogItemUseCase;
	private GetAllNotYetCommittedBacklogItemUseCase getAllNotYetCommittedBacklogItemUseCase;
	private MoveStoryCardUseCase moveStoryCardUseCase;
	
	private AddTaskUseCase addTaskUseCase;
	private DeleteTaskUseCase deleteTaskUseCase;
	private EditTaskUseCase editTaskUseCase;
	private GetAllTaskUseCase getAllTaskUseCase;
	private MoveTaskCardUseCase moveTaskCardUseCase;
	
	private AddRetrospectiveUseCase addRetrospectiveUseCase;
	private DeleteRetrospectiveUseCase deleteRetrospectiveUseCase;
	private EditRetrospectiveUseCase editRetrospectiveUseCase;
	private GetAllRetrospectiveUseCase getAllRetrospectiveUseCase;
	
	private GetAllHistoryUseCase getAllHistoryUseCase;
	
	private ApplicationContext() {}
	
	public static synchronized ApplicationContext getInstance() {
		if(instance == null) {
			instance = new ApplicationContext();
		}
		return instance;
	}
	
	public Repository<Sprint> newSprintRepository(){
		sprintRepository = new SprintRepository();
		return sprintRepository;
	}
	
	public Repository<BacklogItem> newBacklogItemRepository(){
		backlogItemRepository = new BacklogItemRepository();
		return backlogItemRepository;
	}
	
	public Repository<Task> newTaskRepository(){
		taskRepository = new TaskRepository();
		return taskRepository;
	}
	
	public Repository<Retrospective> newRetrospectiveRepository(){
		retrospectiveRepository = new RetrospectiveRepository();
		return retrospectiveRepository;
	}
	
	public Repository<History> newHistoryRepository(){
		historyRepository = new HistoryRepository();
		return historyRepository;
	}
	
	public AddSprintUseCase newAddSprintUseCase() {
		addSprintUseCase = new AddSprintUseCaseImpl(newSprintRepository());
		return addSprintUseCase;
	}
	
	public DeleteSprintUseCase newDeleteSprintUseCase() {
		deleteSprintUseCase = new DeleteSprintUseCaseImpl(newSprintRepository());
		return deleteSprintUseCase;
	}
	
	public EditSprintUseCase newEditSprintUseCase() {
		editSprintUseCase = new EditSprintUseCaseImpl(newSprintRepository());
		return editSprintUseCase;
	}
	
	public GetAllSprintUseCase newGetAllSprintUseCase() {
		getAllSprintUseCase = new GetAllSprintUseCaseImpl(newSprintRepository());
		return getAllSprintUseCase;
	}
	
	public AddBacklogItemUseCase newAddBacklogItemUseCase() {
		addBacklogItemUseCase = new AddBacklogItemUseCaseImpl(newBacklogItemRepository(), newHistoryRepository());
		return addBacklogItemUseCase;
	}
	
	public AssignBacklogItemUseCase newAssignBacklogItemUseCase() {
		assignBacklogItemUseCase = new AssignBacklogItemUseCaseImpl(newBacklogItemRepository(), newSprintRepository(), newHistoryRepository());
		return assignBacklogItemUseCase;
	}
	
	public DeleteBacklogItemUseCase newDeleteBacklogItemUseCase() {
		deleteBacklogItemUseCase = new DeleteBacklogItemUseCaseImpl(newBacklogItemRepository(), newTaskRepository(), newHistoryRepository());
		return deleteBacklogItemUseCase;
	}
	
	public DropBacklogItemUseCase newDropBacklogItemUseCase() {
		dropBacklogItemUseCase = new DropBacklogItemUseCaseImpl(newBacklogItemRepository(), newSprintRepository(), newHistoryRepository());
		return dropBacklogItemUseCase;
	}
	
	public EditBacklogItemUseCase newEditBacklogItemUseCase() {
		editBacklogItemUseCase = new EditBacklogItemUseCaseImpl(newBacklogItemRepository(), newHistoryRepository());
		return editBacklogItemUseCase;
	}
	
	public GetAllBacklogItemUseCase newGetAllBacklogItemUseCase() {
		getAllBacklogItemUseCase = new GetAllBacklogItemUseCaseImpl(newBacklogItemRepository(), newSprintRepository());
		return getAllBacklogItemUseCase;
	}
	
	public GetAllCommittedBacklogItemUseCase newGetAllCommittedBacklogItemUseCase() {
		getAllCommittedBacklogItemUseCase = new GetAllCommittedBacklogItemUseCaseImpl(newBacklogItemRepository(), newSprintRepository());
		return getAllCommittedBacklogItemUseCase;
	}
	
	public GetAllNotYetCommittedBacklogItemUseCase newGetAllNotYetCommittedBacklogItemUseCase() {
		getAllNotYetCommittedBacklogItemUseCase = new GetAllNotYetCommittedBacklogItemUseCaseImpl(newBacklogItemRepository());
		return getAllNotYetCommittedBacklogItemUseCase;
	}
	
	public MoveStoryCardUseCase newMoveStoryCardUseCase() {
		moveStoryCardUseCase = new MoveStoryCardUseCaseImpl(newBacklogItemRepository(), newHistoryRepository());
		return moveStoryCardUseCase;
	}
	
	public AddTaskUseCase newAddTaskUseCase() {
		addTaskUseCase = new AddTaskUseCaseImpl(newTaskRepository(), newHistoryRepository());
		return addTaskUseCase;
	}
	
	public DeleteTaskUseCase newDeleteTaskUseCase() {
		deleteTaskUseCase = new DeleteTaskUseCaseImpl(newTaskRepository(), newHistoryRepository());
		return deleteTaskUseCase;
	}
	
	public EditTaskUseCase newEditTaskUseCase() {
		editTaskUseCase = new EditTaskUseCaseImpl(newTaskRepository(), newHistoryRepository());
		return editTaskUseCase;
	}
	
	public GetAllTaskUseCase newGetAllTaskUseCase() {
		getAllTaskUseCase = new GetAllTaskUseCaseImpl(newTaskRepository());
		return getAllTaskUseCase;
	}
	
	public MoveTaskCardUseCase newMoveTaskCardUseCase() {
		moveTaskCardUseCase = new MoveTaskCardUseCaseImpl(newTaskRepository(), newHistoryRepository());
		return moveTaskCardUseCase;
	}
	
	public AddRetrospectiveUseCase newAddRetrospectiveUseCase() {
		addRetrospectiveUseCase = new AddRetrospectiveUseCaseImpl(newRetrospectiveRepository());
		return addRetrospectiveUseCase;
	}
	
	public DeleteRetrospectiveUseCase newDeleteRetrospectiveUseCase() {
		deleteRetrospectiveUseCase = new DeleteRetrospectiveUseCaseImpl(newRetrospectiveRepository());
		return deleteRetrospectiveUseCase;
	}
	
	public EditRetrospectiveUseCase newEditRetrospectiveUseCase() {
		editRetrospectiveUseCase = new EditRetrospectiveUseCaseImpl(newRetrospectiveRepository());
		return editRetrospectiveUseCase;
	}
	
	public GetAllRetrospectiveUseCase newGetAllRetrospectiveUseCase() {
		getAllRetrospectiveUseCase = new GetAllRetrospectiveUseCaseImpl(newRetrospectiveRepository(), newSprintRepository());
		return getAllRetrospectiveUseCase;
	}
	
	public GetAllHistoryUseCase newGetAllHistoryUseCase() {
		getAllHistoryUseCase = new GetAllHistoryUseCaseImpl(newHistoryRepository());
		return getAllHistoryUseCase;
	}
}
