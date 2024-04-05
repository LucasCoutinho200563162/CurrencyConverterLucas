package org.example.currencyconverterlucas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class CurrencyConverterLucas extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            // Load main scene
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("CurrencyConverterMain.fxml"));
            Parent mainRoot = mainLoader.load();
            CurrencyConverterLucasController mainController = mainLoader.getController();

            // Load CSS file
            Scene mainScene = new Scene(mainRoot, 600, 400);
            mainScene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());

            primaryStage.setTitle("Currency Converter");
            String imagePath = getClass().getResource("/img/images.png").toExternalForm();
            primaryStage.getIcons().add(new Image(imagePath));

            primaryStage.setResizable(false);
            primaryStage.setScene(mainScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}
