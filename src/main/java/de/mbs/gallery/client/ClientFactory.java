package de.mbs.gallery.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.query.client.GQ;
import com.google.gwt.user.client.ui.Composite;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

import de.mbs.gallery.client.activity.AdminActivity;
import de.mbs.gallery.client.activity.CheckoutActivity;
import de.mbs.gallery.client.activity.DownloadActivity;
import de.mbs.gallery.client.activity.GalleryActivity;
import de.mbs.gallery.client.activity.ImageActivity;
import de.mbs.gallery.client.activity.LoginActivity;
import de.mbs.gallery.client.activity.LogoutActivity;
import de.mbs.gallery.client.activity.OrderActivity;
import de.mbs.gallery.client.model.Authorization;
import de.mbs.gallery.client.model.ViewModel;
import de.mbs.gallery.client.presenter.AppPanelPresenter;
import de.mbs.gallery.client.view.AdminView;
import de.mbs.gallery.client.view.AdminViewNavbar;
import de.mbs.gallery.client.view.AppPanel;
import de.mbs.gallery.client.view.CheckoutView;
import de.mbs.gallery.client.view.CheckoutViewNavbar;
import de.mbs.gallery.client.view.DefaultNavbar;
import de.mbs.gallery.client.view.DownloadView;
import de.mbs.gallery.client.view.DownloadViewNavbar;
import de.mbs.gallery.client.view.GalleryView;
import de.mbs.gallery.client.view.GalleryViewNavbar;
import de.mbs.gallery.client.view.ImageView;
import de.mbs.gallery.client.view.ImageViewNavbar;
import de.mbs.gallery.client.view.LoginView;
import de.mbs.gallery.client.view.LogoutView;
import de.mbs.gallery.client.view.OrderView;
import de.mbs.gallery.client.view.OrderViewNavbar;

public class ClientFactory {
	
	private final SimpleEventBus eventBus = new SimpleEventBus();
	private final PlaceController placeController = new PlaceController(eventBus);
	private final Authorization authorization = GQ.create(Authorization.class);
	private final ViewModel viewModel = new ViewModel();
	
	public GalleryResources galleryResources() {
		return new GalleryResources();
	}

	public GalleryView galleryView(GalleryActivity galleryActivity) {
		return new GalleryView(galleryActivity);
	}

	public EventBus eventBus() {
		
		return eventBus;
	}

	public PlaceController placeController() {
		
		return placeController;
	}

	public AppPanel appPanel(AppPanelPresenter presenter) {
		return new AppPanel(presenter);
	}

	public LoginView getLoginView(LoginActivity loginActivity) {
		
		return new LoginView(loginActivity);
	}

	public AdminView getAdminView(AdminActivity adminActivity) {
		return new AdminView(adminActivity);
	}
	
	public Authorization getAuthorization() {
		return authorization;
	}

	public ImageView getImageView(ImageActivity imageActivity) {
		
		return new ImageView(imageActivity);
	}
	
	public ViewModel getViewModel() {
		return viewModel;
	}
	
	public ImageViewNavbar getImageViewNavbar(AppPanelPresenter presenter) {
		return new ImageViewNavbar(presenter);
	}
	
	public GalleryViewNavbar getGalleryViewNavbar(AppPanelPresenter presenter) {
		return new GalleryViewNavbar(presenter);
	}

	public AdminViewNavbar getAdminViewNavbar(AppPanelPresenter presenter) {
		
		return new AdminViewNavbar(presenter);
	}

	public DefaultNavbar getDefaultNavbar(AppPanelPresenter presenter) {
		
		return new DefaultNavbar(presenter);
	}

	public LogoutView getLogoutView(LogoutActivity presenter) {
		return new LogoutView(presenter);
	}

	public OrderView getOrderView(OrderActivity presenter) {
		
		return new OrderView(presenter);
	}

	public Composite getOrderViewNavbar(AppPanelPresenter presenter) {
		
		return new OrderViewNavbar(presenter);
	}

	public CheckoutView getCheckoutView(CheckoutActivity presenter) {
		
		return new CheckoutView(presenter);
	}

	public Composite getCheckoutViewNavbar(AppPanelPresenter presenter) {
		
		return new CheckoutViewNavbar(presenter);
	}

	public DownloadView downloadView(DownloadActivity presenter) {
		return new DownloadView(presenter);
	}

	public DownloadViewNavbar getDownloadViewNavbar(AppPanelPresenter presenter) {
		return new DownloadViewNavbar(presenter);
	}

	public Composite getReadOnlyOrderViewNavbar(AppPanelPresenter presenter) {
		return new OrderViewNavbar(presenter, Boolean.TRUE);
	}
}
