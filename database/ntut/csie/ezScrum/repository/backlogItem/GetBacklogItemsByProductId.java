package ntut.csie.ezScrum.repository.backlogItem;

import ntut.csie.ezScrum.database.BacklogItemTable;
import ntut.csie.ezScrum.useCase.SqlSpecification;

public class GetBacklogItemsByProductId implements SqlSpecification {
	private String productId;

	public GetBacklogItemsByProductId(String productId) {
		this.productId = productId;
	}

	@Override
	public String toSqlQuery() {
		return String.format("Select * From %s Where %s='%s'",
				BacklogItemTable.tableName,
				BacklogItemTable.productId,
				productId);
	}

}