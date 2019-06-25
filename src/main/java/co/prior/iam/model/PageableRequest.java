package co.prior.iam.model;

import java.util.List;

import lombok.Data;

@Data
public class PageableRequest {

	private int page;
	private int size;
	private List<SortedModel> sortedList;
	
}
