
// main vars

attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_texCoord0;

uniform mat4 u_worldTrans;
uniform mat4 u_projViewTrans;

varying vec2 v_texCoord0;
varying vec4 v_color;


// test diffuse 
uniform vec4 u_diffuseUVTransform;
varying vec2 v_diffuseUV;
void testDiffuse(){
	v_diffuseUV = u_diffuseUVTransform.xy + a_texCoord0 * u_diffuseUVTransform.zw;
}

// test more
varying vec3 v_normal;
varying vec4 v_pos;
uniform mat3 u_normalMatrix;

// main

void main() {
	testDiffuse();
	v_normal = normalize(u_normalMatrix * a_normal);
	
	vec4 pos = u_worldTrans * vec4(a_position, 1.0);
	// pass the pos to the fragment shader // couldnt i just use gl_Position??
	v_pos =  pos;
	gl_Position = u_projViewTrans * pos;
	
	
	// base
	v_color = gl_Color;
    v_texCoord0 = a_texCoord0;
    gl_Position = u_projViewTrans * u_worldTrans * vec4(a_position, 1.0);
}
