package com.mini.util.http.builder;

import com.mini.util.http.HttpRequest;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import java.util.Iterator;

public final class FormBuilder extends BaseBuilder {
	private final FormBody.Builder builder;

	public FormBuilder(HttpRequest request) {
		super(request);
		builder = new FormBody.Builder();
	}

	public FormBuilder add(String name, Object value) {
		builder.add(name, value == null ? "" : String.valueOf(value));
		return this;
	}

	public FormBuilder addEncoded(String name, Object value) {
		builder.addEncoded(name, value == null ? "" : String.valueOf(value));
		return this;
	}

	public <T> FormBuilder addIterable(String name, Iterable<T> array) {
		for (T t : array)
			add(name, t);
		return this;
	}

	public <T> FormBuilder addIterableEncoded(String name, Iterable<T> array) {
		for (T t : array)
			addEncoded(name, t);
		return this;
	}

	public <T> FormBuilder addIterator(String name, Iterator<T> array) {
		for (; array.hasNext();)
			add(name, array.next());
		return this;
	}

	public <T> FormBuilder addIteratorEncoded(String name, Iterator<T> array) {
		for (; array.hasNext();)
			addEncoded(name, array.next());
		return this;
	}

	public <T> FormBuilder addArray(String name, T[] array) {
		for (T t : array)
			add(name, t);
		return this;
	}

	public FormBuilder addArray(String name, long[] array) {
		for (long t : array)
			add(name, t);
		return this;
	}

	public FormBuilder addArray(String name, int[] array) {
		for (int t : array)
			add(name, t);
		return this;
	}

	public FormBuilder addArray(String name, short[] array) {
		for (short t : array)
			add(name, t);
		return this;
	}

	public FormBuilder addArray(String name, byte[] array) {
		for (byte t : array)
			add(name, t);
		return this;
	}

	public FormBuilder addArray(String name, double[] array) {
		for (double t : array)
			add(name, t);
		return this;
	}

	public FormBuilder addArray(String name, float[] array) {
		for (float t : array)
			add(name, t);
		return this;
	}

	public FormBuilder addArray(String name, boolean[] array) {
		for (boolean t : array)
			add(name, t);
		return this;
	}

	public FormBuilder addArray(String name, char[] array) {
		for (char t : array)
			add(name, t);
		return this;
	}

	public <T> FormBuilder addArrayEncode(String name, T[] array) {
		for (T t : array)
			addEncoded(name, t);
		return this;
	}

	public FormBuilder addArrayEncode(String name, long[] array) {
		for (long t : array)
			addEncoded(name, t);
		return this;
	}

	public FormBuilder addArrayEncode(String name, int[] array) {
		for (int t : array)
			addEncoded(name, t);
		return this;
	}

	public FormBuilder addArrayEncode(String name, short[] array) {
		for (short t : array)
			addEncoded(name, t);
		return this;
	}

	public FormBuilder addArrayEncode(String name, byte[] array) {
		for (byte t : array)
			addEncoded(name, t);
		return this;
	}

	public FormBuilder addArrayEncode(String name, double[] array) {
		for (double t : array)
			addEncoded(name, t);
		return this;
	}

	public FormBuilder addArrayEncode(String name, float[] array) {
		for (float t : array)
			addEncoded(name, t);
		return this;
	}

	public FormBuilder addArrayEncode(String name, boolean[] array) {
		for (boolean t : array)
			addEncoded(name, t);
		return this;
	}

	public FormBuilder addArrayEncode(String name, char[] array) {
		for (char t : array)
			addEncoded(name, t);
		return this;
	}

	@Override
	public RequestBody getRequestBody() {
		return builder.build();
	}
}
