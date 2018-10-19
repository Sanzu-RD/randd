package com.souchy.randd.commons.tealwaters.commons;

public class Namespace {
	
	
	protected StringBuilder namespace;
	private CharSequence delimiter;
	
	/**
	 * TODO Namespace.class, put the delimiters in a config
	 * @author Souchy
	 *
	 */
	public static class RedisNamespace extends Namespace {
		public RedisNamespace(String... name) {
			super(":", name);
			//super.delimiter = "'";
		}
	}
	public static class HttpNamespace extends Namespace {
		public HttpNamespace(String... name) {
			super("/", name);
			//super.delimiter = "/";
		}
	}

	public static class JavaPackageNamespace extends Namespace {
		public JavaPackageNamespace(String... name) {
			super(".", name);
			//super.delimiter = ".";
		}
	}
	
	
	private Namespace(CharSequence delimiter, String... name) {
		this.delimiter = delimiter;
		this.namespace = new StringBuilder(String.join(delimiter, name));
	}
	
	
	/**
	 * Permanently appends a delimiter and the requested keyword to the underlying stringbuilder.
	 * @param name
	 * @return
	 */
	public Namespace append(String keyword) {
		this.namespace.append(delimiter).append(keyword);
		return this;
	}
	
	/**
	 * Creates new a namespace containing the current namespace and the requested keyword.
	 * <b> Doesn't modify the udnerlying stringbuilder.
	 * @param name
	 * @return
	 */
	public Namespace in(String keyword) {
		return new Namespace(String.join(delimiter, namespace, keyword));
	}
	
	@Override
	public String toString() {
		return namespace.toString();
	}
	
}