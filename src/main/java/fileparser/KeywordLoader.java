package fileparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KeywordLoader {

	public List<String> loadWordsFromCSV(final String filePath) {
		List<String> keywords = new ArrayList<String>();
		File file = new File(filePath);
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			scanner.useDelimiter(",");
			while (scanner.hasNext()) {
				keywords.add(scanner.next());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
		return keywords;
	}

}
