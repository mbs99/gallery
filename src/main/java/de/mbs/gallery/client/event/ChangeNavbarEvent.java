package de.mbs.gallery.client.event;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import de.mbs.gallery.client.event.ChangeNavbarEvent.ChangeNavbarEventEventHandler;

public class ChangeNavbarEvent extends GwtEvent<ChangeNavbarEventEventHandler> {
	
	private ENavbarType navbarType;
	
	private Map<String, String> eventParams = new LinkedHashMap<>();
	
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
	
	public void addEventParam(String key, String value) {
		eventParams.put(key, value);
	}
	
	public String getEventParam(String key) {
		return eventParams.containsKey(key) ? eventParams.get(key) : "";
	}
}
