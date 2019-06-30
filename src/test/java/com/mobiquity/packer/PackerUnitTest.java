package com.mobiquity.packer;


import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class PackerUnitTest {

	private static final String EXPECTED_RESULTS = "\n4\n-\n2,7\n8,9";
	private File testCasesFile;

	@Before
	public void setUp() {
		this.testCasesFile = new File("src/test/resources/testcases.txt");
	}

	@Test
	public void successScenario() {
		String result = Packer.pack(testCasesFile.getAbsolutePath());
		assertEquals("Result is as expected", EXPECTED_RESULTS, result);
	}
}