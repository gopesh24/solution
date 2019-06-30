package com.mobiquity.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.Item;
import com.mobiquity.model.Package;

/**
 * This class is responsible for reading an input file and parse into items and max weight
 */
public class FileParser {

	private static final String UNABLE_TO_READ_THE_FILE = "Unable to read the file.";

	private static String VALID_REGEX = "^\\d+\\s*?:\\s*\\(\\s*\\d+\\s*,\\s*\\d*\\.{0,1}\\d+\\s*,\\s*€\\d*\\.{0,1}\\d+\\s*\\).*$";

	private static Pattern VALID_ITEM_REGEX = Pattern.compile("\\((\\d+),(\\d+\\.?\\d*?),(\\d+)\\)");

	
	/**
	 * @param inputLine 
	 * @return returns true if the line is valid or false
	 */
	public boolean isValidLine(String inputLine) {
		return inputLine.matches(VALID_REGEX);
	}
	/**
	 * This method is used to parse the test case file and prepare items based on package capacity.
	 * @param inputFilePath
	 * @return List of packages containing items and its capacity.
	 */
	public List<Package> parse(String inputFilePath) {
		File inputFile = new File(inputFilePath);
		List<Package> packages = new ArrayList<>();
		
		try (Scanner scanner = new Scanner(inputFile)) {
			while (scanner.hasNextLine()) {
				//Scan and parse the input file.Add each line to the package list
				String inputLine = scanner.nextLine();
				packages.add(parseLine(inputLine));
			}

		} catch (IOException e) {
			throw new APIException(UNABLE_TO_READ_THE_FILE, e); // wrap IO exception
		}
		return packages;
	}

	/**
	 * This method is used to parse each line. Throws APIException if format not valid.
	 * @param inputLine
	 * @return package
	 */
	 public Package parseLine(String inputLine) {
		if (!isValidLine(inputLine)) {
			throw new APIException("Error parsing: invalid format.");
		}

		String parameters[] = inputLine.replaceAll("[ €]", "").split(":");//removing euro symbol
		int maxPackageWeight = Integer.parseInt(parameters[0]);

		List<Item> items = new LinkedList<>();
		Matcher itemMatcher = VALID_ITEM_REGEX.matcher(parameters[1]);

		while (itemMatcher.find()) {
			Integer index = Integer.valueOf(itemMatcher.group(1));
			Float weight = Float.valueOf(itemMatcher.group(2));
			Integer cost = Integer.valueOf(itemMatcher.group(3));

			// Exclude items which has weight greater than package capacity
			if (weight <= maxPackageWeight) {
				items.add(new Item(index, weight, cost));
			}
		}
		return new Package(maxPackageWeight, items);
	}
}
