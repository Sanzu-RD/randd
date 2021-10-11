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
		checkMusic();
	}
	
	public void loadTrackList() {
//		tracks.forEach(m -> {
//		});
		tracks = LapisAssets.getAll(Music.class, new Array<>());
//		LapisAssets.assets.unload();
		//LapisAssets.blocking = true;
		//LapisAssets.loadMusics(Gdx.files.internal("res/music/"));
		//LapisAssets.blocking = false;
		//tracks = LapisAssets.assets.getAll(Music.class, new Array<>());
		//tracks.forEach(m -> m.setOnCompletionListener(this::onTrackComplete));
	}
	
	private boolean checkMusic() {
		if(music == null) {
			// finished loading
			if(tracks == null) tracks = LapisAssets.getAll(Music.class, new Array<>());
			
			if(tracks == null || tracks.size < currentIndex - 1) return false;
			tracks.forEach(m -> m.setOnCompletionListener(this::onTrackComplete));
			
			// get a track
			music = tracks.get(currentIndex);
		}
		return true;
	}
	
	public void togglePlayPause() {
		if(!checkMusic()) return;
		if(music.isPlaying()) pause();
		else play();
	}
	public void play() {
		if(!checkMusic()) return;
		music = tracks.get(currentIndex);
		music.setVolume(volume);
		music.play();
	}
	public void pause() {
		if(!checkMusic()) return;
		music = tracks.get(currentIndex);
		music.setVolume(volume);
		music.pause();
	}
	public void next() {
		if(!checkMusic()) return;
		music.stop();
		currentIndex++;
		if(currentIndex >= tracks.size)
			currentIndex = 0;
		play();
	}
	public void previous() {
		if(!checkMusic()) return;
		music.stop();
		currentIndex--;
		if(currentIndex < 0)
			currentIndex = Math.max(0, tracks.size - 1); // if track.size == 0 this would be a problem
		play();
	}
	public void volume(float v) {
		volume = v;
		volume = clamp(volume, 0, 1);
		if(!checkMusic()) return;
		music.setVolume(volume);
	}
	public void volumeUp() {
		volume += 0.1f;
		volume = clamp(volume, 0, 1);
		if(!checkMusic()) return;
		music.setVolume(volume);
	}
	public void volumeDown() {
		volume -= 0.1f;
		volume = clamp(volume, 0, 1);
		if(!checkMusic()) return;
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
