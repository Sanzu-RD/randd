

require 'java'

$CLASSPATH << "GameMechanicSituationTests";

java_import Java::com.souchy.randd.situationtest.models.IEffect
#java_import Java::com.souchy.randd.situationtest.events.eventshandlers.OnCastHandler
#myclass = JavaUtilities.get_proxy_class('com.souchy.randd.situationtest.situations.Situation3SpellScript.ScriptedSpell')
#@myclass  = myclass.new


class DamageEffect < IEffect

  def initialize ()
    super("DamageEffect");
  end

  def description()
    puts "Does damage for a Hit or a Dot."
  end

  def apply(Damage type, Character source, List<Characters> targets, Element outputEle, ElementValue scl, ElementValue flat)
    # for each targets
    for c in targets
      # scaling element
      Elements ele = scl.element;
      # attention à l'élément d'output vs l'élément de scaling
      ElementValue dmg = ElementValue.new(outputEle, source.stats.scl(ele) * scl).add(source.stats.flat(ele) + ele);
      # applique les res sur l'output element
      dmg.sub(c.stats.flatRes(outputEle)).mult(1 - c.stats.perRes(outputEle))
      # formula = (source.dark.scl * sclDmg) + (source.dark.flat + flatDmg)

      # pourrait faire que certains spells ou certains DoTs passent au travers des shields dans une autre formule
      # faut checker les buffs d'invulnérabilité etc ici
      if dmg.value > 0
        if target.resource_3 > 0 # if shield
          if target.resource_3 > dmg.value # if shield > dmg
            target.resource_3 -= dmg.value;
          else  # if dmg > shield
            dmg.value -= target.resource_3;
            target.resource_3 = 0;
            target.resource_1 -= dmg.value;
          end
        else  #if no shield
          target.resource_1 -= dmg.value;
        end
        if type == Damage.Hit
          post(OnHitEvent.new(source));
        end
        if type == Damage.Dot
          post(OnDotEvent.new(source));
        end
      end


    end # end apply

  end # end class
