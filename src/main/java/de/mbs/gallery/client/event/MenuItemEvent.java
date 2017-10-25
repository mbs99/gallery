package de.mbs.gallery.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import de.mbs.gallery.client.event.MenuItemEvent.MenuItemEventEventHandler;

public class MenuItemEvent extends GwtEvent<MenuItemEventEventHandler> {
	
	public enum MENU_ITEM {
		SHOW_GALLERY, SHOW_NEXT_IMAGE, SHOW_PREVIOUS_IMAGE
	};
	
	private MENU_ITEM item;
	
	public MenuItemEvent(MENU_ITEM item) {
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
	
	public MENU_ITEM getItem() {
		return item;
	}
}
