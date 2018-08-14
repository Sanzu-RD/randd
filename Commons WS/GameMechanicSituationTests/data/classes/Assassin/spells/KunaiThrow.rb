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

# Premier sort complètement écrit le 22 juillet 2018 ! =)
class KunaiThrow < ScriptedSpell

  def initialize ()
    super("Kunai Throw");
  end

  def description()
    puts "Throw a Kunai which you can pull yourself to. Reveals a vital point if thrown on ennemies. "
  end

  def effectDescription()
    puts "X damage (physical)"
    puts "X damage (poison)"
    puts "+1 Vital Point status"
    puts "+1 Kunai status"
    puts "-1 Kunai resource"
  end

  def onCast(event)
    # ou ptete qu'on check toutes les conditions avant de post l'event pour cast ? w/e
    isOk = board.checkConditionMatrix(source, targetCell, conditionMatrix());
    if isOk == false return false;
    # Applying damage as 1st effect ==========================
    if event.targetCell.getEntity() != null
      # dommage + apply status
      sclDmg = ElementValue.new(Elements.Physical, 40);
      flatDmg = ElementValue.new(Elements.Physical, 100);
      sclDmg = ElementValue.new(Elements.Poison, 40);
      flatDmg = ElementValue.new(Elements.Poison, 100);
      post(ApplyEffectEvent.new(DamageEffect3, Damage.Hit, event.source, characters, Dark, sclDmg, flatDmg)); # dmg vs persos
    else
      # set status on the cell ???

      # créé l'effet avant
      effect1 = TerrainEffect.new();
      # applique l'effet ici
      targets =  board.getTargets(targetCell, effect1.getMatrix());
      foreach(entity e : targets)
        effect1.apply(e); # -> post(ApplyEffectEvent.new(effect1, targetCell));

      effect1.applyAoe(targetCell); # -> ferait le foreach à l'intérieur de ça pour pas avoir à réécrire le getTargets et foreach à chaque fois



    end


    return true;
  end

  def costs()
    return [CaracteristicValue.new(Stats.Resource2, 3)];
  end

  # Defines the range and area in which the spell can be casted around the player
  def rangeMatrix()
    return
      [
        #First orientation, only one in this case
        [
          [NoFlag,  CanCast, NoFlag],
          [CanCast, Source | CannotCast,  CanCast],
          [NoFlag,  CanCast, NoFlag]
        ]
      ]
  end

  # Defines conditions for positionning and classes of targets
  def conditionMatrix()
    return
      [
        #First orientation, only one in this case
        [
          [TargetCell],
          [Source]
        ]
      ]
  end

  # Defines where to get targets from to apply effects
  def effectMatrix(EffectEnum effect)
    return
      [
        #First orientation, only one in this case
        [
          [Effect1 | Effect2]
        ]
      ]
      & effect;
  end


end

KunaiThrow.new
