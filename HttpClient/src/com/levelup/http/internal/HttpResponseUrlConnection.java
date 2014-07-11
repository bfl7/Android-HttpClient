package com.levelup.http.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import com.levelup.http.HttpResponse;

/**
 * Created by Steve Lhomme on 09/07/2014.
 */
public class HttpResponseUrlConnection implements HttpResponse {

	private final HttpURLConnection response;

	public HttpResponseUrlConnection(HttpURLConnection response) {
		if (null == response) throw new NullPointerException();
		this.response = response;
	}

	@Override
	public String getContentType() {
		return response.getContentType();
	}

	@Override
	public int getResponseCode() throws IOException {
		return response.getResponseCode();
	}

	@Override
	public Map<String, List<String>> getHeaderFields() {
		return response.getHeaderFields();
	}

	@Override
	public Map<String, List<String>> getRequestProperties() {
		return response.getRequestProperties();
	}

	@Override
	public String getHeaderField(String name) {
		return response.getHeaderField(name);
	}

	@Override
	public int getContentLength() {
		return response.getContentLength();
	}

	@Override
	public String getResponseMessage() throws IOException {
		return response.getResponseMessage();
	}

	@Override
	public String getContentEncoding() {
		return response.getContentEncoding();
	}

	@Override
	public void disconnect() {
		response.disconnect();
	}

	public InputStream getErrorStream() {
		return response.getErrorStream();
	}

	public InputStream getInputStream() throws IOException {
		return response.getInputStream();
	}
}