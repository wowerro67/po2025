package pl.wowo.samochod.symulator;

public class Silnik extends Komponent {
    private int maxObroty;
    private int obroty;
    public Silnik(String nazwa, double waga, double cena, int maxObroty) {
        super(nazwa, waga, cena);
        this.maxObroty = maxObroty;
        this.obroty = 0;
    }
    public void uruchom(){
        this.obroty = 800;
    }
    public void zatrzymaj() {
        this.obroty = 0;

    }
    public void zwiekszObroty() {
        if (this.maxObroty > this.obroty) {this.obroty += 200; }
    }
    public void zmniejszObroty() {
        if (this.obroty > 800 ) {this.obroty -= 200;}
    }

    public void zwiekszObroty(int delta) {
        if (this.obroty + delta <= this.maxObroty) {this.obroty += delta;}
        else {this.obroty = this.maxObroty;}
    }
    public void zmniejszObroty(int delta) {
        if (this.obroty - delta >= 800) {this.obroty -= delta;}
        else {this.obroty = 800;}
    }


    public int getMaxObroty() {
        return maxObroty;
    }
    public int getObroty() {
        return obroty;
    }
    public void setmaxObroty(int maxObroty) {
        this.maxObroty = maxObroty;
    }
}
