package de.mbs.gallery.client.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.Callback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.query.client.GQ;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.event.ChangeNavbarEvent;
import de.mbs.gallery.client.event.EMenuItem;
import de.mbs.gallery.client.event.ENavbarType;
import de.mbs.gallery.client.event.MenuItemEvent;
import de.mbs.gallery.client.model.EOrderState;
import de.mbs.gallery.client.model.Gallery;
import de.mbs.gallery.client.model.GalleryImage;
import de.mbs.gallery.client.model.Order;
import de.mbs.gallery.client.place.CheckoutPlace;
import de.mbs.gallery.client.place.GalleryPlace;
import de.mbs.gallery.client.place.OrderPlace;
import de.mbs.gallery.client.view.OrderView;

public class OrderActivity extends AbstractGalleryActivity<OrderPlace, OrderView> {

	private static final Logger logger = Logger.getLogger(OrderActivity.class.getName());

	private List<HandlerRegistration> handlerRegistration = new ArrayList<>();

	public OrderActivity(OrderPlace place, ClientFactory clientFactory) {
		super(place, clientFactory);
	}

	@Override
	public void start(AcceptsOneWidget parent, EventBus eventBus) {

		if (isAuthorized()) {

			handlerRegistration.add(clientFactory.eventBus().addHandler(MenuItemEvent.TYPE,
					new MenuItemEvent.MenuItemEventEventHandler() {

						@Override
						public void menuItem(MenuItemEvent event) {
							if (event.getItem() == EMenuItem.SUBMIT_ORDER) {
								submitOrder();
							}
							else if (event.getItem() == EMenuItem.SHOW_GALLERY) {
								showGallery();
							}
							else {
								logger.severe("unbekannter Event: " + event.toDebugString());
							}
						}
					}));
			clientFactory.eventBus().fireEvent(new ChangeNavbarEvent(ENavbarType.ORDER_VIEW));

			this.view = clientFactory.getOrderView(this);
			
			asyncLoadGallery(place.getRole(), new Callback<Gallery, String>() {
				
				@Override
				public void onSuccess(Gallery result) {

					if(! EOrderState.OPEN.equals(EOrderState.safeParseValue(result.getOrderState()))) {
						clientFactory.eventBus().fireEvent(new ChangeNavbarEvent(ENavbarType.READONLY_ORDER_VIEW));
					}
					
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

	@Override
	public void onStop() {
		for(HandlerRegistration iter : handlerRegistration) {
			iter.removeHandler();
		}
	}

	public Order getOrder() {
		Gallery gallery = getGallery(place.getRole());
		List<GalleryImage> images = new ArrayList<>();
		for (GalleryImage img : gallery.getImages()) {
			if (null != img.getVote() && 3 == img.getVote()) {
				images.add(img);
			}
		}

		Order order = GQ.create(Order.class);
		order.setImages(images);
		order.setGalleryName(gallery.getName());
		if(null == gallery.getOrderState()) {
			gallery.setOrderState(EOrderState.OPEN.toString());
		}
		order.setOrderState(gallery.getOrderState());

		return order;
	}

	protected void submitOrder() {
		Gallery gallery = model.getGallery(place.getRole());
		EOrderState orderState = EOrderState.safeParseValue(gallery.getOrderState());
		if(null == orderState
				|| EOrderState.OPEN.equals(orderState)) {
		
			view.bindToModel();
			
			gallery.setOrderState(EOrderState.SUBMIT.toString());
			
			saveGallery(place.getRole(), new Callback<Void, String>() {
	
				@Override
				public void onFailure(String reason) {
					view.onError(reason);
					
				}
	
				@Override
				public void onSuccess(Void result) {
					clientFactory.galleryResources().submitOrder(model.getGallery(place.getRole()), new Callback<Void, String>() {
	
						@Override
						public void onFailure(String reason) {
							logger.log(Level.SEVERE, reason);
	
							Gallery gallery = model.getGallery(place.getRole());
							gallery.setOrderState(EOrderState.OPEN.toString());
						}
	
						@Override
						public void onSuccess(Void result) {
							
							Gallery gallery = model.getGallery(place.getRole());
							gallery.setOrderState(EOrderState.WIP.toString());
							
							clientFactory.galleryResources().saveGallery(gallery, new Callback<Void, String>() {
	
								@Override
								public void onFailure(String reason) {
									logger.log(Level.SEVERE, reason);
									
									view.onError(reason);
								}
	
								@Override
								public void onSuccess(Void result) {
									view.onSubmitOrder();
									
									clientFactory.placeController().goTo(new CheckoutPlace(place.getRole()));
									
								}
							});
						}
					});		
				}
			});
		}
		else {
			view.onSubmitOrder();
			
			clientFactory.placeController().goTo(new CheckoutPlace(place.getRole()));
		}
	}

	public void removeImage(String id) {
		Gallery gallery = getGallery(place.getRole());
		for(GalleryImage iter : gallery.getImages()) {
			if(iter.getId().equals(id)) {
				iter.setVote(0);	
			}
		}
		
		model.setGallery(gallery);
		
		view.onRemoveImage(id);
	}
	
	private Gallery getGallery(String galleryName) {
		Gallery gallery = model.getGallery(place.getRole());
		return gallery;
	}
	
	private void showGallery() {
		Gallery gallery = clientFactory.getViewModel().getGallery(place.getRole());
		
		clientFactory.placeController().goTo(new GalleryPlace(gallery.getName()));
	}

	public void updateOrder(Order order) {
		Gallery gallery = getGallery(place.getRole());
		for(GalleryImage iter : gallery.getImages()) {
			for(GalleryImage orderIter : order.getImages()) {
				if(iter.getId().equals(orderIter.getId())) {
					iter.setComments(orderIter.getComments());
				}
			}
		}
		
		clientFactory.getViewModel().setGallery(gallery);
	}
}
