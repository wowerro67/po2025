package symulator;

public class Silnik extends Komponent{
    private int maxObroty;
    private int obroty;

    public Silnik(String nazwa, double cena, double waga, int maxObroty) {
        super(nazwa, cena, waga);
        this.maxObroty = maxObroty;
        this.obroty = 0;
    }

    public void uruchom(){
        this.obroty = 1000;
        System.out.println("Silnik uruchomiony, obroty: " + obroty);
    }

    public void zatrzymaj(){
        this.obroty = 0;
        System.out.println("Silnik zatrzymany, obroty: " + obroty);
    }

    public int getMaxObroty() {
        return maxObroty;
    }

    public int getObroty() {
        return obroty;
    }
}
