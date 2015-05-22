package com.canteen.dto;

public class OrderDTO {
	private String id;
	private String order;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "OrderDTO [id=" + id + ", order=" + order + "]";
	}

}
