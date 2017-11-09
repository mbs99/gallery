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


public class OrderViewNavbar extends Composite {
	
	private static GalleryViewNavbarUiBinder uiBinder = GWT
			.create(GalleryViewNavbarUiBinder.class);

	interface GalleryViewNavbarUiBinder extends UiBinder<Widget, OrderViewNavbar> {
	}
	
	private static final Logger logger = Logger.getLogger("GalleryViewNavbar");
	
	private AppPanelPresenter presenter;

	public OrderViewNavbar(AppPanelPresenter presenter) {
		
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
		
		$("#orderButton").click(new Function() {
			@Override
			public boolean f(Event e) {
				
				presenter.submitOrder();
				
				return false;
			}
		});
	}
	
	@Override
	protected void onUnload() {
		super.onUnload();
		
		$("#filter").off();
		$("#logoutButton").off();
		$("#orderButton").off();
		
		logger.finest("onUnload");
	}
}
