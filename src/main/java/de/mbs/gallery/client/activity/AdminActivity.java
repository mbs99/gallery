package de.mbs.gallery.client.activity;

import java.util.Arrays;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.query.client.GQ;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.event.ChangeNavbarEvent;
import de.mbs.gallery.client.event.ENavbarType;
import de.mbs.gallery.client.model.Email;
import de.mbs.gallery.client.model.UserAccount;
import de.mbs.gallery.client.place.AdminPlace;
import de.mbs.gallery.client.view.AdminView;

public class AdminActivity extends AbstractGalleryActivity<AdminPlace, AdminView> {

	public AdminActivity(AdminPlace place, ClientFactory clientFactory) {
		super(place, clientFactory);
	}

	@Override
	public void start(AcceptsOneWidget parent, EventBus eventBus) {

		if (isAuthorized() && isAdmin()) {

			clientFactory.eventBus().fireEvent(new ChangeNavbarEvent(ENavbarType.ADMIN_VIEW));

			this.view = clientFactory.getAdminView(this);

			parent.setWidget(view.asWidget());
		} else {
			redirectToLogin();
		}

	}

	@Override
	public void onStop() {
	}

	public void getGalleriesAndUsers() {

		getGalleries();
		getUsers();
	}
	
	protected void getGalleries() {

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
	}
	
	protected void getUsers() {

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

		if (isValidSelection(user) && isValidSelection(gallery)) {
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
		} else {
			view.onAddUserToGalleryFailure("Benutzername und Galerie sind erforderlich.");
		}

	}

	public void createUser(String username, String password, String password2) {

		if (!username.isEmpty() && !password.isEmpty() && !password2.isEmpty()) {

			if (password.equals(password2)) {

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
			} else {
				view.onCreateUserFailure("Die Passwörter stimmen nicht überein!");
			}
		} else {
			view.onCreateUserFailure("Benutzername und Passwort sind erforderlich.");
		}
	}

	public void removeUserFromGallery(String user, String gallery) {

		if (isValidSelection(user) && isValidSelection(gallery)) {

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
		} else {
			view.onRemoveUserFromGalleryFailure("Benutzername und Galerie sind erforderlich.");
		}

	}

	private boolean isValidSelection(String value) {
		return !value.equals("-") && !value.isEmpty();
	}

	public void deleteUser(String user) {
		if (isValidSelection(user)) {

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
		} else {
			view.onDeleteUserFailure("Der Benutzername ist erforderlich.");
		}
	}

	public void changeLogo(JsArray<JavaScriptObject> files) {
		galleryResources.changeLogo(files, new Callback<Void, String>() {

			@Override
			public void onFailure(String reason) {
				view.onChangLogoFailure(reason);
			}

			@Override
			public void onSuccess(Void result) {
				view.onChangeLogo();

			}
		});

	}

	public void createEmail(String email) {
		if (isValidEmail(email)) {

			galleryResources.createEmail(GQ.create(Email.class).setEmail(email), new Callback<Void, String>() {

				@Override
				public void onFailure(String reason) {
					view.onCreateEmailFailure(reason);
				}

				@Override
				public void onSuccess(Void result) {
					view.onCreateEmail(email);

				}
			});
		} else {
			view.onCreateEmailFailure("Bitte geben sie eine Mailadresse an.");
		}

	}

	private boolean isValidEmail(String email) {
		return email != null && !email.isEmpty() && -1 != email.indexOf('@') && 0 != email.indexOf('@')
				&& email.length() - 1 != email.indexOf('@') && email.indexOf('@') == email.lastIndexOf('@');

	}

	public void deleteGallery(String name) {
		if (isValidSelection(name)) {

			galleryResources.deleteGallery(name, new Callback<Void, String>() {

				@Override
				public void onFailure(String reason) {
					view.onDeleteGalleryFailure(reason);
				}

				@Override
				public void onSuccess(Void result) {
					view.onDeleteGallery(name);

				}
			});
		} else {
			view.onDeleteGalleryFailure("Keine Galerie ausgewählt.");
		}
	}

	public void createGallery(String name, JsArray<JavaScriptObject> files) {
		galleryResources.createGallery(name, files, new Callback<Void, String>() {

			@Override
			public void onFailure(String reason) {
				view.onCreateGalleryFailure(reason);
			}

			@Override
			public void onSuccess(Void result) {
				
				getGalleries();
				
				view.onCreateGallery(name);

			}
		});
		
	}
	
	private boolean isAdmin() {
		return -1 != Arrays.binarySearch(clientFactory.getAuthorization().getRoles(), "admin");
	}
}
