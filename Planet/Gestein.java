import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
/**
 * Ein Gestein kann rot oder blau sein. Es hat einen Wassergehalt von 0 bis 19.
 */
public class Gestein extends Actor {
    private String farbe;
    private int wassergehalt;
    private String input;
    //farbe hier auswählbar: ("rot" oder "blau") oder ("1" oder "2")
    String farbauswahl = "1";
    
    public Gestein() {
        wassergehalt = Greenfoot.getRandomNumber(20);
        
        if (farbauswahl=="rot" || farbauswahl=="1") {
            farbe = "rot";
            setImage("images/gesteinRot.png"); 
        } else if (farbauswahl=="blau" || farbauswahl=="2") {
            farbe = "blau";
            setImage("images/gesteinBlau.png"); 
        } else {
            if (Greenfoot.getRandomNumber(2)==0) {
                farbe = "rot";
                setImage("images/gesteinRot.png");
                
            } else {
                farbe = "blau";
                setImage("images/gesteinBlau.png");
            }    
        }
    }

    public int getWassergehalt() {
        return wassergehalt;
    }
}