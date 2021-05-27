import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Game over message appears on the screen when the game is over
 */

public class GameOver extends Actor {
    private GreenfootImage gameOver = new GreenfootImage("gameOver.png"); //load picture

       // resize gameOver picture and show on screen
       public GameOver() {
        gameOver.scale(480,100);
        setImage(gameOver);
    }
}
