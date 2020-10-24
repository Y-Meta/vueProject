package com.plmm.generate.model;

public class Field {
	private String columnName;
	
	private String name;
	
	private String label;
	
	private String type;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return this.columnName + "|" + this.label + "|" + this.name + "|" + this.type;
	}
	
}
