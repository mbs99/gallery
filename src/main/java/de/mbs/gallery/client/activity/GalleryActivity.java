package de.mbs.gallery.client.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.Callback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.query.client.GQ;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.GalleryResources;
import de.mbs.gallery.client.event.ChangeFilterEvent;
import de.mbs.gallery.client.event.ChangeNavbarEvent;
import de.mbs.gallery.client.event.EMenuItem;
import de.mbs.gallery.client.event.ENavbarType;
import de.mbs.gallery.client.event.MenuItemEvent;
import de.mbs.gallery.client.model.Gallery;
import de.mbs.gallery.client.model.GalleryImage;
import de.mbs.gallery.client.model.ViewModel;
import de.mbs.gallery.client.place.GalleryPlace;
import de.mbs.gallery.client.place.ImagePlace;
import de.mbs.gallery.client.presenter.StarVoterPresenter;
import de.mbs.gallery.client.view.GalleryView;

public class GalleryActivity extends AbstractGalleryActivity<GalleryPlace> implements StarVoterPresenter {

	GalleryView view;
	ViewModel model;
	
	private HandlerRegistration handlerRegistration; 
	
	private static final Logger logger = Logger.getLogger("GalleryActivity");
	

	public GalleryActivity(GalleryPlace place, ClientFactory clientFactory) {
		super(place, clientFactory);
		this.model = clientFactory.getViewModel();
	}

	public GalleryActivity(ClientFactory clientFactory) {
		
		super(null, clientFactory);
		
		this.model = clientFactory.getViewModel();
	}
	
	public void setPlace(GalleryPlace place) {
		this.place = place;
	}

	@Override
	public void start(AcceptsOneWidget parent, EventBus eventBus) {
		
		if(isAuthorized()) {
			
			handlerRegistration = clientFactory.eventBus().addHandler(MenuItemEvent.TYPE, new MenuItemEvent.MenuItemEventEventHandler() {
				
				@Override
				public void menuItem(MenuItemEvent event) {
					if(event.getItem() == EMenuItem.SUBMIT_ORDER) {
						submitOrder();
					}
					
				}
			});
		
			clientFactory.eventBus().fireEvent(new ChangeNavbarEvent(ENavbarType.GALLERY_VIEW));
			clientFactory.eventBus().fireEvent(new ChangeFilterEvent(place.getFilter()));
	
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
		else {
			redirectToLogin();
		}
	}
	
	protected void submitOrder() {
		saveGallery(new Callback<Void, String>() {

			@Override
			public void onFailure(String reason) {
				logger.log(Level.SEVERE, reason);
				
			}

			@Override
			public void onSuccess(Void result) {
				
				clientFactory.galleryResources().submitOrder(new Callback<Void, String>() {

					@Override
					public void onFailure(String reason) {
						logger.log(Level.SEVERE, reason);
						
					}

					@Override
					public void onSuccess(Void result) {
						view.onSubmitOrder();
						
					}
				});
			}
		});
		
	}

	@Override
	public void onStop() {
		
		if(null != handlerRegistration) {
			handlerRegistration.removeHandler();
		}
		
		Gallery gallery = model.getGallery(place.getId());
		
		if(null != gallery) {
			
			saveGallery();
		
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
		saveGallery(new Callback<Void, String>() {

			@Override
			public void onFailure(String reason) {
				logger.warning(reason);
				
			}

			@Override
			public void onSuccess(Void result) {
				view.onSaveGallery();
				
			}
		});
	}
	
	protected void saveGallery(Callback<Void, String> callback) {
		GalleryResources res = clientFactory.galleryResources();
		Gallery gallery = model.getGallery(place.getId());
		res.saveGallery(gallery, callback);
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

	@Override
	public void updateVote(GalleryImage img) {
		
		logger.log(Level.FINEST, "enter updateVote");
		
		Gallery gallery = model.getGallery(place.getId());
		for(GalleryImage iter : gallery.getImages()) {
			if(iter.getId().equals(img.getId())) {
				iter.setVote(img.getVote());
				
				saveImage(iter);
				
				break;
			}
		}
		
		logger.log(Level.FINEST, "leave updateVote");
	}
	
	protected void saveImage(GalleryImage img) {
		
	}
}
