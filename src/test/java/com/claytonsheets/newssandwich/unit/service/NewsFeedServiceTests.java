package com.claytonsheets.newssandwich.unit.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.claytonsheets.newssandwich.client.GoogleNewsClient;
import com.claytonsheets.newssandwich.dto.Article;
import com.claytonsheets.newssandwich.service.NewsFeedService;

public class NewsFeedServiceTests {
	
	private SoftAssert softAssert;
	
	@InjectMocks
	private NewsFeedService service;
	
	private Article article;
	
	@Mock
	private GoogleNewsClient client;

	@BeforeMethod
	public void setup() {
		softAssert = new SoftAssert();
		MockitoAnnotations.initMocks(this);
		article = new Article();
		article.setTitle("title");
		article.setDescription("desc");
		article.setSourceID("source-id");
		article.setUrl("http://someurl.cool");
		article.setUrlToImage("http://someurltoimage.cool");
	}
	
	@Test
	public void fetchHeadlinesTest() throws IOException {
		final List<Article> expected = Arrays.asList(article);
		when(client.fetchGoogleNewsHeadlines()).thenReturn(expected);
		final List<Article> actual = service.fetchHeadlines();
		
		verify(client).fetchGoogleNewsHeadlines();
		softAssert.assertEquals(actual.size(), expected.size(), "Should be the same size lists");
		softAssert.assertEquals(actual.get(0), expected.get(0), "Should having matching field values");
		softAssert.assertAll();
	}
	
	@Test
	public void filterPositiveTest() throws IOException {
		article.setTitle("amazing happy great");
		final List<Article> expected = Arrays.asList(article);
		when(client.fetchArticlesForAllSources()).thenReturn(expected);
		final List<Article> actual = service.fetchArticles();
		
		verify(client).fetchArticlesForAllSources();
		softAssert.assertEquals(actual.size(), expected.size(), "Should be the same size lists");
		softAssert.assertEquals(actual.get(0), expected.get(0), "Should having matching field values");
		softAssert.assertTrue(actual.get(0).getWeight() > 0, "Weight should have been added to article");
		softAssert.assertAll();
	}
	
	@Test
	public void filterNegativeTest() throws IOException {
		article.setTitle("death horrible brutal");
		final List<Article> expected = Arrays.asList(article);
		when(client.fetchArticlesForAllSources()).thenReturn(expected);
		final List<Article> actual = service.fetchArticles();
		
		verify(client).fetchArticlesForAllSources();
		softAssert.assertEquals(actual.size(), 0, "Article should be filtered out");
		softAssert.assertAll();
	}
}
