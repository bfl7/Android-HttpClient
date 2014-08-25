package com.levelup.http.gson;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.levelup.http.DataErrorException;
import com.levelup.http.ImmutableHttpRequest;
import com.levelup.http.ParserException;
import com.levelup.http.Util;
import com.levelup.http.parser.DataTransform;
import com.levelup.http.parser.DataTransformInputStreamString;

/**
 * Parse the network data using Gson to type {@link T}
 * <p/>
 * Created by robUx4 on 8/1/2014.
 */
public class DataTransformViaGson<T> implements DataTransform<InputStream,T> {
	private static Gson defaultGsonParser = new GsonBuilder().create();

	private boolean debugData;

	final Gson gson;
	final Type type;
	final TypeToken typeToken;

	public DataTransformViaGson(Type type) {
		this(defaultGsonParser, type);
	}

	public DataTransformViaGson(Gson gson, Type type) {
		this(gson, type, null);
	}

	public DataTransformViaGson(TypeToken<T> typeToken) {
		this(defaultGsonParser, typeToken);
	}

	public DataTransformViaGson(Gson gson, TypeToken<T> typeToken) {
		this(gson, typeToken.getType(), typeToken);
	}

	private DataTransformViaGson(Gson gson, Type type, TypeToken<T> typeToken) {
		this.gson = gson;
		this.type = type;
		this.typeToken = typeToken;
	}

	/**
	 * Enable debugging of bogus data by providing the data in the {@link com.levelup.http.ParserException} message
	 * @param enable
	 */
	public DataTransformViaGson<T> enableDebugData(boolean enable) {
		debugData = enable;
		return this;
	}

	public boolean debugEnabled() {
		return debugData;
	}

	@Override
	public T transform(InputStream inputStream, ImmutableHttpRequest request) throws IOException, ParserException, DataErrorException {
		Charset readCharset = Util.getInputCharsetOrUtf8(request.getHttpResponse());

		String dataString = null;
		if (debugData) {
			dataString = DataTransformInputStreamString.INSTANCE.transform(inputStream, request);
			inputStream = new ByteArrayInputStream(dataString.getBytes());
		}

		InputStreamReader ir = new InputStreamReader(inputStream, readCharset);
		try {
			JsonReader reader = new JsonReader(ir);
			return gson.fromJson(reader, type);
		} catch (JsonParseException e) {
			throw new ParserException("Bad data for GSON", e, dataString);
		} finally {
			try {
				ir.close();
			} catch (IOException ignored) {
			}
		}
	}

	public Gson getGsonHandler() {
		return gson;
	}

	public Type getGsonOutputType() {
		return type;
	}

	public TypeToken getGsonOutputTypeToken() {
		return typeToken;
	}
}
