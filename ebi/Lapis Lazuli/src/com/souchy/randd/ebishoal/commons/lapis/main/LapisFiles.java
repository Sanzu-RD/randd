package com.souchy.randd.ebishoal.commons.lapis.main;

import java.io.File;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglFileHandle;
import com.badlogic.gdx.files.FileHandle;

public class LapisFiles implements Files {

	public static final String externalPath = System.getProperty("user.home") + File.separator;
	public static final String localPath = new File("").getAbsolutePath() + File.separator;
	
	public final String rootPrefix;
	
	public LapisFiles(String root) {
		rootPrefix = root + File.separator;
	}
	
	@Override
	public FileHandle getFileHandle (String fileName, FileType type) {
		return new LwjglFileHandle(rootPrefix + fileName, type);
	}

	@Override
	public FileHandle classpath (String path) {
		return new LwjglFileHandle(/* rootPrefix + */ path, FileType.Classpath);
	}

	@Override
	public FileHandle internal (String path) {
		if(path.substring(1).startsWith(":/")) return absolute(path);
		return new LwjglFileHandle(rootPrefix + path, FileType.Internal);
	}

	@Override
	public FileHandle external (String path) {
		return new LwjglFileHandle(rootPrefix + path, FileType.External);
	}

	@Override
	public FileHandle absolute (String path) {
		return new LwjglFileHandle(/* rootPrefix + */ path, FileType.Absolute);
	}

	@Override
	public FileHandle local (String path) {
		return new LwjglFileHandle(rootPrefix + path, FileType.Local);
	}

	@Override
	public String getExternalStoragePath () {
		return externalPath;
	}

	@Override
	public boolean isExternalStorageAvailable () {
		return true;
	}

	@Override
	public String getLocalStoragePath () {
		return localPath;
	}

	@Override
	public boolean isLocalStorageAvailable () {
		return true;
	}
	
}
