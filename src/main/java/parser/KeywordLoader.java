package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * This class provides methods to load words from various file types into a
 * list. For instance, a CSV file can be loaded with the loadWordsFromCSV
 * method.
 * 
 * @author Clayton Sheets
 *
 */
public class KeywordLoader {

	/**
	 * Loads a file of values that can be split by a common delimiter and returns a
	 * List of the given values.
	 * 
	 * @param filePath  the path to the file that will be loaded
	 * @param delimiter a value used to split text into multiple chunks. I.E., ","
	 *                  for CSV files or " " for words separated by spaces
	 * @return a List<String> containing the separated values
	 */
	public Set<String> loadWords(final String filePath, final String delimiter) {
		Set<String> keywords = new HashSet<>();
		File file = new File(filePath);
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			scanner.useDelimiter(",");
			while (scanner.hasNext()) {
				keywords.add(scanner.next().toLowerCase());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
		return keywords;
	}

	/**
	 * Loads a CSV file and returns a List of the values.
	 * 
	 * @param filePath the path to the file that will be loaded
	 * @return a List<String> containing the separated values
	 */
	public Set<String> loadWordsFromCSV(final String filePath) {
		return loadWords(filePath, ",");
	}

}
