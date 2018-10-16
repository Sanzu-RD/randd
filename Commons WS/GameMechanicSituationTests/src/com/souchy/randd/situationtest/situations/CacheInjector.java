package com.souchy.randd.situationtest.situations;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.inject.Binding;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.MembersInjector;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeConverterBinding;
import com.souchy.randd.situationtest.modules.CacheModule;

public class CacheInjector implements Injector {

	/**
	 * Static ? or not ? 
	 */
    private final Injector injector = Guice.createInjector(new CacheModule());
    
	public CacheInjector() {
		
	}

	@Override
	public void injectMembers(Object instance) {
		injector.injectMembers(instance);
	}


	@Override
	public <T> MembersInjector<T> getMembersInjector(TypeLiteral<T> typeLiteral) {
		return injector.getMembersInjector(typeLiteral);
	}


	public <T> MembersInjector<T> getMembersInjector(Class<T> type) {
		return injector.getMembersInjector(type);
	}


	public Map<Key<?>, Binding<?>> getBindings() {
		return injector.getBindings();
	}


	public Map<Key<?>, Binding<?>> getAllBindings() {
		return injector.getAllBindings();
	}


	public <T> Binding<T> getBinding(Key<T> key) {
		return injector.getBinding(key);
	}


	public <T> Binding<T> getBinding(Class<T> type) {
		return injector.getBinding(type);
	}


	public <T> Binding<T> getExistingBinding(Key<T> key) {
		return injector.getExistingBinding(key);
	}


	public <T> List<Binding<T>> findBindingsByType(TypeLiteral<T> type) {
		return injector.findBindingsByType(type);
	}


	public <T> Provider<T> getProvider(Key<T> key) {
		return injector.getProvider(key);
	}


	public <T> Provider<T> getProvider(Class<T> type) {
		return injector.getProvider(type);
	}


	public <T> T getInstance(Key<T> key) {
		return injector.getInstance(key);
	}


	public <T> T getInstance(Class<T> type) {
		return injector.getInstance(type);
	}


	public Injector getParent() {
		return injector.getParent();
	}


	public Injector createChildInjector(Iterable<? extends Module> modules) {
		return injector.createChildInjector(modules);
	}


	public Injector createChildInjector(Module... modules) {
		return injector.createChildInjector(modules);
	}


	public Map<Class<? extends Annotation>, Scope> getScopeBindings() {
		return injector.getScopeBindings();
	}


	public Set<TypeConverterBinding> getTypeConverterBindings() {
		return injector.getTypeConverterBindings();
	}


	
	
}
