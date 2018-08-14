



require 'java'
require 'F:/Users/Souchy/Desktop/Robyn/Git/res/libs other/guava-18.0.jar'

$CLASSPATH << "GameMechanicSituationTests";

java_import Java::com.souchy.randd.situationtest.models.ScriptedSpell
#java_import Java::com.souchy.randd.situationtest.events.eventshandlers.OnCastHandler
#myclass = JavaUtilities.get_proxy_class('com.souchy.randd.situationtest.situations.Situation3SpellScript.ScriptedSpell')
#@myclass  = myclass.new


BEGIN {
  puts("fireball   imp")
}

class Fireball < ScriptedSpell
  #include ScriptedSpell
  #java_implements ScriptedSpell

  #attr_accessor :name
  def initialize ()
    super("asdf")
  end

  #override
  def hiRuby()
    puts("ruby hi")
  #  puts("ruby init name : " + self.name)

    effects = java.util.List.new

    #effect1 = {
    #}
  end

  #override
  def test()
    puts("ruby test")
    puts("ruby get name " + getName())
    puts("ruby set name")
    setName("ruby_changed_name")
    puts("ruby get name " + getName())
    puts("ruby name " + name)
  end

  def onCast()

  end

=begin

  onCast = {|event|

    caster = event.source
    cell = event.target
    spell = event.spell

    #if(spell == this)
    for(Effect e : effects)
      e.apply();
    end
  }

  onTurnStart = {|event|


  }
=end

  #CastSpellEvent
  #OnCastHandler.new
  #() -> puts("onCast!")

end


Fireball.new
