
#ifdef GL_ES
	#define LOWP lowp
	precision mediump float;
#else
	#define LOWP 
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoords;
varying float v_TextureType;

uniform sampler2D u_texture;

//RADIUS of our vignette, where 0.5 results in a circle fitting the screen
const float RADIUS = 0.75;

//softness of our vignette, between 0.0 and 1.0
const float SOFTNESS = 0;

void main() {

	vec4 finalColor = v_color * texture2D(u_texture, v_texCoords);
	
	float width = 1920.0; // * 5; //3000; //
	float height = 1080.0; // * 5; //2000; //
	
	float array[3] = float[3](2.5, 7.0, 1.5);
	
	vec4 top         = texture2D(u_texture, vec2(v_texCoords.x, v_texCoords.y + 1.0 / height));
	vec4 bottom      = texture2D(u_texture, vec2(v_texCoords.x, v_texCoords.y - 1.0 / height));
	vec4 left        = texture2D(u_texture, vec2(v_texCoords.x - 1.0 / width, v_texCoords.y));
	vec4 right       = texture2D(u_texture, vec2(v_texCoords.x + 1.0 / width, v_texCoords.y));
	vec4 topLeft     = texture2D(u_texture, vec2(v_texCoords.x - 1.0 / width, v_texCoords.y + 1.0 / height));
	vec4 topRight    = texture2D(u_texture, vec2(v_texCoords.x + 1.0 / width, v_texCoords.y + 1.0 / height));
	vec4 bottomLeft  = texture2D(u_texture, vec2(v_texCoords.x - 1.0 / width, v_texCoords.y - 1.0 / height));
	vec4 bottomRight = texture2D(u_texture, vec2(v_texCoords.x + 1.0 / width, v_texCoords.y - 1.0 / height));
		
 	// Noise reduction
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
	if(false){
		vec4 center = finalColor + top + bottom + left + right + topLeft + topRight + bottomLeft + bottomRight;
		center /= 9;
		finalColor = center; 
	}
	
	// Sobel operator
	if(false){
		vec4 sx = -topLeft - 2 * left - bottomLeft + topRight   + 2 * right  + bottomRight;
		vec4 sy = -topLeft - 2 * top  - topRight   + bottomLeft + 2 * bottom + bottomRight;
		vec4 sobel = sqrt(sx * sx + sy * sy);
		finalColor += sobel;
	}
	
	// Round Image
	if(v_TextureType == 1){
		//determine the vector length of the center position
		float len = length(v_texCoords - vec2(0.5));
		
		finalColor -= step(0.4, len); 
	}
	
	//if (finalColor.a  < 0.5) discard;
	
	gl_FragColor = finalColor;
	//gl_FragColor = vec4(1, 0, 0, 1);
}
