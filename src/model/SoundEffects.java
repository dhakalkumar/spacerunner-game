package model;

import java.net.URI;

import javafx.scene.media.AudioClip;

public class SoundEffects {
	
	private static double VOL = 0.2; // default volume 20%
	
	public static void playSound(URI sound) {		
		AudioClip clip = new AudioClip(sound.toString());
		clip.setVolume(VOL);
		clip.play();
	}
}
