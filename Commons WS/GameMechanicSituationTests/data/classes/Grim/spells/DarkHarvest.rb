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
class DarkHarvest < ScriptedSpell

  def initialize ()
    super("Dark Harvest");
  end

  def description()
    puts "Strike enemies in front of you in a crescent moon shape and @Cripple them. Steals HP on summons."
  end

  def onCast(event)
    # ou ptete qu'on check toutes les conditions avant de post l'event pour cast ? w/e
    isOk = board.checkConditionMatrix(source, targetCell, conditionMatrix());
    if isOk == false return false;
    # Applying damage as 1st effect ==========================
    #targets1 = getTargets(event.targetCell, effectMatrix(Effect1))
    #characters = getTargets(event.target, effectMatrix(Effect1), EntityType.Fighter).filterOut(EntityType.Summon);
    characters = board.getTargets(event.targetCell, effectMatrix(Effect1), EntityType.Character);
    summons = getTargets(event.targetCell, effectMatrix(Effect1), EntityType.Summon);
    sclDmg = ElementValue.new(Elements.Dark, 40); # will multiply with scaling Dark stats from source
    flatDmg = ElementValue.new(Elements.Dark, 100); # will go get flat Dark dmg from source too
    # formula = (source.dark.scl * sclDmg) + (source.dark.flat + flatDmg)
    # params : type of dmg output + type of scaling + type of flat scaling
    post(ApplyEffectEvent.new(DamageEffect1, Damage.Hit, event.source, characters, Dark, sclDmg, flatDmg)); # dmg vs persos
    post(ApplyEffectEvent.new(StealEffect1, Damage.Hit, event.source, summons, Dark, sclDmg, flatDmg));     # vdv vs invocs
                                  # Will have Damage.Dot to differentiate hits and dots
                                  # so we can proc things OnHitEvent in the DamageEffect1
    # Applying cripple status as 2nd effect ==================
    targets2 = getTargets(event.target, effectMatrix(Effect2))
    stacks = StatusProperty.new(StatusProperties.Stacks, 1);
    duration = StatusProperty.new(StatusProperties.Duration, 1);
    # the status will use default values unless specified here
    # params : "StatusProperty... properties", then set them in the effect in a list of w/e of StatusProperty so that u dont have to care about what property it is underneath
    post(ApplyEffectEvent.new(Status, event.source, targets2, "Cripple", stacks, duration));
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
          [CanCast, Source | CannotCast,  CanCast]
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
          [Effect1 | Effect2, TargetCell | Effect1 | Effect2, Effect1 | Effect2],
          [Effect1 | Effect2, Source,                         Effect1 | Effect2]
        ]
      ]
      & effect;
  end


end

DarkHarvest.new
