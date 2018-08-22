package com.claytonsheets.newssandwich.unit.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.claytonsheets.newssandwich.dto.Article;
import com.claytonsheets.newssandwich.service.CacheService;
import com.claytonsheets.newssandwich.service.NewsFeedService;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CacheServiceTests {
	
	@InjectMocks
	private CacheService cacheService;
	
	@Mock
	private NewsFeedService newsFeedService;
	
	private SoftAssert softAssert;
	
	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);
		softAssert = new SoftAssert();
	}
	
	@Test
	public void fetchArticles() throws IOException {
		Article article = new Article();
		article.setTitle("title");
		article.setDescription("desc");
		article.setSourceID("source-id");
		article.setUrl("http://someurl.cool");
		article.setUrlToImage("http://someurltoimage.cool");
		List<Article> expected = Arrays.asList(article, article, article, article, article, article, article, article, article, article);
		when(newsFeedService.fetchHeadlines()).thenReturn(expected);
		when(newsFeedService.fetchArticles()).thenReturn(expected);
		final List<Article> actual = cacheService.fetchArticles();
		
		softAssert.assertEquals(actual.get(0), expected.get(0), "Articles should match");
		softAssert.assertAll();
	}

}
