package com.souchy.randd.tools.mapeditor.shader;

import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Renderable;

public class ShaderUtils {

	private final static Attributes tmpAttributes = new Attributes();

	public static final Attributes combineAttributes (final Renderable renderable) {
		tmpAttributes.clear();
		if (renderable.environment != null) tmpAttributes.set(renderable.environment);
		if (renderable.material != null) tmpAttributes.set(renderable.material);
		return tmpAttributes;
	}

	public static final long combineAttributeMasks (final Renderable renderable) {
		long mask = 0;
		if (renderable.environment != null) mask |= renderable.environment.getMask();
		if (renderable.material != null) mask |= renderable.material.getMask();
		return mask;
	}
	
	static final String createPrefix(Renderable r, Shader2Config c) {
		String prefix = "";
		for(var m : c.modules)
			prefix += m.prefixFlags(r, c);
		return prefix;
	}
	
	public static final boolean and (final long mask, final long flag) {
		return (mask & flag) == flag;
	}

	public static final boolean or (final long mask, final long flag) {
		return (mask & flag) != 0;
	}
	
	public static final boolean hasAttribute(long mask, long type) {
		return type != 0 && (mask & type) == type;
	}
	
}
