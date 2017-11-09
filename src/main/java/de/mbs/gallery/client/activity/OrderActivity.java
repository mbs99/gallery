package de.mbs.gallery.client.activity;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.query.client.GQ;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.event.ChangeNavbarEvent;
import de.mbs.gallery.client.event.ENavbarType;
import de.mbs.gallery.client.model.Gallery;
import de.mbs.gallery.client.model.GalleryImage;
import de.mbs.gallery.client.model.Order;
import de.mbs.gallery.client.place.OrderPlace;
import de.mbs.gallery.client.view.OrderView;

public class OrderActivity extends AbstractGalleryActivity<OrderPlace, OrderView> {

	public OrderActivity(OrderPlace place, ClientFactory clientFactory) {
		super(place,clientFactory);
	}

	@Override
	public void start(AcceptsOneWidget parent, EventBus eventBus) {
		
		clientFactory.eventBus().fireEvent(new ChangeNavbarEvent(ENavbarType.DEFAULT));

		this.view = clientFactory.getOrderView(this);

		parent.setWidget(view.asWidget());

	}
	
	@Override
	public void onStop() {
	}

	public Order getOrder() {
		Gallery gallery = model.getGallery(place.getRole());
		List<GalleryImage> images = new ArrayList<>();
		for(GalleryImage img : gallery.getImages()) {
			if(null != img.getVote()
				&& 3 == img.getVote()) {
				images.add(img);
			}
		}
		
		Order order = GQ.create(Order.class);
		order.setImages(images);
		order.setGalleryName(gallery.getName());
		
		return order;
	}
}
