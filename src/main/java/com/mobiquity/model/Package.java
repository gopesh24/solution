package com.mobiquity.model;

import java.util.Comparator;
import java.util.List;

import com.mobiquity.exception.APIException;

/**
 * This class represents a package of items.
 */
public class Package {

	private static final String ITEM_OUT_OF_BOUND = "Maximum weight capacity of package supplied is out of bound.";
	private int maxWeight;
	private List<Item> items;

	/**
	 * Constructor 
	 */
	public Package(int maxWeight, List<Item> items) {
		super();
		if (maxWeight < 0 || maxWeight > 100) {
			throw new APIException(ITEM_OUT_OF_BOUND);
		}
		
		this.maxWeight = maxWeight * 100;
		this.items = items;
		this.items.sort(Comparator.comparing(Item::getWeight).thenComparing(Item::getCost));
	}

	/**
	 * @return the max weight 
	 */
	public int getMaxWeight() {
		return this.maxWeight;
	}

	/**
	 * @return the list of items
	 */
	public List<Item> getItems() {
		return this.items;
	}

	/**
	 * @return true if list has items, false otherwise
	 */
	public boolean hasItems() {
		return !this.items.isEmpty();
	}
	/**
	 * @return the sum of the items
	 */
	public Double getTotalItemsWeight() {
		return this.items.stream().mapToDouble(Item::getWeight).sum();
	}

}
