import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * UI button element
 * Starts a new game when the player clicks on "New Game"
 * This button is on the cover page to start the game and also on the game over page.
 */

public class NewGame extends Actor {
    private GreenfootImage newGame = new GreenfootImage("newGame.png"); // get picture

    public NewGame() { 
        newGame.scale(480,200); // resize picture
        setImage(newGame); // show picture on screen
    }
    
    // starts game if the user clicks on it
    public void act() {
       if (Greenfoot.mouseClicked(this)) {
            Greenfoot.setWorld(new GameBoard());
        }
    }    
}
