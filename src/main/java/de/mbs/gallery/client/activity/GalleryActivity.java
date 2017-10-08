package de.mbs.gallery.client.activity;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.Callback;
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
import de.mbs.gallery.client.view.GalleryView;

public class GalleryActivity extends AbstractActivity {

	GalleryPlace place;
	ClientFactory clientFactory;
	GalleryView view;
	Gallery gallery;

	public GalleryActivity(GalleryPlace place, ClientFactory clientFactory) {
		super();

		this.place = place;
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget parent, EventBus eventBus) {

		this.view = clientFactory.galleryView(this);
		GalleryResources res = clientFactory.galleryResources();
		
		if(null == gallery) {
			gallery = GQ.create(Gallery.class);
		}
		
		String cachedGallery = JsUtils.jsni("sessionStorage.getItem", place.getId());
		if(null != cachedGallery && ! cachedGallery.isEmpty()) {
			gallery = gallery.parse(cachedGallery);
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
		else {

			res.getGallery(place.getId(), new Callback<Gallery, String>() {
	
				@Override
				public void onSuccess(Gallery result) {
					
					gallery = result;
					
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
	}
	
	@Override
	public void onStop() {
		
		if(null != gallery) {
			JsUtils.jsni("sessionStorage.setItem",
					gallery.getName(),
					gallery.toJson());
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
}
