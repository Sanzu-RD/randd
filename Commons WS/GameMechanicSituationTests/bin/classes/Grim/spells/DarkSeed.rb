require 'java'
require 'F:/Users/Souchy/Desktop/Robyn/Git/res/libs other/guava-18.0.jar'

$CLASSPATH << "GameMechanicSituationTests";

java_import Java::com.souchy.randd.situationtest.scripts.ScriptedSpell;
java_import Java::com.souchy.randd.situationtest.interfaces.IEffect;
java_import Java::com.souchy.randd.situationtest.interfaces.IEntity;
java_import Java::com.souchy.randd.situationtest.models.Effect;
java_import Java::com.souchy.randd.situationtest.eventshandlers.OnCastHandler;
java_import Java::com.souchy.randd.situationtest.events.CastSpellEvent;
java_import Java::com.souchy.randd.situationtest.models.effects.DamageEffect;

BEGIN {
  puts("DarkSeed imp")
}

class DarkSeed < ScriptedSpell

  #attr_accessor :onCast
  def initialize ()
    super("Dark Seed");
  end

  def onCast(event)
    event.target;

    targetCells = board.getCells(source, event.targetCell, effectMatrix(Effect1), EntityType.Character);

    characters = board.getTargets(event.targetCell, effectMatrix(Effect1), EntityType.Character);
    source.multipost(characters, c -> ApplyEffectEvent.new(DamageEffect1, Damage.Hit, source, c, Dark, sclDmg, flatDmg))

    # doit être targeté sur une cellule occupée par un ennemi

  end

end

DarkSeed.new
