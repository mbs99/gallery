package de.mbs.gallery.client.view;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.user.client.ui.Composite;

public abstract class AbstractView extends Composite {
	
	protected void showMessage(String selector, String msg) {
		InfoMessage.showMessage($(selector).parent(), msg, 1000);
	}
	
	protected void showError(String selector, String msg) {
		InfoMessage.showError($(selector).parent(), msg, 1000);
	}
}
