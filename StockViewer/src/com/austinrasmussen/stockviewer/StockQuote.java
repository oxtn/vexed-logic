package com.austinrasmussen.stockviewer;

public class StockQuote {
	private String tickerSymbol;
	private Double quote;

	public StockQuote() {
	}

	public StockQuote(String tickerSymbol, Double quote) {
		this.tickerSymbol = tickerSymbol;
		this.quote = quote;
	}
	
	public void setTickerSymbol(String tickerSymbol) {
		this.tickerSymbol = tickerSymbol;
	}

	public String getTickerSymbol() {
		return tickerSymbol;
	}

	public void setQuote(double quote) {
		this.quote = quote;
	}

	public Double getQuote() {
		return quote;
	}	

	public StockQuote copy() {
		StockQuote clonedQuote = new StockQuote();
		clonedQuote.setQuote(getQuote());
		clonedQuote.setTickerSymbol(getTickerSymbol());
		return clonedQuote;
	}
}