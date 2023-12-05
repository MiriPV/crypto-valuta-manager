package com.miranda.cryptovalutamanager.currency;

public class CurrencyDto {
    private String ticker;
    private String name;
    private Double numberOfCoins;
    private Double marketCap;

    public CurrencyDto(String ticker, String name, Double numberOfCoins, Double marketCap) {
        this.ticker = ticker;
        this.name = name;
        this.numberOfCoins = numberOfCoins;
        this.marketCap = marketCap;
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
        return "CurrencyDto {" +
                "ticker ='" + ticker + '\'' +
                ", name ='" + name + '\'' +
                ", numberOfCoins =" + numberOfCoins +
                ", marketCap =" + marketCap +
                '}';
    }
}
