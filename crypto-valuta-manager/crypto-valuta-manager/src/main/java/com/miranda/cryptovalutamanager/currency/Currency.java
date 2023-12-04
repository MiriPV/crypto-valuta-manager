package com.miranda.cryptovalutamanager.currency;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Currency {
    @Id
    private String ticker;
    private String name;
    private Double numberOfCoins;
    private Double marketCap;

    public Currency(CurrencyBuilder builder) {
        this.ticker = builder.getTicker();
        this.name = builder.getName();
        this.numberOfCoins = builder.getNumberOfCoins();
        this.marketCap = builder.getMarketCap();
    }

    public Currency() {

    }

    public String getTicker() {
        return ticker;
    }

    public String getName() {
        return name;
    }

    public Double getNumberOfCoins() {
        return numberOfCoins;
    }

    public Double getMarketCap() {
        return marketCap;
    }

    @Override
    public String toString() {
        return "Currency {" +
                "ticker = '" + ticker + '\'' +
                ", name = '" + name + '\'' +
                ", numberOfCoins = " + numberOfCoins +
                ", marketCap = " + marketCap +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberOfCoins(Double numberOfCoins) {
        this.numberOfCoins = numberOfCoins;
    }

    public void setMarketCap(Double marketCap) {
        this.marketCap = marketCap;
    }
}
