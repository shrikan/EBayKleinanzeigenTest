package com.ebay.utitls;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.ebay.model.Table;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class CsvParser implements FileParser {

	@Override
	public Table parseFile(String fileName) {

		String fullyQualifiedName = getClass().getClassLoader().getResource(fileName).getFile();

		try (CSVReader reader = new CSVReader(new FileReader(fullyQualifiedName))) {
			String[] row;

			// Assumption: First row of the file contains the column names
			List<String> columns = Arrays.asList(reader.readNext());

			// Table is created with the columns from the file
			Table table = new Table(columns);

			while ((row = reader.readNext()) != null) {
				table.insert(Arrays.asList(row));
			}

			return table;

		} catch (FileNotFoundException e) {
			System.out.println("Can not find this file, please check the file name");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CsvException e) {
			e.printStackTrace();
		}

		return null;
	}

}
