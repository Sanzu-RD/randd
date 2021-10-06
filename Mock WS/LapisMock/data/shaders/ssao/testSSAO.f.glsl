
// header

#ifdef GL_ES 
precision mediump float;
#endif

// base vars
varying vec3 v_normal;
varying vec2 v_texCoord0;
varying vec4 v_color;

// custom
varying vec3 v_pos;
varying vec4 v_worldpos; 
varying float v_depth;

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


// ???????????
uniform vec3 u_colorU;
uniform vec3 u_colorV;


// test diffuse
varying vec2 v_diffuseUV;
uniform sampler2D u_diffuseTexture;
uniform vec4 u_diffuseColor;

// shadows
//uniform mat4 u_shadowMapProjViewTrans;
//varying vec3 v_shadowMapUv;
//uniform sampler2D u_shadowTexture;
//uniform float u_shadowPCFOffset;


// ssao
uniform int u_samplesSize;
uniform vec3 u_samples[192]; // u_samplesSize*3];
uniform sampler2D u_noiseTexture;
uniform sampler2D u_depthMap;
uniform mat4 u_depthMapTrans;



void ssao(){
	/*
    vec2 texelSize = 1.0 / vec2(textureSize(ssaoInput, 0));
    float result = 0.0;
    for (int x = -2; x < 2; ++x) {
        for (int y = -2; y < 2; ++y) {
            vec2 offset = vec2(float(x), float(y)) * texelSize;
            result += texture(ssaoInput, TexCoords + offset).r;
        }
    }
    gl_FragColor = result / (4.0 * 4.0);
	*/
}

// main

