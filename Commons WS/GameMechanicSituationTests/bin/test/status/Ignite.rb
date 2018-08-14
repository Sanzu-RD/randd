



require 'java'
require 'F:/Users/Souchy/Desktop/Robyn/Git/res/libs other/guava-18.0.jar'

$CLASSPATH << "GameMechanicSituationTests";

java_import Java::com.souchy.randd.situationtest.scripts.ScriptedSpell;
java_import Java::com.souchy.randd.situationtest.scripts.Status;
java_import Java::com.souchy.randd.situationtest.interfaces.IEffect;
java_import Java::com.souchy.randd.situationtest.interfaces.IEntity;
java_import Java::com.souchy.randd.situationtest.models.Effect;
java_import Java::com.souchy.randd.situationtest.eventshandlers.OnCastHandler;
java_import Java::com.souchy.randd.situationtest.events.CastSpellEvent;
java_import Java::com.souchy.randd.situationtest.models.effects.DamageEffect;

BEGIN {
  puts("IgniteStatus imp")
}

class IgniteStatus < Status

  def initialize ()
    super("IgniteStatus");
  end

  def registerHandlers()
    #this.<OnTurnEndHandler>register(e -> { });
		OnTurnEndHandler onEnd = register(-> (event) {
      post(ApplyEffectEvent.new(){

      });
		});
  end


end

IgniteStatus.new
