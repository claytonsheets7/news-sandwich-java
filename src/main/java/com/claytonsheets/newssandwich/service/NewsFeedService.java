package com.claytonsheets.newssandwich.service;

import java.util.List;

import com.claytonsheets.newssandwich.dto.Article;

public class NewsFeedService {

	public NewsFeedService() {
	}

	/*
	 * Makes get calls to the google news API to fetch all available articles and
	 * then filters out articles that do not contain any positively associated
	 * words. The top headlining articles for the day are inserted into the middle
	 * of the article list.
	 * 
	 * @return a list of articles
	 * 
	 * @see Article
	 */
	public List<Article> fetchArticles() {
		// TODO Auto-generated method stub
		return null;
	}

}
