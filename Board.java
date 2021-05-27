    import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

    /**
     * Just some methods to help with GameSquare.
     * These methods get the value or the boolean state from private methods in GameSquare to be used for other private methods in GameSquares
     */
    
   public class Board extends Actor {
   private boolean moved; //checks: has the block been moved?
   private int value; //value of block

    // checks value of blocks
    public int getValue() { 
        return value;
    }

    // set value of blocks
    public void setValue(int set) {
        value = set;
    }

    // new blocks have an initial value of 2
    public void setInitialValue() { 
       value = 2;
    }

    // check if block was moved
    public boolean isMoved() {
        return moved;
    }

    // if moved, set the block's status to moved
    public void setMoved(boolean hasMoved) {
        moved = hasMoved;
    }   
 }
