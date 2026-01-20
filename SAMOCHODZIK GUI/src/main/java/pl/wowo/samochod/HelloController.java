package pl.wowo.samochod;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import pl.wowo.samochod.symulator.Listener;
import pl.wowo.samochod.symulator.Pozycja;
import pl.wowo.samochod.symulator.Samochod;

import java.io.IOException;

public class HelloController {

    private Samochod mojSamochod;
    public AnchorPane mapPane;
    private ObservableList<Samochod> listaSamochodow = FXCollections.observableArrayList();


    public void odswiezWidok(){
        if (mojSamochod == null) {
            carmodelTextField.setText("");
            carregistrationTextField.setText("");
            carweightTextField.setText("");
            enginenameTextField.setText("");
            enginerpmTextField.setText("");
            engineweightTextField.setText("");
            enginepriceTextField.setText("");
            gearboxgearTextField.setText("");
            gearboxweightTextField.setText("");
            gearboxpriceTextField.setText("");
            gearboxnameTextField.setText("");
            clutchweightTextField.setText("");
            clutchpriceTextField.setText("");
            clutchnameTextField.setText("");
            clutchstateTextField.setText("");

            return;
        }
        //wypelnianie menu samochód
        carmodelTextField.setText(mojSamochod.getModel());
        carregistrationTextField.setText(mojSamochod.getNrRejestracyjny());
        carweightTextField.setText(String.valueOf(mojSamochod.getWaga()));
        carspeedTextField.setText(String.format("%.1f km/h", mojSamochod.getAktualnaPredkosc()));

        //wypelnianie menu silnik
        enginenameTextField.setText(mojSamochod.getSilnik().getNazwa());
        enginerpmTextField.setText(String.valueOf(mojSamochod.getSilnik().getObroty()));
        enginepriceTextField.setText(String.valueOf(mojSamochod.getSilnik().getCena()));
        engineweightTextField.setText(String.valueOf(mojSamochod.getSilnik().getWaga()));

        //wypelnianie menu skrzynia biegow
        gearboxgearTextField.setText(String.valueOf(mojSamochod.getSkrzynia().getAktualnyBieg()));
        gearboxnameTextField.setText(mojSamochod.getSkrzynia().getNazwa());
        gearboxpriceTextField.setText(String.valueOf(mojSamochod.getSkrzynia().getCena()));
        gearboxweightTextField.setText(String.valueOf(mojSamochod.getSkrzynia().getWaga()));

        //wypelnianie menu sprzeglo
        clutchnameTextField.setText(mojSamochod.getSkrzynia().getSprzeglo().getNazwa());
        clutchpriceTextField.setText(String.valueOf(mojSamochod.getSkrzynia().getSprzeglo().getCena()));
        clutchstateTextField.setText(String.valueOf(mojSamochod.getSkrzynia().getSprzeglo().getStanSprzegla()));
        clutchweightTextField.setText(String.valueOf(mojSamochod.getSkrzynia().getSprzeglo().getWaga()));

        boolean czyWlaczony = mojSamochod.getSilnik().getObroty() > 0;
        accelerateButton.setDisable(!czyWlaczony);
        deaccelerateButton.setDisable(!czyWlaczony);
        startButton.setDisable(czyWlaczony);
        stopButton.setDisable(!czyWlaczony);

        boolean czySprzeglo = mojSamochod.getSkrzynia().getSprzeglo().getStanSprzegla();

        accelerateButton.setDisable(!czyWlaczony || czySprzeglo);
        deaccelerateButton.setDisable(!czyWlaczony || czySprzeglo);
        shiftupButton.setDisable(!czyWlaczony || !czySprzeglo);
        shiftdownButton.setDisable(!czyWlaczony || !czySprzeglo);

    }

    // W HelloController.java:

    private void stworzIkonkeDlaAuta(Samochod auto) {
        javafx.scene.image.Image obrazek = new javafx.scene.image.Image(getClass().getResourceAsStream("/car.png"));
        ImageView ikonka = new ImageView(obrazek);
        ikonka.setFitWidth(50);
        ikonka.setPreserveRatio(true);

        Label podpis = new Label(auto.getModel() + "\n" + auto.getNrRejestracyjny());
        podpis.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-background-color: rgba(255,255,255,0.7); -fx-padding: 2;");
        podpis.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        VBox kontener = new VBox(ikonka, podpis);
        kontener.setAlignment(javafx.geometry.Pos.CENTER);
        kontener.setLayoutX(0);
        kontener.setLayoutY(0);

        auto.setIkonka(kontener);
        mapPane.getChildren().add(kontener);

        auto.addListener(new Listener() {
            @Override
            public void update() {
                Platform.runLater(() -> {
                    Pozycja p = auto.getPozycja();
                    kontener.setLayoutX(p.getX() - 25);
                    kontener.setLayoutY(p.getY() - 25);

                    if (auto == mojSamochod) {
                        double predkosc = auto.getAktualnaPredkosc();
                        carspeedTextField.setText(String.format("%.1f km/h", predkosc));
                    }
                });
            }
        });

        auto.start();
    }


