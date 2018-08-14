



require 'java'
require 'F:/Users/Souchy/Desktop/Robyn/Git/res/libs other/guava-18.0.jar'

$CLASSPATH << "GameMechanicSituationTests";

java_import Java::com.souchy.randd.situationtest.models.ScriptedSpell;
java_import Java::com.souchy.randd.situationtest.interfaces.IEffect;
java_import Java::com.souchy.randd.situationtest.interfaces.IEntity;
java_import Java::com.souchy.randd.situationtest.models.Effect;
java_import Java::com.souchy.randd.situationtest.eventshandlers.OnCastHandler;
java_import Java::com.souchy.randd.situationtest.events.CastSpellEvent;
java_import Java::com.souchy.randd.situationtest.models.effects.DamageEffect;

BEGIN {
  puts("ChargeUpSpell imp")
}

class ChargeUpSpell < ScriptedSpell

  #attr_accessor :onCast
  def initialize ()
    super("ChargeUpSpell");


    # !!!!!!!!!!  delete les objets effects !!!!!!!!!!! :D

    #effects = java.util.ArrayList.new;

    #effect1 = DamageEffect.new("chargeupDmg1")
    # def effect1.apply(entity) #works !
    #    puts("applying damage to entity [#{entity}]")
    #end

    #effect2 = DamageEffect.new("chargeupDmg2")
    # def effect2.apply(entity) #works !
    #    puts("applying damage to entity [#{entity}]")
    #end

    #effect3 = DamageEffect.new("chargeupBuff")
    # def effect3.apply(entity) #works !

    #end



    #effect1.apply = -> {
    #  puts("applying damage")
    #}
    effects.add(effect1);
    puts("ruby number of effects : #{effects.size().to_s}" );
    puts

    onCasthandler = -> (event) {
      puts("ruby on cast handling : " + event.to_s)
      begin
        if event.source.hasBuff("chargeup") && event.source.getBuff("chargeup").remaining() == 0
          scalingDmg = 30;
          baseDmg = 200;
        else
          scalingDmg = 20;
          baseDmg = 100;
        end

        entities = event.target.getEntities(event.context);
        entities.each do |e|
          dmg = formula1(source, e, scalingDmg, baseDmg);
          #new DamageEvent(event.source, e, dmg);
          effect1.apply(e);
        end

        if event.source.hasBuff("chargeup") == false
          event.source.addBuff(effect2);
        end

        effects.each do |e|
          e.apply(targetEntity); #works !
        end
      rescue => exception
        puts "onCastHandler error"
        warn exception.message
      end





    }

    setOnCast(onCasthandler);

  end

  #def rubyOnCast(event)
  #  puts("ruby on cast handling : " + event.to_s)
  #end


end

PoisonTrap.new
