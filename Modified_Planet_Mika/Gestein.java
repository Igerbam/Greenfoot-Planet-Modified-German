import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Ein Gestein kann rot oder blau sein. Es hat einen Wassergehalt von 0 bis 19.
 */
public class Gestein extends Actor
{
    private String farbe;
    private int wassergehalt;

    public Gestein()
    {
        wassergehalt = Greenfoot.getRandomNumber(20);

        if (Greenfoot.getRandomNumber(2)==0)
        {
            farbe = "rot";
            setImage("images/gesteinRot.png");
        }
        else
        {
            farbe = "blau";
            setImage("images/gesteinBlau.png");
        }
    }

    public int getWassergehalt()
    {
        return wassergehalt;
    }
}