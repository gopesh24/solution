package com.mobiquity.packer;

import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.List;

import com.mobiquity.model.Item;
import com.mobiquity.model.Package;
import com.mobiquity.parser.FileParser;



/**
 * This class is responsible of finding the best items combination to find best suite package.
 * with a max weight.
 */
public class Packer {

	
	private static final String DELIMITER = ",";
	private static final String DEFAULT_INDEX = "-";

	/**
	 * @param inputFilePath
	 * @return List of string with Item indexes considered for building the package.
	 */
	public static String pack(String inputFilePath) {
		StringBuilder result = new StringBuilder();
		
		for (Package eachPackage : new FileParser().parse(inputFilePath)) {
			result.append("\n" + getItemsForPackage(eachPackage));
		}
		return result.toString();
	}

	/**
	 * This method is used to get result items for each test case.
	 * @param eachPackage
	 * @return comma separated indexes as string
	 */
	private static String getItemsForPackage(Package eachPackage) {
		// If the package does not contain any items return "-"
		if (!eachPackage.hasItems()) {
			return DEFAULT_INDEX;
		}
		 // If all the items fit inside the package, return the complete list of items
		if (eachPackage.getTotalItemsWeight() < eachPackage.getMaxWeight()) {
			return generateIndexesStringFromListOfItems(eachPackage.getItems());
		}
		List<Item> selectedItems = knapsackForBestFitItems(eachPackage);
		return generateIndexesStringFromListOfItems(selectedItems);
	}
	/**
	 * This method is used to return result string.
	 * @param items
	 * @return comma separated indexes as string
	 */
	private static String generateIndexesStringFromListOfItems(List<Item> items) {
		String indexes[] = items.stream().map(item ->item.getIndex()).sorted().map(sorted -> sorted.toString()).toArray(size -> new String[size]);
		return String.join(DELIMITER, indexes);
	}

	/**
	 * This method is used to calculate the best fit items using knapsack Algorithm
	 * @param eachPackage
	 * @return List of best fit items for given package
	 */
	private static List<Item> knapsackForBestFitItems(Package eachPackage) {
		int itemSize = eachPackage.getItems().size();
		double[][] solutionMatrix = new double[itemSize + 1][eachPackage.getMaxWeight() + 1];
        // initialize
		for (int i = 0; i <= eachPackage.getMaxWeight(); i++) {
			solutionMatrix[0][i] = 0;
		}

		for (int index = 1; index <= itemSize; index++) {	
			Item item = eachPackage.getItems().get(index - 1);	
			for (int weight = 1; weight <= eachPackage.getMaxWeight(); weight++) {
				// If the item does not fits
				if (item.getWeight() > weight) {
					solutionMatrix[index][weight] = solutionMatrix[index - 1][weight];
				} else {
					// Choosing the item
					double tookItemCost =  solutionMatrix[index - 1][weight - item.getWeight()] + item.getCost() ;
					// Maximum of tookItemCost and cost of previous
					solutionMatrix[index][weight] = max(tookItemCost, solutionMatrix[index - 1][weight]);
				}
			}
		}
		return findItemsFromMatrix(eachPackage, solutionMatrix);
	}

	/**
	 * This method is used for finding out the best suite items from the calculated solutions
	 * @param eachPackage
	 * @param solutionMatrix
	 * @return List of Items selected for possible best scenarios
	 */
	private static List<Item> findItemsFromMatrix(Package eachPackage, double[][] solutionMatrix) {
		
		int capacity = eachPackage.getMaxWeight();
		List<Item> selectedItems = new ArrayList<>();
		int itemSize = eachPackage.getItems().size();
		// Traversing solution from reverse
		for (int index = itemSize; index > 0; index--) {
			
			// If value is same for last item corresponding row and previous row - (skip item case)
			// If value is different item has been picked in the matrix
			if (solutionMatrix[index][capacity] != solutionMatrix[index - 1][capacity]) {
				Item item = eachPackage.getItems().get(index - 1);
				selectedItems.add(item);
				//Update the total capacity once picked
				capacity -= item.getWeight();
			}
		}
		return selectedItems;
	}
}
