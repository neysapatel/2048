import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Declares and sets up intro picture (game title, quick instructions for how to play the game)
 */

public class Intro extends Actor {
    
    private GreenfootImage intro = new GreenfootImage("intro.png"); //get picture
    
    public Intro() {
        intro.scale(480, 250); //resize picture
        setImage(intro); //show picture on screen
    }
}
