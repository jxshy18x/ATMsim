package com.atmbanksimulator;


import javax.sound.sampled.*; // Importing the entire javax.sound.sampled package.
import java.net.URL; // Importing the URL class from the java.net package.

public class ATMNoise {

    private static Clip clip; // Storing background sfx

    public static void start(String fileName) {
        try {
            if (clip != null && clip.isRunning()) return; /* Prevents background sfx from restarting if it is already
             active. */

            URL url = ATMNoise.class.getResource("/com/atmbanksimulator/sfx/" + fileName); /* Finding the
            location of the sfx in the project. */

            AudioInputStream audio = AudioSystem.getAudioInputStream(url); // Loads sfx file to be played.

            clip = AudioSystem.getClip(); // Creates a sound player.
            clip.open(audio); // Prepares the sound to be played.

            clip.loop(Clip.LOOP_CONTINUOUSLY); // Makes the sfx repeat until application is stopped.
            clip.start(); // Starts playing the background sfx.
        } catch (Exception e) {
            e.printStackTrace(); // Printing error messages if an issue occurs with playing the sfx.
        }
    }
    public static void stop() { // Stops background SFX
        if (clip != null) { //  Checks the SFX is loaded.
            clip.stop(); // Stops the SFX playing
            clip.close(); // Stops using the sfx and removes it from memory.
        }
    }
}
