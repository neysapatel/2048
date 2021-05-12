import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Displays the score on the screen as a picture
 * (the game's current score for each game is caluclated in the GameSquare() class)
 */

public class Score extends Actor {
    private Board board=new Board();
    private String text ="Score: 0";
    private GreenfootImage image;

    public Score(int gameScore, boolean isGameOver) {
    
        if (isGameOver) {
            text = "Final score: "+ gameScore;
            printText(text,70); //make the font size 70 when the game is over
        }
        else {
            text = "Score: "+ gameScore;
            printText(text,40); //make the font size 40 during the game
        }
    }

     public void printText(String val,int size) { //show score on the screen
        setImage(new GreenfootImage(val, size, Color.WHITE, Color.GRAY));
    }
}
