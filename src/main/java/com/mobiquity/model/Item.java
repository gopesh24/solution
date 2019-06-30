package com.mobiquity.model;


import static com.mobiquity.constants.ItemConstants.ITEM_COST_IS_NOT_IN_RANGE;
import static com.mobiquity.constants.ItemConstants.ITEM_INDEX_IS_NOT_IN_RANGE;
import static com.mobiquity.constants.ItemConstants.ITEM_WEIGHT_IS_NOT_IN_RANGE;
import com.mobiquity.exception.APIException;

/**
 * This class represents model of Item
 */
public class Item {

	private int index;
	private int weight;
	private float cost;

	/**
	 * @param index
	 * @param weight
	 * @param cost
	 * @throws APIException
	 */
	public Item(int index, float weight, int cost) {
		if (weight < 0 || weight > 100) {
			throw new APIException(ITEM_WEIGHT_IS_NOT_IN_RANGE);
		}
		if (cost < 0 || cost > 100) {
			throw new APIException(ITEM_COST_IS_NOT_IN_RANGE);
		}
		if (index > 15) {
			throw new APIException(ITEM_INDEX_IS_NOT_IN_RANGE);
		}
		this.index = index;
		this.cost = cost;
		/*
		 * Multiply the weight * 100  to use Knapsack algorithm
		 */
		this.weight = (int) weight * 100;
	}

	/**
	 * @return index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @return weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @return cost
	 */
	public float getCost() {
		return cost;
	}
}
