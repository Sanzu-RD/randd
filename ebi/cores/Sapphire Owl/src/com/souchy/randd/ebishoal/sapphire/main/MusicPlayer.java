package com.souchy.randd.ebishoal.sapphire.main;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Array;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;

public class MusicPlayer {

	public Array<Music> tracks;

	private int currentIndex;
	
	public float volume = 0.5f;
	
	public Music music;
	
	
	public MusicPlayer() {
		loadTrackList();
		currentIndex = 0;
		music = tracks.get(currentIndex);
	}
	
	public void loadTrackList() {
//		tracks.forEach(m -> {
//		});
//		tracks = LapisAssets.assets.getAll(Music.class, new Array<>());
//		LapisAssets.assets.unload();
		LapisAssets.loadMusics(Gdx.files.internal("res/music/"));
		tracks = LapisAssets.assets.getAll(Music.class, new Array<>());
		tracks.forEach(m -> m.setOnCompletionListener(this::onTrackComplete));
	}
	
	public void play() {
		music = tracks.get(currentIndex);
		music.setVolume(volume);
		music.play();
	}
	public void pause() {
		music = tracks.get(currentIndex);
		music.setVolume(volume);
		music.pause();
	}
	public void next() {
		music.stop();
		currentIndex++;
		if(currentIndex >= tracks.size)
			currentIndex = 0;
		play();
	}
	public void previous() {
		music.stop();
		currentIndex--;
		if(currentIndex < 0)
			currentIndex = tracks.size - 1;
		play();
	}
	public void volume(float v) {
		volume = v;
		volume = clamp(volume, 0, 1);
		music.setVolume(volume);
	}
	public void volumeUp() {
		volume += 0.1f;
		volume = clamp(volume, 0, 1);
		music.setVolume(volume);
	}
	public void volumeDown() {
		volume -= 0.1f;
		volume = clamp(volume, 0, 1);
		music.setVolume(volume);
	}
	
	
	/**
	 * utility function to clamp volume
	 */
	private float clamp(float val, float min, float max) {
		return Math.min(max, Math.max(min, val));
	}
	
	/** 
	 * event handler on track completion
	 */
	private void onTrackComplete(Music m) {
		next();
	}
	
}
