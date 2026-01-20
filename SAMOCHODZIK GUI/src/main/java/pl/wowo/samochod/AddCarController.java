package pl.wowo.samochod;

import pl.wowo.samochod.symulator.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class AddCarController {
    @FXML
    private TextField modelField;
    @FXML
    private TextField plateField;

    private Samochod noweAuto;



    @FXML private TextField engineNameField, engineWeightField, enginePriceField, engineMaxRpmField;
    @FXML private TextField gearboxNameField, gearboxCountField, gearboxWeightField, gearboxPriceField;
    @FXML private TextField clutchNameField, clutchWeightField, clutchPriceField;

    @FXML
    public void onAdd() {

        String model = modelField.getText();
        String nr = plateField.getText();

        if (model.isEmpty() || nr.isEmpty()) {
            System.out.println("Model i Nr rej są wymagane!");
            return;
        }


        noweAuto = new Samochod(model, nr, null);


        try {
            String eName = engineNameField.getText();
            double eWeight = Double.parseDouble(engineWeightField.getText());
            double ePrice = Double.parseDouble(enginePriceField.getText());
            int eMaxRpm = Integer.parseInt(engineMaxRpmField.getText());

            Silnik customSilnik = new Silnik(eName, eWeight, ePrice, eMaxRpm);
            noweAuto.setSilnik(customSilnik);


            String gName = gearboxNameField.getText();
            int gCount = Integer.parseInt(gearboxCountField.getText());
            double gWeight = Double.parseDouble(gearboxWeightField.getText());
            double gPrice = Double.parseDouble(gearboxPriceField.getText());

            SkrzyniaBiegow customSkrzynia = new SkrzyniaBiegow(gName, gWeight, gPrice, gCount);
            noweAuto.setSkrzynia(customSkrzynia);

            String cName = clutchNameField.getText();
            double cWeight = Double.parseDouble(clutchWeightField.getText());
            double cPrice = Double.parseDouble(clutchPriceField.getText());
            boolean cState = true;
            Sprzeglo customSprzeglo = new Sprzeglo(cName, cWeight, cPrice, cState);
            noweAuto.getSkrzynia().setSprzeglo(customSprzeglo);
            closeWindow();

        } catch (NumberFormatException e) {
            System.out.println("Błąd: Wpisz poprawne liczby w polach wagi/ceny!");
        }
    }

    @FXML
    public void onCancel() {
        noweAuto = null;
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) modelField.getScene().getWindow();
        stage.close();
    }

    public Samochod getNoweAuto() {
        return noweAuto;
    }
}
