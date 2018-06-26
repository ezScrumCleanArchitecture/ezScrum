package ntut.csie.ezScrum.useCase.retrospective.io;

public class EditRetrospectiveInput {
	private String retrospectiveId;
	private String description;
	private String productId;
	private String sprintId;

	public String getRetrospectiveId() {
		return retrospectiveId;
	}

	public void setRetrospectiveId(String retrospectiveId) {
		this.retrospectiveId = retrospectiveId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSprintId() {
		return sprintId;
	}

	public void setSprintId(String sprintId) {
		this.sprintId = sprintId;
	}
}
