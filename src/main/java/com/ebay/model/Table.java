package com.ebay.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {
	private Map<String, Integer> columnIndexMapper;
	private List<String> columns;
	private List<List<String>> rows;

	public Table() {

	}

	public Table(Table table) {
		setColumnIndexMapper(new HashMap<String, Integer>());
		setRows(new ArrayList<List<String>>());
		setColumns(new ArrayList<String>());

		getColumnIndexMapper().putAll(table.getColumnIndexMapper());
		getColumns().addAll(table.getColumns());
		getRows().addAll(table.getRows());
	}

	public Table(List<String> inputColumns) {
		setColumnIndexMapper(new HashMap<>());
		int idx = 0;

		for (String columnName : inputColumns) {
			getColumnIndexMapper().put(columnName, idx++);
		}
		setRows(new ArrayList<List<String>>());
		setColumns(new ArrayList<String>());
		getColumns().addAll(inputColumns);
	}

	public void insert(List<String> row) {
		this.getRows().add(row);
	}

	public void display() {
		System.out.println(String.join("\t\t", getColumns()));
		for (List<String> row : getRows()) {
			System.out.println(String.join("\t\t", row));
		}
	}

	public Map<String, Integer> getColumnIndexMapper() {
		return columnIndexMapper;
	}

	public void setColumnIndexMapper(Map<String, Integer> columnIndexMapper) {
		this.columnIndexMapper = columnIndexMapper;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public List<List<String>> getRows() {
		return rows;
	}

	public void setRows(List<List<String>> rows) {
		this.rows = rows;
	}
}
