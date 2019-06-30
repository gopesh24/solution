package com.mobiquity.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.Package;


public class FileParserUnitTest {

	private File testFile;
	private FileParser parser;

	/**
	 * Setup the test file class to be used in all the tests.
	 */
	@Before
	public void setUp() {
		this.testFile = new File("src/test/resources/testcases.txt");
		this.parser = new FileParser();
	}

	@Test
	public void failureScenarioWithInvalidItems() {
		String line = "1 : ";
		assertFalse(parser.isValidLine(line));
	}

	@Test
	public void failureScenarioWithInvalidFormat() {
		String line = "81 (1,53.38) ";
		assertFalse(parser.isValidLine(line));
	}

	@Test(expected = APIException.class)
	public void failureScenarioWithInvalidFileThrowException() throws APIException {
		this.parser.parse("d://fail.text");
	}
	@Test(expected = APIException.class)
	public void failureScenarionForParseLineShouldThrowException() throws APIException {
		this.parser.parseLine("81 (1,53.38) (88.62,€98) (6,46.34,€48)");
	}
	
	@Test
	public void successScenarioWithValidation() {
		String line = "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
		assertTrue(parser.isValidLine(line));
	}

	@Test
	public void scenarioWithNoItems() throws APIException {
		Package eachPackage = this.parser.parseLine("8 : (1,15.3,€34)");
		assertNotNull("Package should not be null", eachPackage);
		assertFalse("Item package should be empty", eachPackage.hasItems());
	}
	@Test
	public void successScenarioWithValidFile() throws APIException {
		this.parser.parse(this.testFile.getAbsolutePath());
	}
}
