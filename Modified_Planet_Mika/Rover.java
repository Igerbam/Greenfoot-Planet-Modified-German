import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
/**
 * Ein Rover ist ein Fahrzeug, dass sich auf einem Planeten bewegen und Gestein analysieren kann.
 */
public class Rover extends Actor
{
    private Display anzeige;
    
    private String farbe="normal"; //farbe
    
    private String gang="vorwärts"; //eingelegter Gang
    
    private int Geschwindigkeit=1; //Schnelligkeit
    
    private int Treibstoffverbrauch=0; //Treibstoffverbrauch
    
    private int Treibstoff=2; //Treibstoff
    private boolean unendlichTreibstoff=true;
    private boolean beimDrehenTreibstoffAbziehen=true;
    
    private boolean kaputt=false; //kaputt
    private boolean beiKollisonZerstören=false;
    
    public Rover()
    {
        
    }
    
    /**
    In der Methode act können andere Methoden des Rovers angewendet werden.
    Die Methoden werden dann nacheinander ausgeführt, wenn man nach dem
    Kompilieren den Act-Knopf drückt.
    
    Folgende Attribute wurden hinzugefügt:
        String farbe
        String gang
        int Geschwindigkeit
        int Treibstoffverbrauch
        int Treibstoff
          boolean unendlichTreibstoff
          boolean beimDrehenTreibstoffAbziehen
        boolean kaputt
          boolean beiKollisionZerstören
    
        
    Folgende Methoden wurden bearbeitet, da die neuen Attribute in diesen Methoden eine Rolle spielen:
        fahre() // folgende neuen Attributen mussten berücksichtigt werden: gang, Geschwindigkeit, Treibstoffverbrauch, Treibstoff, unendlichTreibstoff, kaputt, bei KollisionZerstören
        drehe() // folgende neuen Attribute mussten berücksichtigt werden: Treibstoffverbrauch, Treibstoff, unendlichTreibstoff, beimDrehenTreibstoffAbziehen, kaputt
        analysiereGestein() // folgende neuen Attribute mussten berücksichtigt werden:  kaputt
        setzeMarke() // folgende neuen Attribute mussten berücksichtigt werden: kaputt
        entferneMarke() // folgende neuen Attribute mussten berücksichtigt werden: kaputt
     
          
    Folgende Methoden wurden hinzugefügt, um die Attribute zu benutzen:
        andereGeschwindigkeit(int neueGeschwindidkeit) //Geschwindigkeit kann zwischen 1 und 100 sein.
        andererGang(String neuerGang) //neuerGang kann "rückwärts", "vorwärts" oder "raus" sein. Dieser Parameter ist NICHT Case-Sensitive.
        andereFarbe(String neueFarbe) //neueFarbe kann "blau", "gelb", "grün", "lila", "orange", "rot", "weiß", "schwarz" oder auch "normal" sein. Dieser Parameter ist NICHT Case-Sensitive.
        selbstzerstörung() //zerstört sich selbst - wird auch in fahre() benutzt, um sich selbst zu zerstören, falls der Rover auf ein Hindernis trifft
        reparieren() //repariert sich selbst
    */
    public void act()
    {
        gang="rückwärts";
        fahre();
        gang="vorwärts";
        fahre();
    }
    
