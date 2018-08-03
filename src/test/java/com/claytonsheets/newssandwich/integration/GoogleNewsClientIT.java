package com.claytonsheets.newssandwich.integration;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.claytonsheets.newssandwich.client.GoogleNewsClient;
import com.claytonsheets.newssandwich.dto.Article;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GoogleNewsClientIT {

	private GoogleNewsClient client;

	@BeforeMethod
	public void setup() {
		client = new GoogleNewsClient();
	}

	@Test
	public void fetchSourceIDsTest() throws IOException {
		final Set<String> sourceIDs = client.fetchSourceIDs();
		// check for a well known source ID
		Assert.assertNotNull(sourceIDs.contains("national-geographic"), "Should contain national-geographic source ID");
	}

	@Test
	public void fetchArticlesForSourceID() throws IOException {
		final AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
		final Response response = client.fetchArticlesForSource("national-geographic", asyncHttpClient);
		final JsonNode responseNode = new ObjectMapper().readTree(response.getResponseBody()).get("articles").get(0);
		asyncHttpClient.close();
		// make sure desired source fields for Article are not null
		Assert.assertNotNull(responseNode.get("title"), "title should not be null");
		Assert.assertNotNull(responseNode.get("description"), "description should not be null");
		Assert.assertNotNull(responseNode.get("url"), "url should not be null");
		Assert.assertNotNull(responseNode.get("urlToImage"), "urlToImage should not be null");
	}

	@Test
	public void fetchArticlesFromSources() throws IOException {
		// it is very important to keep this number low as developer accounts are
		// limited to 1000 requests per day
		final int requests = 2;
		final List<Article> articles = client.fetchArticlesForAllSources(requests);
		for(Article article : articles) {
			Assert.assertNotNull(article.getTitle(), "title should not be null");
			Assert.assertNotNull(article.getDescription(), "description should not be null");
			Assert.assertNotNull(article.getUrl(), "url should not be null");
			Assert.assertNotNull(article.getUrlToImage(), "urlToImage should not be null");
		}
	}

}
