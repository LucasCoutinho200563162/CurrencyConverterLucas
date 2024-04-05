module org.example.currencyconverterlucas {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens org.example.currencyconverterlucas to javafx.fxml;
    exports org.example.currencyconverterlucas;
}