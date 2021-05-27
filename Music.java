import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Adds background music to the game.
 * If the music picture is clicked when the background music is playing, the music turns off.
 * If the music picture is clicked when the background music is not playing, the music turns on.
 * The picture also changes depending on whether the music is on or off.
 */

public class Music extends Actor {
    
    static boolean musicPlaying = true;
    private GreenfootImage musicOn = new GreenfootImage("musicOn.png"); //get picture
    private GreenfootImage noMusic = new GreenfootImage("noMusic.png"); //get picture
    static GreenfootSound backgroundMusic = new GreenfootSound("backgroundMusic.mp3"); //get music

    public Music() {
        musicOn.scale(40,40); //resize picture
        noMusic.scale(40,40); //resize picture
        try {
                backgroundMusic.setVolume(100); //turn on the volume for the background music
            }
            catch (Exception e) {
                System.err.println("Error!");
            }
    }

    public void playMusic() {
	// if the player doesn't stop the music
        if(musicPlaying) {
            try {
                backgroundMusic.playLoop(); // repeat playing the background music
            }
            catch (Exception e) {
                System.err.println("Error!");
            }
        }
    }

    public void act() {
	// if the player clicks on the music picture, they can turn the music off
	// clicking on it again will turn the music on
        if (Greenfoot.mouseClicked(this)) {
            
	    // if the music was playing when the user clicked on the picture, pause the music
            if (musicPlaying) {
                try {
                    backgroundMusic.pause();
                }
                catch (Exception e) {
                    System.err.println("Error!");
                }
                musicPlaying = false; // music not playing
            }
	    // if the music was not playing when the user clicked on the picture, start the music
            else if (!musicPlaying) {
                try {
                    backgroundMusic.playLoop();
                }
                catch (Exception e) {
                    System.err.print('\f');
                    System.err.println("Error!");
                }
                musicPlaying = true; //music playing
            }
        }

        if (musicPlaying) {
            setImage(musicOn);
        }

        if (!musicPlaying) {
            setImage(noMusic);
        }
    }    
}
