package com.atmbanksimulator;

import javax.sound.sampled.*; // Importing the entire javax.sound.sampled package.
import java.net.URL; // Importing the URL class from the java.net package.

public class SFX {
    public static boolean muted = false;
    public static void playSFX(String fileName) { // Static method that plays SFX based on file name.
        try {
            if (muted) return;
            URL url = SFX.class.getResource("/com/atmbanksimulator/SFX/" + fileName); /* Gets the location
            of SFX file based on the name of the file. */

            AudioInputStream audio = AudioSystem.getAudioInputStream(url); /* Loads the file so it's ready to
             play. */

            Clip clip = AudioSystem.getClip(); // Creates a sound player.
            clip.open(audio); // Prepares the sound to be played.
            clip.start(); // Plays designated sound effect.

        } catch (Exception e) {
            e.printStackTrace(); // Prints error message in console if there's an issue with playing SFX.
        }
    }
}