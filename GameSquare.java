import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.*;

/**
 * The game square is developed in this class
 */

public class GameSquare extends Actor {
    private Board[][] board; //declare board as array
    
    //declare/ initialize objects
    private BlockValue block;
    private Highscore showHighscore;
    private Score showScore;
    private GameOver gameOver = new GameOver();
    private NewGame newGame = new NewGame();

    private boolean debug;
    private boolean boardSetUp = false;
    private boolean up = false;
    private boolean right = false;
    private boolean down = false;
    private boolean left = false;
    private int highscore = 0;
    private int score = 0;
    private boolean isGameOver = false;
    
    public GameSquare(){ 
        board = new Board[4][4];
        debug = false; // set to true for testing purposes
        highscore = loadHighscore(); //get highscore from Highscore class
        generateBoard(); //call up method to generate board
        placeRandomBlock(); //this method places one random block
        placeRandomBlock(); //therefore, need to call this method up twice to place the first two blocks
	
	// prevent null pointers
        if(getWorld() != null) {
            addBlocks();
            printScore(false); 
            //boolean false tells printScore() that the game is not over, so it should print the score on the bottom of 
            //  the screen, rather than the center of the screen when the game is over
        }
    }

    public void act() {
        // set the board up by adding blocks, if the board has not already been set up
        if(getWorld() != null && !boardSetUp) {
            addBlocks();
            boardSetUp = true; //board is now set up
        }

	// only allow the keys to be pressed when there are still some possible moves AND the game is not over
        if(checkForMovableBlocks() && !isGameOver) { 
            if (Greenfoot.isKeyDown("up") && !up) { 
                // do this only is the up key is pressed and set up = true
                // !up is part of error checking done below
                move(1); //overload move with switch case = 1
                up = true;
            } 

            // same logic as up key
            if (Greenfoot.isKeyDown("right") && !right) {
                move(2);
                right = true;
            }

            // same logic as up key
            if (Greenfoot.isKeyDown("down") && !down) {
                move(3);
                down = true;
            }

            // same logic as up key
            if (Greenfoot.isKeyDown("left") && !left) {
                move(4);
                left = true;
            }
            setNewHighscore(); //set current score as the new Highscore if it is greater that the Highscore on the file
            printScore(false); // boolean is false since the game is still not over
        }
        // if there are no more possible moves, the game is over
        else {
            isGameOver = true; //set boolean = true
            gameOverPage(); //call method to pull up game over screen
            setNewHighscore(); //check if current score is larger than the Highscore on file
            saveHighscore(highscore); //Replace the Highscore on file if the current score is larger
            printScore(true); 
            // setting boolean = true allows prints the score at the center of the page now since the game is over
        }

        // error checking so that only one move() is run per key pressed
        if (!Greenfoot.isKeyDown("up")) {
            up = false; //if the up key is not pressed, boolean up = false
            //therefore, move(1) will not be conducted for whatever key was just pressed, until the next key is pressed
        }

        if (!Greenfoot.isKeyDown("right")) {
            right = false; //if the right key is not pressed, boolean right = false
            //therefore, move(2) will not be conducted for whatever key was just pressed, until the next key is pressed
        }

        if (!Greenfoot.isKeyDown("down")) {
            down = false; //if the down key is not pressed, boolean down = false
            //therefore, move(3) will not be conducted for whatever key was just pressed, until the next key is pressed
        }

        if (!Greenfoot.isKeyDown("left")) {
            left = false; //if the left key is not pressed, boolean left = false
            //therefore, move(4) will not be conducted for whatever key was just pressed, until the next key is pressed
        }
    }

