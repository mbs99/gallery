package de.mbs.gallery.client.view;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import de.mbs.gallery.client.presenter.AppPanelPresenter;


public class DefaultNavbar extends Composite {
	
	private static DefaultNavbarUiBinder uiBinder = GWT
			.create(DefaultNavbarUiBinder.class);

	interface DefaultNavbarUiBinder extends UiBinder<Widget, DefaultNavbar> {
	}
	
	private static final Logger logger = Logger.getLogger("DefaultNavbar");
	
	private AppPanelPresenter presenter;

	public DefaultNavbar(AppPanelPresenter presenter) {
		
		this.presenter = presenter;
		initWidget(uiBinder.createAndBindUi(this));
	}
}
