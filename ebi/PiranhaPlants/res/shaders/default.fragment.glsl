
varying vec4 v_pos;

#ifdef GL_ES
#define LOWP lowp
#define MED mediump
#define HIGH highp
precision mediump float;
#else
#define MED
#define LOWP
#define HIGH
#endif

#if defined(specularTextureFlag) || defined(specularColorFlag)
#define specularFlag
#endif

#ifdef normalFlag
varying vec3 v_normal;
#endif //normalFlag

#if defined(colorFlag)
varying vec4 v_color;
#endif

#ifdef blendedFlag
varying float v_opacity;
#ifdef alphaTestFlag
varying float v_alphaTest;
#endif //alphaTestFlag
#endif //blendedFlag

#if defined(diffuseTextureFlag) || defined(specularTextureFlag) || defined(emissiveTextureFlag)
#define textureFlag
#endif

#ifdef diffuseTextureFlag
varying MED vec2 v_diffuseUV;
#endif

#ifdef specularTextureFlag
varying MED vec2 v_specularUV;
#endif

#ifdef emissiveTextureFlag
varying MED vec2 v_emissiveUV;
#endif

#ifdef diffuseColorFlag
uniform vec4 u_diffuseColor;
#endif

#ifdef diffuseTextureFlag
uniform sampler2D u_diffuseTexture;
#endif

#ifdef specularColorFlag
uniform vec4 u_specularColor;
#endif

#ifdef specularTextureFlag
uniform sampler2D u_specularTexture;
#endif

#ifdef normalTextureFlag
uniform sampler2D u_normalTexture;
#endif

#ifdef emissiveColorFlag
uniform vec4 u_emissiveColor;
#endif

#ifdef emissiveTextureFlag
uniform sampler2D u_emissiveTexture;
#endif

#ifdef lightingFlag
varying vec3 v_lightDiffuse;

#if	defined(ambientLightFlag) || defined(ambientCubemapFlag) || defined(sphericalHarmonicsFlag)
#define ambientFlag
#endif //ambientFlag

#ifdef specularFlag
varying vec3 v_lightSpecular;
#endif //specularFlag

#ifdef shadowMapFlag
uniform sampler2D u_shadowTexture;
uniform float u_shadowPCFOffset;
varying vec3 v_shadowMapUv;
#define separateAmbientFlag

float getShadowness(vec2 offset)
{
    const vec4 bitShifts = vec4(1.0, 1.0 / 255.0, 1.0 / 65025.0, 1.0 / 16581375.0);

    // EDIT
    float bias = 0.005f;

	//if(v_shadowMapUv.z - bias < texture(u_shadowTexture, v_shadowMapUv.xy).r){
	//	return 1;
	//}
	//return 0;
	
    return step(v_shadowMapUv.z - bias, dot(texture2D(u_shadowTexture, v_shadowMapUv.xy + offset), bitShifts)); //+(1.0/255.0));
}

float getShadow()
{
//	u_shadowPCFOffset = 0.01;
//	vec2 poissonDisk[4] = vec2[4](
//	  vec2( -0.94201624, -0.39906216 ),
//	  vec2( 0.94558609, -0.76890725 ),
//	  vec2( -0.094184101, -0.92938870 ),
//	  vec2( 0.34495938, 0.29387760 )
//	);
//	return poissonDisk;

	float pcfOffset = u_shadowPCFOffset;
	
	// average des 4 coins genre
	return (//getShadowness(vec2(0,0)) +
				getShadowness(vec2(pcfOffset, pcfOffset)) +
				getShadowness(vec2(-pcfOffset, pcfOffset)) +
				getShadowness(vec2(pcfOffset, -pcfOffset)) +
				getShadowness(vec2(-pcfOffset, -pcfOffset))) * 0.25;

	//return (//getShadowness(vec2(0,0)) +
	//		getShadowness(vec2(u_shadowPCFOffset, u_shadowPCFOffset)) +
	//		getShadowness(vec2(-u_shadowPCFOffset, u_shadowPCFOffset)) +
	//		getShadowness(vec2(u_shadowPCFOffset, -u_shadowPCFOffset)) +
	//		getShadowness(vec2(-u_shadowPCFOffset, -u_shadowPCFOffset))) * 0.25;
}
#endif //shadowMapFlag

#if defined(ambientFlag) && defined(separateAmbientFlag)
varying vec3 v_ambientLight;
#endif //separateAmbientFlag

#endif //lightingFlag

#ifdef fogFlag
uniform vec4 u_fogColor;
varying float v_fog;
#endif // fogFlag

