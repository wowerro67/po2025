package symulator;

public class Sprzeglo extends Komponent {
    private boolean stanSprzegla;

    public Sprzeglo(String nazwa, double waga, double cena) {
        super(nazwa, waga, cena);
        this.stanSprzegla = false;
    }
    public void wcisnij(){
        stanSprzegla = true;
        System.out.println("Sprzeglo wcisniete");
    }

    public void zwolnij(){
        stanSprzegla = false;
        System.out.println("Sprzeglo zwolnione");
    }
}
