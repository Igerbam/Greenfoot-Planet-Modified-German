import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Die einzigen aktiven Akteure in der Roboterwelt sind die Roboter.
 * Die Welt besteht aus 16 * 10 Feldern.
 */
public class Planet extends World
{
    private static int zellenGroesse = 50;

    /**
     * Erschaffe eine Welt mit 16 * 12 Zellen.
     */
    public Planet()
    {
        super(34, 18, zellenGroesse);
        setBackground("images/boden.png");
        setPaintOrder(String.class, Rover.class, Marke.class, Gestein.class, Huegel.class);
        Greenfoot.setSpeed(20); 
    }
}