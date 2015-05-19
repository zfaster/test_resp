package com.zzy.base.entity;

import java.math.BigDecimal;


public class Order {

	private Long oid;
	private BigDecimal price;
	private Person person;
	
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	@Override
	public String toString() {
		return "Order [orderNo=" + oid + ", price=" + price + ", person="
				+ person + "]";
	}
	
}
