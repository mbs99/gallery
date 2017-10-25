package de.mbs.gallery.client.activity;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.Callback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.query.client.GQ;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.GalleryResources;
import de.mbs.gallery.client.event.ChangeNavbarEvent;
import de.mbs.gallery.client.event.ChangeNavbarEvent.NAVBAR_TYPE;
import de.mbs.gallery.client.model.Gallery;
import de.mbs.gallery.client.model.GalleryImage;
import de.mbs.gallery.client.model.ViewModel;
import de.mbs.gallery.client.place.GalleryPlace;
import de.mbs.gallery.client.place.ImagePlace;
import de.mbs.gallery.client.view.GalleryView;

public class GalleryActivity extends AbstractActivity {

	GalleryPlace place;
	ClientFactory clientFactory;
	GalleryView view;
	ViewModel model;

	public GalleryActivity(GalleryPlace place, ClientFactory clientFactory) {
		super();

		this.place = place;
		this.clientFactory = clientFactory;
		this.model = clientFactory.getViewModel();
	}

	public GalleryActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		this.model = clientFactory.getViewModel();
	}
	
	public void setPlace(GalleryPlace place) {
		this.place = place;
	}

	@Override
	public void start(AcceptsOneWidget parent, EventBus eventBus) {
		
		clientFactory.eventBus().fireEvent(new ChangeNavbarEvent(NAVBAR_TYPE.GALLERY_VIEW));

		this.view = clientFactory.galleryView(this);
		GalleryResources res = clientFactory.galleryResources();
		
		if(null == model.getGallery(place.getId())) {

			res.getGallery(place.getId(), new Callback<Gallery, String>() {
	
				@Override
				public void onSuccess(Gallery result) {
					
					Gallery gallery = result;
					
					model.setGallery(gallery);
					
					
					if(null != place.getFilter()) {
						if(place.getFilter().equals("1Star")) {
							
							gallery = filterGalleryImagesByVote(result, new Integer(1));
						}
						else if(place.getFilter().equals("2Stars")) {
							gallery = filterGalleryImagesByVote(result, new Integer(2));
						}
						else if(place.getFilter().equals("3Stars")) {
							gallery = filterGalleryImagesByVote(result, new Integer(3));
						}
					}
					
					view.setGallery(gallery);
					parent.setWidget(view.asWidget());
				}
	
				@Override
				public void onFailure(String reason) {
					Window.alert(reason);
	
				}
			});
		}
		else {
			Gallery gallery = model.getGallery(place.getId());
			if(null != place.getFilter()) {
				if(place.getFilter().equals("1Star")) {
					
					gallery = filterGalleryImagesByVote(gallery, new Integer(1));
				}
				else if(place.getFilter().equals("2Stars")) {
					gallery = filterGalleryImagesByVote(gallery, new Integer(2));
				}
				else if(place.getFilter().equals("3Stars")) {
					gallery = filterGalleryImagesByVote(gallery, new Integer(3));
				}
			}
			
			view.setGallery(gallery);
			parent.setWidget(view.asWidget());
		}
	}
	
	@Override
	public void onStop() {
		
		Gallery gallery = model.getGallery(place.getId());
		
		if(null != gallery) {
		
			/*String cachedGalleryJson = JsUtils.jsni("sessionStorage.getItem", place.getId());
			
			Gallery cachedGallery = GQ.create(Gallery.class, cachedGalleryJson);
			cachedGallery.setName(gallery.getName());
			
			List<GalleryImage> syncedImgages = new ArrayList<>();
			syncedImgages.addAll(Arrays.asList(cachedGallery.getImages()));
			
			for(GalleryImage img : gallery.getImages()) {
				boolean addImage = true;
				for(GalleryImage cachedImg : syncedImgages) {
					if(cachedImg.getId().equals(img.getId())) {
						cachedImg.setVote(img.getVote());
						addImage = false;
						
						break;
					}
				}
				
				if(addImage) {
					syncedImgages.add(img);
				}
			}
		
			//update cache
			cachedGallery.setImages(syncedImgages.toArray(cachedGallery.getImages()));
			
			JsUtils.jsni("sessionStorage.setItem",
					cachedGallery.getName(),
					cachedGallery.toJson());
			*/
		}
	}
	
	private Gallery filterGalleryImagesByVote(Gallery gallery, Integer filter) {
		Gallery filteredGallery = GQ.create(Gallery.class);
		
		filteredGallery.setName(gallery.getName());
		List<GalleryImage> filteredImages = new ArrayList<>();
		for(GalleryImage image : gallery.getImages()) {
			if(null != image.getVote()
					&& image.getVote().equals(filter))
			{
				filteredImages.add(image);
			}
		}
		filteredGallery.setImages(filteredImages.toArray(filteredGallery.getImages()));
		
		return filteredGallery;
	}
	
	public void saveGallery() {
		GalleryResources res = clientFactory.galleryResources();
		Gallery gallery = model.getGallery(place.getId());
		res.saveGallery(null, gallery, new Callback<Void, String>() {

			@Override
			public void onFailure(String reason) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Void result) {
				view.onSaveGallery();
				
			}
		});
	}

	public void clickImage(String id) {
		Gallery gallery = model.getGallery(place.getId());
		GalleryImage selectedImage = null;
		for(GalleryImage iter : gallery.getImages()) {
			if(iter.getId().equals(id)) {
				selectedImage = iter;
				break;
			}
		}
		if(null != selectedImage) {
			clientFactory.placeController().goTo(new ImagePlace(gallery.getName(), id));
		}
	}
}
