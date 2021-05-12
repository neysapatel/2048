import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.*;

/**
 * This class shows the game's coverpage when the game is run.
 * It shows an intro (title of the game with quick instructions), music, highscore, and a UI newGame button.
 * The game will start the (new) game when "New Game" is clicked on the screen.
 */

public class CoverPage extends World {
    //declare objects
    private Intro intro = new Intro();
    private NewGame newGame = new NewGame();
    private Music music = new Music();
    private Highscore showHighscore;
    
    public CoverPage() {    
        super(480, 600, 1);
        
        addObject(intro, 240, 140); //add intro object with centre at (240, 140)
        addObject(newGame, 240, 400); //add newGame object with centre at (240, 400)
        addObject(music,70,530); //add music object with centre at (70, 530)
        
        showHighscore = new Highscore(getHighscore(),false); 
        //initiate object showHighscore, but pass 'false' for the boolean (this boolean affects the size of showHighscore)
        addObject(showHighscore,240,540);  //add showHighscore object with centre at (240, 540)
        
        music.playMusic(); //play music
    }

    public int getHighscore() { //Load the game's highscore from the highscore.txt file
        int hs = 0;
        try { //set up file reader, read highscore from the file, close file reader
            InputStream input = getClass().getClassLoader().getResourceAsStream("highscore.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String score = reader.readLine();
            reader.close();
            if (score != "") { //if the file is not empty, save the highscore as hs
                hs = Integer.parseInt(score);
            }
        } 
        catch (Exception e) {
            System.err.println("Error!");
        }
        return hs;
    }
}
