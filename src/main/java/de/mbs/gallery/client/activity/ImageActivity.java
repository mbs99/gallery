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
import de.mbs.gallery.client.place.GalleryPlace;
import de.mbs.gallery.client.place.ImagePlace;
import de.mbs.gallery.client.view.ImageView;
import net.sourceforge.htmlunit.corejs.javascript.ast.Yield;

public class ImageActivity extends AbstractActivity {

	ImagePlace place;
	ClientFactory clientFactory;
	ImageView view;
	Gallery gallery;

	public ImageActivity(ImagePlace place, ClientFactory clientFactory) {
		super();

		this.place = place;
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget parent, EventBus eventBus) {

		this.view = clientFactory.getImageView(this);
		
		GalleryResources res = clientFactory.galleryResources();
		
		String cachedGallery = JsUtils.jsni("sessionStorage.getItem", place.getGalleryName());
		if(null != cachedGallery && ! cachedGallery.isEmpty()) {
			gallery = GQ.create(Gallery.class, cachedGallery);
			
			clientFactory.getViewModel().setGallery(gallery);
			
			int pos = 0;
			for(GalleryImage iter : gallery.getImages()) {
				if(iter.getId().equals(place.getImageId())) {
					String url = getImageUrl(gallery, iter);
					view.setImage(iter, url, pos);
					break;
				}
				pos++;
			}
			
			parent.setWidget(view.asWidget());
		}
		else {

			res.getGallery(place.getGalleryName(), new Callback<Gallery, String>() {
	
				@Override
				public void onSuccess(Gallery result) {
					
					gallery = result;
					
					clientFactory.getViewModel().setGallery(gallery);
					int pos =0;
					for(GalleryImage iter : result.getImages()) {
						if(iter.getId().equals(place.getImageId())) {
							String url = getImageUrl(result, iter);
							view.setImage(iter, url, pos);
						}
						pos++;
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
	
	public void nextImage(int currentIndex) {
		if(currentIndex < gallery.getImages().length -1) {
			String nextImageId = gallery.getImages()[currentIndex + 1].getId();
			clientFactory.placeController().goTo(new ImagePlace(gallery.getName(), nextImageId));
		}
	}
	
	public void previousImage(int currentIndex) {
		if(currentIndex > 0) {
			String previousImageId = gallery.getImages()[currentIndex - 1].getId();
			clientFactory.placeController().goTo(new ImagePlace(gallery.getName(), previousImageId));
		}
	}
	
	public void showGallery() {
		clientFactory.placeController().goTo(new GalleryPlace(gallery.getName()));
	}
}
