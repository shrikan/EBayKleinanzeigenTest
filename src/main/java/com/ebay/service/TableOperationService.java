package com.ebay.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ebay.model.Table;

public class TableOperationService {
	public Table orderBy(Table table, String columnName) {
		return orderBy(table, columnName, "desc");
	}

	public Table orderBy(Table table, String columnName, String order) {
		Table resultTable = new Table(table);

		// Check if the column name is present in the table. Throw exception otherwise
		if (!resultTable.getColumnIndexMapper().containsKey(columnName))
			throw new IllegalArgumentException("Invalid column name provided");

		// Get the index of the given column name in each row
		int columnIndex = resultTable.getColumnIndexMapper().get(columnName);

		// Type of the order, desc or asc
		if (order.equals("desc"))
			resultTable.setRows(resultTable.getRows().stream()
					.sorted((x, y) -> ((String) y.get(columnIndex)).compareTo((String) x.get(columnIndex)))
					.collect(Collectors.toList()));
		else
			resultTable.setRows(resultTable.getRows().stream()
					.sorted((x, y) -> ((String) x.get(columnIndex)).compareTo((String) y.get(columnIndex)))
					.collect(Collectors.toList()));

		return resultTable;
	}

	public Table innerJoin(Table left, Table right, String leftTableJoinColumn, String rightTableJoinColumn) {
		List<String> columns = new ArrayList<>();

		// Unified column names are generated by combining column names of both tables
		// For name resolution conflict, I assign A. prefix to left and B. prefix to the
		// right table.
		for (String columnName : left.getColumns()) {
			String newColumnName = "A." + columnName;
			columns.add(newColumnName);
		}
		for (String columnName : right.getColumns()) {
			String newColumnName = "B." + columnName;
			columns.add(newColumnName);
		}

		Table resultTable = new Table(columns);

		int leftColumnIndex = left.getColumnIndexMapper().get(leftTableJoinColumn);
		int rightColumnIndex = right.getColumnIndexMapper().get(rightTableJoinColumn);

		// For each row in the left table, it is checked if there exists a
		// row in the right having same field value based on the column names
		for (List<String> leftTableRow : left.getRows()) {
			for (List<String> rightTableRow : right.getRows()) {
				if (leftTableRow.get(leftColumnIndex).equals(rightTableRow.get(rightColumnIndex))) {
					List<String> row = new ArrayList<>();
					row.addAll(leftTableRow);
					row.addAll(rightTableRow);

					resultTable.insert(row);
				}
			}
		}

		return resultTable;
	}
}
