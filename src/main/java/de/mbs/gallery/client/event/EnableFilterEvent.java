package de.mbs.gallery.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import de.mbs.gallery.client.event.EnableFilterEvent.EnableFilterEventHandler;

public class EnableFilterEvent extends GwtEvent<EnableFilterEventHandler> {
	
	private boolean enableFilter;
	
	public EnableFilterEvent(boolean enableFilter) {
		this.enableFilter = enableFilter;
	}
	
	public interface EnableFilterEventHandler extends EventHandler {
	    void changeFilter(EnableFilterEvent event);
	}
	
	public static Type<EnableFilterEventHandler> TYPE = new Type<EnableFilterEventHandler>();


	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<EnableFilterEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(EnableFilterEventHandler handler) {
		handler.changeFilter(this);
	}
	
	public boolean getEnableFilter() {
		return this.enableFilter;
	}
}
