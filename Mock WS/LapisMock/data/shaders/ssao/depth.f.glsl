
// header

#ifdef GL_ES 
precision mediump float;
#endif


// global uniforms --------------
uniform mat4 u_projTrans; // camera.projection
uniform mat4 u_viewTrans; // camera.view
uniform mat4 u_projViewTrans; // camera.combined // transform world to clip
uniform vec4 u_cameraPosition;
uniform vec3 u_cameraDirection;
uniform vec3 u_cameraUp;
uniform vec2 u_cameraNearFar;
uniform float u_time;

// object uniforms --------------
uniform mat4 u_worldTrans; // transform local to world
uniform mat4 u_viewWorldTrans;
uniform mat4 u_projViewWorldTrans;
uniform mat3 u_normalMatrix;


// input from vertex shader -------
varying vec2 v_texCoord0;
varying vec4 v_color;
varying vec4 v_worldpos;
varying vec3 v_normal;
varying float v_depth;
varying vec3 v_pos;


// base vars
uniform vec3 u_colorU;
uniform vec3 u_colorV;


/*
// test diffuse
uniform sampler2D u_diffuseTexture;
uniform vec4 u_diffuseColor;
varying vec2 v_diffuseUV;
*/


void old(){
    //gl_FragColor = vec4(v_texCoord0, 0.0, 1.0);
    //gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
    //gl_FragColor = vec4(v_texCoord0.x * u_colorU + v_texCoord0.y * u_colorV, 1.0);
    //gl_FragColor = vec4(u_colorU, 1.0); // v_color; //vec4(v_color.rgb , 1.0);
}

// main
void main() {

	vec4 finalColor = vec4(0, 0, 0, 1);
	
	//vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV) * u_diffuseColor * v_color;
	//finalColor = diffuse;
	
	if(false){
		vec4 r = normalize(vec4(v_worldpos.x + 1, 0, 0, 1));
		vec4 g = normalize(vec4(0, v_worldpos.y + 1, 0, 1));
		vec4 b = normalize(vec4(0, 0, v_worldpos.z + 1, 1));
		
		
		//if(c.x > 0 && c.x < 5){
		//if(gl_FragCoord.x > 2 && gl_FragCoord.x < 500){
			finalColor = vec4(r.r, r.g, r.b, r.a);
		//}
		finalColor = vec4(v_worldpos.x, v_worldpos.y, v_worldpos.z, v_depth);
	}
	
	
	// distance to camera
	if(false){
		vec4 clippos = u_projViewTrans * v_worldpos;
		float p = clippos.z;
		p = p * 0.5f + 0.5f;
		gl_FragColor = vec4(p, 0, 0, 1);
	}
	
	if(false){
		//finalColor = vec4(v_depth, 0, 0, 1);
		finalColor = vec4(v_worldpos.x / 19.0f, 0, 0, 1);
	}
	if(true){
		finalColor = vec4(v_worldpos.x, v_worldpos.y, v_worldpos.z, 1);
	}
	
	if(false){
		//if(v_worldpos.y >= 2.5 && v_worldpos.y <= 5.5){
			vec4 pos =  u_worldTrans * vec4(v_pos, 1.0); // u_projViewTrans *
			vec4 sp = pos;
			finalColor = vec4(round((sp.x-7)) / 12, 0, 0, 1);
		//}
		
			finalColor = vec4(round((sp.x)) / 19, round((sp.y)) / 19, 0, 1);
	}
		
	
	gl_FragColor = finalColor;
}
