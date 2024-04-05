package org.example.currencyconverterlucas;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class CurrencyConverterLucasDisplayController {
    @FXML
    private Label calculationLabel;

    public void initialize(double rate, double amount, String newCurrency) {
        double convertedAmount = rate * amount;
        calculationLabel.setText("Converted Amount: \n" + convertedAmount + " " + newCurrency);
    }

    @FXML
    private void switchToOriginalScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CurrencyConverterMain.fxml"));
            Parent newView = loader.load();

            // Setting the stage and setting a new view as scene
            Scene newScene = new Scene(newView, 600, 400);
            Stage stage = (Stage) calculationLabel.getScene().getWindow();
            newScene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());

            stage.setTitle("Currency Converter");
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
