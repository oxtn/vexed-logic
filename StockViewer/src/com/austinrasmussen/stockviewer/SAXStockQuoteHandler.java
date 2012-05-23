package com.austinrasmussen.stockviewer;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXStockQuoteHandler extends DefaultHandler {

	private ArrayList<StockQuote> quotes = new ArrayList<StockQuote>();
	private StockQuote currentQuote = new StockQuote();
	private String currentNodeText;

	private final String STOCK = "Stock";
	private final String SYMBOL = "Symbol";
	private final String LAST = "Last";

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// Create a new StockQuote for every corresponding
		// <Stock> node in the XML document
		if (localName.equalsIgnoreCase(STOCK)) {
			currentQuote = new StockQuote();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// Retrieve the text content of the current node 
		// that is being processed
		currentNodeText = new String(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(localName.equalsIgnoreCase(SYMBOL)){
			currentQuote.setTickerSymbol(currentNodeText);
		}else if(localName.equalsIgnoreCase(LAST)){
			currentQuote.setQuote(Double.parseDouble(currentNodeText));
		}else if(localName.equalsIgnoreCase(STOCK)){
			// When the </Stock> element is reached, this quote object is complete.
			quotes.add(currentQuote);
		}
	}
	
	public ArrayList<StockQuote> getQuotes()
	{
		return quotes;
	}
}
