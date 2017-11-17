package de.mbs.gallery.client.view;

import static com.google.gwt.query.client.GQuery.$;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import de.mbs.gallery.client.presenter.AppPanelPresenter;


public class CheckoutViewNavbar extends Composite {
	
	private static CheckoutViewNavbarUiBinder uiBinder = GWT
			.create(CheckoutViewNavbarUiBinder.class);

	interface CheckoutViewNavbarUiBinder extends UiBinder<Widget, CheckoutViewNavbar> {
	}
	
	private static final Logger logger = Logger.getLogger("CheckoutViewNavbar");
	
	private AppPanelPresenter presenter;

	public CheckoutViewNavbar(AppPanelPresenter presenter) {
		
		this.presenter = presenter;
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	protected void onLoad() {
		
		super.onLoad();
		
		$("#logoutButton").click(new Function() {
			@Override
			public boolean f(Event e) {
				
				presenter.logout();
				
				return false;
			}
		});
	}
	
	@Override
	protected void onUnload() {
		super.onUnload();
		
		$("#logoutButton").off();
		
		logger.finest("onUnload");
	}
}
