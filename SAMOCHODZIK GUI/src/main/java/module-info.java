module pl.wowo.samochod {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens pl.wowo.samochod to javafx.fxml;
    exports pl.wowo.samochod;
}