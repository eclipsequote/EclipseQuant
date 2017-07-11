/*
 * Created on Sep 7, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.xclu.eclipsequote.views;

import java.text.NumberFormat;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * @author Justin Sher
 */
public class QuoteLabelProvider implements ITableLabelProvider {

	/**
	 * 
	 */
	public QuoteLabelProvider() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
	 */
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 */
	public String getColumnText(Object element, int columnIndex) {
		Stock stock= (Stock) element;
		if (columnIndex==0)  { return stock.getSymbol(); }
		if (columnIndex==1)  { return nullConvert(stock.getPrice()); }
		if (columnIndex==2)  { return nullConvert(stock.getChange()); }
		if (columnIndex==3)  { return nullConvert(stock.getChangePercent()); }
		if (columnIndex==4)  { return nullConvert(stock.getHigh()); }
		if (columnIndex==5)  { return nullConvert(stock.getLow()); }
		if (columnIndex==6)  { return nullConvert(stock.getOpen()); }
		if (columnIndex==7)  { return nullConvert(stock.getPrevClose()); }
		if (columnIndex==8)  { return nullConvert(stock.getVolume()); }

		return "";
	}

	/**
	 * @param string
	 * @return
	 */
	private String nullConvert(String value) {
		if (value==null) { return "";}
		return value.toString();
	}

	/**
	 * @param double1
	 * @return
	 */
	private String nullConvert(Double value) {
		if (value==null) { return "";}
		NumberFormat nf=NumberFormat.getNumberInstance();
		nf.setMaximumIntegerDigits(2);
		nf.setMinimumFractionDigits(2);
		nf.setMinimumIntegerDigits(1);
		nf.setGroupingUsed(true);
		return nf.format(value);
	}

	/**
	 * @param double1
	 * @return
	 */
	private String nullConvert(Long value) {
		if (value==null) { return "";}
		NumberFormat nf=NumberFormat.getNumberInstance();
		nf.setMinimumIntegerDigits(1);
		nf.setGroupingUsed(true);
		return nf.format(value);
	}

}
