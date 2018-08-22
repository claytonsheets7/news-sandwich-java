package com.claytonsheets.newssandwich.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claytonsheets.newssandwich.client.GoogleNewsClient;
import com.claytonsheets.newssandwich.dto.Article;
import com.claytonsheets.newssandwich.parser.KeywordLoader;
import com.claytonsheets.newssandwich.parser.PhraseParser;

/**
 * This class contains method that makes calls to the google news API to fetch
 * articles. This class provides methods with the capability to filter out
 * articles based on a list of words provided via a .csv file.
 * 
 * @author Clayton Sheets
 * @see GoogleNewsClient
 * @see Article
 */
@Service
public class NewsFeedService {

	private GoogleNewsClient googleNewsClient;

	@Autowired
	public NewsFeedService(final GoogleNewsClient googleNewsClient) {
		this.googleNewsClient = googleNewsClient;
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
	private List<Article> filterArticles(final List<Article> articles) throws IOException {
		final KeywordLoader loader = new KeywordLoader();
		final Set<String> positiveWords = loader.loadWordsFromCSV("src\\main\\resources\\static\\positive.csv");
		final Set<String> usuallyPositiveWords = loader
				.loadWordsFromCSV("src\\main\\resources\\static\\usuallyPositive.csv");
		final Set<String> negativeWords = loader.loadWordsFromCSV("src\\main\\resources\\static\\negative.csv");
		List<Article> filteredArticles = new ArrayList<>();
		for (final Article article : articles) {
			// split title into set of words
			final PhraseParser parser = new PhraseParser();
			Set<String> elements = parser.extractWords(article.getTitle());
			elements.addAll(parser.extractWords(article.getDescription()));
			// loop over words in title and check if it contains any of the keywords
			for (String element : elements) {
				// remove non alphabetic characters from string
				if (negativeWords.contains(element.toLowerCase())) {
					article.setWeight(0);
					break;
				}
				if (positiveWords.contains(element.toLowerCase())) {
					article.setWeight(article.getWeight() + 3);
				}
				if (usuallyPositiveWords.contains(element.toLowerCase())) {
					article.setWeight(article.getWeight() + 1);
				}
			}
			if (article.getWeight() > 0) {
				filteredArticles.add(article);
			}
		}
		return filteredArticles;
	}

	/**
	 * Gathers the articles that are perceived to be positive and then then sorts
	 * the articles with the most positive on top.
	 * 
	 * @return a list of articles
	 * @throws IOException
	 * @see Article
	 * @see KeywordLoader
	 */
	public List<Article> fetchArticles() throws IOException {
		List<Article> articles = filterArticles(googleNewsClient.fetchArticlesForAllSources());
		Collections.sort(articles);
		return articles;
	}

	/**
	 * Makes a get call to the google news API to fetch the top headlines.
	 * 
	 * @return a list of articles
	 * @throws IOException
	 * @see Article
	 */
	public List<Article> fetchHeadlines() throws IOException {
		return googleNewsClient.fetchGoogleNewsHeadlines();
	}

}
