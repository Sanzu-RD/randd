package com.souchy.randd.situationtest.properties;


import com.google.inject.Inject;
import com.souchy.randd.commons.cache.api.Cache;
import com.souchy.randd.situationtest.properties.types.Elements;
import com.souchy.randd.situationtest.properties.types.StatProperties;
import com.souchy.randd.situationtest.situations.CacheInjector;


/**
 * initialize via JSON and inject via Guice
 * 
 * 
 * TODO stats : what if, � chq fois qu'on fait un .get(), on fait un foreach sur tous les items et on additionne ceux qui donnent des stats ?
 * 	aussi, un status dont la description qui donne +60% vita p.ex, pourrait �tre cod� en hp fixe, ex sur un gars � 1000hp*60%, on donne un +600hp fixe, c'est simple 
 * 
 */
public class Stats {
	
	/**
	 * Propri�t�s de base (resources (hp, mana, pm, shields)
	 */
    @Inject
	private Cache<StatProperties, StatProperty> properties;

	/**
	 * Affinit�s aux �l�ments (dmg et res, scl et flat)
	 */
    @Inject
	private Cache<Elements, ElementBundle> elementAffinities;
	
	
	/**
	 * Use Guice to inject cache implementations
	 */
	public Stats(CacheInjector injector) {
        injector.injectMembers(this);
		for(StatProperties type : StatProperties.values()){
			properties.set(type, new StatProperty(type, 0));
		}
		for(Elements type : Elements.values()) {
			elementAffinities.set(type, new ElementBundle(type, 0, 0, 0, 0));
		}
	}
	
	/**
	 * @param type : propri�t� � inspecter
	 * @return Stat voulue 
	 */
	public StatProperty get(StatProperties type) {
		return properties.get(type);
	}
	
	public void setElementBundle(ElementBundle bundle) {
		elementAffinities.set(bundle.scl.element, bundle);
	}
	
	/**
	 * @param type : element � inspecter
	 * @return Dommages scaling associ�s � cet �l�ment
	 */
	public ElementValue scl(Elements type) {
		return elementAffinities.get(type).scl;
	}
	/**
	 * @param type : element � inspecter
	 * @return Dommages flat associ�s � cet �l�ment
	 */
	public ElementValue flat(Elements type) {
		return elementAffinities.get(type).flat;
	}
	/**
	 * @param type : element � inspecter
	 * @return R�sistances scaling associ�se � cet �l�ment
	 */
	public ElementValue resScl(Elements type) {
		return elementAffinities.get(type).resScl;
	}
	/**
	 * @param type : element � inspecter
	 * @return R�sistances flat associ�es � cet �l�ment
	 */
	public ElementValue resFlat(Elements type) {
		return elementAffinities.get(type).resFlat;
	}
	

}
