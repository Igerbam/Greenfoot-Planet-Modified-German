import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Das Display dient dem Rover zum Anzeigen von Informationen.
 */
class Display extends Actor
{
    public void anzeigen(String text)
    {
        loeschen();
        GreenfootImage bild = new GreenfootImage(text, 30, Color.BLACK, Color.WHITE); 
        getImage().drawImage(bild, 8,0);
    }

    public void loeschen()
    {
        getImage().clear();
        setImage("images/nachricht.png");
    }
}