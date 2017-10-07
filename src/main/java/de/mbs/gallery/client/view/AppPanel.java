package de.mbs.gallery.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AppPanel extends Composite {
	
	private static AppPanelUiBinder uiBinder = GWT
			.create(AppPanelUiBinder.class);

	interface AppPanelUiBinder extends UiBinder<Widget, AppPanel> {
	}
	
	@UiField
	AcceptsOneWidget contentPanel;
 
    public AppPanel() {
    	
        initWidget(uiBinder.createAndBindUi(this));
        
    }
    
    public AcceptsOneWidget getContentPanel() {
    	return contentPanel;
    }

}
