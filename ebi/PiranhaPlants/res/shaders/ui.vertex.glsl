
attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;
attribute float a_TextureType;

uniform mat4 u_projTrans;

varying vec4 v_color;
varying vec2 v_texCoords;
varying float v_TextureType;

void main() {
	v_color = a_color;
	v_color.a = v_color.a * (255.0 / 254.0);
	v_texCoords = a_texCoord0;
	v_TextureType = a_TextureType;
	
	gl_Position = u_projTrans * a_position;
}
