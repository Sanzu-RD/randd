require 'java'
require 'F:/Users/Souchy/Desktop/Robyn/Git/res/libs other/guava-18.0.jar'

$CLASSPATH << "GameMechanicSituationTests";

java_import Java::com.souchy.randd.situationtest.models.ScriptedSpell
java_import Java::com.souchy.randd.situationtest.events.eventshandlers.OnCastHandler


BEGIN {
  puts("Skill CrimsonFlame")
}

class CrimsonFlame < ScriptedSpell

  =begin
    name = "CrimsonFlame"
    costs = { {4,PA}, {1,PM} }

    effects = java.util.List.new

    effect1 = {
      #damage effect
      matrix1 = {
      0,0,2,0,0
      0,0,0,0,0
      0,0,1,0,0
      0,0,0,0,0
      0,0,0,0,0
      }
      conditions.add(-> (context) {
        # return false si une cellule est vide (inadmissible)
        flag = 2
        return context.getCells(matrix1, flag).any(c -> c.isEmpty()) == false;

      })
    }

    effect2 = {
      #movement effect

      matrix2 = {
      0,0,2,0,0
      0,0,0,0,0
      0,0,1,0,0
      0,0,0,0,0
      0,0,0,0,0
      }


      conditions.add(-> (context) {
        # return false si une cellule est occupée (inadmissible)
        # return true si toutes les cellules sont vides
        flag = 2
        return context.getCells(matrix2, flag).all(c -> c.isEmpty())

      })

      apply = (target) - > {
        target.damage(10, FLAT, FIRE, source);
        target.damage(10, CURRENT_RESOURCE_1_PERCENT, FIRE, source);
      }

    }


    conditions.add(-> (context) {
      # return false si une cellule est occupée (inadmissible)
      # return true si toutes les cellules sont vides

      behind

      return context.getCells(effectMatrix, 2).all(c -> c.isEmpty())

    })


    onCast = -> (event) {

      caster = event.source
      cell = event.target
      spell = event.spell

      # dans notre cas on veut que toutes les conditions d'effets soient remplies pour pouvoir caster le spell
      if(this.assertConditions(context) && effects.all(e -> e.assertConditions(context)))

        #if(spell == this)
        for(Effect e : effects)
          #if(e.assertConditions(context))

          #end
        end

      end

    }

  =end

    #onTurnStart = {|event|
    #}

    #CastSpellEvent
    #OnCastHandler.new
    #() -> puts("onCast!")

end
