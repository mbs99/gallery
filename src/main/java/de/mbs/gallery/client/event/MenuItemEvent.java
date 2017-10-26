package de.mbs.gallery.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import de.mbs.gallery.client.event.MenuItemEvent.MenuItemEventEventHandler;

public class MenuItemEvent extends GwtEvent<MenuItemEventEventHandler> {
	
	private EMenuItem item;
	
	public MenuItemEvent(EMenuItem item) {
		this.item = item;
	}
	
	public interface MenuItemEventEventHandler extends EventHandler {
	    void menuItem(MenuItemEvent event);
	}
	
	public static Type<MenuItemEventEventHandler> TYPE = new Type<MenuItemEventEventHandler>();


	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<MenuItemEventEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MenuItemEventEventHandler handler) {
		handler.menuItem(this);
	}
	
	public EMenuItem getItem() {
		return item;
	}
}
