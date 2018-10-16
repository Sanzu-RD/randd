require 'java'

$CLASSPATH << "GameMechanicSituationTests";

java_import Java::com.souchy.randd.situationtest.scripts.ScriptedSpell;
java_import Java::com.souchy.randd.situationtest.interfaces.IEffect;
java_import Java::com.souchy.randd.situationtest.interfaces.IEntity;
java_import Java::com.souchy.randd.situationtest.models.Effect;
java_import Java::com.souchy.randd.situationtest.eventshandlers.OnCastHandler;
java_import Java::com.souchy.randd.situationtest.events.CastSpellEvent;
java_import Java::com.souchy.randd.situationtest.models.effects.DamageEffect;


class KunaiThrow < ScriptedSpell

  def initialize ()
    super("Bubble");
  end

  def description()
    puts "Shoot a bubble to an ennemy."
  end

  def effectDescription()
    puts "X damage (water)"
  end

  def onCast(event)
    # ou ptete qu'on check toutes les conditions avant de post l'event pour cast ? w/e
    isOk = board.checkConditionMatrix(source, targetCell, conditionMatrix());
    if isOk == false return false;

    # Applying damage as 1st effect ==========================
    if event.targetCell.getEntity() != null
      # dommage + apply status
      sclDmg = ElementValue.new(Elements.Water, 40);
      flatDmg = ElementValue.new(Elements.Water, 100);
      post(ApplyEffectEvent.new(DamageEffect1, Damage.Hit, event.source, event.targetCell.getEntity(), Elements.Water, sclDmg, flatDmg)); # dmg vs persos
    else
      return false;
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
