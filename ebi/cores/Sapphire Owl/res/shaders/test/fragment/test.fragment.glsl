#ifdef GL_ES 
precision mediump float;
#endif

uniform vec3 u_colorU;
uniform vec3 u_colorV;

varying vec2 v_texCoord0;

varying vec4 v_color;

void main() {
    // gl_FragColor = vec4(v_texCoord0, 0.0, 1.0);
    // gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);


    // gl_FragColor = vec4(v_texCoord0.x * u_colorU + v_texCoord0.y * u_colorV, 1.0);
     gl_FragColor = vec4(u_colorU, 1.0);// v_color; //vec4(v_color.rgb , 1.0);
}
