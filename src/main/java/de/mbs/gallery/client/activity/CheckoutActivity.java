package de.mbs.gallery.client.activity;

import com.google.gwt.core.client.Callback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.query.client.GQ;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.event.ChangeNavbarEvent;
import de.mbs.gallery.client.event.ENavbarType;
import de.mbs.gallery.client.model.EOrderState;
import de.mbs.gallery.client.model.Gallery;
import de.mbs.gallery.client.model.Order;
import de.mbs.gallery.client.place.CheckoutPlace;
import de.mbs.gallery.client.view.CheckoutView;

public class CheckoutActivity extends AbstractGalleryActivity<CheckoutPlace, CheckoutView> {

	public CheckoutActivity(CheckoutPlace place, ClientFactory factory) {
		super(place, factory);
	}

	@Override
	public void start(AcceptsOneWidget parent, EventBus eventBus) {
		if (isAuthorized()) {

			clientFactory.eventBus().fireEvent(new ChangeNavbarEvent(ENavbarType.CHECKOUT_VIEW));

			this.view = clientFactory.getCheckoutView(this);
			
			asyncLoadGallery(place.getRole(), new Callback<Gallery, String>() {
				
				@Override
				public void onSuccess(Gallery result) {

					parent.setWidget(view.asWidget());
					
				}
				
				@Override
				public void onFailure(String reason) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		else {
			redirectToLogin();
		}
		
	}

	public Order getOrder() {
		Gallery gallery = getGallery(place.getRole());

		Order order = GQ.create(Order.class);
		order.setGalleryName(gallery.getName());
		if(null == gallery.getOrderState()) {
			gallery.setOrderState(EOrderState.OPEN.toString());
		}
		order.setOrderState(gallery.getOrderState());

		return order;
	}
	
	private Gallery getGallery(String galleryName) {
		Gallery gallery = model.getGallery(place.getRole());
		return gallery;
	}

	public void getDownloadUrl() {
		
		galleryResources.isDownloadReady(place.getRole(), new Callback<String, String>() {

			@Override
			public void onFailure(String reason) {
				
			}

			@Override
			public void onSuccess(String url) {
				view.onGetDownloadUrl(url);
				
			}
		});
		
	}

}
