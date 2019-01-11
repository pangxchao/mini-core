package com.mini.util.http.builder;

import com.mini.util.http.HttpRequest;
import com.mini.util.lang.FileUtil;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Iterator;

public final class PartBuilder extends BaseBuilder {
	private final MultipartBody.Builder builder;

	public PartBuilder(HttpRequest request) {
		super(request);
		builder = new MultipartBody.Builder();
		this.setType(MultipartBody.FORM);
	}

	public PartBuilder setType(MediaType type) {
		builder.setType(type);
		return this;
	}

	public PartBuilder addFormDataPart(String name, Object value) {
		builder.addFormDataPart(name, value == null ? "" : String.valueOf(value));
		return this;
	}

	public <T> PartBuilder addFormDataPartIterable(String name, Iterable<T> array) {
		for (T t : array) addFormDataPart(name, t);
		return this;
	}

	public <T> PartBuilder addFormDataPartIterator(String name, Iterator<T> array) {
		for (; array.hasNext();) addFormDataPart(name, array.next());
		return this;
	}

	public <T> PartBuilder addFormDataPartArray(String name, T[] array) {
		for (T t : array) addFormDataPart(name, t);
		return this;
	}

	public PartBuilder addFormDataPartArray(String name, long[] array) {
		for (long t : array) addFormDataPart(name, t);
		return this;
	}

	public PartBuilder addFormDataPartArray(String name, int[] array) {
		for (int t : array) addFormDataPart(name, t);
		return this;
	}

	public PartBuilder addFormDataPartArray(String name, short[] array) {
		for (short t : array) addFormDataPart(name, t);
		return this;
	}

	public PartBuilder addFormDataPartArray(String name, byte[] array) {
		for (byte t : array) addFormDataPart(name, t);
		return this;
	}

	public PartBuilder addFormDataPartArray(String name, double[] array) {
		for (double t : array) addFormDataPart(name, t);
		return this;
	}

	public PartBuilder addFormDataPartArray(String name, float[] array) {
		for (float t : array) addFormDataPart(name, t);
		return this;
	}

	public PartBuilder addFormDataPartArray(String name, boolean[] array) {
		for (boolean t : array) addFormDataPart(name, t);
		return this;
	}

	public PartBuilder addFormDataPartArray(String name, char[] array) {
		for (char t : array) addFormDataPart(name, t);
		return this;
	}

	public PartBuilder addFormDataPart(String name, String fileName, RequestBody body) {
		builder.addFormDataPart(name, fileName, body);
		return this;
	}

	public PartBuilder addPart(String name, File file) {
		return addFormDataPart(name, file.getName(), RequestBody.create(MediaType.parse(FileUtil.getMiniType(file)), file));
	}

	public PartBuilder addPart(String name, final File file, final long offset, final long contentLength) {
		builder.addPart(createPart(name, file, offset, contentLength));
		return this;
	}

	public PartBuilder addPart(MultipartBody.Part part) {
		builder.addPart(part);
		return this;
	}

	public PartBuilder addPart(RequestBody body) {
		builder.addPart(body);
		return this;
	}

	public PartBuilder addPart(Headers headers, RequestBody body) {
		builder.addPart(headers, body);
		return this;
	}

	public PartBuilder addPart(String name, String fileName, byte[] content) {
		builder.addPart(createPart(name, fileName, content));
		return this;
	}

	public PartBuilder addPart(String name, String fileName, final String contentType, final long contentLength, final InputStream content) {
		builder.addPart(createPart(name, fileName, contentType, contentLength, content));
		return this;
	}

	@Override
	public RequestBody getRequestBody() { return builder.build(); }

	public static MultipartBody.Part createPart(String name, File file, final long offset, final long contentLength) {
		return MultipartBody.Part.create(Headers.of("Content-Disposition", "form-data; name=\"" + name + "\"; filename=\"" + file.getName() + "\""),
				new RequestBody() {
					public MediaType contentType() {
						return MediaType.parse(FileUtil.getMiniType(file));
					}

					public long contentLength() {
						return contentLength;
					}

					public void writeTo(BufferedSink sink) throws IOException {
						try (RandomAccessFile accessFile = new RandomAccessFile(file, "r")) {
							if (offset > 0) accessFile.seek(offset);
							long totalLength = 0;
							byte[] buf = new byte[1024];
							for (int length; (length = accessFile.read(buf)) != -1;) {
								totalLength = totalLength + length;
								if (totalLength >= contentLength) {
									int l = (int) (totalLength - contentLength);
									sink.write(buf, 0, length - l);
									break;
								}
								sink.write(buf, 0, length);
							}
						}
					}
				});
	}

	public static MultipartBody.Part createPart(String name, String fileName, byte[] content) {
		return MultipartBody.Part.create(Headers.of("Content-Disposition", "form-data; name=\"" + name + "\"; filename=\"" + fileName + "\""),
				RequestBody.create(MediaType.parse("application/octet-stream"), content));
	}

	public static MultipartBody.Part createPart(String name, String fileName, final String contentType, final long contentLength, final InputStream content) {
		return MultipartBody.Part.create(Headers.of("Content-Disposition", "form-data; name=\"" + name + "\"; filename=\"" + fileName + "\""),
				new RequestBody() {
					public MediaType contentType() {
						return MediaType.parse(contentType);
					}

					public long contentLength() {
						return contentLength;
					}

					public void writeTo(BufferedSink sink) throws IOException {
						try (Source source = Okio.source(content)) {
							sink.writeAll(source);
						}
					}
				});
	}

}
