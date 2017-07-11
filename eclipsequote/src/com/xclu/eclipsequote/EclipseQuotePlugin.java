package com.xclu.eclipsequote;


import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPluginDescriptor;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.xclu.eclipsequote.views.QuoteViewContentProvider;

/**
 * The main plugin class to be used in the desktop.
 *
 * @author Justin Sher
 * 
 * 
 */
public class EclipseQuotePlugin extends AbstractUIPlugin {
	//The shared instance.
	private static EclipseQuotePlugin plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;
	private QuoteViewContentProvider contentProvider;
	/**
	 * The constructor.
	 */
	public EclipseQuotePlugin(IPluginDescriptor descriptor) {
		super(descriptor);
		plugin = this;
		try {
			resourceBundle= ResourceBundle.getBundle("EclipseQuote.EclipseQuotePluginResources");
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
	}

	/**
	 * Returns the shared instance.
	 */
	public static EclipseQuotePlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the workspace instance.
	 */
	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	/**
	 * Returns the string from the plugin's resource bundle,
	 * or 'key' if not found.
	 */
	public static String getResourceString(String key) {
		ResourceBundle bundle= EclipseQuotePlugin.getDefault().getResourceBundle();
		try {
			return (bundle!=null ? bundle.getString(key) : key);
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns the plugin's resource bundle,
	 */
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	/**
	 * @param memento
	 * @return
	 */
	public QuoteViewContentProvider getContentProvider(IMemento memento) {
		if (contentProvider==null) {
			contentProvider=new QuoteViewContentProvider(memento);
		} 
		return contentProvider;
		
		
	}
}
