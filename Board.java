    import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

    /**
     * Just some methods to help with GameSquare.
     * These methods get the value or the boolean state from private methods in GameSquare to be used for other private methods in GameSquares
     */
    
   public class Board extends Actor {
   private boolean moved; //checks: has the block been moved?
   private int value; //value of block
     
    public int getValue() { //checks value of blocks
        return value;
    }
    
    public void setValue(int set) { //set value of blocks
        value = set;
    }
    
    public void setInitialValue() { //new blocks have an initial value of 2
       value = 2;
    }
    
    public boolean isMoved() { //check if block was moved
        return moved;
    }
    
    public void setMoved(boolean hasMoved) { //if moved, set the block's status to moved
        moved = hasMoved;
    }   
    }
