



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
  puts("Cripple imp")
}

class Cripple < Status

  #attr_accessor :onCast
  def initialize ()
    super("Cripple");
  end

  def registerHandlers()
    #this.<OnTurnEndHandler>register(e -> { });
		#OnTurnEndHandler onEnd = register(-> (event) {
    #  post(ApplyEffectEvent.new(){
    #
    #  });
		#});

    # TODO : register method to apply the status effect cuz it's on OnTurnEnd, it's effective as long as the target has the Status
    # then the actionscript that gives statuses will call this event
    onApply = -> () {
      target.dmgStats -=5;
      target.mvm -= 2;
    }
    register(onApply);

  end



end

Cripple.new
