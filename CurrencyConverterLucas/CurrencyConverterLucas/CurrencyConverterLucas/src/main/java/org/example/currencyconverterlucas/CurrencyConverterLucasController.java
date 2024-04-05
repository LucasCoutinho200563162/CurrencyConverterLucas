package org.example.currencyconverterlucas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Map.Entry;
import javafx.scene.Scene;


public class CurrencyConverterLucasController implements Initializable {

    @FXML
    private TextField originalCurrencyField;

    @FXML
    private TextField amountField;

    @FXML
    private TableView<CurrencyRate> currencyTableView;

    @FXML
    private TableColumn<CurrencyRate, String> currencyColumn;

    private ObservableList<CurrencyRate> currencyRates;

    private JSONObject rates;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the table columns
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));
        currencyRates = FXCollections.observableArrayList();
        currencyTableView.setItems(currencyRates);
        currencyTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        // Set the event handler for table row selection
        currencyTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Get the selected currency rate
                String selectedCurrency = newSelection.getCurrency();
                double rate = rates.getDouble(selectedCurrency);

                // Get the amount to convert
                double amountToConvert = Double.parseDouble(amountField.getText());

                // Calculate the converted amount
                double convertedAmount = amountToConvert * rate;

                // Set the converted amount to the label
                switchToDisplayScene(rate, amountToConvert, selectedCurrency);
            }
        });
    }

    @FXML
    private void switchToDisplayScene(double rate, double amount, String newCurrency) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CurrencyConverterDisplay.fxml"));
            Parent newView = loader.load();

            // Get the controller from the loader
            CurrencyConverterLucasDisplayController controller = loader.getController();
            controller.initialize(rate, amount, newCurrency);

            // Setting the stage and setting a new view as scene
            Scene newScene = new Scene(newView);
            Stage stage = (Stage) originalCurrencyField.getScene().getWindow();

            newScene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
            stage.setTitle("Currency Converter - Result");
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void convert() {
        // Get the original currency from the input field
        String originalCurrency = originalCurrencyField.getText().trim();
        String amountText = amountField.getText().trim();

        // Check if originalCurrencyField and amountField are not empty
        if (originalCurrency.isEmpty() && amountText.isEmpty()) {
            // Show warning message
            showAlert("Please enter both original currency and amount to be converted.");
            return;
        } else if (originalCurrency.isEmpty()) {
            showAlert("Please enter the original currency.");
            return;
        } else if (amountText.isEmpty()){
            showAlert("Please enter the amount to be converted.");
            return;
        }

        // Construct the API URL
        String apiUrl = "https://api.exchangerate-api.com/v4/latest/" + originalCurrency;
        System.out.println(amountText);
        try {
            // Fetch data from the API
            String response = CurrencyConverterAPI.fetchDataFromAPI(apiUrl);
            JSONObject data = new JSONObject(response);

            // Check if 'rates' object exists in the response
            if (data.has("rates")) {
                rates = data.getJSONObject("rates");

                // Populate the table view with currency rates
                currencyRates.clear();

                // Sort the currency names alphabetically
                List<String> sortedCurrencies = new ArrayList<>(rates.keySet());
                Collections.sort(sortedCurrencies);

                // Add the sorted currency rates to the list
                for (String currency : sortedCurrencies) {
                    currencyRates.add(new CurrencyRate(currency));
                }
            } else {
                System.out.println("No rates found in the API response.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
