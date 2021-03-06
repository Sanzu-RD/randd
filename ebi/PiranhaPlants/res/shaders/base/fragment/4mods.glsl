
uniform float u_time;

varying vec4 v_pos;

vec4 floored = floor(v_pos);

bool activateCheckers = true;
bool activateOutline = false;
bool activateWater = false;

float shade = 0.30f; // shade difference for chess/checkers pattern
float outlineWidth = 0.02; // grid lines width
vec4 outlineColor = vec4(0, 0, 0, 1); // color of grid lines
float waterlevel = 0.3; // Z-level for the water plane


void modCheckers(){
	if(v_pos.z <= 1){ // && (!activateWater || (activateWater && v_pos.z > waterlevel)) ) {
		float m = mod(floored.x + floored.y, 2);
		m *= shade;
		m += (1 - shade);
		gl_FragColor *= vec4(m, m, m, 1);
	}
}
	
	
void modOutlines(){
	float dx = abs(floored.x - v_pos.x);
	float dy = abs(floored.y - v_pos.y);
	float dz = abs(floored.z - v_pos.z);
	//float b = abs(floored.x + 1 - v_pos.x);
	bool inX = dx < outlineWidth || abs(floored.x + 1 - v_pos.x) < outlineWidth;
	bool inY = dy < outlineWidth || abs(floored.y + 1 - v_pos.y) < outlineWidth;
	bool inZ = dz < outlineWidth || abs(floored.z + 1 - v_pos.z) < outlineWidth || (v_pos.z >= 1 && abs(floored.z + 0.5 - v_pos.z) < outlineWidth);
	if(  (inZ && (inX || inY))  ||  inY && inX ){
		gl_FragColor = outlineColor;
	}
	if(inX && inZ){
	//	smoothstep(vec3(0.0), gl_FragColor, vBC);
	//	float coeff = ((dx + dz) / 2) / outlineWidth;
	//	gl_FragColor *= vec4(coeff, coeff, coeff, 1);
	}
	if(inY && inZ){
	//	float coeff = ((dy + dz) / 2) / outlineWidth;
	//	gl_FragColor *= vec4(coeff, coeff, coeff, 1);
	}
	if(inX && inY){
	//	float coeff = ((dx + dy) / 2) / outlineWidth;
	//	gl_FragColor *= vec4(coeff, coeff, coeff, 1);
	}
}
	
void water(){
	if(v_pos.z <= waterlevel){
		float transparency = 0.07;
		float speed = 1.0;
		float waterscale = 0.8;
		 
		float iTime = u_time * speed;
	    vec2 st = vec2(v_pos.x, v_pos.y) * waterscale; //fragCoord.xy/iResolution.xy*3.;
	    // st += st * abs(sin(u_time*0.1)*3.0);
	    vec3 color = vec3(0.0);
	
	    vec2 q = vec2(0.);
	    q.x = fbm( st + 0.00*iTime);
	    q.y = fbm( st + vec2(1.0));
	
	    vec2 r = vec2(0.);
	    r.x = fbm( st + 1.0*q + vec2(1.7,9.2)+ 0.15*iTime);
	    r.y = fbm( st + 1.0*q + vec2(8.3,2.8)+ 0.126*iTime);
	
	    float f = fbm(st+r);
	
	    color = mix(vec3(0.101961,0.619608,0.666667),
	                vec3(0.666667,0.666667,0.498039),
	                clamp((f*f)*4.0,0.0,1.0));
	
	    color = mix(color,
					vec3(0,0,0.164706),
					clamp(length(q),0.0,1.0));
		
	    color = mix(color,
					vec3(0.666667,1,1),
	                clamp(length(r.x),0.0,1.0));
		
		color = (f*f*f+.6*f*f+.5*f)*color;
			
		gl_FragColor += vec4(color, 1 - transparency);
		    
		//float thres = 0.2;
		//if(gl_FragColor.r < thres && gl_FragColor.g <??thres && gl_FragColor.b < thres) {
		//	gl_FragColor.a = 0.0;
		//}
		    		    
		    		    
		// mod
		//gl_FragColor += vec4(normalize(vec3(52, 161, 235)), 0.8);
		    
		}
}

// test shadows
#ifdef shadowMapFlag
void shadowMapRender(){
	// depth of the current pixel
	float currentDepth = v_shadowMapUv.z;
	gl_FragColor = vec4(currentDepth, currentDepth, currentDepth, 1);
	
	// depth of the pixel in the shadow map at the current position
	float shadowDepth = texture(u_shadowTexture, v_shadowMapUv.xy).r; // - 0.005f;
	shadowDepth = getShadow();
	gl_FragColor = vec4(shadowDepth, shadowDepth, shadowDepth, 1);
}
#endif



void mods(){

	// checkers shader
	if(activateCheckers){
		modCheckers();
	}
	
	// outline shader
	if(activateOutline && (!activateWater || v_pos.z != waterlevel)){
		modOutlines();
	}
	// transparency fade out on the main terrain blocks
	if(true && v_pos.z >= 0 && v_pos.z <= 1){
		gl_FragColor = vec4(gl_FragColor.r, gl_FragColor.g, gl_FragColor.b, clamp(v_pos.z, 0, 1));
	}
	
	// water shader
	if(activateWater){
		water();
	}
	
	// test shadows
	#ifdef shadowMapFlag
	if(false){
		shadowMapRender();
	}
	#endif
}