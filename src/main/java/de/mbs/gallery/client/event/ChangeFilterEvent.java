package de.mbs.gallery.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import de.mbs.gallery.client.event.ChangeFilterEvent.ChangeFilterEventEventHandler;

public class ChangeFilterEvent extends GwtEvent<ChangeFilterEventEventHandler> {
	
	private String filter;
	
	public ChangeFilterEvent(String filter) {
		this.filter = filter;
	}
	
	public interface ChangeFilterEventEventHandler extends EventHandler {
	    void changeFilter(ChangeFilterEvent event);
	}
	
	public static Type<ChangeFilterEventEventHandler> TYPE = new Type<ChangeFilterEventEventHandler>();


	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChangeFilterEventEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ChangeFilterEventEventHandler handler) {
		handler.changeFilter(this);
	}
	
	public String getFilter() {
		return this.filter;
	}
}
