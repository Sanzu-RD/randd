
/*
	attribute vec3 a_position;
	uniform mat4 u_projViewTrans;
	
	#if defined(colorFlag)
	varying vec4 v_color;
	attribute vec4 a_color;
	#endif // colorFlag
	
	#ifdef textureFlag
	attribute vec2 a_texCoord0;
	#endif // textureFlag
	
	
	#ifdef diffuseTextureFlag
	uniform sampler2D u_diffuseTexture;
	uniform vec4 u_diffuseUVTransform;
	varying vec2 v_diffuseUV;
	#endif
*/


#ifdef dissolveFlag
uniform float u_dissolveIntensity;
uniform float u_dissolveBorderWidth;
uniform vec4 u_dissolveBorderColor;
uniform sampler2D u_dissolveTexture;

varying vec2 v_dissolveUV; // will come from the vertex shader, will be like a_texCoord0 modified by u_dissolveUVTransform

//uniform vec4 u_dissolveUVTransform; // i gues we could have a uv transform to move the noise around ? that's only for vertex shader tho
//varying MED vec2 v_diffuseUV; // already have the other uv vec2, idk what MED is 
//uniform vec4 u_diffuseColor; // dont need this


// get noise from the noise texture
float dissolve_noise(){
	// vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV) * u_diffuseColor * v_color;
	vec2 uv = vec2(v_dissolveUV.x, v_dissolveUV.y); // v_dissolveUV
	float noise = texture2D(u_dissolveTexture, uv).g;   
	noise += (u_dissolveIntensity - 1);
	return noise;
}

vec3 dissolve_color(float noise, vec3 glcolor){
	// reverse the noise so that white = dissolved areas
	noise = 1 - noise;
	// make it pure black/white and remove the border width from the black parts 
	// (aka make only 95% of gray areas completely black, so that white is a bit bigger the mask's black parts)
	vec3 enlargedNoise = step(1 - u_dissolveBorderWidth, noise);
	
	// color the dissolved areas that are enlarged  (white = dissovled, black = main color here)
	vec3 border = enlargedNoise * u_dissolveBorderColor.rgb;
	
	// go back to normal color (white = main color, black = dissolved)
	// (aka take a multiplier of 1 and remove the noise from it so that only the normal areas remain)
	vec3 remainingColor = 1 - enlargedNoise;
	
	// apply the main color to the normal areas
	glcolor *= remainingColor;
	
	// add the border to the main color (aka fill the black dissolved removed areas with the border color)
	glcolor += border;
	
	// therefore, here we have the normal texture + a colored noise overwriting parts of it (enlarged version)
	return glcolor;
}

float dissolve_alpha(float noise, float glalpha){
	// make the noise 100% black/white where black=hidden(dissolved) and white=shown(main color+border)
	noise = step(0, noise);
	
	// multiply the alpha noise with the main alpha
	return glalpha * noise;
}

vec4 dissolve(vec4 glcolor) {
	// get noise
	float noise = dissolve_noise();
	// get color
	vec3 disscolor = dissolve_color(noise, glcolor.rgb);
	// get alpha
	float dissalpha = dissolve_alpha(noise, glcolor.a);
	
	

	// real one
	glcolor = vec4(disscolor.r, disscolor.g, disscolor.b, dissalpha);
	
	
	// test
	
	
	//if(v_dissolveUV.x >= 0.875000f && v_dissolveUV.y > 0.5f){
	//if(v_dissolveUV.y == 0.5f){
	if(v_dissolveUV.x > 0.950f && v_dissolveUV.x < 0.960f){
	//	glcolor = vec4(1,0,0,1);
	}
	
	//glcolor = vec4(1,0,0,1);
	
	//glcolor = vec4(1, 1, 1, dissalpha);
	//noise = step(0, noise) * glcolor.a;
	//glcolor = vec4(noise, noise, noise, 1);
	//glcolor = vec4(noise, noise, noise, dissalpha);
	
	//glcolor = vec4(disscolor.r, disscolor.r, disscolor.r, 1);
	//glcolor = vec4(1, 0, disscolor.b, dissalpha);
	
	//glcolor = vec4(v_dissolveUV.x, v_dissolveUV.y, 0, 1);
	
	
	// return all
	return glcolor;
}


#endif

