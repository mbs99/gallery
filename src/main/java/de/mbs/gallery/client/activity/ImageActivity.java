package de.mbs.gallery.client.activity;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.GalleryResources;
import de.mbs.gallery.client.event.ChangeFilterEvent;
import de.mbs.gallery.client.event.ChangeNavbarEvent;
import de.mbs.gallery.client.event.MenuItemEvent;
import de.mbs.gallery.client.event.ENavbarType;
import de.mbs.gallery.client.event.EnableFilterEvent;
import de.mbs.gallery.client.model.Gallery;
import de.mbs.gallery.client.model.GalleryImage;
import de.mbs.gallery.client.place.GalleryPlace;
import de.mbs.gallery.client.place.ImagePlace;
import de.mbs.gallery.client.presenter.StarVoterPresenter;
import de.mbs.gallery.client.view.ImageView;

public class ImageActivity extends AbstractGalleryActivity<ImagePlace, ImageView> implements StarVoterPresenter {

	HandlerRegistration handler;

	public ImageActivity(ImagePlace place, ClientFactory clientFactory) {
		super(place, clientFactory);
	}

	public ImageActivity(ClientFactory clientFactory) {
		super(null, clientFactory);
	}
	
	public void setPlace(ImagePlace place) {
		this.place = place;
	}

	@Override
	public void start(AcceptsOneWidget parent, EventBus eventBus) {
		
		if(isAuthorized()) {
		
			clientFactory.eventBus().fireEvent(new ChangeNavbarEvent(ENavbarType.IMAGE_VIEW));
			
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
			if(null != place.getFilter() && ! place.getFilter().isEmpty()) {
				clientFactory.eventBus().fireEvent(new ChangeFilterEvent(place.getFilter()));
			}
			clientFactory.eventBus().fireEvent(new EnableFilterEvent(false));
			
		}
		else {
			redirectToLogin();
		}
	}
	
	@Override
	public void onStop() {
		handler.removeHandler();
		
		clientFactory.eventBus().fireEvent(new EnableFilterEvent(true));
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
			
			saveGallery();
			
			String filter = place.getFilter();
			if(null != filter && ! filter.isEmpty() && ! "off".equals(filter)) {
				goToNextImage(gallery, currentIndex, convertFilterToVote(filter));
			}
			else {
				goToNextImage(gallery, currentIndex);
			}		
		}
	}
	
	private Integer convertFilterToVote(String filter) {
		if("1Star".equals(filter)) {
			return Integer.valueOf(1);
		} else if("2Stars".equals(filter)) {
			return Integer.valueOf(2);
		}
		else if("3Stars".equals(filter)) {
			return Integer.valueOf(3);
		}
		return Integer.valueOf(0);
	}

	private void goToNextImage(Gallery gallery, int currentIndex) {
		
		GalleryImage image = gallery.getImages()[currentIndex + 1];
		
		String nextImageId = image.getId();
		clientFactory.placeController().goTo(new ImagePlace(gallery.getName(), nextImageId, place.getFilter()));	
	}
	
	private void goToNextImage(Gallery gallery, int currentIndex, int vote) {
		String nextImageId = null;
		do {
			GalleryImage image = gallery.getImages()[++currentIndex];
			if(null != image.getVote() && vote == image.getVote().intValue()) {
				nextImageId = image.getId();
				break;
			}
		}
		while(currentIndex < gallery.getImages().length);
		
		if(null != nextImageId) {
			clientFactory.placeController().goTo(new ImagePlace(gallery.getName(), nextImageId, place.getFilter()));	
		}
	}

	public void previousImage() {
		Gallery gallery = clientFactory.getViewModel().getGallery(place.getGalleryName());
		int currentIndex = clientFactory.getViewModel().getSlideshowPos();
		
		if(currentIndex > 0) {
			
			saveGallery();
			
			String filter = place.getFilter();
			if(null != filter && ! filter.isEmpty() && ! "off".equals(filter)) {
				goToPreviousImage(gallery, currentIndex, convertFilterToVote(filter));
			}
			else {
				goToPreviousImage(gallery, currentIndex);
			}
		}
	}
	
	private void goToPreviousImage(Gallery gallery, int currentIndex) {
		GalleryImage image = gallery.getImages()[currentIndex - 1];
		String previousImageId = image.getId();
		clientFactory.placeController().goTo(new ImagePlace(gallery.getName(), previousImageId, place.getFilter()));
	}
	
	private void goToPreviousImage(Gallery gallery, int currentIndex, Integer vote) {
		String previousImageId = null;
		do {
			GalleryImage image = gallery.getImages()[--currentIndex];
			if(vote == image.getVote()) {
				previousImageId = image.getId();
				break;
			}
		}
		while(currentIndex > 0);
		
		if(null != previousImageId) {
			clientFactory.placeController().goTo(new ImagePlace(gallery.getName(), previousImageId, place.getFilter()));	
		}
	}
	
	public void showGallery() {
		Gallery gallery = clientFactory.getViewModel().getGallery(place.getGalleryName());
		
		clientFactory.placeController().goTo(new GalleryPlace(gallery.getName()));
	}

	@Override
	public void updateVote(GalleryImage img) {
		Gallery gallery = clientFactory.getViewModel().getGallery(place.getGalleryName());
		for(GalleryImage iter : gallery.getImages()) {
			if(iter.getId().equals(img.getId())) {
				iter.setVote(img.getVote());
				
				break;
			}
		}
	}
	
	private void saveGallery() {
		GalleryResources res = clientFactory.galleryResources();
		Gallery gallery = clientFactory.getViewModel().getGallery(place.getGalleryName());
		res.saveGallery(gallery, new Callback<Void, String>() {

			@Override
			public void onFailure(String reason) {
				Window.alert(reason);
				
			}

			@Override
			public void onSuccess(Void result) {
				view.onSaveGallery();
				
			}
		});
	}
}
