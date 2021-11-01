package com.souchy.randd.tools.mapeditor.shader;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;


/**
 * The code in a shader can be split in 3 parts : <br>
 * - the variable definitions, <br>
 * - the functions, <br>
 * - and the code to call in main <br>
 * <p>
 * 
 * This represents 1 shader total code. It can be either a fragment or vertex shader. <br>
 * It can be loaded from a directory or directly from a single file. <br>
 * <br>
 * 
 * Directory mode will concatenate all files in that folder, looking principaly for "define" and "main"-named files. <br>
 * <br>
 * File mode will split a file based on tags for "define" and "main" or just take the whole text directly if there are no tags. <br>
 * The tags need to be before AND after whatever code needs to be tagged.
 * 
 * 
 * @author Blank
 * @date 1 nov. 2021
 */
public class ShaderPart {
	
	/** variables definitions */
	public String define = "";
	/** functions definitions */
	public String library = "";
	/** functions to call in the main method */
	public String main = "";
	/** children shader parts */
	public List<ShaderPart> children = new ArrayList<>();
	
	/** compiled code (concatenated define + library + main) */
	public String compiled = "";
	
	
	/**
	 * 
	 */
	public ShaderPart load(String path) {
		if(path.isBlank()) return this;
		var handle = Gdx.files.internal(path);
		if(handle.isDirectory()) {
			for(var f : handle.list()) {
				if(f.name().contains("define"))
					define += f.readString();
				else
				if(f.name().contains("main"))
					main += f.readString();
				else
//				if(f.name().contains("library"))
					//library += f.readString(); // library of functions contains any other file, in alphabetical order
					parse(f.readString());
			}
		} else {
			parse(handle.readString());
		}
		compile();
		return this;
	}
	
	/** parse a file */
	public void parse(String code) {
		// if there is no tag, just put everything in the function library
		if(!code.contains("// ---------------------------------------------------------------")) {
			library += code;
			return;
		}
		
		// define tag
		var definetag = code.split("// --------------------------------------------------------------- define");
		if(definetag.length > 1)
			define += definetag[definetag.length - 2];
		
		// functions tag
		var functiontag = code.split("// --------------------------------------------------------------- library");
		if(functiontag.length > 1)
			library += functiontag[functiontag.length - 2];
		
		// main tag
		var maintag = code.split("// --------------------------------------------------------------- main");
		if(maintag.length > 1)
			main += maintag[maintag.length - 2];
	}
	
	public String compile() {
		String s = "";
		// definitions
		s += define + "\n\n";
		for(var c : children) 
			s += c.define + "\n\n";
		// functions
		s += library + "\n\n";
		for(var c : children) 
			s += c.library + "\n\n";
		// main method
		if(main != "") {
			s += "void main() {\n";
			s += main + "\n\n";
			for(var c : children) 
				s += c.main + "\n\n";
			s += "}";
		}
		// 
		return compiled = s;
	}
	
	
}