void main() {
	vec4 g1 = u_projViewTrans * vec4(v_pos, 1.0);	// vec4 pos = u_worldTrans * vec4(a_position, 1.0);
	vec4 g2 = g1; 									// vec4 spos = u_shadowMapProjViewTrans * pos;
	vec3 v_depthUv = (g2.xyz / g2.w) * 0.5 + 0.5; 	// v_shadowMapUv.xyz = (spos.xyz / spos.w) * 0.5 + 0.5;
	//vec4 g3 = texture(u_depthMap, v_depthUv.xy);	// texture(u_shadowTexture, v_shadowMapUv.xy).r
		

	vec4 finalColor = vec4(0, 0, 0, 1);

	ssao();
	
	vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV) * u_diffuseColor * v_color;
	finalColor = diffuse;
	
	//gl_FragColor = v_color;
	
	//gl_FragColor = finalColor;
	if(gl_FragCoord.x > 2 && gl_FragCoord.x < 500){
	//	gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
	}
	
	gl_FragColor = diffuse;
	
	vec2 noiseScale = vec2(1600.0/4.0, 900.0/4.0); // screen/4 because the noise texture is 4x4
	
	vec2 coords = v_depthUv.xy; // gl_FragCoord; // v_texCoord0
	vec3 origin = texture2D(u_depthMap, coords).xyz; // gl_FragCoord; // fragPos // vec3(v_worldpos.x, v_worldpos.y, v_worldpos.z); // 
	// normal = v_normal
	vec3 rvec = texture2D(u_noiseTexture, coords * noiseScale).xyz; // * 2.0 - 1.0;
	vec3 tangent = normalize(rvec - v_normal * dot(rvec, v_normal));
	vec3 bitangent = cross(v_normal, tangent);
	mat3 tbn = mat3(tangent, bitangent, v_normal);
	
	float occlusion = 0.0;
	float uRadius = 0.1f;
	float bias = 0.002f;
	for (int i = 0; i < 64; i++) { // u_samplesSize
		// get sample position:
	    vec3 sample = tbn * u_samples[i];
	   	sample = sample * uRadius + origin;
	  	
	   	// project sample position:
	   	vec4 offset = vec4(sample, 1.0); 
	   	offset = offset * u_projTrans; // from view to clip-space  // u_viewTrans // u_projTrans //u_projViewTrans; // uProjectionMat // u_projViewWorldTrans // u_worldTrans // u_shadowMapProjViewTrans
	   	if(false){ 
		   	offset.xy /= offset.w;
		   	offset.xy = offset.xy * 0.5 + 0.5;
		   	
		   	// get sample depth:
		   	float sampleDepth = texture2D(u_depthMap, offset.xy).r; // uTexLinearDepth // v_worldpos.z; // u_depthMap // u_shadowTexture
	   		
	   		// range check & accumulate:
		   	float rangeCheck = abs(origin.z - sampleDepth) < uRadius ? 1.0 : 0.0;
		   	occlusion += (sampleDepth <= sample.z ? 1.0 : 0.0) * rangeCheck;
	   	} else {
		   	offset.xyz /= offset.w;               // perspective divide
			offset.xyz  = offset.xyz * 0.5 + 0.5; // transform to range 0.0 - 1.0  
			
		   	// get sample depth:
		   	float sampleDepth = texture2D(u_depthMap, offset.xy).z; // uTexLinearDepth // v_worldpos.z; // u_depthMap // u_shadowTexture
		   	
	   		// range check & accumulate:
		   	float rangeCheck = smoothstep(0.0, 1.0, uRadius / abs(origin.z - sampleDepth));
		   	occlusion += (sampleDepth >= sample.z + bias ? 1.0 : 0.0) * rangeCheck;     
	   	}
	}
	occlusion /= 64.0;
	occlusion = 1.0 - occlusion;

	
	gl_FragColor = occlusion;
	//gl_FragColor = diffuse* occlusion;
	
	if(true){
		return;
	}
	
	if(v_worldpos.x >= 2.5 && v_worldpos.x <= 5.5){
		gl_FragColor = diffuse * occlusion;
	}
	if(v_worldpos.x >= 6.5 && v_worldpos.x <= 9.5){
		gl_FragColor = vec4(occlusion, 0, 0, 1);
	}
	
	if(v_worldpos.y >= 0 && v_worldpos.y <= 2.5){
		vec4 pos = u_worldTrans * vec4(v_pos, 1.0);
		vec4 sp = pos;
		gl_FragColor = vec4(round((sp.x-7)) / 12, 0, 0, 1);
	}
	
	if(v_worldpos.y >= 2.5 && v_worldpos.y <= 6.5){
		// world pos
		vec4 wp = vec4(normalize(v_worldpos.xyz), 1.0); 
		vec4 sp = texture2D(u_depthMap, gl_FragCoord); // v_texCoord0 // gl_FragCoord
		vec4 ss = vec4(normalize(sp.xyz), 1.0);
		
		// camera pos
		vec4 pp = u_projViewTrans * vec4(v_pos, 1.0) * 0.5 + 0.5;
		vec4 pos = vec4(normalize(pp.xyz), 1);
		
		//
		//vec4 g1 = u_projViewTrans * vec4(v_pos, 1.0);						// vec4 pos = u_worldTrans * vec4(a_position, 1.0);
		//vec4 g2 = g1; 									// vec4 spos = u_shadowMapProjViewTrans * pos;
		//vec3 v_depthUv = (g2.xyz / g2.w) * 0.5 + 0.5; 	// v_shadowMapUv.xyz = (spos.xyz / spos.w) * 0.5 + 0.5;
		vec4 g3 = texture(u_depthMap, v_depthUv.xy);	// texture(u_shadowTexture, v_shadowMapUv.xy).r
		
		// set
		gl_FragColor = g3; 
		
		//vec4 mm = vec4(v_pos, 1.0); 
		//gl_FragColor = vec4(round((mm.x)) / 19, round((mm.y)) / 19, 0, 1);
	}
	
		
	
	// depth = u_projViewTrans * u_worldTrans * texture2D(u_depthMap, v_texCoord0);
	
	
	
	
	
	/*
	vec3 sp = texture2D(u_shadowTexture, v_shadowMapUv);
	vec4 shadowpos = vec4(sp, 1) * u_shadowMapProjViewTrans;
	
	//if(shadowpos.x > 0.3f && shadowpos.x < 0.5f){
		gl_FragColor = shadowpos; // vec4(shadowpos, 1);
		//float currentDepth = shadowpos.z;
		//gl_FragColor = vec4(currentDepth, currentDepth, currentDepth, 1);
	//}
	
	vec4 spos = u_projViewWorldTrans * v_worldpos;
		
	float currentDepth = spos.y;
	gl_FragColor = vec4(spos.x, spos.y, spos.z, 1);
	*/
	
	
	
	
	
    //gl_FragColor = vec4(v_texCoord0, 0.0, 1.0);
    //gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);

    //gl_FragColor = vec4(v_texCoord0.x * u_colorU + v_texCoord0.y * u_colorV, 1.0);
    //gl_FragColor = vec4(u_colorU, 1.0); // v_color; //vec4(v_color.rgb , 1.0);
}
