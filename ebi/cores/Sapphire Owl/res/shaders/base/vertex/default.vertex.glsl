

void main() {

	// use the diffuse texture coords for dissolve
	#ifdef dissolveFlag
		//#ifdef diffuseTextureFlag
			dissolveVertex(a_texCoord0);
		//#endif //diffuseTextureFlag
	#endif // dissolveFlag
	
	
	#ifdef diffuseTextureFlag
		v_diffuseUV = u_diffuseUVTransform.xy + a_texCoord0 * u_diffuseUVTransform.zw;
	#endif //diffuseTextureFlag


	#ifdef specularTextureFlag
		v_specularUV = u_specularUVTransform.xy + a_texCoord0 * u_specularUVTransform.zw;
	#endif //specularTextureFlag



	#if defined(colorFlag)
		v_color = a_color;
	#endif // colorFlag

	#ifdef blendedFlag
		v_opacity = u_opacity;
		#ifdef alphaTestFlag
			v_alphaTest = u_alphaTest;
		#endif //alphaTestFlag
	#endif // blendedFlag

	#ifdef skinningFlag
		mat4 skinning = mat4(0.0);
		#ifdef boneWeight0Flag
			skinning += (a_boneWeight0.y) * u_bones[int(a_boneWeight0.x)];
		#endif //boneWeight0Flag
		#ifdef boneWeight1Flag
			skinning += (a_boneWeight1.y) * u_bones[int(a_boneWeight1.x)];
		#endif //boneWeight1Flag
		#ifdef boneWeight2Flag
			skinning += (a_boneWeight2.y) * u_bones[int(a_boneWeight2.x)];
		#endif //boneWeight2Flag
		#ifdef boneWeight3Flag
			skinning += (a_boneWeight3.y) * u_bones[int(a_boneWeight3.x)];
		#endif //boneWeight3Flag
		#ifdef boneWeight4Flag
			skinning += (a_boneWeight4.y) * u_bones[int(a_boneWeight4.x)];
		#endif //boneWeight4Flag
		#ifdef boneWeight5Flag
			skinning += (a_boneWeight5.y) * u_bones[int(a_boneWeight5.x)];
		#endif //boneWeight5Flag
		#ifdef boneWeight6Flag
			skinning += (a_boneWeight6.y) * u_bones[int(a_boneWeight6.x)];
		#endif //boneWeight6Flag
		#ifdef boneWeight7Flag
			skinning += (a_boneWeight7.y) * u_bones[int(a_boneWeight7.x)];
		#endif //boneWeight7Flag
	#endif //skinningFlag

	#ifdef skinningFlag
		vec4 pos = u_worldTrans * skinning * vec4(a_position, 1.0);
	#else
		vec4 pos = u_worldTrans * vec4(a_position, 1.0);
	#endif

	// pass the pos to the fragment shader // couldnt i just use gl_Position??
	v_pos =  pos;

	gl_Position = u_projViewTrans * pos;

	#ifdef shadowMapFlag
	 	// project vertex pos to shadowmap pos
		vec4 spos = u_shadowMapProjViewTrans * pos;
		// convert screenspace to texturespace pos
		//v_shadowMapUv.xy = (spos.xy / spos.w) * 0.5 + 0.5;
		//v_shadowMapUv.z = min(spos.z * 0.5 + 0.5, 0.998);
		
		v_shadowMapUv.xyz = (spos.xyz / spos.w) * 0.5 + 0.5;
		v_shadowMapUv.z = min(v_shadowMapUv.z, 0.998);
	#endif //shadowMapFlag

	#if defined(normalFlag)
		#if defined(skinningFlag)
			vec3 normal = normalize((u_worldTrans * skinning * vec4(a_normal, 0.0)).xyz);
		#else
			vec3 normal = normalize(u_normalMatrix * a_normal);
		#endif
		v_normal = normal;
	#endif // normalFlag

    #ifdef fogFlag
        vec3 flen = u_cameraPosition.xyz - pos.xyz;
        float fog = dot(flen, flen) * u_cameraPosition.w;
        v_fog = min(fog, 1.0);
    #endif

	#ifdef lightingFlag
		#if	defined(ambientLightFlag)
        	vec3 ambientLight = u_ambientLight;
		#elif defined(ambientFlag)
        	vec3 ambientLight = vec3(0.0);
		#endif

		#ifdef ambientCubemapFlag
			vec3 squaredNormal = normal * normal;
			vec3 isPositive  = step(0.0, normal);
			ambientLight += squaredNormal.x * mix(u_ambientCubemap[0], u_ambientCubemap[1], isPositive.x) +
					squaredNormal.y * mix(u_ambientCubemap[2], u_ambientCubemap[3], isPositive.y) +
					squaredNormal.z * mix(u_ambientCubemap[4], u_ambientCubemap[5], isPositive.z);
		#endif // ambientCubemapFlag

		#ifdef sphericalHarmonicsFlag
			ambientLight += u_sphericalHarmonics[0];
			ambientLight += u_sphericalHarmonics[1] * normal.x;
			ambientLight += u_sphericalHarmonics[2] * normal.y;
			ambientLight += u_sphericalHarmonics[3] * normal.z;
			ambientLight += u_sphericalHarmonics[4] * (normal.x * normal.z);
			ambientLight += u_sphericalHarmonics[5] * (normal.z * normal.y);
			ambientLight += u_sphericalHarmonics[6] * (normal.y * normal.x);
			ambientLight += u_sphericalHarmonics[7] * (3.0 * normal.z * normal.z - 1.0);
			ambientLight += u_sphericalHarmonics[8] * (normal.x * normal.x - normal.y * normal.y);
		#endif // sphericalHarmonicsFlag

		#ifdef ambientFlag
			#ifdef separateAmbientFlag
				v_ambientLight = ambientLight;
				v_lightDiffuse = vec3(0.0);
			#else
				v_lightDiffuse = ambientLight;
			#endif //separateAmbientFlag
		#else
	        v_lightDiffuse = vec3(0.0);
		#endif //ambientFlag

			
		#ifdef specularFlag
			v_lightSpecular = vec3(0.0);
			vec3 viewVec = normalize(u_cameraPosition.xyz - pos.xyz);
		#endif // specularFlag

		#if (numDirectionalLights > 0) && defined(normalFlag)
			for (int i = 0; i < numDirectionalLights; i++) {
				vec3 lightDir = -u_dirLights[i].direction;
				float NdotL = clamp(dot(normal, lightDir), 0.0, 1.0);
				vec3 value = u_dirLights[i].color * NdotL;
				v_lightDiffuse += value;
				#ifdef specularFlag
					float halfDotView = max(0.0, dot(normal, normalize(lightDir + viewVec)));
					v_lightSpecular += value * pow(halfDotView, u_shininess);
				#endif // specularFlag
			}
		#endif // numDirectionalLights

		#if (numPointLights > 0) && defined(normalFlag)
			for (int i = 0; i < numPointLights; i++) {
				vec3 lightDir = u_pointLights[i].position - pos.xyz;
				float dist2 = dot(lightDir, lightDir);
				lightDir *= inversesqrt(dist2);
				float NdotL = clamp(dot(normal, lightDir), 0.0, 1.0);
				vec3 value = u_pointLights[i].color * (NdotL / (1.0 + dist2));
				v_lightDiffuse += value;
				#ifdef specularFlag
					float halfDotView = max(0.0, dot(normal, normalize(lightDir + viewVec)));
					v_lightSpecular += value * pow(halfDotView, u_shininess);
				#endif // specularFlag
			}
		#endif // numPointLights
	#endif // lightingFlag
}
