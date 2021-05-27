import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Thsi class coverts the value of a block (for example 2, 4, 8, 16, 32, 64,...) 
 * into pictures of the corresponding block from the original game.
 * For example, a space on the game board with a value of 32 will add a picture of the 32 block from the original game, at that space
 */

public class BlockValue extends Actor {
    private int blockValue = 0;
    private GreenfootImage makeBlock;

    //replace number with image of the block number from the original game
    //   for example, 2 becomes the picture of the block '2' from the original game
    public BlockValue(int block, int size) { 
        fixBlock(block); //changes number to picture
        size(size); //resizes picture to fit game square
    }
    
    public void fixBlock (int block) {
        if(block <= 2048 && block !=0) {
            makeBlock = new GreenfootImage(block + ".png");
            setImage(makeBlock);
        } 
        // if picture can't be found, output error image (blank picture)
        else {
            makeBlock = new GreenfootImage("error.png");
            setImage(makeBlock);
        }
    }

    // resize picture to fit inside the game square
    public void size(int size) {
        makeBlock.scale(size,size);
        setImage(makeBlock);
    }
}
