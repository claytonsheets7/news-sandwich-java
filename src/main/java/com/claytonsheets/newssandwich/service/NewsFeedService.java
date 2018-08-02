package com.claytonsheets.newssandwich.service;

import java.util.List;
import java.util.Set;

import com.claytonsheets.newssandwich.dto.Article;

/**
 * This class contains method that makes calls to the google news API to fetch
 * articles. This class provides methods with the capability to filter out
 * articles based on a list of words provided via a .csv file.
 * 
 * @author Clayton Sheets
 * @see Article
 */
public class NewsFeedService {

	public NewsFeedService() {
	}

	/**
	 * Makes get calls to the google news API to fetch all available articles and
	 * then filters out articles that do not contain any positively associated
	 * words. The top headlining articles for the day are inserted into the middle
	 * of the article list.
	 * 
	 * @return a list of articles
	 * @see Article
	 */
	public List<Article> fetchArticles() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 * Gathers all available article sources from the google news API.
	 */
	private Set<String> fetchSources() {
		
	}

}
