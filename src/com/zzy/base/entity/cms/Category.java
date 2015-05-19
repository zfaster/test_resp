package com.zzy.base.entity.cms;

public class Category {
	private Integer categoryId;
	private String name;
	private String description;
	private Integer pcid;

	public Category() {
		super();
	}

	public Category(Integer categoryId, String name, String description,
			Integer pcid) {
		super();
		this.categoryId = categoryId;
		this.name = name;
		this.description = description;
		this.pcid = pcid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getPcid() {
		return pcid;
	}

	public void setPcid(Integer pcid) {
		this.pcid = pcid;
	}

	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", name=" + name
				+ ", description=" + description + ", pcid=" + pcid + "]";
	}

}
