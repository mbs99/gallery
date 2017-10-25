package de.mbs.gallery.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.GalleryResources;
import de.mbs.gallery.client.event.ChangeNavbarEvent;
import de.mbs.gallery.client.event.MenuItemEvent;
import de.mbs.gallery.client.event.ChangeNavbarEvent.NAVBAR_TYPE;
import de.mbs.gallery.client.model.Gallery;
import de.mbs.gallery.client.model.GalleryImage;
import de.mbs.gallery.client.place.GalleryPlace;
import de.mbs.gallery.client.place.ImagePlace;
import de.mbs.gallery.client.view.ImageView;

public class ImageActivity extends AbstractActivity {

	ImagePlace place;
	ClientFactory clientFactory;
	ImageView view;
	HandlerRegistration handler;

	public ImageActivity(ImagePlace place, ClientFactory clientFactory) {
		super();

		this.place = place;
		this.clientFactory = clientFactory;
		
	}

	public ImageActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}
	
	public void setPlace(ImagePlace place) {
		this.place = place;
	}

	@Override
	public void start(AcceptsOneWidget parent, EventBus eventBus) {
		
		clientFactory.eventBus().fireEvent(new ChangeNavbarEvent(NAVBAR_TYPE.IMAGE_VIEW));
		
		handler = clientFactory.eventBus().addHandler(MenuItemEvent.TYPE, new MenuItemEvent.MenuItemEventEventHandler() {
			
			@Override
			public void menuItem(MenuItemEvent event) {
				switch (event.getItem()) {
				case SHOW_GALLERY:
					showGallery();
					break;
				case SHOW_NEXT_IMAGE:
					nextImage();
					break;
				case SHOW_PREVIOUS_IMAGE:
					previousImage();
					break;

				default:
					break;
				}
			}
		});
		
		this.view = clientFactory.getImageView(this);
		
		GalleryResources res = clientFactory.galleryResources();
		
		Gallery gallery = clientFactory.getViewModel().getGallery(place.getGalleryName());
		if(null != gallery) {
			int pos = 0;
			for(GalleryImage iter : gallery.getImages()) {
				if(iter.getId().equals(place.getImageId())) {
					String url = getImageUrl(gallery, iter);
					view.setImage(iter, url, pos);
					clientFactory.getViewModel().setSlideshowPos(pos);
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
					
					Gallery gallery = result;
					clientFactory.getViewModel().setGallery(gallery);
					int pos =0;
					for(GalleryImage iter : result.getImages()) {
						if(iter.getId().equals(place.getImageId())) {
							String url = getImageUrl(result, iter);
							view.setImage(iter, url, pos);
							clientFactory.getViewModel().setSlideshowPos(pos);
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
		handler.removeHandler();
	}
	
	private String getImageUrl(Gallery gallery, GalleryImage image) {
		return GWT.getHostPageBaseURL()
				+ "api/gallery/"
				+ gallery.getName()
				+ "/" + image.getId();
	}
	
	public void nextImage() {
		Gallery gallery = clientFactory.getViewModel().getGallery(place.getGalleryName());
		int currentIndex = clientFactory.getViewModel().getSlideshowPos();
		if(currentIndex < gallery.getImages().length -1) {
			GalleryImage image = gallery.getImages()[currentIndex + 1];
			String nextImageId = image.getId();
			clientFactory.placeController().goTo(new ImagePlace(gallery.getName(), nextImageId));
		}
	}
	
	public void previousImage() {
		Gallery gallery = clientFactory.getViewModel().getGallery(place.getGalleryName());
		int currentIndex = clientFactory.getViewModel().getSlideshowPos();
		
		if(currentIndex > 0) {
			
			GalleryImage image = gallery.getImages()[currentIndex - 1];
			String previousImageId = image.getId();
			clientFactory.placeController().goTo(new ImagePlace(gallery.getName(), previousImageId));
		}
	}
	
	public void showGallery() {
		Gallery gallery = clientFactory.getViewModel().getGallery(place.getGalleryName());
		
		clientFactory.placeController().goTo(new GalleryPlace(gallery.getName()));
	}
}
