package com.claytonsheets.newssandwich.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claytonsheets.newssandwich.dto.Article;

/**
 * This class is a cache service to increase the speed of responses and limits
 * the number of calls that are made to google as exceeding 1000 requests a day
 * will cause rate limiting.
 * 
 * @author Clayton Sheets
 * @see NewsFeedService
 * @see Article
 */
@Service
public class CacheService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CacheService.class);

	private List<Article> positiveArticles = new ArrayList<>();
	private List<Article> headlineArticles = new ArrayList<>();
	private NewsFeedService newsFeedService;
	private CacheThread cacheThread;

	@Autowired
	public CacheService(final NewsFeedService newsFeedService) {
		this.newsFeedService = newsFeedService;
		cacheThread = new CacheThread();
		cacheThread.start();
	}

	/**
	 * Gathers the top headlines and positively weighted articles to form a list
	 * that starts with positive articles, has normal headlines in the middle, and
	 * ends with positive articles.
	 * 
	 * @return a List of type Article
	 * @throws IOException
	 */
	public synchronized List<Article> fetchArticles() throws IOException {
		List<Article> articles = new ArrayList<>();
		if (positiveArticles.size() == 0) {
			positiveArticles = newsFeedService.fetchArticles();
		}
		if (headlineArticles.size() == 0) {
			headlineArticles = newsFeedService.fetchHeadlines();
		}
		articles.addAll(positiveArticles.subList(0, 3));
		articles.addAll(headlineArticles);
		articles.addAll(positiveArticles.subList(3, 6));
		return articles;
	}

	/**
	 * This class makes the initial calls to grab the top headlines and positive
	 * articles.
	 * 
	 * @author Clayton Sheets
	 *
	 */
	class CacheThread extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					positiveArticles = newsFeedService.fetchArticles();
					headlineArticles = newsFeedService.fetchHeadlines();
					// update once every 12 hours
					Thread.sleep(1000 * 60 * 60 * 12);
				} catch (InterruptedException | IOException e) {
					LOGGER.error(e.getMessage());
				}
			}
		}
	}

}
