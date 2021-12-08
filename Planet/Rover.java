import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Ein Rover ist ein Fahrzeug, dass sich auf einem Planeten bewegen und Gestein analysieren kann.
 */
public class Rover extends Actor {
    private Display anzeige;
    public int AktionenInsgesamt;
    public int RechtsGedreht;
    public int LinksGedreht;
    public int Gedreht;
    public int Gefahren;
    public int Analysiert;
    public int Aufgehoben;
    public int MarkenGesetzt;
    public int MarkenEntfernt;
    public int GesteinWassergehaltInsgesamt;
    public int GesteinWassergehaltDurchschnitt;
    
    public Rover() {

    }
    
    /**
     * In der Methode act können andere Methoden des Rovers angewendet werden.
     * Die Methoden werden dann nacheinander ausgeführt, wenn man nach dem
     * Kompilieren den Act-Knopf drückt.
     */
    public void act() {
        start();
        //ab hier können sie nun ihren Code schreiben:
        
        //und bis hier hin können sie ihren Code schreiben // Ende des Codes hier.
        end();
    }
    
    public void Aufgabe(String Aufgabe) {
        if (Aufgabe=="Problem1") {
            while(!gesteinVorhanden()) {
                if (huegelVorhanden("vorne")) {
                    drehe("links");
                    if (!huegelVorhanden("vorne")) {
                        fahre();
                    }
                } else if (huegelVorhanden("rechts")) {
                    fahre();
                } else {
                    drehe("rechts");
                    if (!huegelVorhanden("vorne")) {
                        fahre();
                    }
                }
            }
            analysiereGestein();
        } 
        
        
        
        if (Aufgabe=="Problem2") {
            String Error="";
            while(!gesteinVorhanden() && Error=="") {
                if (!huegelVorhanden("rechts")) {
                    drehe("rechts");
                    fahre();
                } else if (!huegelVorhanden("vorne")) {
                    fahre();
                } else if (!huegelVorhanden("links")){
                    drehe("links");
                    fahre();
                } else {
                    drehe("links");
                    drehe("links");
                    if (huegelVorhanden("vorne")) {
                        Error="Ich stecke fest! :(";
                    }
                }
            }
            if (Error=="") {
                analysiereGestein();
                anzeige.anzeigen("Ich habe es geschafft!");
            } else {
                anzeige.anzeigen(Error);
            }
        }
        
        
        
        if (Aufgabe=="Problem3") {
            zuWaendenfahren(2);
            zurWandFahren("links", false);
            zurWandFahren("rechts", true);
        }
    }
    
    public void zuWaendenfahren(int anzahl) {
        for (int i = 0; i<anzahl; i++) {
            zurWandFahren("rechts", false);
            zurWandFahren("links", false);
        }
    }
    
    public void zurWandFahren(String richtung, Boolean ende) {
        String Drehrichtung = "";
        if (richtung=="links") {
            Drehrichtung = "rechts";
        } else if (richtung=="rechts") {
            Drehrichtung = "links";
        }
        if (Drehrichtung=="") {
            anzeige.anzeigen("Ungültige Richtung");
        } else {
            bisZurWandFahren();
            if (!ende) {
                drehe(Drehrichtung);
                fahre();
                drehe(Drehrichtung);
            }
        }
    }
    
    public void bisZurWandFahren()
    {
        while (!huegelVorhanden("vorne")) {
            fahre();
        }
        linksUmHuegelFahren();
        while (!huegelVorhanden("vorne")) {
            fahre();
        }
    }
    
    public void linksUmHuegelFahren ()
    {
        drehe("links");
        fahre();
        drehe("rechts");
        fahre();
        fahre();
        drehe("rechts");
        fahre();
        drehe("links");
    }
    
    public void start() {
        RechtsGedreht=0;
        LinksGedreht=0;
        Gedreht=0;
        Gefahren=0;
        Analysiert=0;
        Aufgehoben=0;
        MarkenGesetzt=0;
        MarkenEntfernt=0;
        
        clearConsole();
        System.out.println("----------Programmstart----------");
    }
    
