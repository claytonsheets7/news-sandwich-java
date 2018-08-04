package com.claytonsheets.newssandwich.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.claytonsheets.newssandwich.dto.Article;
import com.claytonsheets.newssandwich.service.NewsFeedService;

/**
 * This class acts as the central 'News Sandwich' controller. As such, it
 * contains a set of request mappings to retrieve lists of articles.
 * 
 * <p>
 * For example, to retrieve a article list sandwich, call the '/news' endpoint.
 * 
 * @author Clayton Sheets
 * @see NewsFeedService
 * @see Article
 *
 */
@RestController
public class NewsFeedController {

	private NewsFeedService newsFeedService;

	@Autowired
	public NewsFeedController(NewsFeedService newsFeedService) {
		this.newsFeedService = newsFeedService;
	}

	/**
	 * Returns a list of articles with positively associated articles at the
	 * beginning and end. The middle of the list will contain the standard top
	 * headlines for the day.
	 * 
	 * @return a list of articles
	 * @throws IOException
	 * @see Article
	 */
	@RequestMapping("/news")
	List<Article> news() throws IOException {
		return newsFeedService.fetchAndFilterArticles();
	}

}
