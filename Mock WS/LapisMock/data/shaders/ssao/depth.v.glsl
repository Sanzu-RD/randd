
// object attributes --------------

attribute vec3 a_position; // local pos to object
attribute vec3 a_normal;
attribute vec2 a_texCoord0;


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



// output to fragment shader -------
varying vec2 v_texCoord0;
varying vec4 v_color;
varying vec4 v_worldpos;
varying vec3 v_normal;
varying float v_depth;
varying vec3 v_pos;


// test diffuse --------------
//uniform vec4 u_diffuseUVTransform;
//varying vec2 v_diffuseUV;
//void testDiffuse(){
//	v_diffuseUV = u_diffuseUVTransform.xy + a_texCoord0 * u_diffuseUVTransform.zw;
//}



// main -------------------
void main() {
	//testDiffuse();
	
	v_normal = normalize(u_normalMatrix * a_normal);
	v_pos = a_position;
	
	vec4 pos = u_worldTrans * vec4(a_position, 1.0);
	//gl_Position = u_projViewTrans * pos;
	
	// pass the pos to the fragment shader // couldnt i just use gl_Position??
	v_worldpos =  pos;
	
	vec4 posdepth = u_projViewWorldTrans * vec4(a_position, 1.0);
	v_depth = posdepth.z / posdepth.w * 0.5 + 0.5;
	
	
	
	// base
	v_color = gl_Color;
    v_texCoord0 = a_texCoord0;
    gl_Position = u_projViewTrans * u_worldTrans * vec4(a_position, 1.0);
}
