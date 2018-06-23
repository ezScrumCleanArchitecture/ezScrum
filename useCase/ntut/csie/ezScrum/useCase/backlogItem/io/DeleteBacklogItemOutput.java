package ntut.csie.ezScrum.useCase.backlogItem.io;

public class DeleteBacklogItemOutput {
	private boolean deleteSuccess;

	public boolean isDeleteSuccess() {
		return deleteSuccess;
	}

	public void setDeleteSuccess(boolean deleteSuccess) {
		this.deleteSuccess = deleteSuccess;
	}
}
