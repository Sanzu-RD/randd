
require 'java'

$CLASSPATH << "GameMechanicSituationTests";

java_package 'com.souchy.randd.situationtest.scripts.actionhandlers'
java_import Java::com.souchy.randd.situationtest.scripts.actionhandlers.Damage1ActionHandler;

@Inject
class Damage1Script
  include Damage1ActionHandler
  java_implements Damage1ActionHandler

  def initialize ()
    #super("Damage1");
    puts("Hi damage 1 script")
  end

  def description()
    puts "Does damage for a Hit or a Dot."
  end

  def handle(event) #Damage1Action event
    IEntity source = event.source;
    IEntity target = event.target;
    Damages type = event.dmgType;
    Elements outputEle = event.dmgEle;
    ElementValue scl = event.scl;
    ElementValue flat = event.flat;

    ElementValue dmg = ElementValue.new(outputEle, 0).
    # scl dmg
    addSet(scl.value * source.stats.scl(scl.element).value).
    # flat dmg
    addSet(flat.value + source.stats.flat(flat.element).value).
    # res fix
    subSet(c.stats.resFlat(outputEle)).
    # res scl
    multSet(1 - c.stats.resPer(outputEle) / 100);

    if dmg.value <= 0
      proc(type);
      return;
    end

    hp = source.stats.get(StatProperties.Resource1);
    shield = source.stats.get(StatProperties.Resource1Shield);

  end # end apply

  def proc(type)
    if type == Damage.Hit
      post(OnHitEvent.new(source, target));
    end
    if type == Damage.Dot
      post(OnDotEvent.new(source, target));
    end
  end

end # end class



Damage1Script.new
