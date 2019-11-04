package data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;

import gamemechanics.creatures.CreatureType;
import gamemechanics.creatures.CreatureType.CreatureTypeI18N;

public class EbiCreatureTypeI18N extends CreatureTypeI18N {
	
	public static final I18NBundle i18n = I18NBundle.createBundle(Gdx.files.internal("creatures/types/i18n/bundle"));

	public String get(CreatureType type) {
		return type.id();
	}
	
}
