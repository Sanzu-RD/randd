
//uniform sampler2D ssaoInput;


#ifdef GL_ES 
precision mediump float;
#endif

uniform vec3 u_colorU;
uniform vec3 u_colorV;

varying vec2 v_texCoord0;

varying vec4 v_color;

void main() {
	/*
    vec2 texelSize = 1.0 / vec2(textureSize(ssaoInput, 0));
    float result = 0.0;
    for (int x = -2; x < 2; ++x) {
        for (int y = -2; y < 2; ++y) {
            vec2 offset = vec2(float(x), float(y)) * texelSize;
            result += texture(ssaoInput, TexCoords + offset).r;
        }
    }
    FragColor = result / (4.0 * 4.0);
	*/

    // gl_FragColor = vec4(v_texCoord0, 0.0, 1.0);
    // gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);


    // gl_FragColor = vec4(v_texCoord0.x * u_colorU + v_texCoord0.y * u_colorV, 1.0);
    gl_FragColor = vec4(u_colorU, 1.0);// v_color; //vec4(v_color.rgb , 1.0);
}
