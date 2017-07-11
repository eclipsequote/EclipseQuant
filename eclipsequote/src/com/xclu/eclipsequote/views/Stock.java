/*
 * Created on Sep 7, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.xclu.eclipsequote.views;

import java.text.NumberFormat;

/**
 * @author Justin Sher
 */
public class Stock  {
	String symbol;
	Double price;
	Double open;
	Double high;
	Double low;
	Double change;
	Long volume;
	/**
	 * 
	 */
	public void clearPrice() {
		price=null; 
		open=null;
		high=null;
		low=null;
		volume=null;		
	} 
	
	

	/**
	 * @param symbol2
	 */
	public Stock(String symbol2) {
		this.symbol=symbol2;		
	}

	/**
	 * @return
	 */


	/**
	 * @return
	 */
	public Double getHigh() {
		return high;
	}

	/**
	 * @param high
	 */
	public void setHigh(Double high) {
		this.high = high;
	}

	/**
	 * @return
	 */
	public Double getLow() {
		return low;
	}

	/**
	 * @param low
	 */
	public void setLow(Double low) {
		this.low = low;
	}

	/**
	 * @return
	 */
	public Double getOpen() {
		return open;
	}

	/**
	 * @param open
	 */
	public void setOpen(Double open) {
		this.open = open;
	}

	/**
	 * @return
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return
	 */
	public Long getVolume() {
		return volume;
	}

	/**
	 * @param volume
	 */
	public void setVolume(Long volume) {
		this.volume = volume;
	}


	public String getPrevClose() {
		if (price!=null && open!=null) { 
			Double prevClose=new Double(price.doubleValue()-change.doubleValue());
		   NumberFormat nf=NumberFormat.getNumberInstance();
		   nf.setMaximumIntegerDigits(2);
		   nf.setMinimumFractionDigits(2);
		   nf.setMinimumIntegerDigits(1);
           return nf.format(prevClose);
		}
		return null;
	}



	/**
	 * @return
	 */
	public String getChangePercent() {
		if (change!=null && price!=null) { 
		Double changePercent=new Double(change.doubleValue()/(getPrice().doubleValue()-change.doubleValue()));
	   NumberFormat nf=NumberFormat.getPercentInstance();
	   nf.setMaximumIntegerDigits(2);
	   nf.setMinimumFractionDigits(2);
	   nf.setMinimumIntegerDigits(1);
	   if (change.doubleValue()>0) 
	   	{ 
	   		return "+"+nf.format(changePercent); 
	   	}
	   	
	   	
	   return nf.format(changePercent);
		}
		return null;
	}


	/**
	 * @return
	 */
	public Double getChange() {
		return change;
	}

	/**
	 * @param change
	 */
	public void setChange(Double change) {
		this.change = change;
	}

}
