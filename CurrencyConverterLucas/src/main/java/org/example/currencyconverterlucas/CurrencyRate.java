package org.example.currencyconverterlucas;
import javafx.beans.property.SimpleStringProperty;

public class CurrencyRate {

    private final SimpleStringProperty currency;

    public CurrencyRate(String currency) {
        this.currency = new SimpleStringProperty(currency);
    }

    public String getCurrency() {
        return currency.get();
    }

}

