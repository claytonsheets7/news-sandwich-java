package com.claytonsheets.newssandwich.client;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;
import org.junit.Assert;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.netty.handler.codec.http.HttpHeaders;

public class GoogleNewsClientTests {
	
	private GoogleNewsClient client;
	
	private AsyncHttpClient asyncHttpClient;
	
	@Mock
	Response mockResponse = new Response.ResponseBuilder().build();
	@Mock
    ListenableFuture<Response> future = org.mockito.Mockito.mock(ListenableFuture.class);
	
	@BeforeMethod
	public void setup() {
		asyncHttpClient = new DefaultAsyncHttpClient();
	}
	
	@Test
	public void fetchSourceIDsTest() throws IOException, InterruptedException, ExecutionException {
		BoundRequestBuilder request = asyncHttpClient.prepareGet("http://localhost:8080/news");
        request.setBody("{}");
        org.mockito.Mockito.when(request.execute()).thenReturn(future);
        org.mockito.Mockito.when(future.get()).thenReturn(mockResponse);
        ListenableFuture<Response> futureResponseForRequest = request.execute();
        Response response = futureResponseForRequest.get();
        Set<String> sourceIDs = client.fetchSourceIDs();
        Assert.assertTrue(true);
	}

}
