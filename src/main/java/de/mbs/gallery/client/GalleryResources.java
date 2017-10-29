package de.mbs.gallery.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQ;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.query.client.plugins.ajax.Ajax.Settings;
import com.google.gwt.user.client.Window;

import de.mbs.gallery.client.model.Authorization;
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

	public void login(Authorization authorization, Callback<Authorization, String> callback) {
		Ajax.post(GWT.getHostPageBaseURL() + "api/login", authorization)
 		.done(new Function() {
 			@Override
 			public Object f(Object... args) {
 				
 				Authorization auth = GQ.create(Authorization.class);
 				
 				auth = auth.parse((String)args[0]);
 				
 				callback.onSuccess(auth);
 				
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
	
	public void saveGallery(Gallery gallery, Callback<Void, String> callback) {
		
		Settings settings = Ajax.createSettings();
		settings.setUrl(GWT.getHostPageBaseURL() + "api/gallery/" + gallery.getName());
		settings.setType("post");
		
		Ajax.ajax(settings)
		.done(new Function() {
 			@Override
 			public Object f(Object... args) {
 				
 				callback.onSuccess(null);
 				
 				return null;
 			}
 		})
 		.fail(new Function() {
 			@Override
 			public void f() {
 				callback.onFailure("");
 			}
 		});
	}
	
	public void logout(Callback<Void, String> callback) {
		Ajax.post(GWT.getHostPageBaseURL() + "api/logout", null)
 		.done(new Function() {
 			@Override
 			public Object f(Object... args) {
 				
 				callback.onSuccess(null);
 				
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
