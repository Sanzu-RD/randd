
varying vec4 v_pos;

uniform float u_time;

bool activateCheckers = true;
bool activateOutline = true;
bool activateWater = true;

float shade = 0.25f; // shade difference for chess/checkers pattern
float outlineWidth = 0.02; // grid lines width
vec4 outlineColor = vec4(0, 0, 0, 1); // color of grid lines
float waterlevel = 0.3; // Z-level for the water plane

#define NUM_OCTAVES 6

	

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







float random (in vec2 _st) {
    return fract(sin(dot(_st.xy,
                         vec2(12.9898,78.233)))*
        43758.5453123);
}

// Based on Morgan McGuire @morgan3d
// https://www.shadertoy.com/view/4dS3Wd
float noise (in vec2 _st) {
    vec2 i = floor(_st);
    vec2 f = fract(_st);

    // Four corners in 2D of a tile
    float a = random(i);
    float b = random(i + vec2(1.0, 0.0));
    float c = random(i + vec2(0.0, 1.0));
    float d = random(i + vec2(1.0, 1.0));

    vec2 u = f * f * (3.0 - 2.0 * f);

    return mix(a, b, u.x) +
            (c - a)* u.y * (1.0 - u.x) +
            (d - b) * u.x * u.y;
}

float fbm ( in vec2 _st) {
    float v = 0.0;
    float a = 0.5;
    vec2 shift = vec2(100.0);
    // Rotate to reduce axial bias
    mat2 rot = mat2(cos(0.5), sin(0.5),
                    -sin(0.5), cos(0.50));
    for (int i = 0; i < NUM_OCTAVES; ++i) {
        v += a * noise(_st);
        _st = rot * _st * 2.0 + shift;
        a *= 0.5;
    }
    return v;
}


// All components are in the range [0…1], including hue.
vec3 rgb2hsv(vec3 c)
{
    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));

    float d = q.x - min(q.w, q.y);
    float e = 1.0e-10;
    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}







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



	vec4 floored = floor(v_pos);

	// checkers shader
	if(activateCheckers){
		if(v_pos.z <= 1){ // && (!activateWater || (activateWater && v_pos.z > waterlevel)) ) {
			float m = mod(floored.x + floored.y, 2);
			m *= shade;
			m += (1 - shade);
			gl_FragColor *= vec4(m, m, m, 1);
		}
	}

	// increase intensity (black becomes gray)
	//gl_FragColor += 0.5;

	// shadow map shader
	//gl_FragColor = texture2D(u_shadowTexture, v_shadowMapUv.xy);

	// gradiant shader
	//gl_FragColor *= normalize(vec4(v_pos) * vec4(1, 1, 1, 1)) ;

	//gl_FragColor -= getShadow();

	// outline shader
	if(activateOutline && v_pos.z != waterlevel){
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
	
	// water shader
	if(activateWater){
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
		    //if(gl_FragColor.r < thres && gl_FragColor.g < thres && gl_FragColor.b < thres) {
		    //	gl_FragColor.a = 0.0;
		    //}
		    		    
		    		    
		    // mod
		    //gl_FragColor += vec4(normalize(vec3(52, 161, 235)), 0.8);
		    
		}
	}
	
	
	//gl_FragColor = vec4(1, 0, 0, 1);

}
