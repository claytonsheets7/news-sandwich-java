package parser;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Clayton Sheets
 *
 */
public class PhraseParser {

	// takes in a string and extracts words into a list, removing all non-alpha
	// numeric characters
	public Set<String> extractWords(final String phrase) {
		Set<String> words = new HashSet<>();
		final char[] phraseArr = phrase.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < phraseArr.length; i++) {
			if (Character.isWhitespace(phraseArr[i]) || i == phrase.length() - 1) {
				words.add(sb.toString().toLowerCase());
				sb = new StringBuilder();
			} else if (Character.isAlphabetic(phraseArr[i])) {
				sb.append(phraseArr[i]);
			}
		}
		return words;
	}

}
