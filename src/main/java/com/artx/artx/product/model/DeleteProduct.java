package com.artx.artx.product.model;

import lombok.Getter;
import java.util.List;

public class DeleteProduct {

	@Getter
	public static class Request {
		private List<Long> productIds;
	}

}
