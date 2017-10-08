package de.mbs.gallery.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQ;
import com.google.gwt.query.client.plugins.ajax.Ajax;

import de.mbs.gallery.client.model.Gallery;

public class GalleryResources {

	public void getGallery(String id, Callback<Gallery, String> callback) {
 		Ajax.get(GWT.getHostPageBaseURL() + "api/gallery/" + id)
 		.done(new Function() {
 			@Override
 			public Object f(Object... args) {
 				
 				
 				Gallery gallery = GQ.create(Gallery.class);
 				
 				gallery = gallery.parse((String)args[0]);
 				
 				callback.onSuccess(gallery);
 				
 				return null;
 			}
 		})
 		.fail(new Function() {
 			@Override
 			public void f() {
 				callback.onFailure("bla");
 			}
 		});
	}

}
