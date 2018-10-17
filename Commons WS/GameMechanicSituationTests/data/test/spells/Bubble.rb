



require 'java'
require 'F:/Users/Souchy/Desktop/Robyn/Git/res/libs other/guava-18.0.jar'

$CLASSPATH << "GameMechanicSituationTests";

java_import Java::com.souchy.randd.situationtest.scripts.ScriptedSkill;
java_import Java::com.souchy.randd.jade.api.IEffect;
java_import Java::com.souchy.randd.jade.api.IEntity;
java_import Java::com.souchy.randd.situationtest.models.Effect;
java_import Java::com.souchy.randd.situationtest.eventshandlers.OnCast;
java_import Java::com.souchy.randd.situationtest.events.CastSpellEvent;
java_import Java::com.souchy.randd.situationtest.effects.resources.DamageEffect;
java_import Java::com.souchy.randd.situationtest.math.matrixes.EffectMatrix;
java_import Java::com.souchy.randd.situationtest.math.matrixes.ConditionMatrix;
java_import Java::com.souchy.randd.situationtest.math.matrixes.MatrixFlags;
java_import Java::com.souchy.randd.situationtest.properties.ElementValue;
java_import Java::com.souchy.randd.situationtest.properties.types.Elements;
java_import Java::com.souchy.randd.situationtest.properties.types.Damages;
java_import Java::com.souchy.randd.situationtest.properties.types.Orientation;

BEGIN {
  puts("Bubble imp")
}

class Bubble < ScriptedSkill

  #attr_accessor :onCast
  def initialize ()
    super("Bubble");
# Cant access java vars directly from ruby
#    effects = java.util.ArrayList.new;
#    effect1 = DamageEffect.new
#    def effect1.apply(entity) #works !
#        puts("applying damage to entity [#{entity}]")
#    end
    #effect1.apply = -> {
    #  puts("applying damage")
    #}
#    effects.add(effect1);
#    puts("ruby number of effects : #{effects.size().to_s}" );
#    puts

    handler = -> (event) {
      puts("ruby on cast handling : " + event.to_s)
      begin
        puts("handling context : " + event.context.to_s)
        puts("handling caster id : " + event.source.id.to_s)
        puts("handling target cell : [#{event.target.getPos().x.to_s}, #{event.target.getPos().y.to_s}, #{event.target.getPos().z.to_s}]")
        puts("handling spell : " + event.spell.name)

        predicate = -> (c) {
          c.hasCharacter();
        }

        aoe1 = EffectMatrix.new([[4,4], [4,4]]);
        cond1 = ConditionMatrix.new([[0,0], [0,0]]);
        # Spell effects creation :
        scl = ElementValue.new(Elements::Water, 50);
        flat = ElementValue.new(Elements::Water, 1);
        effect1 = DamageEffect.new(event.context, event.source, Damages::Hit, Elements::Water, scl, flat, aoe1, cond1);
        # Spell application :
        effect1.applyAoe(event.target, Orientation::North, predicate);

      rescue => exception
        puts "parameter is not an event"
        warn exception.message
      end
    }


    # DAMAGE EVENTS/HANDLERS/APPLICATION EXAMPLES ------ BEGIN
    # normal damage handler for HP
    resource1DamageHandler = -> (guy, dmg) {
      guy.Resource1 -= dmg;
    }
#    guy.register(resource1DamageHandler);

    # special damage handler that guy can have with a class passive, or buff etc
    #guy.bus.onDamageHandler =
    specialOnDamageHandler = -> (guy, dmg) {
      # exemple du genre mind over matter
      guy.Resource1 -= dmg * 2/3;  # 2/3 sur les hp
      guy.Resource2 -= dmg * 1/3;  # 1/3 sur la mana
    }
  #  guy.register(specialOnDamageHandler);
    # DAMAGE EVENTS/HANDLERS/APPLICATION EXAMPLES ------ END



    #handler = rubyOnCast; #doesnt work
    #handler = lambda(&method(:rubyOnCast)); #works
    #handler = method(:rubyOnCast); #works

    #event1 = CastSpellEvent.new(nil, nil, nil, self);

    #onCast = handler; #impossible d'accéder à des variables directement
    setOnCast(handler);
    #getOnCastHandler().handle("hello xD"); #works !!!!
    #puts
    #getOnCastHandler().handle(event1); #works !!!!
    puts
  end

  #def rubyOnCast(event)
  #  puts("ruby on cast handling : " + event.to_s)
  #end


end

Bubble.new
