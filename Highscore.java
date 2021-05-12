import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Displays highscore on the screen as a picture
 * (the CoverPage() class and the GameSquare() class already read the highscore from a file)
 */

public class Highscore extends Actor{
    private GameSquare gameSquare = new GameSquare();
    private String text ="Score: 0";
    private GreenfootImage image;

    public Highscore(int gameScore, boolean isGameOver){ //Displays the highscore in different sizes depending on the game status (game over or not)
        
        text = "Highscore: "+ gameScore;
        if (isGameOver) {
            printText(text,70); //make the font size 70 when the game is over
        }
        else {
            printText(text,40); //make the font size 40 during the game
        }
    }

    public void printText(String val,int size) {  //show Highscore on the screen
        setImage(new GreenfootImage(val, size, Color.WHITE, Color.GRAY));
    }
}
