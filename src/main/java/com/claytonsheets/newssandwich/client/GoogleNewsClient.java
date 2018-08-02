package com.claytonsheets.newssandwich.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.claytonsheets.newssandwich.dto.Article;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class acts as a client to make requests to the google news API. Methods
 * are provided for gathering all sources from google and grabbing the top
 * headlines from each source.
 * 
 * @author Jet Sunrise
 *
 */
public class GoogleNewsClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleNewsClient.class);

	private final String baseUrl = "https://newsapi.org/v2";
	private final String apiKey = "edd0276dc8344c2abaeb40a3f6fb439f";

	/**
	 * Makes a call to the google new API to fetch all available news sources.
	 * 
	 * @return a Response object containing sources
	 * @throws IOException
	 * @see org.asynchttpclient.Response
	 */
	private Response fetchSources() throws IOException {
		final String sourceURL = baseUrl + "/sources?apiKey=" + apiKey;
		AsyncHttpClient client = new DefaultAsyncHttpClient();
		Response response = null;
		try {
			response = client.prepareGet(sourceURL).execute().get();
		} catch (InterruptedException | ExecutionException e) {
			LOGGER.error(e.getMessage());
		} finally {
			// prevent memory leak
			client.close();
		}
		return response;
	}

	/**
	 * Fetches available source ids and places them into a Set.
	 * 
	 * @return a Set of source ids
	 * @throws IOException
	 */
	public Set<String> fetchSourceIDs() throws IOException {
		Set<String> sourceIds = new HashSet<>();
		final Response response = fetchSources();
		LOGGER.info(response.getResponseBody());
		final JsonNode resultNode = new ObjectMapper().readTree(response.toString()).get("sources");
		resultNode.forEach(source -> {
			if (source != null && source.get("id") != null) {
				sourceIds.add(source.get("id").asText());
			}
		});
		return sourceIds;
	}

	/**
	 * Makes a call to the google news API to gather the top headlines from a
	 * particular source. An AsyncHttpClient is used to execute the request.
	 * 
	 * @param source
	 *            a source id such as 'google-news'
	 * @param client
	 *            an AsyncHttpClient
	 * @return a Response object containing the top headlines of the provided source
	 * @throws IOException
	 * @see org.asynchttpclient.Response
	 */
	public Response fetchArticlesForSource(final String source, final AsyncHttpClient client) throws IOException {
		final String url = baseUrl + "/top-headlines?sources=" + source + "&apiKey=" + apiKey;
		Response response = null;
		try {
			response = client.prepareGet(url).execute().get();
		} catch (InterruptedException | ExecutionException e) {
			LOGGER.error(e.getMessage());
		} finally {
			// prevent memory leak
			client.close();
		}
		return response;
	}

	/**
	 * Gathers top headline articles from all available sources in the google news
	 * API. For instance, the top headlines from NYTimes, NPR, LATimes, etc. will be
	 * collected and placed into a List of type Article.
	 * 
	 * @return a List of type Article
	 * @throws IOException
	 */
	public List<Article> fetchArticlesForSources() throws IOException {
		final Set<String> sources = fetchSourceIDs();
		final AsyncHttpClient client = new DefaultAsyncHttpClient();
		List<Article> articles = new ArrayList<>();
		sources.forEach(source -> {
			Response response = null;
			try {
				response = fetchArticlesForSource(source, client);
				JsonNode result = new ObjectMapper().readTree(response.toString()).get("articles");
				result.forEach(articleNode -> {
					Article article = new Article();
					article.setTitle(articleNode.get("title").asText());
					article.setUrl(articleNode.get("url").asText());
					article.setDescription(articleNode.get("description").asText());
					article.setUrlToImage(articleNode.get("urlToImage").asText());
					articles.add(article);
				});
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			}
		});
		return articles;
	}

}
