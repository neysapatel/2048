import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Construct world and add objects to start the game
 */

public class GameBoard extends World {
    //declare objects
    private GameSquare gameSquare = new GameSquare();
    private Music music = new Music();
    
    public GameBoard() {    
        super(480, 600, 1); 
        
        addObject(gameSquare,60,540); //Add gameSquare
        addObject(music,70,530); //add music picture
    }
}
