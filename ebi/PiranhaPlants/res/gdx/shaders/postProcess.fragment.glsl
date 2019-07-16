
#ifdef GL_ES
	#define LOWP lowp
	precision mediump float;
#else
	#define LOWP 
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

//RADIUS of our vignette, where 0.5 results in a circle fitting the screen
const float RADIUS = 0.95;

//softness of our vignette, between 0.0 and 1.0
const float SOFTNESS = 0.8;

void main() {

	vec4 finalColor = v_color * texture2D(u_texture, v_texCoords);
	
	if(false) {
		gl_FragColor = finalColor;
		return;
	}
	
	float width = 1920.0; // * 5; //3000; //
	float height = 1080.0; // * 5; //2000; //
	
	vec4 top         = texture2D(u_texture, vec2(v_texCoords.x, v_texCoords.y + 1.0 / height));
	vec4 bottom      = texture2D(u_texture, vec2(v_texCoords.x, v_texCoords.y - 1.0 / height));
	vec4 left        = texture2D(u_texture, vec2(v_texCoords.x - 1.0 / width, v_texCoords.y));
	vec4 right       = texture2D(u_texture, vec2(v_texCoords.x + 1.0 / width, v_texCoords.y));
	vec4 topLeft     = texture2D(u_texture, vec2(v_texCoords.x - 1.0 / width, v_texCoords.y + 1.0 / height));
	vec4 topRight    = texture2D(u_texture, vec2(v_texCoords.x + 1.0 / width, v_texCoords.y + 1.0 / height));
	vec4 bottomLeft  = texture2D(u_texture, vec2(v_texCoords.x - 1.0 / width, v_texCoords.y - 1.0 / height));
	vec4 bottomRight = texture2D(u_texture, vec2(v_texCoords.x + 1.0 / width, v_texCoords.y - 1.0 / height));
		
 	// Noise reduction /-> fps-intensive, drop from 350 fps to 150 and from 600 to 250
	if(false){
		vec4 colors[9] = vec4[9](finalColor, top, bottom, left, right, topLeft, topRight, bottomLeft, bottomRight);
		
		int maxCount = 0;
		vec4 max = finalColor;
		
    	for(int i = 0; i < colors.length(); i++) {
			vec4 c1 = colors[i];
			int counter = 0;
	    	for(int j = i + 1; j < colors.length(); j++) {
				vec4 c2 = colors[j];
	    		if(c1 == c2){
	    			counter++;
	    		}
	    	}
	    	if(counter > maxCount){
	    		maxCount = counter;
	    		max = c1;
	    	}
		}
		finalColor = max;
	}
	
	// Blur
	if(true){
		vec4 center = finalColor + top + bottom + left + right + topLeft + topRight + bottomLeft + bottomRight;
		center /= 9;
		finalColor = center; 
	}
	
	// Sobel operator /-> faudrait qu'il affecte juste le world, sans les shadows
	if(true){
		vec4 sx = -topLeft - 2 * left - bottomLeft + topRight   + 2 * right  + bottomRight;
		vec4 sy = -topLeft - 2 * top  - topRight   + bottomLeft + 2 * bottom + bottomRight;
		vec4 sobel = sqrt(sx * sx + sy * sy);
		finalColor += sobel / 3;
	}
	
	// vignette
	if(true){
		// size of the fbo texture
    	ivec2 size = textureSize(u_texture, 0);
    
		vec2 position = (gl_FragCoord.xy / size.xy) - vec2(0.5);
		
		float len = length(position);
		
		//our vignette effect, using smoothstep
		float vignette = smoothstep(RADIUS, RADIUS-SOFTNESS, len);
		
		// mult only rgb unless you want to modify the alpha (that would make the fbo transparent and show the background image thats under)
		//finalColor.rgb *= vignette;
		
		// transparence only in the vignette around the map so it doesnt affect the board
		finalColor.ra *= vignette;
	}
	
	
	//if (finalColor.a  < 0.5) discard;
	gl_FragColor = finalColor;
	//gl_FragColor = vec4(1, 0, 0, 1);
}
