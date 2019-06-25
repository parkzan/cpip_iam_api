package co.prior.iam.model;

import org.springframework.data.domain.Sort.Direction;

import lombok.Data;

@Data
public class SortedModel {

	private String field;
	private Direction direction;
	
}
