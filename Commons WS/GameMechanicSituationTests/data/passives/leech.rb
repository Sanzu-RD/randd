

// todo ?


def initialize ()

  onDamageInstance = -> (event) {
    
    leech = dmgHp * 0.2;
    
    scl = ElementValue.new(Elements::PURE, 0);
    flat = ElementValue.new(Elements::PURE, leech);
    aoe = EffectMatrix.new(int[]{ 1 });
    predicate = -> (c) {
    	c.hasCharacter();
    }
       
    effect = new HealEffect(event.context, event.source, scl, flat, aoe);
    
    
    //post(effect);
    effect1.applyAoe(event.target, Orientation::North, predicate);
  }

  
  register(onDamageInstance)
  
end


