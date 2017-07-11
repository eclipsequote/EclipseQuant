package com.xclu.eclipsequote.views;


import com.xclu.eclipsequote.EclipseQuotePlugin;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.markers.internal.TableSorter;


/**
 * 
 * This class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 * @author Justin Sher
 */

public class QuoteView extends ViewPart {
	private Action newQuote;
	private Action refreshQuotes;
	private Action deleteQuote;
	Action doubleClickAction;
	private TableSorter sorter;
	TableViewer viewer;
	QuoteViewContentProvider contentProvider;


	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public QuoteView() {
	}

		
		

	private void hookContextMenu() {
		
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				QuoteView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	/**
	 * @param manager
	 */
	protected void fillContextMenu(IMenuManager manager) {
		manager.add(refreshQuotes);
		manager.add(newQuote);
		manager.add(deleteQuote);		
	}




	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(refreshQuotes);
		manager.add(new Separator());
		manager.add(deleteQuote);
		manager.add(newQuote);
	}

	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(refreshQuotes);
		manager.add(newQuote);
		manager.add(deleteQuote);
	}

	private void makeActions() {
		newQuote = new Action() {
			public void run() {
				String symbol=getValue("a ticker symbol");
				if (symbol!=null && symbol.length()>0) {
						contentProvider.addSymbol(symbol.toUpperCase());

				}
				contentProvider.updateQuotes();
				viewer.refresh();
			}
		};
		newQuote.setText("New Quote");
		newQuote.setToolTipText("New Quote");
		newQuote.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
		
		refreshQuotes = new Action() {
			public void run() {
				contentProvider.updateQuotes();
				viewer.refresh();
				
			}
		};
		refreshQuotes.setText("Refresh Quotes");
		refreshQuotes.setToolTipText("Refresh Quotes");
		refreshQuotes.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_TOOL_REDO));


		deleteQuote = new Action() {
			public void run() {
				StructuredSelection selection = (StructuredSelection) viewer.getSelection();
				contentProvider.remove(selection.toList());
				viewer.refresh();
			}
		};
		deleteQuote.setText("Delete Quote");
		deleteQuote.setToolTipText("Delete Quote");
		deleteQuote.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));

		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				showMessage("Double-click detected on "+obj.toString());
			}
		};
	}

	private void hookDoubleClickAction() {
		
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"QuoteView",
			message);
	}

	public String getValue (String valName) {
		InputDialog valueDialog=new InputDialog(viewer.getControl().getShell(),"Enter "+valName,"Please Enter "+valName,"",null);
		valueDialog.open();
		return valueDialog.getValue();
	} 

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
 			viewer.getControl().setFocus();
	}




	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPartControl(Composite parent) {
		int tableStyle = SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION;
		 viewer = new TableViewer(parent, tableStyle);
		 viewer.setUseHashlookup(true);

		 Table quoteTable = viewer.getTable();

		 TableColumn messageColumn = new TableColumn(quoteTable, SWT.LEFT);
		 messageColumn.setResizable(true);
		 messageColumn.setText("Symbol");
		 messageColumn.setWidth(50);

		 TableColumn ruleColumn = new TableColumn(quoteTable, SWT.LEFT);
		 ruleColumn.setResizable(true);
		 ruleColumn.setText("Price");
		 ruleColumn.setWidth(50);

		TableColumn changeColumn = new TableColumn(quoteTable, SWT.LEFT);
		changeColumn.setResizable(false);
		changeColumn.setText("Change");
		changeColumn.setWidth(50);
		 
		 TableColumn pctColumn = new TableColumn(quoteTable, SWT.LEFT);
		 pctColumn.setResizable(true);
		 pctColumn.setText("Pct. Change");
		 pctColumn.setWidth(80);

		TableColumn projectColumn = new TableColumn(quoteTable, SWT.LEFT);
		projectColumn.setResizable(true);
		projectColumn.setText("High");
		projectColumn.setWidth(50);


		TableColumn lineColumn = new TableColumn(quoteTable, SWT.LEFT);
		lineColumn.setResizable(false);
		lineColumn.setText("Low");
		lineColumn.setWidth(50);

		TableColumn openColumn = new TableColumn(quoteTable, SWT.LEFT);
		openColumn.setResizable(false);
		openColumn.setText("Open");
		openColumn.setWidth(50);

		TableColumn prevClose = new TableColumn(quoteTable, SWT.LEFT);
		prevClose.setResizable(false);
		prevClose.setText("Prev. Close");
		prevClose.setWidth(80);


		TableColumn volumeColumn = new TableColumn(quoteTable, SWT.LEFT);
		volumeColumn.setResizable(true);
		volumeColumn.setText("Volume");
		volumeColumn.setWidth(80);


		 quoteTable.setLinesVisible(true);
		 quoteTable.setHeaderVisible(true);

		viewer.setLabelProvider(new QuoteLabelProvider());
		 viewer.setContentProvider(contentProvider);
		viewer.setInput(getTargetResource());		
		makeActions();
		hookDoubleClickAction();		
		hookContextMenu();
		contributeToActionBars();
		contentProvider.updateQuotes();
		viewer.refresh();

	}

	/**
	 * Return the appropriate resource displayed in the view.
	 * Currently, it returns always the workspace root
	 */
	private IResource getTargetResource() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}



	/* (non-Javadoc)
	 * @see org.eclipse.ui.IViewPart#init(org.eclipse.ui.IViewSite, org.eclipse.ui.IMemento)
	 */
	public void init(IViewSite site, IMemento memento)
		throws PartInitException {
		if (contentProvider==null) { 
			contentProvider=EclipseQuotePlugin.getDefault().getContentProvider(memento);
		}		
		super.init(site, memento);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IViewPart#saveState(org.eclipse.ui.IMemento)
	 */
	public void saveState(IMemento memento) {
		contentProvider.save(memento);
		super.saveState(memento);
	}

}
