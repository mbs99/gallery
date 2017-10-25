package de.mbs.gallery.client.view;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import de.mbs.gallery.client.presenter.AppPanelPresenter;

public class AppPanel extends Composite {
	
	private static AppPanelUiBinder uiBinder = GWT
			.create(AppPanelUiBinder.class);

	interface AppPanelUiBinder extends UiBinder<Widget, AppPanel> {
	}
	
	@UiField
	AcceptsOneWidget contentPanel;
	
	@UiField
	HTMLPanel navbarContainer;
	
	private AppPanelPresenter presenter;
	private GalleryViewNavbar galleryViewNavpanel;
 
    public AppPanel(AppPanelPresenter appPanelPresenter) {
    	this.presenter = appPanelPresenter;
    	
        initWidget(uiBinder.createAndBindUi(this));
        
        galleryViewNavpanel = new GalleryViewNavbar(appPanelPresenter);
        navbarContainer.add(galleryViewNavpanel);
    }
    
    public AcceptsOneWidget getContentPanel() {
    	return contentPanel;
    }
    
    @Override
    protected void onLoad() {
    	super.onLoad();
    	
    	$("#filter").change(new Function() {
    		@Override
    		public boolean f(Event e, Object...objects) {
    			
    			presenter.setFilter($(e.getEventTarget()).val());
    			
    			return false;
    		}
		}); 	
    }

	public void setNavbar(Composite navbar) {
		navbarContainer.clear();
		navbarContainer.add(navbar);
	}

}