void main() {
	#if defined(normalFlag)
		vec3 normal = v_normal;
	#endif // normalFlag

	#if defined(diffuseTextureFlag) && defined(diffuseColorFlag) && defined(colorFlag)
		vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV) * u_diffuseColor * v_color;
	#elif defined(diffuseTextureFlag) && defined(diffuseColorFlag)
		vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV) * u_diffuseColor;
	#elif defined(diffuseTextureFlag) && defined(colorFlag)
		vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV) * v_color;
	#elif defined(diffuseTextureFlag)
		vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV);
	#elif defined(diffuseColorFlag) && defined(colorFlag)
		vec4 diffuse = u_diffuseColor * v_color;
	#elif defined(diffuseColorFlag)
		vec4 diffuse = u_diffuseColor;
	#elif defined(colorFlag)
		vec4 diffuse = v_color;
	#else
		vec4 diffuse = vec4(1.0);
	#endif


	#if defined(emissiveTextureFlag) && defined(emissiveColorFlag)
		vec4 emissive = texture2D(u_emissiveTexture, v_emissiveUV) * u_emissiveColor;
	#elif defined(emissiveTextureFlag)
		vec4 emissive = texture2D(u_emissiveTexture, v_emissiveUV);
	#elif defined(emissiveColorFlag)
		vec4 emissive = u_emissiveColor;
	#else
		vec4 emissive = vec4(0.0);
	#endif


	#if (!defined(lightingFlag))
		gl_FragColor.rgb = diffuse.rgb + emissive.rgb;
	#elif (!defined(specularFlag))
		#if defined(ambientFlag) && defined(separateAmbientFlag)
			#ifdef shadowMapFlag
				gl_FragColor.rgb = (diffuse.rgb * (v_ambientLight + getShadow() * v_lightDiffuse)) + emissive.rgb;
				//gl_FragColor.rgb = (diffuse.rgb * (v_ambientLight + getShadow() * v_lightDiffuse)) + emissive.rgb;
				//gl_FragColor.rgb = texture2D(u_shadowTexture, v_shadowMapUv.xy);
			#else
				gl_FragColor.rgb = (diffuse.rgb * (v_ambientLight + v_lightDiffuse)) + emissive.rgb;
			#endif //shadowMapFlag
		#else
			#ifdef shadowMapFlag
				gl_FragColor.rgb = getShadow() * (diffuse.rgb * v_lightDiffuse) + emissive.rgb;
			#else
				gl_FragColor.rgb = (diffuse.rgb * v_lightDiffuse) + emissive.rgb;
			#endif //shadowMapFlag
		#endif
	#else
		#if defined(specularTextureFlag) && defined(specularColorFlag)
			vec3 specular = texture2D(u_specularTexture, v_specularUV).rgb * u_specularColor.rgb * v_lightSpecular;
		#elif defined(specularTextureFlag)
			vec3 specular = texture2D(u_specularTexture, v_specularUV).rgb * v_lightSpecular;
		#elif defined(specularColorFlag)
			vec3 specular = u_specularColor.rgb * v_lightSpecular;
		#else
			vec3 specular = v_lightSpecular;
		#endif

		#if defined(ambientFlag) && defined(separateAmbientFlag)
			#ifdef shadowMapFlag
			gl_FragColor.rgb = (diffuse.rgb * (getShadow() * v_lightDiffuse + v_ambientLight)) + specular + emissive.rgb;
				//gl_FragColor.rgb = texture2D(u_shadowTexture, v_shadowMapUv.xy);
			#else
				gl_FragColor.rgb = (diffuse.rgb * (v_lightDiffuse + v_ambientLight)) + specular + emissive.rgb;
			#endif //shadowMapFlag
		#else
			#ifdef shadowMapFlag
				gl_FragColor.rgb = getShadow() * ((diffuse.rgb * v_lightDiffuse) + specular) + emissive.rgb;
			#else
				gl_FragColor.rgb = (diffuse.rgb * v_lightDiffuse) + specular + emissive.rgb;
			#endif //shadowMapFlag
		#endif
	#endif //lightingFlag

	#ifdef fogFlag
		gl_FragColor.rgb = mix(gl_FragColor.rgb, u_fogColor.rgb, v_fog);
	#endif // end fogFlag

	#ifdef blendedFlag
		gl_FragColor.a = diffuse.a * v_opacity;
		#ifdef alphaTestFlag
			if (gl_FragColor.a <= v_alphaTest)
				discard;
		#endif
	#else
		gl_FragColor.a = 1.0;
	#endif




	// checkers shader
	vec4 floored = floor(v_pos);
	if(v_pos.z <= 1){
		float shade = 0.2f;
		float m = mod(floored.x + floored.y, 2);
		m *= shade;
		m += (1 - shade);
		gl_FragColor *= vec4(m, m, m, 1);
	}

	// increase intensity (black becomes gray)
	//gl_FragColor += 0.5;

	// shadow map shader
	//gl_FragColor = texture2D(u_shadowTexture, v_shadowMapUv.xy);

	// gradiant shader
	//gl_FragColor *= normalize(vec4(v_pos) * vec4(1, 1, 1, 1)) ;

	//gl_FragColor -= getShadow();

	// outline shader
	if(false){
		float outlineWidth = 0.02;
		float dx = abs(floored.x - v_pos.x);
		float dy = abs(floored.y - v_pos.y);
		float dz = abs(floored.z - v_pos.z);
		//float b = abs(floored.x + 1 - v_pos.x);
		bool inX = abs(floored.x - v_pos.x) < outlineWidth || abs(floored.x + 1 - v_pos.x) < outlineWidth;
		bool inY = abs(floored.y - v_pos.y) < outlineWidth || abs(floored.y + 1 - v_pos.y) < outlineWidth;
		bool inZ = abs(floored.z - v_pos.z) < outlineWidth || abs(floored.z + 1 - v_pos.z) < outlineWidth;
		if(  (inZ && (inX || inY))  ||  inY && inX ){
			gl_FragColor = vec4(0, 0, 0, 1);
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
	
	
	//gl_FragColor = vec4(1, 0, 0, 1);

}
