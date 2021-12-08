import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Ein Hügel hat eine Steigung von 31 bis 60.
 */
public class Huegel extends Actor {
    private int steigung;

    public Huegel() {
        steigung = Greenfoot.getRandomNumber(30)+31;
        setImage("images/huegel.png");
    }

    public int getSteigung() {
        return steigung;
    }
}