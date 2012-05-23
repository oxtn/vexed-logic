package com.austinrasmussen.stockviewer;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Xml;

public class StockQuoteFetcher {
	private final String NAMESPACE = "http://www.webserviceX.NET/";
	private final String METHOD_NAME = "GetQuote";
	private final String SOAP_ACTION = "http://www.webserviceX.NET/GetQuote";
	private final String URL = "http://www.webservicex.net/stockquote.asmx";
	
	private final SoapSerializationEnvelope envelope;
	
	public StockQuoteFetcher(String quotes)
	{
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		PropertyInfo quotesProperty = new PropertyInfo();
		quotesProperty.setName("symbol");
		quotesProperty.setValue(quotes);
		quotesProperty.setType(String.class);
		request.addProperty(quotesProperty);
		
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.addMapping(NAMESPACE, "StockQuote", new StockQuote().getClass());
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
	}
	
	public List<StockQuote> Fetch()
	{
		HttpTransportSE httpRequest = new HttpTransportSE(URL);
		
		try
        {
			httpRequest.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			
			StockQuoteHandler quoteParser = new StockQuoteHandler(response.toString());
			quoteParser.parse();
			return quoteParser.getQuotes();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
		return new ArrayList<StockQuote>();
	}
		
}
