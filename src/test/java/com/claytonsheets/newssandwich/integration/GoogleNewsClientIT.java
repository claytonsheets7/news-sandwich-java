package com.claytonsheets.newssandwich.integration;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.claytonsheets.newssandwich.client.GoogleNewsClient;
import com.claytonsheets.newssandwich.dto.Article;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GoogleNewsClientIT {

	private GoogleNewsClient client;
	private SoftAssert softAssert;

	@BeforeMethod
	public void setup() {
		client = new GoogleNewsClient();
		softAssert = new SoftAssert();
	}

	@Test
	public void fetchSourceIDsTest() throws IOException {
		final Set<String> sourceIDs = client.fetchSourceIDs();
		sourceIDs.forEach(i -> System.out.println(i));
		// check for a well known source ID
		softAssert.assertNotNull(sourceIDs.contains("national-geographic"), "Should contain national-geographic source ID");
		softAssert.assertAll();
	}

	@Test
	public void fetchArticlesForSourceID() throws IOException {
		final AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
		final Response response = client.fetchArticlesForSource("national-geographic", asyncHttpClient);
		final JsonNode responseNode = new ObjectMapper().readTree(response.getResponseBody()).get("articles").get(0);
		asyncHttpClient.close();
		// make sure desired source fields for Article are not null
		softAssert.assertNotNull(responseNode.get("title"), "title should not be null");
		softAssert.assertNotNull(responseNode.get("description"), "description should not be null");
		softAssert.assertNotNull(responseNode.get("url"), "url should not be null");
		softAssert.assertNotNull(responseNode.get("urlToImage"), "urlToImage should not be null");
		softAssert.assertAll();
	}

	@Test
	public void fetchArticlesFromSources() throws IOException {
		final List<Article> articles = client.fetchArticlesForAllSources();
		for(Article article : articles) {
			softAssert.assertNotNull(article.getTitle(), "title should not be null");
			softAssert.assertNotNull(article.getDescription(), "description should not be null");
			softAssert.assertNotNull(article.getUrl(), "url should not be null");
			softAssert.assertNotNull(article.getUrlToImage(), "urlToImage should not be null");
		}
		softAssert.assertAll();
	}

}
