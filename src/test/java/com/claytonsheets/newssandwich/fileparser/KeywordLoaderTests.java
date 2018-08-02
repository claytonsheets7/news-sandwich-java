package com.claytonsheets.newssandwich.fileparser;

import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import fileparser.KeywordLoader;

public class KeywordLoaderTests {

	private final String filePath = "src\\main\\resources\\static\\positive.csv";

	private KeywordLoader loader;

	@BeforeMethod
	public void setup() {
		loader = new KeywordLoader();
	}

	@Test
	public void loadKeywordsFromCSVTest() {
		final Set<String> keywords = loader.loadWordsFromCSV(filePath);
		// assert that a few of the values from the CSV file are in the list
		Assert.assertTrue(keywords.contains("happy"), "Should have loaded word in file");
		Assert.assertEquals(keywords.contains("laugh"), "Should have loaded word in file");
	}
}
