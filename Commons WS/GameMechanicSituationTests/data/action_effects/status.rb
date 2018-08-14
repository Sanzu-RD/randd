

require 'java'

$CLASSPATH << "GameMechanicSituationTests";

java_import Java::com.souchy.randd.situationtest.models.IEffect
#java_import Java::com.souchy.randd.situationtest.events.eventshandlers.OnCastHandler
#myclass = JavaUtilities.get_proxy_class('com.souchy.randd.situationtest.situations.Situation3SpellScript.ScriptedSpell')
#@myclass  = myclass.new


BEGIN {
  puts("damage effect imp")
}

class DamageEffect < IEffect

  def apply(source, target, amount)
    if(target.resource_3 > 0){ #if shield
      if(target.resource_3 > amount){ #if shield > dmg
        target.resource_3 -= amount;
      } else { # if dmg > shield
        amount -= target.resource_3;
        target.resource_3 = 0;
        target.resource_1 -= amount;
      }
    } else { #if no shield
      target.resource_1 -= amount;
    }
  end

end
