package com.souchy.randd.commons.tealwaters.commons;

public abstract class Namespace {
	
	
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
		}
		@Override
		protected RedisNamespace copy() {
			return new RedisNamespace();
		}
	}
	public static class HttpNamespace extends Namespace {
		public HttpNamespace(String... name) {
			super("/", name);
		}
		@Override
		public HttpNamespace in(String keyword) {
			return new HttpNamespace(toString(), keyword);
		}
		@Override
		protected HttpNamespace copy() {
			return new HttpNamespace();
		}
	}
	public static class JavaPackageNamespace extends Namespace {
		public JavaPackageNamespace(String... name) {
			super(".", name);
		}
		@Override
		protected JavaPackageNamespace copy() {
			return new JavaPackageNamespace();
		}
	}
	public static class MongoNamespace extends Namespace {
		public final String db;
		public final String collection;
		//public MongoNamespace(String fullname) {
		//	super(".", fullname);
		//}
		public MongoNamespace(String db, String collection) {
			super(".", db, collection);
			this.db = db;
			this.collection = collection;
		}
		@Override
		protected MongoNamespace copy() {
			return new MongoNamespace(db, collection);
		}
	}
	public static class I18NNamespace extends Namespace {
		public I18NNamespace(String... name) {
			super(".", name);
		}
		@Override
		public I18NNamespace in(String keyword) {
			return new I18NNamespace(toString(), keyword);
		}
		@Override
		protected I18NNamespace copy() {
			return new I18NNamespace();
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
	 * <b> Doesn't modify the underlying stringbuilder.
	 * @param name
	 * @return
	 */
	public Namespace in(String keyword) {
		return copy().append(toString()).append(keyword);
		//return new Namespace(toString()).append(keyword); //String.join(delimiter, toString(), keyword));
	}
	
	/**
	 * Returns a string from this namespace + its delimiter + added keyword
	 */
	public String inString(String keyword) {
		return this.toString() + delimiter + keyword;
	}
	
	protected abstract Namespace copy();
	

	public CharSequence delimiter() {
		return delimiter;
	}
	
	@Override
	public String toString() {
		return namespace.toString();
	}
	
}
