package com.ebay.main;

import java.io.FileNotFoundException;

import com.ebay.model.Table;
import com.ebay.service.TableOperationService;
import com.ebay.utitls.CsvParser;
import com.ebay.utitls.FileParser;

public class Application {
	public static void main(String[] args) throws FileNotFoundException {
		FileParser fileParser = new CsvParser();

		// Create tables from csv file parsing
		Table purchases = fileParser.parseFile("purchases.csv");
		Table users = fileParser.parseFile("users.csv");

		// Display the tables generated
		System.out.println("Purchase Table");
		purchases.display();

		System.out.println("Users Table");
		users.display();

		TableOperationService sqlOperations = new TableOperationService();

		// Task 1: Order by desc
		// Order users by NAME
		Table usersOrderedByName = sqlOperations.orderBy(users, "NAME");

		System.out.println("Users ordered Table");
		usersOrderedByName.display();

		// Order purchases by TITTLE
		Table purchasesOrderedByTitle = sqlOperations.orderBy(purchases, "TITLE");
		System.out.println("Purchase ordered Table");
		purchasesOrderedByTitle.display();

		// Task 2: Inner join
		// Join users and purchases table by USER_ID
		Table joinedTable = sqlOperations.innerJoin(users, purchases, "USER_ID", "USER_ID");
		System.out.println("Joined Table");
		joinedTable.display();
	}
}
