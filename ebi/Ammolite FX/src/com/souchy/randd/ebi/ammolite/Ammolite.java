package com.souchy.randd.ebi.ammolite;

import java.util.HashMap;
import java.util.Map;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.models.CreatureModel;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.damage.DmgEvent;
import com.souchy.randd.commons.diamond.statusevents.damage.DmgEvent.OnDmgHandler;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent.OnCastSpellHandler;
import com.souchy.randd.commons.tealwaters.io.files.ClassDiscoverer.DefaultClassDiscoverer;

import particles.EffekseerManager;
import particles.ParticleEffekseer;

/**
 * EbiShoal Library to play Visual and Audio FX
 * 
 * @author Souchy
 * @date 11 janv. 2021
 */
public class Ammolite implements OnCastSpellHandler {
	
	public static EffekseerManager manager;
	public static Fight fight;
	public static Map<Integer, SpellFXPlayer> spellsFX = new HashMap<>();
	
	public Ammolite(Fight f, EffekseerManager manager) {
		Ammolite.fight = f;
		Ammolite.manager = manager;
		
		f.statusbus.reactors.register(this);

		var spellFxPlayers = new DefaultClassDiscoverer<SpellFXPlayer>(SpellFXPlayer.class).explore("com.souchy.randd.ebi.ammolite.spells");
		spellFxPlayers.forEach(p -> {
			try {
				var fx = p.getDeclaredConstructor().newInstance();
				spellsFX.put(fx.modelid(), fx);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	public static ParticleEffekseer particle() {
		return new ParticleEffekseer(Ammolite.manager);
	}
	
	
	@Override
	public void onCastSpell(CastSpellEvent event) {
		if(spellsFX.containsKey(event.spell.modelid()))
			spellsFX.get(event.spell.modelid()).play(event);
	}

	
//	@Subscribe
//	public void handleEffectEvent(Event event) {
//		if(!check(event)) return;
//		var msg =  event.testMessage();
//		if(msg == "") return;
//		//addMsg(new ICM("effect", "fight", msg));
//	}

	
}
