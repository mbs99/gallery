package de.mbs.gallery.client.view;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import de.mbs.gallery.client.activity.LogoutActivity;


public class LogoutView extends Composite {
	
	
	LogoutActivity presenter;
	
	private static LogoutViewUiBinder uiBinder = GWT
			.create(LogoutViewUiBinder.class);

	interface LogoutViewUiBinder extends UiBinder<Widget, LogoutView> {
	}
	
	private static final Logger logger = Logger.getLogger("LogoutView");

	public LogoutView(LogoutActivity activity) {
		this.presenter = activity;
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	protected void onLoad() {
		
		super.onLoad();
		
		logger.finest("onLoad");
		
	}
}
