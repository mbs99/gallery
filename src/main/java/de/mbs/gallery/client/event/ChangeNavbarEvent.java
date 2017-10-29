package de.mbs.gallery.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import de.mbs.gallery.client.event.ChangeNavbarEvent.ChangeNavbarEventEventHandler;

public class ChangeNavbarEvent extends GwtEvent<ChangeNavbarEventEventHandler> {
	
	private ENavbarType navbarType;
	
	public ChangeNavbarEvent(ENavbarType navbarType) {
		this.navbarType = navbarType;
	}
	
	public interface ChangeNavbarEventEventHandler extends EventHandler {
	    void changeNavbar(ChangeNavbarEvent event);
	}
	
	public static Type<ChangeNavbarEventEventHandler> TYPE = new Type<ChangeNavbarEventEventHandler>();


	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChangeNavbarEventEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ChangeNavbarEventEventHandler handler) {
		handler.changeNavbar(this);
	}
	
	public ENavbarType getNavbarType() {
		return navbarType;
	}
}
