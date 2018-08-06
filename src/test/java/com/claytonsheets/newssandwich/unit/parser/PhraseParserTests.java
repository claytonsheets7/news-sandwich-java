package com.claytonsheets.newssandwich.unit.parser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.claytonsheets.newssandwich.parser.PhraseParser;

public class PhraseParserTests {

	private PhraseParser parser;

	@BeforeMethod
	public void setup() {
		parser = new PhraseParser();
	}

	@Test
	public void parsePhraseTest() {
		final SoftAssert softAssert = new SoftAssert();
		final String phrase = "this is a phrase: I, want,, a clean// list of \\words. 4de3spite$ having $o many str&nge character$s^.";
		final Set<String> expected = new HashSet<>(Arrays.asList("this", "is", "a", "phrase", "I", "want", "a", "clean",
				"list", "of", "words", "despite", "having", "o", "many", "strnge", "characters"));
		final Set<String> actual = new HashSet<>(parser.extractWords(phrase));
		softAssert.assertEquals(actual.size(), expected.size(), "Should be the same size");
		for (final String actualWord : actual) {
			softAssert.assertTrue(expected.contains(actualWord), "Set should have contained the word " + actualWord);
		}
		softAssert.assertAll();
	}

}
