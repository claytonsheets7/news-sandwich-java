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
	private NewsFeedService newsFeedService;
	private CacheThread cacheThread;

	@Autowired
	public CacheService(final NewsFeedService newsFeedService) {
		this.newsFeedService = newsFeedService;
		cacheThread = new CacheThread();
		cacheThread.start();
	}

	public synchronized List<Article> fetchArticles() throws IOException {
		if (positiveArticles == null) {
			List<Article> articles = newsFeedService.fetchAndFilterArticles();
			articles.forEach(i -> positiveArticles.add(i));
		}
		return positiveArticles;
	}

	class CacheThread extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					final List<Article> articles = newsFeedService.fetchAndFilterArticles();
					for(Article article : articles) {
						positiveArticles.add(article);
					}
//					articles.forEach(i -> positiveArticles.add(i));
					// update once every 6 hours
					Thread.sleep(1000 * 60 * 60 * 6);
				} catch (InterruptedException | IOException e) {
					LOGGER.error(e.getMessage());
				}
			}
		}
	}

}
