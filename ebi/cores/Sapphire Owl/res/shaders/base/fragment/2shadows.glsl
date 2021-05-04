
#ifdef shadowMapFlag

// original file : 
// https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/graphics/g3d/shaders/default.fragment.glsl
// shadowmaps : 
// https://lwjglgamedev.gitbooks.io/3d-game-development-with-lwjgl/content/chapter18/chapter18.html
// http://www.opengl-tutorial.org/intermediate-tutorials/tutorial-16-shadow-mapping/


float getShadowness(vec2 offset)
{
    // EDIT
    float bias = 0.002f;
    
    const vec4 bitShifts = vec4(1.0, 1.0 / 255.0, 1.0 / 65025.0, 1.0 / 16581375.0);

	//return texture(u_shadowTexture, v_shadowMapUv.xy + offset).r; 

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
	//return (getShadowness(vec2(0,0)) +
	//			getShadowness(vec2(pcfOffset, pcfOffset)) +
	//			getShadowness(vec2(-pcfOffset, pcfOffset)) +
	//			getShadowness(vec2(pcfOffset, -pcfOffset)) +
	//			getShadowness(vec2(-pcfOffset, -pcfOffset))) * 0.25;
				
				
	int radius = 1;
    float shadowFactor = 0.0;
	vec2 inc = 1.0 / textureSize(u_shadowTexture, 0);
	for(int row = -radius; row <= radius; ++row)
	{
	    for(int col = -radius; col <= radius; ++col)
	    {
	        shadowFactor += getShadowness(vec2(row, col) * inc); 
	    }    
	}
	shadowFactor /= ((radius * 2 + 1) * (radius * 2 + 1)); //9.0;
	return shadowFactor;

	//return (//getShadowness(vec2(0,0)) +
	//		getShadowness(vec2(u_shadowPCFOffset, u_shadowPCFOffset)) +
	//		getShadowness(vec2(-u_shadowPCFOffset, u_shadowPCFOffset)) +
	//		getShadowness(vec2(u_shadowPCFOffset, -u_shadowPCFOffset)) +
	//		getShadowness(vec2(-u_shadowPCFOffset, -u_shadowPCFOffset))) * 0.25;
}

#endif //shadowMapFlag