package gamemechanics.main;

import com.google.inject.AbstractModule;

import gamemechanics.statics.creatures.CreatureType;
import gamemechanics.statics.creatures.CreatureType.CreatureTypeI18N;

public class DefaultMechanicsModule extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(CreatureTypeI18N.class).to(CreatureTypeI18N.class);
		
		requestStaticInjection(CreatureTypeModel.class);
	}
	
	
}
