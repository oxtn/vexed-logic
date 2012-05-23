package com.austinrasmussen.stockviewer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class PullParserStockQuoteHandler {

	private ArrayList<StockQuote> quotes;
	private StringReader xmlReader;

	private final String STOCK = "Stock";
	private final String SYMBOL = "Symbol";
	private final String LAST = "Last";

	public PullParserStockQuoteHandler(String xml) {
		xmlReader = new StringReader(xml);
	}

	public void parse() throws XmlPullParserException, IOException {
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(xmlReader);

		// The StockQuote that is currently being parsed
		StockQuote currentQuote = null;
		// The current event returned by the parser
		int eventType = parser.getEventType();

		while (eventType != XmlPullParser.END_DOCUMENT) {
			String xmlNodeName;

			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				quotes = new ArrayList<StockQuote>();
				break;
			case XmlPullParser.START_TAG:
				xmlNodeName = parser.getName();
				if (xmlNodeName.equalsIgnoreCase(STOCK)) {
					// When the <Stock> element is reached, create a new
					// StockQuote.
					currentQuote = new StockQuote();
				} else if (xmlNodeName.equalsIgnoreCase(SYMBOL)) {
					currentQuote.setTickerSymbol(parser.nextText());
				} else if (xmlNodeName.equalsIgnoreCase(LAST)) {
					currentQuote
							.setQuote(Double.parseDouble(parser.nextText()));
				}
				break;
			case XmlPullParser.END_TAG:
				xmlNodeName = parser.getName();
				if (xmlNodeName.equalsIgnoreCase(STOCK)) {
					quotes.add(currentQuote);
					break;
				}
			}

			eventType = parser.next();
		}
	}

	public ArrayList<StockQuote> getQuotes() {
		return quotes;
	}
}
