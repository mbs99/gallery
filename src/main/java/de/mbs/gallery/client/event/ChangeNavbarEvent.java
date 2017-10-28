package de.mbs.gallery.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import de.mbs.gallery.client.event.ChangeNavbarEvent.ChangeNavbarEventEventHandler;

public class ChangeNavbarEvent extends GwtEvent<ChangeNavbarEventEventHandler> {
	
	public enum NAVBAR_TYPE { GALLERY_VIEW, IMAGE_VIEW, ADMIN_VIEW};
	private NAVBAR_TYPE navbarType;
	
	public ChangeNavbarEvent(NAVBAR_TYPE navbarType) {
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
	
	public NAVBAR_TYPE getNavbarType() {
		return navbarType;
	}
}
