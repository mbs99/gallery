package de.mbs.gallery.client.activity;

import com.google.gwt.core.client.Callback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.query.client.GQ;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.event.ChangeNavbarEvent;
import de.mbs.gallery.client.event.ENavbarType;
import de.mbs.gallery.client.model.UserAccount;
import de.mbs.gallery.client.place.AdminPlace;
import de.mbs.gallery.client.view.AdminView;

public class AdminActivity extends AbstractGalleryActivity<AdminPlace, AdminView> {

	public AdminActivity(AdminPlace place, ClientFactory clientFactory) {
		super(place, clientFactory);
	}

	@Override
	public void start(AcceptsOneWidget parent, EventBus eventBus) {
		
		if(isAuthorized()) {
		
			clientFactory.eventBus().fireEvent(new ChangeNavbarEvent(ENavbarType.ADMIN_VIEW));
	
			this.view = clientFactory.getAdminView(this);
	
			parent.setWidget(view.asWidget());
		}
		else {
			redirectToLogin();
		}

	}
	
	@Override
	public void onStop() {
	}

	public void getGalleriesAndUsers() {
		
		galleryResources.getGalleries(new Callback<String[], String>() {
			
			@Override
			public void onSuccess(String[] result) {
				view.onGetGalleries(result);
			}
			
			@Override
			public void onFailure(String reason) {
				// TODO Auto-generated method stub
				
			}
		});
		
		galleryResources.getUsers(new Callback<String[], String>() {
			
			@Override
			public void onSuccess(String[] users) {
				view.onGetUsers(users);
			}
			
			@Override
			public void onFailure(String reason) {
				view.onFailure(reason);
			}
		});
		
	}

	public void addUserToGallery(String user, String gallery) {
		
		if(isValidSelection(user) && isValidSelection(gallery)) {
			galleryResources.addUserToGallery(user, gallery, new Callback<Void, String>() {
	
				@Override
				public void onFailure(String reason) {
					view.onAddUserToGalleryFailure(reason);
					
				}
	
				@Override
				public void onSuccess(Void result) {
					view.onAddUserToGallery();
					
				}
			});
		}
		else {
			view.onAddUserToGalleryFailure("Benutzername und Galerie sind erforderlich.");
		}
		
	}

	public void createUser(String username, String password, String password2) {
		
		if(! username.isEmpty() && ! password.isEmpty() && ! password2.isEmpty()) {
			
			if(password.equals(password2)) {
			
				UserAccount user = GQ.create(UserAccount.class);
				user.setUsername(username);
				user.setPassword(password);
				
				galleryResources.createUser(user, new Callback<Void, String>() {
	
					@Override
					public void onFailure(String reason) {
						view.onCreateUserFailure(reason);
						
					}
	
					@Override
					public void onSuccess(Void result) {
						view.onCreateUser();
						
					}
				});
			}
			else {
				view.onCreateUserFailure("Die Passwörter stimmen nicht überein!");
			}	
		}
		else {
			view.onCreateUserFailure("Benutzername und Passwort sind erforderlich.");
		}
	}

	public void removeUserFromGallery(String user, String gallery) {
		
		if(isValidSelection(user) && isValidSelection(gallery)) {
		
			galleryResources.removeUserFromGallery(user, gallery, new Callback<Void, String>() {
	
				@Override
				public void onFailure(String reason) {
					view.onRemoveUserFromGalleryFailure(reason);
				}
	
				@Override
				public void onSuccess(Void result) {
					view.onRemoveUserFromGallery();
					
				}
			});
		}
		else {
			view.onRemoveUserFromGalleryFailure("Benutzername und Galerie sind erforderlich.");
		}
		
	}
	
	private boolean isValidSelection(String value) {
		return ! value.equals("-") && ! value.isEmpty();
	}

	public void deleteUser(String user) {
		if(isValidSelection(user)) {
			
			galleryResources.deleteUser(user, new Callback<Void, String>() {
	
				@Override
				public void onFailure(String reason) {
					view.onDeleteUserFailure(reason);
				}
	
				@Override
				public void onSuccess(Void result) {
					view.onDeleteUser(user);
					
				}
			});
		}
		else {
			view.onDeleteUserFailure("Der Benutzername ist erforderlich.");
		}
	}
}