    public void generateBoard() { //generate the board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j]= new Board();
            }
        }
    }
    
    public void addBlocks(){ //adds pictures of blocks to the screen
        getWorld().removeObjects(getWorld().getObjects(BlockValue.class));
        //remove all old blocks from the screen since the block positions will likely change everytime a key is pressed

	// start at the x coordinate on board
        for (int x = 0; x < board.length; x++) { 
	    // y coordinate on board
            for (int y = 0; y < board.length; y++) { 
                int val = board[x][y].getValue(); //find the value of the block
                block = new BlockValue(val,105); //create new block based on val by sending this info to class BlockValue()
                //  for example, if the block had value 64, it will be replaced with a picture of the 64 block from the 
                //  original game. This picture is resized to 105.

                getWorld().addObject(block, x*120+60, y*120+60); //add the block object at postion (x*120+60, y*120+60)
                //   this is the position becase each block is 120 by 120, and the distance between blocks is 60
            }
        }
    }

    // Randomly places a block in an empty space
    public void placeRandomBlock() {
        boolean blockPlaced = false;
        int x = Greenfoot.getRandomNumber(4);
        int y = Greenfoot.getRandomNumber(4);
        int value = 2;
        
	// keep doing this until a block is placed
        while (blockPlaced == false) {
	    // checks if the random space is empty
            if (board[x][y].getValue() == 0) {
               board[x][y].setInitialValue(); //set inital value of 2 to place a 2 block in this space
               value = board[x][y].getValue();
               blockPlaced = true; //breaks while loop
            }
	    // if the random space is occupied, generate another random space to place the block on
            else {
               x = Greenfoot.getRandomNumber(4);
               y = Greenfoot.getRandomNumber(4);
            }
        }
     }
    
    // Moves blocks; takes in value from act()
    // up key = move(1), right key = move(2), down key = move (3), left key = move (4), as determined by act()
    public void move(int direction){ 
        int x;
        int y;
        boolean moved = false;
        allMoved(); //double checks that all blocks have moved before the next key is pressed
        
	// case determined using act()
        switch (direction) {

            // If the up key was pressed
            case 1: 
 		// starts at y = 1 (second row) (to allow blocks to travel up) and works its way down the board
                for (y = 1; y < board.length; y++) {
		    // starts at x = 0 and works its way to the right
                    for (x = 0; x < board.length; x++) {
                        for (int i = 1; i < board.length; i++) { 
                            if ( (board[x][i].getValue() != 0) && (board[x][i-1].isMoved() == false) && (board[x][i].isMoved() == false) && (board[x][i-1].getValue() == board[x][i].getValue() || board[x][i-1].getValue() == 0) ){
                              // only do this if:
                              //  the value at this location is not equal to zero (meaning: this space is not an empty space)
                              //  AND the block above it has not been moved yet
                              //  AND the block in this space has not been moved yet
                              //  AND the block at this space is the same as the block in the space above it OR the space above this block is empty
                               
			       // if the block in the space above this one is not empty:
                               if (board[x][i-1].getValue() != 0) {
                                    addScore(board[i][y].getValue()*2); // add the value of the 2 blocks to the game score
                                    board[x][i-1].setMoved(true); // the block in the space above it has now been moved up to this space
                                    //  boolean set to true to allow for the next move
                                }
                                board[x][i-1].setValue(board[x][i].getValue() + board[x][i-1].getValue());
                                // change the block in this space to a new block if the value of the block above it is the same
                                //  for example if both of the blocks were 16 each, the new block would become 32
                                board[x][i].setValue(0); // this space is now empty
                                moved = true; // the block has been moved
                            }
                        }
                    }
                }
                break;
            
                // If the right key was pressed
                case 2:
		// starts at the right and works its way to the left
                for (x = (board.length - 2); x >= 0; x--){
		    // starts at y = 0 (top of board) and works its way down the board
                    for (y = 0; y < board.length; y++) {
                        for (int i = (board.length - 2); i >= 0; i--) { 
                        if( (board[i][y].getValue() != 0) && (board[i+1][y].isMoved() == false) && (board[i][y].isMoved() == false) && (board[i+1][y].getValue()==board[i][y].getValue() || board[i+1][y].getValue() == 0) ){
                           // only do this if:
                           //  the value at this location is not equal to zero (meaning: this space is not an empty space)
                           //  AND the block to its right (thus moving up into this space) has not been moved yet
                           //  AND the block in this space has not been moved yet
                           //  AND the block at this space is the same as the block in the space next to it OR the space next to this block is empty
                            
			    // if the block in the space to this right of this one is not empty:
                            if(board[i+1][y].getValue() != 0) {
                                addScore(board[i][y].getValue()*2); // add the value of the 2 blocks to the game score
                                board[i+1][y].setMoved(true); // the block in the space to its right has now been moved to this space
                                //  boolean set to true to allow for the next move
                            }
                                board[i+1][y].setValue(board[i][y].getValue() + board[i+1][y].getValue()); 
                                // change the block in this space to a new block if the value of the block next to it is the same
                                //  for example if both of the blocks were 16 each, the new block would become 32
                                board[i][y].setValue(0); // this space is now empty
                                moved = true; // the block has been moved
                            }
                        }
                    }
                }
                break;   

            	// If the down key was pressed
                case 3:
		// starts at the bottom of the board and works its way up the board
                for (y = (board.length - 2); y >= 0; y--){
		    // starts at x = 0 and works its way to the right
                    for (x = 0; x < board.length; x++) {
                        for (int i = (board.length - 2); i >= 0; i--){ 
                            if( (board[x][i].getValue() != 0) && (board[x][i+1].isMoved() == false) && (board[x][i].isMoved() == false) &&(board[x][i+1].getValue() == board[x][i].getValue() || board[x][i+1].getValue() == 0) ){
                           // only do this if:
                           //   the value at this location is not equal to zero (meaning: this space is not an empty space)
                           //   AND the block under it has not been moved yet
                           //   AND the block in this space has not been moved yet
                           //   AND the block at this space is the same as the block under it OR the space under thi block is empty
                            
			   // if the block in the space under this one is not empty:
                           if(board[x][i+1].getValue() != 0) {
                                    addScore(board[i][y].getValue()*2); // add the value of the 2 blocks to the game score
                                    board[x][i+1].setMoved(true); // the block in the space under it has now been moved down to this space
                                    //  boolean set to true to allow for the next move
                                }
                                board[x][i+1].setValue(board[x][i].getValue() + board[x][i+1].getValue()); 
                                // change the block in this space to a new block if the value of the block under it is the same
                                //  for example if both of the blocks were 16 each, the new block would become 32
                                board[x][i].setValue(0); // this space is now empty
                                moved = true; // the block has been moved
                            }
                        }
                    }
                }
                break;
            
		// If the left key was pressed
                case 4: 
		// x starts at 1 (to allow blocks to move to the left) and works its way to the right
                for (x = 1; x < board.length; x++){ 
		    // y starts at 0 (top of the board) and works its way down the board
                    for (y = 0; y < board.length; y++){
                        for (int i = 1; i < board.length; i++){ 
                            if( (board[i][y].getValue() != 0) && (board[i-1][y].isMoved() == false) && (board[i][y].isMoved() == false) && (board[i-1][y].getValue()==board[i][y].getValue() || board[i-1][y].getValue() == 0) ){
                           // only do this if:
                           //   the value at this location is not equal to zero (meaning: this space is not an empty space)
                           //   AND the block to its left (thus moving up into this space) has not been moved yet
                           //   AND the block in this space has not been moved yet
                           //   AND the block at this space is the same as the block in the space next to it OR the space next to this block is empty
                            
				// if the block in the space to the left of this one is not empty:
                                if(board[i-1][y].getValue() != 0) {
                                    addScore(board[i][y].getValue()*2); // add the value of the 2 blocks to the game score
                                    board[i-1][y].setMoved(true); // the block in the space to its left has now been moved to this space
                                    //  boolean set to true to allow for the next move
                                }
                                board[i-1][y].setValue(board[i][y].getValue() + board[i-1][y].getValue()); 
                                // change the block in this space to a new block if the value of the block next to it is the same
                                //  for example if both of the blocks were 16 each, the new block would become 32
                                board[i][y].setValue(0); // this space is now empty
                                moved = true; // the block has been moved
                            }
                        }
                    }
                }
                break;
        }
        
        if (moved) {
            addBlocks(); // remove blocks from their old on-screen position and place them in their new on-screen position
            placeRandomBlock(); // Place a new random block once all blocks have moved
        }
    }

    // the next move can happen once all steps from this move are completed
    public void allMoved(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j].setMoved(false); // boolean set false until move(int direction) sets it to true after every move
            }
        }
    }
    
    // Check whether there are still movable blocks on the board
    //   if there are movable blocks, the game can continue
    //   if there are no more movable blocks, the game is over
    public boolean checkForMovableBlocks() {
        int y;
        int x;
        boolean anyBlocksMovable=false;
        
        // up key
	// starts in the second row, works its way down the board
        for (y = 1; y < board.length; y++) {
	    // starts at left, works its way to the right
            for (x = 0; x < board.length; x++) {
                if ( (board[x][y].getValue() != 0) && (board[x][y-1].isMoved() == false) && (board[x][y-1].getValue() == board[x][y].getValue() || board[x][y-1].getValue() == 0) ){
                    anyBlocksMovable = true; //moves possible if the same conditions listed in move(int direction) possible
                }
                else if (board[x][y].getValue() == 0) {
                    anyBlocksMovable = true; //moves possible if empty space
                }
            }
        }
        
        // right key
	// starts at the left right of the board and works its way to the left
        for (x = (board.length - 2); x >= 0; x--) {
	    // starts at the top of the board and travels down to the bottom of the board
            for (y = 0; y < board.length; y++) {
                for (int i = (board.length - 2); i >= 0; i--) {
                    if( (board[x][y].getValue() != 0) && (board[x+1][y].isMoved() == false) && (board[x+1][y].getValue() == board[x][y].getValue() || board[x+1][y].getValue()==0) ){
                        anyBlocksMovable=true; // moves possible if the same conditions listed in move(int direction) possible
                }
                else if (board[x][y].getValue() == 0) {
                    anyBlocksMovable = true; // moves possible if empty space
                }
                }
            }
        }
        
        // down key
	// starts at the bottom of the board and works its way up the board
        for (y= (board.length - 2); y >= 0; y--) {
	    // starts the left and works its way to the right
            for (x = 0; x < board.length; x++) {
                if((board[x][y].getValue() != 0) && (board[x][y+1].isMoved() == false) && (board[x][y+1].getValue() == board[x][y].getValue() || board[x][y+1].getValue() == 0) ){
                    anyBlocksMovable=true; // moves possible if the same conditions listed in move(int direction) possible
                }
                else if (board[x][y].getValue() == 0) {
                    anyBlocksMovable = true; // moves possible if empty space
                }
            }
        }
        
        // left key
	// x starts at 1 (to allow blocks to move to the left) and works its way to the right
        for (x = 1; x < board.length; x++) {
	    // y starts at 0 (top of the board) and works its way down the board
            for (y = 0; y < board.length; y++){
                for (int i = 1; i < board.length; i++) {
                    if( (board[x][y].getValue() != 0) && (board[x-1][y].isMoved() == false) && (board[x-1][y].getValue() == board[x][y].getValue() || board[x-1][y].getValue() == 0) ){
                        anyBlocksMovable=true; // moves possible if the same conditions listed in move(int direction) possible
                }
                else if (board[x][y].getValue() == 0) {
                        anyBlocksMovable = true; // moves possible if empty space
                }
                    }
                }
            }
        return anyBlocksMovable; // return if it is possible for blocks to move or not
    }
    
    // increases score in the current game score
    public void addScore(int add){
        score = score + add;
    }

    // get score to display it
    public int getScore(){
        return score;
    }

    // Loads highscore from highscore.txt (currently has random defualt values)
    public int loadHighscore(){
        int readHighscore = 0;
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("highscore.txt");
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            String stringHighscore = r.readLine();
            r.close();
            if(stringHighscore!="")
            {
                readHighscore = Integer.parseInt(stringHighscore);
            }
        } catch (Exception e) {
            System.err.println("Error: "+e.getMessage());
        }
        return readHighscore;
    }
    
    public void printScore(boolean isGameOver) {
	// if the game is not over:
        if(getWorld()!=null && !isGameOver) {
            getWorld().removeObject(showHighscore); //don't show the Highscore at the centre of the screen
            getWorld().removeObject(showScore); //don't show the score at the centre of the screen
            showScore = new Score(getScore(),false); //show the score at the bottom of the screen
            getWorld().addObject(showScore,240,520);
            showHighscore = new Highscore(highscore,false); //show the Highscore at the bottom of the screen
            getWorld().addObject(showHighscore,240,560);
        }
	// if the game is over
        else if(getWorld() != null && isGameOver) {
            getWorld().removeObject(showScore); //don't show the score at the bottom of the screen
            getWorld().removeObject(showHighscore); //don't show the Highscore at the bottom of the screen
            showScore = new Score(getScore(),true); //show the score at the centre of the screen
            getWorld().addObject(showScore,240,170);
            showHighscore = new Highscore(highscore,true); //show the Highscore at the centre of the screen
            getWorld().addObject(showHighscore,240,270);
        }
    }

    public void gameOverPage() {
        printScore(true); // boolean true tells the code to print the score at the centre of the page now that the game is over
        getWorld().addObject(gameOver,240,65); // adds game over message
        getWorld().addObject(newGame, 240, 420); // adds option to start new game
    }
    
    // set current score as the new Highscore if it is greater that the Highscore on the file
    public void setNewHighscore() {
        if (score > highscore) {
            highscore = score;
        }
    }

    // save the new Highscore to the file
    public void saveHighscore(int gameScore) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("highscore.txt"));
            bw.write(gameScore + "");
            bw.close();
        }
        catch (Exception e) {
            System.err.println("Error!");
        }
    }

    // for testing purposes
    public void testUsingDebug() {
        if (debug) {
            debug = false;
        }
        else {
            debug = true;
        }
    }
}
