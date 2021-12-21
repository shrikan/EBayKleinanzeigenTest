package com.ebay.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ebay.model.Table;
import com.ebay.service.TableOperationService;
import com.ebay.utitls.CsvParser;
import com.ebay.utitls.FileParser;

class ApplicationTest {

	Table purchases, users;
	TableOperationService sqlOperations;

	@BeforeEach
	void setup(){
		FileParser fileParser = new CsvParser();
		purchases = fileParser.parseFile("purchases.csv");
		users = fileParser.parseFile("users.csv");

		sqlOperations = new TableOperationService();
	}

	@Test
	void orderByForUserTableIdSuccessful() {
		Table usersOrderedByName = sqlOperations.orderBy(users, "USER_ID");
		int idx = 5;

		for (List<String> row : usersOrderedByName.getRows()) {
			assertEquals(row.get(0), String.valueOf(idx--));
		}
	}

	@Test
	void orderByForPurchasesTableTitleSuccessful() {
		Table usersOrderedByName = sqlOperations.orderBy(purchases, "TITLE");
		List<List<String>> orderedTable = new ArrayList<>();

		orderedTable.add(List.of("6", "table-2", "4"));
		orderedTable.add(List.of("7", "table-1", "4"));
		orderedTable.add(List.of("5", "guitar-2", "3"));
		orderedTable.add(List.of("4", "guitar-1", "2"));
		orderedTable.add(List.of("9", "chair-1", "1"));
		orderedTable.add(List.of("3", "car-3", "1"));
		orderedTable.add(List.of("2", "car-2", "1"));
		orderedTable.add(List.of("1", "car-1", "1"));

		int idx = 0;

		for (List<String> row : usersOrderedByName.getRows()) {
			assertEquals(row.get(1), orderedTable.get(idx++).get(1));
		}
	}

	@Test
	void tableOrderByInvalidColumn() {
		assertThrows(IllegalArgumentException.class, () -> sqlOperations.orderBy(purchases, "ABC", "desc"));
	}

}