    private static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }
    
    public void andereGeschwindigkeit(int neueGeschwindidkeit)
    {
        if (neueGeschwindidkeit>0 && neueGeschwindidkeit<101)
        {
            Geschwindigkeit=neueGeschwindidkeit;
        }
        else
        {
            nachricht("Geschwindkeit ungültig");
        }
    }
    
    public void andererGang(String neuerGang)
    {
        if (neuerGang.toLowerCase()=="vorwärts" || neuerGang.toLowerCase()=="rückwärts" || neuerGang.toLowerCase()=="raus")
        {
            gang=neuerGang.toLowerCase();
        }
        else
        {
            nachricht("Gang nicht gefunden");
        }
    }
    
    public void andereFarbe(String neueFarbe)
    {
        File f = new File("images/"+neueFarbe.toLowerCase()+"_rover.png");
        neueFarbe=neueFarbe.toLowerCase();
        if (neueFarbe=="normal") {
            setImage("images/rover.png");
            farbe=neueFarbe;
        }
        else if (f.exists())
        {
            setImage("images/"+neueFarbe+"_rover.png");
            farbe=neueFarbe;
        }
        else
        {
            nachricht("Farbe nicht gefunden");
        }
    }
    
    public void selbstzerstörung()
    {
        setImage("images/explosion.png");
        kaputt=true;
    }
    
    public void reparieren()
    {
        File f = new File("images/"+farbe+"_rover.png");
        
        if (farbe=="normal") {
            setImage("images/rover.png");
        }
        else if (f.exists())
        {
            setImage("images/"+farbe+"_rover.png");
        }
        kaputt=false;
    }
    
    /**
     * Der Rover bewegt sich ein Feld in Fahrtrichtung weiter. 
     * Sollte sich in Fahrtrichtung ein Objekt der Klasse Huegel befinden oder er sich an der Grenze der Welt befinden,
     * dann erscheint eine entsprechende Meldung auf dem Display.
     */
    public void fahre()
    {
        boolean erfolg = true;
        int neueGeschwindigkeit=Geschwindigkeit;
        while (erfolg && neueGeschwindigkeit > 0 && Treibstoff>0 && !kaputt) {
            erfolg=true;
            if (unendlichTreibstoff)
            {
                Treibstoff=100;
            }
            if (gang=="vorwärts")
            {
                int posX = getX();
                int posY = getY();
                if(huegelVorhanden("vorne"))
                {
                    if (beiKollisonZerstören)
                    {
                        selbstzerstörung();
                    }
                    nachricht("Zu steil!");
                    erfolg=false;
                }
                else if(getRotation()==270 && getY()==1)
                {
                    if (beiKollisonZerstören)
                    {
                        selbstzerstörung();
                    }
                    nachricht("Ich kann mich nicht bewegen.");
                    erfolg=false;
                }
                else
                {
                    move(1);
                    erfolg=true;
                }
                if(posX==getX()&&posY==getY()&&!huegelVorhanden("vorne") && erfolg)
                {
                    if (beiKollisonZerstören)
                    {
                        selbstzerstörung();
                    }
                    nachricht("Ich kann mich nicht bewegen.");
                    erfolg=false;
                }
            }
            else if (gang=="rückwärts")
            {
                int oldRotation = getRotation();
                setRotation(getRotation()+180);
                
                int posX = getX();
                int posY = getY();
                if(huegelVorhanden("vorne"))
                {
                    setRotation(oldRotation);
                    if (beiKollisonZerstören)
                    {
                        selbstzerstörung();
                    }
                    nachricht("Zu steil!");
                    erfolg=false;
                }
                else if(getRotation()==270 && getY()==1)
                {
                    setRotation(oldRotation);
                    if (beiKollisonZerstören)
                    {
                        selbstzerstörung();
                    }
                    nachricht("Ich kann mich nicht bewegen.");
                    erfolg=false;
                }
                else
                {
                    move(1);
                    setRotation(oldRotation);
                    erfolg=true;
                }
                if(posX==getX()&&posY==getY()&&!huegelVorhanden("vorne"))
                {
                    setRotation(oldRotation);
                    if (beiKollisonZerstören)
                    {
                        selbstzerstörung();
                    }
                    nachricht("Ich kann mich nicht bewegen.");
                    erfolg=false;
                }
            }
            else
            {
                nachricht("Es ist kein Gang drin");
            }
            if (erfolg)
            {
                Treibstoffverbrauch++;
                Treibstoff--;
            }
            neueGeschwindigkeit--;
        }
        if (Treibstoff==0)
        {
            nachricht("Treibstoff alle");
        }
        if (kaputt)
        {
            nachricht("Ich bin kaputt");
        }
        Greenfoot.delay(1);
    }

    /**
     * Der Rover dreht sich um 90 Grad in die Richtung, die mit richtung ("links" oder "rechts") übergeben wurde.
     * Sollte ein anderer Text (String) als "rechts" oder "links" übergeben werden, dann erscheint eine entsprechende Meldung auf dem Display.
     */
    public void drehe(String richtung)
    {
        if (unendlichTreibstoff) {
            Treibstoff=100;
        }
        int tempTreibstoff = Treibstoff;
        if (!beimDrehenTreibstoffAbziehen) 
        {
            tempTreibstoff=100;
        }
        if(richtung=="rechts" && !kaputt && tempTreibstoff>0)
        {
            setRotation(getRotation()+90);
            if (beimDrehenTreibstoffAbziehen)
            {
                Treibstoff--;
                Treibstoffverbrauch++;
            }
        }
        else if (richtung=="links" && !kaputt && tempTreibstoff>0)
        {
            setRotation(getRotation()-90);
            if (beimDrehenTreibstoffAbziehen)
            {
                Treibstoff--;
                Treibstoffverbrauch++;
            }
        }
        else if (tempTreibstoff==0 && beimDrehenTreibstoffAbziehen)
        {
            nachricht("Treibstoff alle");
        }
        else if (kaputt)
        {
            nachricht("Ich bin kaputt");
        }
        else
        {
            nachricht("Befehl nicht korrekt!");
        }
    }

    /**
     * Der Rover gibt durch einen Wahrheitswert (true oder false) zurück, ob sich auf seiner Position ein Objekt der Klasse Gestein befindet.
     * Eine entsprechende Meldung erscheint auch auf dem Display.
     */
    public boolean gesteinVorhanden()
    {
        if(getOneIntersectingObject(Gestein.class)!=null)
        {
            nachricht("Gestein gefunden!");
            return true;
        }
        return false;
    }

    /**
     * Der Rover ermittelt den Wassergehalt des Gesteins auf seiner Position und gibt diesen auf dem Display aus.
     * Sollte kein Objekt der Klasse Gestein vorhanden sein, dann erscheint eine entsprechende Meldung auf dem Display.
     */
    public void analysiereGestein()
    {
        if(gesteinVorhanden() && !kaputt)
        {
            nachricht("Gestein untersucht! Wassergehalt ist " + ((Gestein)getOneIntersectingObject(Gestein.class)).getWassergehalt()+"%.");
            Greenfoot.delay(1);
            removeTouching(Gestein.class);
        }
        else if (!gesteinVorhanden() && !kaputt)
        {
            nachricht("Hier ist kein Gestein.");
        }
        if (kaputt)
        {
            nachricht("Ich bin kaputt");
        }
    }

    /**
     * Der Rover überprüft, ob sich in richtung ("rechts", "links", oder "vorne") ein Objekt der Klasse Huegel befindet.
     * Das Ergebnis wird auf dem Display angezeigt.
     * Sollte ein anderer Text (String) als "rechts", "links" oder "vorne" übergeben werden, dann erscheint eine entsprechende Meldung auf dem Display.
     */
    public boolean huegelVorhanden(String richtung)
    {
        int rot = getRotation();
        if (richtung=="vorne" && rot==0 || richtung=="rechts" && rot==270 || richtung=="links" && rot==90)
        {
            if(getOneObjectAtOffset(1,0,Huegel.class)!=null && ((Huegel)getOneObjectAtOffset(1,0,Huegel.class)).getSteigung() >30)
            {
                return true;
            }
        }
        if (richtung=="vorne" && rot==180 || richtung=="rechts" && rot==90 || richtung=="links" && rot==270)
        {
            if(getOneObjectAtOffset(-1,0,Huegel.class)!=null && ((Huegel)getOneObjectAtOffset(-1,0,Huegel.class)).getSteigung() >30)
            {
                return true;
            }
        }
        if (richtung=="vorne" && rot==90 || richtung=="rechts" && rot==0 || richtung=="links" && rot==180)
        {
            if(getOneObjectAtOffset(0,1,Huegel.class)!=null && ((Huegel)getOneObjectAtOffset(0,1,Huegel.class)).getSteigung() >30)
            {
                return true;
            }
        }
        if (richtung=="vorne" && rot==270 || richtung=="rechts" && rot==180 || richtung=="links" && rot==0)
        {
            if(getOneObjectAtOffset(0,-1,Huegel.class)!=null && ((Huegel)getOneObjectAtOffset(0,-1,Huegel.class)).getSteigung() >30)
            {
                return true;
            }
        }
        if(richtung!="vorne" && richtung!="links" && richtung!="rechts")
        {
            nachricht("Befehl nicht korrekt!");
        }
        return false;
    }

    /**
     * Der Rover erzeugt ein Objekt der Klasse "Marke" auf seiner Position.
     */
    public void setzeMarke()
    {
        if (kaputt)
        {
            nachricht("Ich bin kaputt");
        }
        else
        {
            getWorld().addObject(new Marke(), getX(), getY());
        }
    }

    /**
     * Der Rover gibt durch einen Wahrheitswert (true oder false) zurück, ob sich auf seiner Position ein Objekt der Klasse Marke befindet.
     * Eine entsprechende Meldung erscheint auch auf dem Display.
     */
    public boolean markeVorhanden()
    {
        if(getOneIntersectingObject(Marke.class)!=null)
        {
            return true;
        }
        return false;
    }

    /**
     * Falls an der Position des Rovers eine Markierung vorhanden ist, so entfernt er diese.
     */
    public void entferneMarke()
    {
        if(markeVorhanden() && !kaputt)
        {
            removeTouching(Marke.class);
        }
        if (kaputt)
        {
            nachricht("Ich bin kaputt");
        }
    }

    /**
     * Das Display des Rovers wird entfernt, d. h. gelöscht.
     */
    public void schalteDisplayAus()
    {
        getWorld().removeObject(anzeige);
    }    

    private void nachricht(String text)
    {
        if(anzeige!=null)
        {
            anzeige.anzeigen(text);
            Greenfoot.delay(1);
            anzeige.loeschen();
        }
    }

    protected void addedToWorld(World world)
    {
        setImage("images/rover.png");
        world = getWorld();
        anzeige = new Display();
        world.addObject(anzeige, 7, 0);
        if(getY()==0)
        {
            setLocation(getX(),1);
        }
        anzeige.anzeigen("Ich bin bereit");
    }
}