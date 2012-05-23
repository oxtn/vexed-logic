package com.austinrasmussen.stockviewer;

import android.sax.EndTextElementListener;

public class LastEndTextElementListener implements EndTextElementListener {
	private final StockQuote currentQuote;

	LastEndTextElementListener(StockQuote currentQuote) {
		this.currentQuote = currentQuote;
	}

	public void end(String body) {
		currentQuote.setQuote(Double.parseDouble(body));
	}
}