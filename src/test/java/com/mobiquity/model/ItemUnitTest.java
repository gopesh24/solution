package com.mobiquity.model;

import static com.mobiquity.constants.ItemConstants.ITEM_COST_IS_NOT_IN_RANGE;
import static com.mobiquity.constants.ItemConstants.ITEM_INDEX_IS_NOT_IN_RANGE;
import static com.mobiquity.constants.ItemConstants.ITEM_WEIGHT_IS_NOT_IN_RANGE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mobiquity.exception.APIException;	

public class ItemUnitTest {

	@SuppressWarnings("unused")
	@Test(expected = APIException.class)
	public void failureScenarioWithIndex() {
		try {
		Item item = new Item(19, 10, 10);
		}catch (APIException e) {
			assertEquals("Result is as expected",ITEM_INDEX_IS_NOT_IN_RANGE,e.getMessage());
			throw e;
		}
	}
	@SuppressWarnings("unused")
	@Test(expected = APIException.class)
	public void failureScenarioWithWeight() {
		try {
			Item item = new Item(1, 200, 10);
		}catch (APIException e) {
			assertEquals("Result is as expected",ITEM_WEIGHT_IS_NOT_IN_RANGE,e.getMessage());
			throw e;
		}	
	}
	@SuppressWarnings("unused")
	@Test(expected = APIException.class)
	public void failureScenarioWithCost() {
		try {
			Item item = new Item(9, 10, 200);
		}catch (APIException e) {
			assertEquals("Result is as expected",ITEM_COST_IS_NOT_IN_RANGE,e.getMessage());
			throw e;
		}
		
		
	}
}
