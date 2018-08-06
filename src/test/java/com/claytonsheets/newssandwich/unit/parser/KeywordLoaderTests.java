package com.claytonsheets.newssandwich.unit.parser;

import java.util.Set;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.claytonsheets.newssandwich.parser.KeywordLoader;

public class KeywordLoaderTests {

	private final String filePath = "src\\main\\resources\\static\\positive.csv";

	private KeywordLoader loader;

	@BeforeMethod
	public void setup() {
		loader = new KeywordLoader();
	}

	@Test
	public void loadKeywordsFromCSVTest() {
		final SoftAssert softAssert = new SoftAssert();
		final Set<String> keywords = loader.loadWordsFromCSV(filePath);
		// assert that a few of the values from the CSV file are in the list
		softAssert.assertTrue(keywords.contains("happy"), "Should have loaded word in file");
		softAssert.assertEquals(keywords.contains("laugh"), "Should have loaded word in file");
		softAssert.assertAll();
	}
}
