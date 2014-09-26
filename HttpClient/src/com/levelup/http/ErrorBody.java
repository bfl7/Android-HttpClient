package com.levelup.http;

/**
 * Object describing the error data returned by the HTTP server
 * @author Created by robUx4 on 19/08/2014.
 */
public class ErrorBody {
	public final Object errorContent;
	public final String message;

	public ErrorBody(Object errorContent) {
		this(null, errorContent);
	}

	public ErrorBody(String message, Object errorContent) {
		this.message = message;
		this.errorContent = errorContent;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return getMessage() + " errorData:"+errorContent;
	}
}
