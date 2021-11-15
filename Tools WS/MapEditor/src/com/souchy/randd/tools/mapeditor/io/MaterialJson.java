package com.souchy.randd.tools.mapeditor.io;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.backends.lwjgl.LwjglFileHandle;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3FileHandle;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializer;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.badlogic.gdx.utils.JsonValue;

@SuppressWarnings("rawtypes")
public class MaterialJson {
	
	public static final Json js = new Json();
	
	static {
		js.setOutputType(OutputType.json);
		js.setSerializer(IntAttribute.class, new IntAttributeSerializer());
		js.setSerializer(FloatAttribute.class, new FloatAttributeSerializer());
		js.setSerializer(ColorAttribute.class, new ColorAttributeSerializer());
		js.setSerializer(TextureAttribute.class, new TextureAttributeSerializer());
		//js.setSerializer(FileHandle.class, new FileHandleSerializer());
		js.setSerializer(Lwjgl3FileHandle.class, new Lwjgl3FileHandleSerializer());
		js.setSerializer(Texture.class, new TextureSerializer());
//		js.setSerializer(TextureData.class, new TextureDataSerializer());
	}

	public static class MaterialLoaderParameters extends AssetLoaderParameters<Material> { }
	public static class MaterialLoader extends SynchronousAssetLoader<Material, MaterialLoaderParameters> {
		public MaterialLoader(FileHandleResolver resolver) {
			super(resolver);
		}
		@Override
		public Material load(AssetManager assetManager, String fileName, FileHandle file, MaterialLoaderParameters parameter) {
			return js.fromJson(Material.class, file);
		}

		@Override
		public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, MaterialLoaderParameters parameter) {
			return new Array<>();
		}
	}
	
	
	private static interface DeSerializer<T> extends Serializer<T> {
		@Override
		public default void write(Json json, T object, Class knownType) {
		    json.writeObjectStart();
		    json.writeType(object.getClass());
			json.writeFields(object);
		    json.writeObjectEnd();
		}
	}
	
	public static class FloatAttributeSerializer implements DeSerializer<FloatAttribute> {
		@Override
		public FloatAttribute read(Json json, JsonValue jsonData, Class type) {
			var attr = new FloatAttribute(jsonData.getLong("type"), jsonData.getFloat("value"));
			return attr;
		}
	}
	public static class IntAttributeSerializer implements DeSerializer<IntAttribute> {
		@Override
		public IntAttribute read(Json json, JsonValue jsonData, Class type) {
			var attr = new IntAttribute(jsonData.getLong("type"), jsonData.getInt("value"));
			return attr;
		}
	}
	
	public static class ColorAttributeSerializer implements DeSerializer<ColorAttribute> {
		@Override
		public ColorAttribute read(Json json, JsonValue jsonData, Class type) {
			var attr = new ColorAttribute(jsonData.getLong("type"), json.readValue("color", Color.class, jsonData));
			return attr;
		}
	}
	
	public static class TextureAttributeSerializer implements DeSerializer<TextureAttribute> {
		@Override
		public TextureAttribute read(Json json, JsonValue jsonData, Class type) {
			//Log.info("TextureAttributeSerializer");
			
			var texjson = jsonData.get("textureDescription").get("texture");
			//Log.info("texture :\n%s", texjson);
			var tex = json.readValue(Texture.class, texjson);
			
			var attr = new TextureAttribute(jsonData.getLong("type"), tex);
			//json.readFields(attr, jsonData);
			json.readField(attr, "offsetU", jsonData);
			json.readField(attr, "offsetV", jsonData);
			json.readField(attr, "scaleU", jsonData);
			json.readField(attr, "scaleV", jsonData);
			json.readField(attr, "uvIndex", jsonData);
			return attr;
		}
	}

	public static class FileHandleSerializer implements DeSerializer<FileHandle> {
		@Override
		public void write(Json json, FileHandle object, Class knownType) {
		    json.writeObjectStart();
		    json.writeType(object.getClass());
			json.writeValue("path", object.file().getPath());
			json.writeValue("type", object.type().name());
		    json.writeObjectEnd();
		}
		@Override
		public FileHandle read(Json json, JsonValue jsonData, Class type) {
			//Log.info("FileHandleSerializer");
			return Gdx.files.getFileHandle(jsonData.getString("path"), FileType.valueOf(jsonData.getString("type")));
		}
	}
	
	public static class Lwjgl3FileHandleSerializer implements DeSerializer<Lwjgl3FileHandle> {
		@Override
		public void write(Json json, Lwjgl3FileHandle object, Class knownType) {
		    json.writeObjectStart();
		    json.writeType(object.getClass());
			json.writeValue("path", object.file().getPath());
			json.writeValue("type", object.type().name());
		    json.writeObjectEnd();
		}
		@Override
		public Lwjgl3FileHandle read(Json json, JsonValue jsonData, Class type) {
			//Log.info("Lwjgl3FileHandleSerializer");
			return new Lwjgl3FileHandle(jsonData.getString("path"), FileType.valueOf(jsonData.getString("type")));
		}
	}

	public static class TextureSerializer implements DeSerializer<Texture> {
		@Override
		public Texture read(Json json, JsonValue jsonData, Class type) {
			try {
				var texdatajson = jsonData.get("data");
				var file = json.readValue("file", FileHandle.class, texdatajson);
				var format = texdatajson.getString("format");
				var mipmap = texdatajson.getBoolean("useMipMaps");
				
				var tex = new Texture(file, Format.valueOf(format), mipmap); 
				tex.setFilter(TextureFilter.valueOf(jsonData.getString("minFilter")), TextureFilter.valueOf(jsonData.getString("magFilter")));
				tex.setWrap(TextureWrap.valueOf(jsonData.getString("uWrap")), TextureWrap.valueOf(jsonData.getString("vWrap")));
				tex.setAnisotropicFilter(jsonData.getInt("anisotropicFilterLevel"));
				
				return tex;
			} catch(Exception e) {
				Log.error("", e);
			}
			return null;
		}
	}
	
//	public static class TextureDataSerializer implements DeSerializer<TextureData> {
//		@Override
//		public TextureData read(Json json, JsonValue jsonData, Class type) {
//			//Log.info("TextureDataSerializer");
//			FileHandle file = json.readValue("file", FileHandle.class, jsonData);
//			var a = new FileTextureData(file, null, null, (Boolean) null);
//			return null;
//		}
//	}
	
}
