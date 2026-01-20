package pl.wowo.samochod.symulator;

public class Pozycja {
    private double x;
    private double y;
    public Pozycja(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void uaktualnijPozycje(double deltaX, double deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
}
