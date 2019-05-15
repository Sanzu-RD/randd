
#ifdef GL_ES
	#define LOWP lowp
	precision mediump float;
#else
	#define LOWP 
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;


void main() {
	gl_FragColor = v_color * texture2D(u_texture, v_texCoords);

	float width = 3000; //1920.0;
	float height = 2000; //1080.0;

	/*vec4 top         = texture2D(u_texture, vec2(v_texCoords.x, v_texCoords.y + 1.0 / height));
	vec4 bottom      = texture2D(u_texture, vec2(v_texCoords.x, v_texCoords.y - 1.0 / height));
	vec4 left        = texture2D(u_texture, vec2(v_texCoords.x - 1.0 / width, v_texCoords.y));
	vec4 right       = texture2D(u_texture, vec2(v_texCoords.x + 1.0 / width, v_texCoords.y));
	vec4 topLeft     = texture2D(u_texture, vec2(v_texCoords.x - 1.0 / width, v_texCoords.y + 1.0 / height));
	vec4 topRight    = texture2D(u_texture, vec2(v_texCoords.x + 1.0 / width, v_texCoords.y + 1.0 / height));
	vec4 bottomLeft  = texture2D(u_texture, vec2(v_texCoords.x - 1.0 / width, v_texCoords.y - 1.0 / height));
	vec4 bottomRight = texture2D(u_texture, vec2(v_texCoords.x + 1.0 / width, v_texCoords.y - 1.0 / height));
	vec4 sx = -topLeft - 2 * left - bottomLeft + topRight   + 2 * right  + bottomRight;
	vec4 sy = -topLeft - 2 * top  - topRight   + bottomLeft + 2 * bottom + bottomRight;
	vec4 sobel = sqrt(sx * sx + sy * sy);
	gl_FragColor = sobel;
*/

	//gl_FragColor = vec4(1, 0, 0, 1);
}