    public void end() {
        GesteinWassergehaltDurchschnitt=GesteinWassergehaltInsgesamt/Analysiert;
        AktionenInsgesamt = Gedreht+Gefahren+Analysiert+MarkenGesetzt+MarkenEntfernt;
        
        System.out.println("----------Programmende / Zusammenfassung----------");
        System.out.println("Aktionen insgesamt:                "+AktionenInsgesamt);
        System.out.println("Rechts gedreht:                    "+RechtsGedreht);
        System.out.println("Links gedreht:                     "+LinksGedreht);
        System.out.println("Insgesamt gedreht:                 "+Gedreht);
        System.out.println("Insgesamt gefahren:                "+Gefahren);
        System.out.println("Gesteine Analysiert:               "+Analysiert);
        System.out.println("Gesteine Aufgehoben:               "+Aufgehoben);
        System.out.println("Marken gesetzt:                    "+MarkenGesetzt);
        System.out.println("Marken entfernt:                   "+MarkenEntfernt);
        System.out.println("Gestein Wassergehalt Durchschnitt: "+GesteinWassergehaltDurchschnitt+"%");
        System.out.println("----------Programmende / Zusammenfassung----------");
    }
    
    public void drehe(String richtung, Integer wieOft) {
        int i = 0;
        int posX = getX();
        int posY = getY();
        while (i < wieOft) {
            drehe(richtung, "silent");
            Greenfoot.delay(1);
            i++;
        } 
    }
    
    public void drehe(Integer grad) {
       int i = grad;
       int gedreht = 0;
       int posX = getX();
       int posY = getY();
       String extra = "";
       setRotation(getRotation()+grad);
       if (i>0) {
           while (i>0) {
               i=i-90;
               RechtsGedreht++;
               Gedreht++;
           }
       } else if (i<0) {
           while (i<0) {
               i=i-90;
               LinksGedreht++;
               Gedreht++;
           }
       }
       if (i!=0) {
           extra="(Hint: Nimm am besten einen Wert, teilbar durch 90. "+grad+" ist nicht teilbar durch 90; R"+i+")";
       }
       System.out.println("X:"+posX+" Y:"+posY+" - Um "+grad+"° gedreht "+extra);
       Greenfoot.delay(1);
    }
    
    public void fahre(Integer wieOft) {
        int i = 0;
        while (i < wieOft) {
            fahre();
            Greenfoot.delay(1);
            i++;
        }
    }

    /**
     * Der Rover bewegt sich ein Feld in Fahrtrichtung weiter. 
     * Sollte sich in Fahrtrichtung ein Objekt der Klasse Huegel befinden oder er sich an der Grenze der Welt befinden,
     * dann erscheint eine entsprechende Meldung auf dem Display.
     */
    public void fahre() {
        int posX = getX();
        int posY = getY();
        if(huegelVorhanden("vorne")) {
            nachricht("Zu steil!");
        } else if(getRotation()==270 && getY()==1) {
            nachricht("Ich kann mich nicht bewegen.");
        } else {
            move(1);
            int newposX = getX();
            int newposY = getY();
            System.out.println("X:"+posX+" Y:"+posY+" - Gefahren zu X:"+newposX+" Y:"+newposY);
            Greenfoot.delay(1);
            Gefahren++;
        }
    }

    /**
     * Der Rover dreht sich um 90 Grad in die Richtung, die mit richtung ("links" oder "rechts") übergeben wurde.
     * Sollte ein anderer Text (String) als "rechts" oder "links" übergeben werden, dann erscheint eine entsprechende Meldung auf dem Display.
     */
    
    public void drehe(String richtung, String silent) {
        int newposX = getX();
        int newposY = getY();   
        if(richtung=="rechts") {
            setRotation(getRotation()+90);
            if (silent=="silent") {
                System.out.println("X:"+newposX+" Y:"+newposY+" - Nach rechts gedreht");
            }   
            Greenfoot.delay(1);
            Gedreht++;
            RechtsGedreht++;
        } else if (richtung=="links") {
            setRotation(getRotation()-90);
            if (silent=="silent") {
                System.out.println("X:"+newposX+" Y:"+newposY+" - Nach links gedreht");
            }   
            Greenfoot.delay(1);
            Gedreht++;
            LinksGedreht++;
        } else {
            nachricht("'"+richtung+"' - Befehl nicht korrekt!");
        }
    }

