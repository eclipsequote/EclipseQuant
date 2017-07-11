/*
 * Created on Sep 7, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.xclu.eclipsequote.views;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import com.xclu.eclipsequote.EclipseQuotePlugin;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.ui.IMemento;

/**
 * @author Justin Sher
 */

public class QuoteViewContentProvider extends ArrayContentProvider  implements IStructuredContentProvider, IResourceChangeListener {

	ArrayList stocks=new ArrayList();
	
	public QuoteViewContentProvider(IMemento memento) {
		if (memento==null) { return; }
		IMemento[] mementos = memento.getChildren("quote");
		for (int i=0;i<mementos.length;i++) {
			addSymbol(mementos[i].getChild("symbol").getTextData());
		}
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
			return stocks.toArray(new Object[0]);
	}

	public void addSymbol(String symbol) { 
		stocks.add(new Stock(symbol));
	}

	public void addSymbols(List symbol) { 
		Iterator i=symbol.iterator();
		while (i.hasNext()) { 
			String sym=(String)i.next();
			addSymbol(sym);
		}		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
	 */
	public void resourceChanged(IResourceChangeEvent event) {
		// TODO Auto-generated method stub
			
	}

	/**
	 * @param list
	 */
	public void remove(List list) {
		stocks.removeAll(list);
	}

	/**
	 * 
	 */
	public void updateQuotes() {
		Iterator quotesI=stocks.iterator();
		while (quotesI.hasNext()) { 
			Stock stock = (Stock) quotesI.next();
			loadQuote(stock);
		}
		
	}

	/**
	 * @param stock
	 */
	private void loadQuote(Stock stock) {
		URLConnection conn=null;
		stock.clearPrice();
		
		try {
			String symbol=stock.getSymbol();
			conn=new URL("http://finance.yahoo.com/d/quotes.csv?s="+symbol+"&f=sl1d1t1c1ohgv&e=.csv").openConnection();
			conn.setDoInput(true);
			conn.connect();
	
			BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String stockData=br.readLine();
			StringTokenizer stockTok=new StringTokenizer(stockData,",\"");
			String sym=stockTok.nextToken();
			Double price=new Double(stockTok.nextToken());
			stock.setPrice(price);
			String day=stockTok.nextToken();
			String time=stockTok.nextToken();
			String chg=stockTok.nextToken();
			stock.setChange(new Double(chg));
			stock.setOpen(new Double(stockTok.nextToken()));		
			stock.setHigh(new Double(stockTok.nextToken()));		
			stock.setLow(new Double(stockTok.nextToken()));		
			stock.setVolume(new Long(stockTok.nextToken()));
		}
		catch (Exception e) { 
		
		}
	}
	/**
	 * @return
	 */
	/**
	 * @param memento
	 */
	public void save(IMemento memento) {
		Iterator stocksI=stocks.iterator();
		ArrayList list=new ArrayList();
		while (stocksI.hasNext()) { 
			Stock stocks = (Stock) stocksI.next();
			memento.createChild("quote").createChild("symbol").putTextData(stocks.getSymbol());
		}
	}

}