package com.claytonsheets.newssandwich.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claytonsheets.newssandwich.client.GoogleNewsClient;
import com.claytonsheets.newssandwich.dto.Article;

/**
 * This class contains method that makes calls to the google news API to fetch
 * articles.
 * 
 * @author Clayton Sheets
 * @see Article
 */
@Service
public class ArticleService {

	private GoogleNewsClient googleNewsClient;

	@Autowired
	public ArticleService(final GoogleNewsClient googleNewsClient) {
		this.googleNewsClient = googleNewsClient;
	}

	/**
	 * Makes get calls to the google news API to fetch all available headlining
	 * articles from all sources.
	 * 
	 * @return a list of articles
	 * @throws IOException
	 * @see Article
	 */
	public List<Article> fetchArticles() throws IOException {
		// keep this number low to avoid being rate limited
		final int requests = 5;
		return googleNewsClient.fetchArticlesForAllSources(requests);
	}

}
