package pl.wowo.samochod.symulator;

import javafx.scene.image.Image;
import javafx.scene.Node;
import java.util.ArrayList;
import java.util.List;

public class Samochod extends Thread {

    private String model;
    private String nrRejestracyjny;
    private Silnik silnik;
    private SkrzyniaBiegow skrzynia;

    private Pozycja pozycja;
    private Pozycja cel;

    private volatile boolean isRunning = true;
    private volatile boolean maCel = false;

    private Node ikonka;

    private List<Listener> listeners = new ArrayList<>();

    public Samochod(String model, String nrRejestracyjny, Node ikonka) {
        this.model = model;
        this.nrRejestracyjny = nrRejestracyjny;
        this.ikonka = ikonka;

        this.silnik = new Silnik("V12", 300, 50000, 7000);
        this.skrzynia = new SkrzyniaBiegow("Manualna", 80, 5000, 6);

        this.pozycja = new Pozycja(0,0);
        this.cel = new Pozycja(0,0);

        setDaemon(true); //watek to daemon, czyli sie sam zamknie
    }

    public void wlacz() { silnik.uruchom();}
    public void wylacz() { silnik.zatrzymaj();}

    public void jedzDo(double x, double y) {
        this.cel = new  Pozycja(x,y);
        this.maCel = true;

        if (getState() == State.NEW) {
            start();
        }
    }

    public void addListener(Listener l) {
        listeners.add(l);
    }
    public void removeListener(Listener l) {
        listeners.remove(l);
    }
    private void notifyListeners() {
        for (Listener l : listeners) {
            l.update();
        }
    }

    public double getAktualnaPredkosc() {
        // Warunek: Auto ma prędkość tylko gdy ma cel (jedzie), silnik działa i bieg jest wrzucony
        if (maCel && silnik.getObroty() > 0 && skrzynia.getAktualnyBieg() > 0) {

            // Prosty wzór na km/h: (Obroty * Bieg) / 100
            // Np. 2000 obr * 3 bieg = 6000 / 100 = 60 km/h
            return (silnik.getObroty() * skrzynia.getAktualnyBieg()) / 100.0;
        }
        return 0.0; // W przeciwnym razie stoi
    }

    @Override //nadpisuje threada
    public void run() {
        while (isRunning) {
            try {
                if (maCel && silnik.getObroty() > 0 && skrzynia.getAktualnyBieg() > 0) {
                    obliczNowaPozycje();
                    notifyListeners();
                }
                Thread.sleep(20);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

   private void obliczNowaPozycje() {
           double dx = cel.getX() - pozycja.getX();
           double dy = cel.getY() - pozycja.getY();
           double dystans = Math.sqrt(dx * dx + dy * dy);

           if (dystans < 5.0) {
               maCel = false;
               notifyListeners();
               return;
           }
           //predkosc przemieszczania opisana wzorem: (obroty*bieg)/stała
           double wspolczynnikPredkosci = (silnik.getObroty() * skrzynia.getAktualnyBieg()) / 3000.0;
           double moveX = (dx/dystans) * wspolczynnikPredkosci;
           double moveY = (dy/dystans) * wspolczynnikPredkosci;

           pozycja.uaktualnijPozycje(moveX, moveY);
   }

    public void zwiekszBieg() {
        if (silnik.getObroty() == 0) { return; }
        int staryBieg = skrzynia.getAktualnyBieg();
        skrzynia.zwiekszBieg();
        int nowyBieg = skrzynia.getAktualnyBieg();

        if (nowyBieg > staryBieg) {
            silnik.zmniejszObroty(1000);
        }
    }
    public void zmniejszBieg() {
        if (silnik.getObroty() == 0) { return; }
        int staryBieg = skrzynia.getAktualnyBieg();
        skrzynia.zmniejszBieg();
        int nowyBieg = skrzynia.getAktualnyBieg();
        if (nowyBieg < staryBieg) {
            silnik.zwiekszObroty(1000);
        }
    }


    public Silnik getSilnik() {return silnik;}
    public String getModel() {return model;}
    public double getWaga() {return (silnik.getWaga() + skrzynia.getWaga() + skrzynia.getSprzeglo().getWaga());}
    public SkrzyniaBiegow getSkrzynia() {return skrzynia;}
    public String getNrRejestracyjny() {return  nrRejestracyjny;}

    public void setSilnik(Silnik silnik) {
        this.silnik = silnik;
    }
    public void setSkrzynia(SkrzyniaBiegow skrzynia) {
        this.skrzynia = skrzynia;
    }
    public void setIkonka(Node ikonka) {
        this.ikonka = ikonka;
    }
    public Node getIkonka() {return ikonka;}

    @Override
    public String toString() {
        return model + "(" + nrRejestracyjny + ")";
    }
    public Pozycja getPozycja() {return pozycja;}

    public Pozycja getCel() {
        return  cel;
    }
}