    public void initialize(){
        carComboBox.setItems(listaSamochodow);
        odswiezWidok();
    }

    public Button startButton;
    public void onStartButton(){
        if (mojSamochod != null){
            mojSamochod.wlacz();
            odswiezWidok();
        }
    }

    public Button stopButton;
    public void onStopButton() {
        if (mojSamochod != null) {

            // gdy samochod wylaczamy to zrzucamy bieg do 0 (bo gasimy)
            mojSamochod.wylacz();
            if (mojSamochod.getSilnik() != null) {
                mojSamochod.getSilnik().zatrzymaj();
            }
            if (mojSamochod.getSkrzynia() != null) {
                mojSamochod.getSkrzynia().luz();
            }
            odswiezWidok();
        }
    }
    public TextField carmodelTextField;
    public TextField carregistrationTextField;
    public TextField carweightTextField;
    public TextField carspeedTextField;
    public TextField gearboxnameTextField;
    public TextField gearboxpriceTextField;
    public TextField gearboxweightTextField;
    public TextField gearboxgearTextField;
    public Button shiftupButton;
    public void onShiftupButton(){
        if (mojSamochod != null){
            mojSamochod.zwiekszBieg();
            odswiezWidok();
        }
    }
    public Button shiftdownButton;
    public void onShiftdownButton(){
       if (mojSamochod != null){
           mojSamochod.zmniejszBieg();
           odswiezWidok();
       }
    }
    public TextField enginenameTextField;
    public TextField enginepriceTextField;
    public TextField engineweightTextField;
    public TextField enginerpmTextField;
    public Button accelerateButton;
    public void onAccelerateButton(){
        if (mojSamochod != null){
            mojSamochod.getSilnik().zwiekszObroty();
            odswiezWidok();
        }
    }
    public Button deaccelerateButton;
    public void onDeaccelerateButton(){
        if (mojSamochod != null){
            mojSamochod.getSilnik().zmniejszObroty();
            odswiezWidok();
        }
    }
    public TextField clutchnameTextField;
    public TextField clutchpriceTextField;
    public TextField clutchweightTextField;
    public TextField clutchstateTextField;
    public Button clutchengButton;
    public void  onClutchEngButton(){
        if (mojSamochod != null) {
            mojSamochod.getSkrzynia().getSprzeglo().setStanSprzegla(true);
            odswiezWidok();
        }
    }
    public Button clutchdngButton;
    public void onClutchDngButton(){
        if (mojSamochod != null) {
            mojSamochod.getSkrzynia().getSprzeglo().setStanSprzegla(false);
            odswiezWidok();
        }
    }
    public ComboBox carComboBox;
    public void onCarComboBoxButton(){
        Samochod wybrane = (Samochod) carComboBox.getSelectionModel().getSelectedItem();
        if (wybrane != null){
            mojSamochod = wybrane;
            odswiezWidok();
        }
        //odswiezWidok();
    }
    public Button addcarButton;
    public void onAddCarButton(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/add-car-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Dodawanie pojazdu");
            stage.setScene(new Scene(root));

            stage.initModality(Modality.APPLICATION_MODAL); //blokuje glowne gui
            stage.showAndWait();

            AddCarController controller = loader.getController();
            Samochod auto = controller.getNoweAuto();

            if (auto != null){
                stworzIkonkeDlaAuta(auto);
                listaSamochodow.add(auto);
                carComboBox.getSelectionModel().select(auto);
                onCarComboBoxButton();
                System.out.println("Dodano: " + auto.getModel());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Button rmcarButton;
    public void onRmCarButton() {
        if (mojSamochod != null) {
            // Zatrzymujemy wątek (ważne, żeby nie aktualizował GUI po śmierci)
            mojSamochod.interrupt();

            if (mojSamochod.getIkonka() != null) {
                mapPane.getChildren().remove(mojSamochod.getIkonka());
            }
            listaSamochodow.remove(mojSamochod);
            if (!listaSamochodow.isEmpty()) {
                Samochod noweWybrane = listaSamochodow.get(0);

                carComboBox.getSelectionModel().select(noweWybrane);
                mojSamochod = noweWybrane;

                System.out.println("Usunięto. Wybrano automatycznie: " + mojSamochod.getModel());
            } else {
                mojSamochod = null;
                carComboBox.getSelectionModel().clearSelection();
                System.out.println("Usunięto ostatnie auto.");
            }
            odswiezWidok();
        }
    }


    public void onMap(javafx.scene.input.MouseEvent event){
        if (mojSamochod != null){
            double x =  event.getX();
            double y = event.getY();
            System.out.println("x: " + x + " y: " + y);
            mojSamochod.jedzDo(x, y);
        }
    }

}