package pl.wowo.samochod.symulator;

public class Sprzeglo extends Komponent {
    boolean stanSprzegla;
    public Sprzeglo(String name, double waga, double cena, boolean StanSprzegla) {
        super(name, waga, cena);
        this.stanSprzegla = StanSprzegla;
    }
    public void wcisnij(){
        stanSprzegla = true;
    }
    public void zwolnij(){
        stanSprzegla = false;
    }

    public boolean getStanSprzegla() { return stanSprzegla; }
    public void setStanSprzegla(boolean stanSprzegla) {
        this.stanSprzegla = stanSprzegla;
    }
}
