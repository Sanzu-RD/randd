
// header

#ifdef GL_ES 
precision mediump float;
#endif

// base vars

uniform vec3 u_colorU;
uniform vec3 u_colorV;
varying vec2 v_texCoord0;
varying vec4 v_color;


// test diffuse
varying vec2 v_diffuseUV;
uniform sampler2D u_diffuseTexture;
uniform vec4 u_diffuseColor;

// test more
varying vec4 v_pos;
uniform mat4 u_projViewTrans;
uniform mat3 u_normalMatrix;
varying vec3 v_normal;

// ssao
uniform vec3 u_samples[];
uniform sampler2D u_noiseTexture;
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
	ssao();
	
	vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV) * u_diffuseColor * v_color;
	gl_FragColor = diffuse;
	
	//gl_FragColor = v_color;
	
	
	
	
	
	
	
	
	
    //gl_FragColor = vec4(v_texCoord0, 0.0, 1.0);
    // gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);

   // gl_FragColor = vec4(v_texCoord0.x * u_colorU + v_texCoord0.y * u_colorV, 1.0);
    //gl_FragColor = vec4(u_colorU, 1.0); // v_color; //vec4(v_color.rgb , 1.0);
}
