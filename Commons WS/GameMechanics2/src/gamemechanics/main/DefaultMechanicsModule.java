package gamemechanics.main;

import com.google.inject.AbstractModule;

import gamemechanics.creatures.CreatureType;
import gamemechanics.creatures.CreatureType.CreatureTypeI18N;

public class DefaultMechanicsModule extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(CreatureTypeI18N.class).to(CreatureTypeI18N.class);
		
		requestStaticInjection(CreatureType.class);
	}
	
	
}
