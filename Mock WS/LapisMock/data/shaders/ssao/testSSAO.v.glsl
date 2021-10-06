
// main vars

attribute vec3 a_position; // local pos to object
attribute vec3 a_normal;
attribute vec2 a_texCoord0;

uniform mat4 u_worldTrans; // transform local to world
uniform mat4 u_projViewTrans; // transform world to clip

varying vec2 v_texCoord0;
varying vec4 v_color;
varying vec3 v_pos;


// test diffuse 
uniform vec4 u_diffuseUVTransform;
varying vec2 v_diffuseUV;
void testDiffuse(){
	v_diffuseUV = u_diffuseUVTransform.xy + a_texCoord0 * u_diffuseUVTransform.zw;
}

// test more
varying vec4 v_worldpos;
varying vec3 v_normal;
uniform mat3 u_normalMatrix;
uniform mat4 u_projViewWorldTrans;
varying float v_depth;

uniform mat4 u_shadowMapProjViewTrans;
varying vec3 v_shadowMapUv;

// main

void main() {
	testDiffuse();
	v_normal = normalize(u_normalMatrix * a_normal);
	v_pos = a_position;
	
	vec4 pos = u_worldTrans * vec4(a_position, 1.0);
	//gl_Position = u_projViewTrans * pos;
	
	// pass the pos to the fragment shader // couldnt i just use gl_Position??
	v_worldpos =  pos;
	
	vec4 posdepth = u_projViewWorldTrans * vec4(a_position, 1.0);
	v_depth = posdepth.z / posdepth.w * 0.5 + 0.5;
	
	
	#ifdef shadowMapFlag
	 	// project vertex pos to shadowmap pos
		vec4 spos = u_shadowMapProjViewTrans * pos;
		// convert clip space to screen space          // convert screenspace to texturespace pos
		//v_shadowMapUv.xy = (spos.xy / spos.w) * 0.5 + 0.5;
		//v_shadowMapUv.z = min(spos.z * 0.5 + 0.5, 0.998);
		
		v_shadowMapUv.xyz = (spos.xyz / spos.w) * 0.5 + 0.5;
		v_shadowMapUv.z = min(v_shadowMapUv.z, 0.998);
	#endif //shadowMapFlag
	
	
	
	
	
	
	// base
	v_color = gl_Color;
    v_texCoord0 = a_texCoord0;
    gl_Position = u_projViewTrans * u_worldTrans * vec4(a_position, 1.0);
}
