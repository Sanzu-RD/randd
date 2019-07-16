package com.souchy.randd.ebishoal.commons.lapis.lining;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public class Line {
	
	public Vector3 start;
	public Vector3 end;
	public Color color;
	
	// seulement si on donne un Box2d World
//	public Body body;
//	public Fixture fix;
	
	public Line(Color c, Vector3 s, Vector3 e) { //, World w){
		start = s;
		end = e;
		color = c;
		
//		if(w != null){ // si, par exemple, c'est du décor, on ne donne pas de world et on créé pas de body
//			BodyDef bodyDef = new BodyDef();
//			FixtureDef fixDef = new FixtureDef();
//			EdgeShape shape = new EdgeShape();
//			
//			bodyDef.type = BodyType.StaticBody;
//			bodyDef.position.set(0,0);
//			
//			fixDef.density = 1;
//			fixDef.restitution = 0.2f;
//			fixDef.friction = 0;
//			
//			shape.set(s.x, s.y, e.x, e.y);
//			fixDef.shape = shape;
//			
//			body = w.createBody(bodyDef);
//			fix = body.createFixture(fixDef);
//			//body.setUserData(this); osef pas besoin de ça pour des lignes, malgré que peut-être besoin pour l'outil d'efface
//			shape.dispose();
//		}
	}
	
}
