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


class KunaiPull < ScriptedSpell

  def initialize ()
    super("Kunai Pull");
  end

  def description()
    puts "Pull yourself to a kunai on the ground or pull a kunai from an enemy. Can pull multiple kunais in a circle area by targeting yourself."
  end

  def effectDescription()
    puts "X damage (physical)"
    puts "+1 Bleed status" # "bleed" or "bleeding" ...
    puts "-1 Vital Point status"
    puts "-1 Kunai status"
    puts "+X Kunai resource"
  end

  def onCast(event)
    # ou ptete qu'on check toutes les conditions avant de post l'event pour cast ? w/e
    isOk = board.checkConditionMatrix(source, targetCell, conditionMatrix());
    if isOk == false return false;

    if event.targetCell.getCharacter() == event.source
      # pull tous les kunais en aoe autour du caster qui sont sur le terrain (tire pas ceux prits dans des character)
      # -> aoePullEffect.applyAoe(targetCell)
    elsif #pointe un character qui possède le status kunai
      # check si le character possèdee vraiment un kunai, sinon on peut pas cast le spell
      # si oui fait du dommage
      # applique le status Bleed au target
    elsif #pointe une cellule qui possède le terrain kunai
      # check s'il y a un kunai sur la cellule pointée, sinon on peut pas cast le spell
      # si oui, déplace le caster sur la cellule du kunai
      # redonne le kunai dans les ressources de la source
    end


    # Applying damage as 1st effect ==========================
    if event.targetCell.getEntity() != null
      # dommage + apply status
      sclDmg = ElementValue.new(Elements.Physical, 40);
      flatDmg = ElementValue.new(Elements.Physical, 100);
      post(ApplyEffectEvent.new(DamageEffect3, Damage.Hit, event.source, characters, Dark, sclDmg, flatDmg)); # dmg vs persos

      # post damage to target
      # post -1 kunai status to target
      # post -1 vital point status to target
    else
      # set status on the cell ???

      # post movement action
      # post remove status from the cell
    end
      # post add resource action (+1 kunai)

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

KunaiPull.new
