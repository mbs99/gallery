package de.mbs.gallery.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.query.client.GQ;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.GalleryResources;
import de.mbs.gallery.client.model.Gallery;
import de.mbs.gallery.client.model.GalleryImage;
import de.mbs.gallery.client.place.ImagePlace;
import de.mbs.gallery.client.view.ImageView;

public class ImageActivity extends AbstractActivity {

	ImagePlace place;
	ClientFactory clientFactory;
	ImageView view;

	public ImageActivity(ImagePlace place, ClientFactory clientFactory) {
		super();

		this.place = place;
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget parent, EventBus eventBus) {

		this.view = clientFactory.getImageView(this);
		
		GalleryResources res = clientFactory.galleryResources();
		
		Gallery gallery = GQ.create(Gallery.class);
		String cachedGallery = JsUtils.jsni("sessionStorage.getItem", place.getGalleryName());
		if(null != cachedGallery && ! cachedGallery.isEmpty()) {
			gallery = gallery.parse(cachedGallery);
			
			for(GalleryImage iter : gallery.getImages()) {
				if(iter.getId().equals(place.getImageId())) {
					String url = getImageUrl(gallery, iter);
					view.setImage(iter, url);
					break;
				}
			}
			
			parent.setWidget(view.asWidget());
		}
		else {

			res.getGallery(place.getGalleryName(), new Callback<Gallery, String>() {
	
				@Override
				public void onSuccess(Gallery result) {
					
					for(GalleryImage iter : result.getImages()) {
						if(iter.getId().equals(place.getImageId())) {
							String url = getImageUrl(result, iter);
							view.setImage(iter, url);
						}
					}
					parent.setWidget(view.asWidget());
				}
	
				@Override
				public void onFailure(String reason) {
					Window.alert(reason);
	
				}
			});
		}
	}
	
	@Override
	public void onStop() {
	}
	
	private String getImageUrl(Gallery gallery, GalleryImage image) {
		return GWT.getHostPageBaseURL()
				+ "api/gallery/"
				+ gallery.getName()
				+ "/" + image.getId();
	}
}