    public void drehe(String richtung) {
        int newposX = getX();
        int newposY = getY();   
        if(richtung=="rechts") {
            setRotation(getRotation()+90);
            System.out.println("X:"+newposX+" Y:"+newposY+" - Nach rechts gedreht");
            Greenfoot.delay(1);
            Gedreht++;
            RechtsGedreht++;
        } else if (richtung=="links") {
            setRotation(getRotation()-90);
            System.out.println("X:"+newposX+" Y:"+newposY+" - Nach links gedreht");
            Greenfoot.delay(1);
            Gedreht++;
            LinksGedreht++;
        } else {
            nachricht("'"+richtung+"' - Befehl nicht korrekt!");
        }
    }    
    
    /**
      Der Rover gibt durch einen Wahrheitswert (true oder false) zurück, ob sich auf seiner Position ein Objekt der Klasse Gestein befindet.
      Eine entsprechende Meldung erscheint auch auf dem Display.
     */
    public boolean gesteinVorhanden() {
        if(getOneIntersectingObject(Gestein.class)!=null){
            nachricht("Gestein gefunden!");
            return true; 
        }
        return false;
    }
    
    public boolean gesteinVorhanden(String silent) {
        if(getOneIntersectingObject(Gestein.class)!=null){
            if (!(silent=="silent")) {
               nachricht("Gestein gefunden!"); 
            }
            return true;
        }
        return false;
    }

    /**
     * Der Rover ermittelt den Wassergehalt des Gesteins auf seiner Position und gibt diesen auf dem Display aus.
     * Sollte kein Objekt der Klasse Gestein vorhanden sein, dann erscheint eine entsprechende Meldung auf dem Display.
     */
    public void analysiereGestein(String delete) {
        int newposX = getX();
        int newposY = getY();
        if(gesteinVorhanden("silent")) {
            nachricht("Gestein untersucht! Wassergehalt ist " + ((Gestein)getOneIntersectingObject(Gestein.class)).getWassergehalt()+"%.");
            Analysiert++;
            GesteinWassergehaltInsgesamt=GesteinWassergehaltInsgesamt+((Gestein)getOneIntersectingObject(Gestein.class)).getWassergehalt();
            if (delete=="aufsammeln") {
                removeTouching(Gestein.class);
                System.out.println("X:"+newposX+" Y:"+newposY+" - Gestein aufgesammelt");
                Aufgehoben++;
            }
        } else {
            nachricht("Hier ist kein Gestein.");
            System.out.println("X:"+newposX+" Y:"+newposY+" - Analysiere Gestein. Nicht gefunden!");
        }
    }
    
    public void analysiereGestein() {
        int newposX = getX();
        int newposY = getY();
        if(gesteinVorhanden("silent")) {
            nachricht("Gestein untersucht! Wassergehalt ist " + ((Gestein)getOneIntersectingObject(Gestein.class)).getWassergehalt()+"%.");
            Analysiert++;
            GesteinWassergehaltInsgesamt=GesteinWassergehaltInsgesamt+((Gestein)getOneIntersectingObject(Gestein.class)).getWassergehalt();
        } else {
            nachricht("Hier ist kein Gestein.");
            System.out.println("X:"+newposX+" Y:"+newposY+" - Analysiere Gestein. Nicht gefunden!");
        }
    }

