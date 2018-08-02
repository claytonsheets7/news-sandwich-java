package com.claytonsheets.newssandwich.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claytonsheets.newssandwich.client.GoogleNewsClient;
import com.claytonsheets.newssandwich.dto.Article;

/**
 * This class contains method that makes calls to the google news API to fetch
 * articles. This class provides methods with the capability to filter out
 * articles based on a list of words provided via a .csv file.
 * 
 * @author Clayton Sheets
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
	 */
	public List<Article> fetchArticles() throws IOException {
		return googleNewsClient.fetchArticlesForSources();
	}

}
