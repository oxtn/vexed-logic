package com.austinrasmussen.stockviewer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.xml.sax.SAXException;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Xml;

public class StockQuoteHandler {

	private final ArrayList<StockQuote> quotes = new ArrayList<StockQuote>();
	private final StringReader xmlReader;

	private final String STOCK = "Stock";
	private final String SYMBOL = "Symbol";
	private final String LAST = "Last";

	public StockQuoteHandler(String xml) {
		xmlReader = new StringReader(xml);
	}

	public void parse() throws IOException, SAXException {
		// The StockQuote that is currently being parsed
		final StockQuote currentQuote = new StockQuote();
		// The root element of the XML to parse
		RootElement root = new RootElement("StockQuotes");
		// The individual stock quote node
		Element stockQuote = root.getChild(STOCK);

		// Listener invoked when a </Stock> is encountered 
		stockQuote.setEndElementListener(new EndElementListener() {
			public void end() {
				quotes.add(currentQuote.copy());
			}
		});
		
		// Listener invoked when a </Symbol> is encountered		
		stockQuote.getChild(SYMBOL).setEndTextElementListener(new EndTextElementListener() {
			public void end(String body) {
				currentQuote.setTickerSymbol(body);
			}
		});
		
		// Listener invoked when a </Last> is encountered
		stockQuote.getChild(LAST).setEndTextElementListener(new LastEndTextElementListener(currentQuote));

		Xml.parse(xmlReader, root.getContentHandler());		
	}

	public ArrayList<StockQuote> getQuotes() {
		return quotes;
	}
}
