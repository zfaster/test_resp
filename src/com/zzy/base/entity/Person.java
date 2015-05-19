package com.zzy.base.entity;

import java.util.List;

public class Person {

	private Long pid;
	private String name;
	private List<Order> orders;
	
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	@Override
	public String toString() {
		return "Person [id=" + pid + ", name=" + name + ", orders=" + orders
				+ "]";
	}
	
}
