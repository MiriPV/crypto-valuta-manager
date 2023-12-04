package com.miranda.cryptovalutamanager.currency;

public class CurrencyBuilder {
    private String ticker;
    private String name;
    private Double numberOfCoins;
    private Double marketCap;

    public CurrencyBuilder(String ticker) {
        this.ticker = ticker;
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

    public CurrencyBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CurrencyBuilder setNumberOfCoins(Double numberOfCoins) {
        this.numberOfCoins = numberOfCoins;
        return this;
    }

    public CurrencyBuilder setMarketCap(Double marketCap) {
        this.marketCap = marketCap;
        return this;
    }

    public Currency build() {
        return new Currency(this);
    }
}
