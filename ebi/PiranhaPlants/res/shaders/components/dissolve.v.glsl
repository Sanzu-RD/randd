
#ifdef dissolveFlag

attribute vec2 a_texCoord1;

// a_DissolveTexture


varying vec2 v_dissolveUV;
uniform vec4 u_dissolveUVTransform;

void dissolveVertex(vec2 texCoords){
	// xy = offset
	// zw = scale
	
	//texCoords = step(0.5f, texCoords);
	
	
	
	v_dissolveUV = texCoords; // u_dissolveUVTransform.xy + texCoords * u_dissolveUVTransform.zw; // texCoords; // a_texCoord0
	//v_dissolveUV /= 10;
	
	// v_diffuseUV = u_diffuseUVTransform.xy + a_texCoord0 * u_diffuseUVTransform.zw;
}

#endif //dissolveFlag