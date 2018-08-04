package com.claytonsheets.newssandwich.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claytonsheets.newssandwich.dto.Article;

import parser.KeywordLoader;
import parser.PhraseParser;

/**
 * This class contains method that makes calls to the google news API to fetch
 * articles. This class provides methods with the capability to filter out
 * articles based on a list of words provided via a .csv file.
 * 
 * @author Clayton Sheets
 * @see ArticleService
 * @see Article
 */
@Service
public class NewsFeedService {

	private ArticleService articleService;

	@Autowired
	public NewsFeedService(final ArticleService articleService) {
		this.articleService = articleService;
	}

	/**
	 * Makes get calls to the google news API to fetch all available articles and
	 * then filters out articles that do not contain any positively associated
	 * words. The top headlining articles for the day are inserted into the middle
	 * of the article list.
	 * 
	 * @return a list of articles
	 * @throws IOException
	 * @see Article
	 * @see KeywordLoader
	 */
	public List<Article> fetchAndFilterArticles() throws IOException {
		final List<Article> articles = articleService.fetchArticles();
		final KeywordLoader loader = new KeywordLoader();
		final Set<String> positiveWords = loader.loadWordsFromCSV("src\\main\\resources\\static\\positive.csv");
		final Set<String> negativeWords = loader.loadWordsFromCSV("src\\main\\resources\\static\\negative.csv");
		List<Article> filteredArticles = new ArrayList<>();
		for (final Article article : articles) {
			// split title into set of words
			final PhraseParser parser = new PhraseParser();
			Set<String> elements = parser.extractWords(article.getTitle());
			elements.addAll(parser.extractWords(article.getDescription()));
			boolean containedNegativeWord = false;
			// loop to avoid articles that contain negative words
			for (String element : elements) {
				if (negativeWords.contains(element.toLowerCase())) {
					containedNegativeWord = true;
					break;
				}
			}
			// loop over words in title and check if it contains any of the keywords
			if (!containedNegativeWord) {
				for (String element : elements) {
					// remove non alphabetic characters from string
					if (positiveWords.contains(element.toLowerCase())) {
						filteredArticles.add(article);
						break;
					}
				}
			}
		}
		return filteredArticles;
		// Stream<Article> filteredArticles = articles.stream().filter(article ->
		// Collections.)
	}

}