    /**
     * Der Rover überprüft, ob sich in richtung ("rechts", "links", oder "vorne") ein Objekt der Klasse Huegel befindet.
     * Das Ergebnis wird auf dem Display angezeigt.
     * Sollte ein anderer Text (String) als "rechts", "links" oder "vorne" übergeben werden, dann erscheint eine entsprechende Meldung auf dem Display.
     */
    public boolean huegelVorhanden(String richtung) {
        int rot = getRotation();
        if (richtung=="vorne" && rot==0 || richtung=="rechts" && rot==270 || richtung=="links" && rot==90) {
            if(getOneObjectAtOffset(1,0,Huegel.class)!=null && ((Huegel)getOneObjectAtOffset(1,0,Huegel.class)).getSteigung() >30) {
                return true;
            }
        }
        if (richtung=="vorne" && rot==180 || richtung=="rechts" && rot==90 || richtung=="links" && rot==270) {
            if(getOneObjectAtOffset(-1,0,Huegel.class)!=null && ((Huegel)getOneObjectAtOffset(-1,0,Huegel.class)).getSteigung() >30) {
                return true;
            }
        }
        if (richtung=="vorne" && rot==90 || richtung=="rechts" && rot==0 || richtung=="links" && rot==180) {
            if(getOneObjectAtOffset(0,1,Huegel.class)!=null && ((Huegel)getOneObjectAtOffset(0,1,Huegel.class)).getSteigung() >30) {
                return true;
            }
        }
        if (richtung=="vorne" && rot==270 || richtung=="rechts" && rot==180 || richtung=="links" && rot==0) {
            if(getOneObjectAtOffset(0,-1,Huegel.class)!=null && ((Huegel)getOneObjectAtOffset(0,-1,Huegel.class)).getSteigung() >30) {
                return true;
            }
        }
        if(richtung!="vorne" && richtung!="links" && richtung!="rechts") {
            nachricht("Befehl nicht korrekt!");
        }
        return false;
    }

    /**
     * Der Rover erzeugt ein Objekt der Klasse "Marke" auf seiner Position.
     */
    public void setzeMarke() {
        int newposX = getX();
        int newposY = getY();
        getWorld().addObject(new Marke(), getX(), getY());
        System.out.println("X:"+newposX+" Y:"+newposY+" - Marke gesetzt");
        MarkenGesetzt++;
    }

    /**
     * Der Rover gibt durch einen Wahrheitswert (true oder false) zurück, ob sich auf seiner Position ein Objekt der Klasse Marke befindet.
     * Eine entsprechende Meldung erscheint auch auf dem Display.
     */
    public boolean markeVorhanden() {
        if(getOneIntersectingObject(Marke.class)!=null) {
            return true;
        }
        return false;
    }

    /**
     * Falls an der Position des Rovers eine Markierung vorhanden ist, so entfernt er diese.
     */
    public void entferneMarke() {
        int newposX = getX();
        int newposY = getY();
        if(markeVorhanden()) {
            removeTouching(Marke.class);
            System.out.println("X:"+newposX+" Y:"+newposY+" - Marke entfernt");
            MarkenEntfernt++;
        } else {
            System.out.println("X:"+newposX+" Y:"+newposY+" - Marke vergeblich entfernt");
        }
    }

    /**
     * Das Display des Rovers wird entfernt, d. h. gelöscht.
     */
    public void schalteDisplayAus() {
        int newposX = getX();
        int newposY = getY();
        getWorld().removeObject(anzeige);
        System.out.println("X:"+newposX+" Y:"+newposY+" - Display ausgeschaltet");
    }    

    protected void nachricht(String text) {
        int newposX = getX();
        int newposY = getY(); 
        if(anzeige!=null) {
            anzeige.anzeigen(text);
            Greenfoot.delay(1);
            anzeige.loeschen();
            System.out.println("X:"+newposX+" Y:"+newposY+" - "+text);
        }
    }

    public String ls() {
        return System.lineSeparator();
    }
    
    protected void addedToWorld(World world) {
        setImage("images/rover.png");
        world = getWorld();
        anzeige = new Display();
        world.addObject(anzeige, 7, 0);
        if(getY()==0) {
            setLocation(getX(),1);
        }
        anzeige.anzeigen("Ich bin bereit");
    }
    
    public static void clearConsole() {  
        for(int clear = 0; clear < 400; clear++) {
            System.out.println("\b") ;
        }
    }  
}