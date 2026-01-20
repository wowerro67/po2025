package pl.wowo.samochod.symulator;

public class SkrzyniaBiegow extends Komponent {
    private int aktualnyBieg;
    private int iloscBiegow;
    private Sprzeglo sprzeglo;
    public SkrzyniaBiegow(String nazwa,  double waga, double cena, int iloscBiegow) {
        super(nazwa, waga, cena);
        this.iloscBiegow = iloscBiegow;
        this.sprzeglo = new Sprzeglo("Basic", 200, 1000,true);
    }
    public void zwiekszBieg(){
        if (sprzeglo.getStanSprzegla()) {
            if (aktualnyBieg < iloscBiegow) {
                aktualnyBieg++;
                System.out.println("Wrzucono bieg " + aktualnyBieg +".");
            } else {
                System.out.println("TRRRRRRZRTZRTR");
            }
        }
    }
    public void zmniejszBieg(){
        if  (sprzeglo.getStanSprzegla()) {
            if (aktualnyBieg > 0) {
                aktualnyBieg--;
                System.out.println("Redukcja do biegu " + aktualnyBieg +".");
            }
        } else {
            System.out.println("TRRRRRRZRTZRTR");
        }
    }
    public void luz(){
        aktualnyBieg = 0;
    }

    public int getAktualnyBieg() {return aktualnyBieg;}
    public int getIloscBiegow() {return iloscBiegow;}
    public Sprzeglo getSprzeglo() {return sprzeglo;}

    public void setIloscBiegow(int iloscBiegow) {
        this.iloscBiegow = iloscBiegow;
    }
    public void setSprzeglo(Sprzeglo sprzeglo) {
        this.sprzeglo = sprzeglo;
    }

}
