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


public class DownloadViewNavbar extends Composite {
	
	private static DownloadViewNavbarUiBinder uiBinder = GWT
			.create(DownloadViewNavbarUiBinder.class);

	interface DownloadViewNavbarUiBinder extends UiBinder<Widget, DownloadViewNavbar> {
	}
	
	private static final Logger logger = Logger.getLogger(DownloadViewNavbar.class.getSimpleName());
	
	private AppPanelPresenter presenter;

	public DownloadViewNavbar(AppPanelPresenter presenter) {
		
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
		
		$("#zipDownloadButton").click(new Function() {
			@Override
			public boolean f(Event e) {
				
				presenter.zipDownload();
				
				return false;
			}
		});
	}
	
	@Override
	protected void onUnload() {
		super.onUnload();
		
		$("#logoutButton").off();
		$("#zipDownloadButton").off();
		
		logger.finest("onUnload");
	}
}
