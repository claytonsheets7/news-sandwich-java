package com.claytonsheets.newssandwich.client;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;
import org.asynchttpclient.Response.ResponseBuilder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.claytonsheets.newssandwich.parser.KeywordLoader;

public class GoogleNewsClientTests {
	
	@Mock
	KeywordLoader mockLoader;
	
	@InjectMocks
	private GoogleNewsClient client;
	
	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void fetchSourceIDsTest() throws IOException, InterruptedException, ExecutionException {
		Set<String> csvValues = new HashSet<>();
		csvValues.add("blah");
	    org.mockito.Mockito.when(mockLoader.loadWordsFromCSV(org.mockito.Mockito.anyString())).thenReturn(csvValues);
	    Set<String> sourceIds = client.fetchSourceIDsFromCSV();

	    Assert.assertTrue(sourceIds.contains("blah"), "Should contain blah in set");
	}


}
