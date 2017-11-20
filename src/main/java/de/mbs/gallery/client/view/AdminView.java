package de.mbs.gallery.client.view;

import static com.google.gwt.query.client.GQuery.$;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import de.mbs.gallery.client.activity.AdminActivity;

public class AdminView extends Composite {

	@UiField
	HTMLPanel adminViewPanel;

	AdminActivity presenter;

	private static AdminViewUiBinder uiBinder = GWT.create(AdminViewUiBinder.class);

	interface AdminViewUiBinder extends UiBinder<Widget, AdminView> {
	}

	interface GallerySelectOptionTemplate extends SafeHtmlTemplates {
		@Template("<option value=\"{0}\">{0}</option>")
		SafeHtml addOption(String option);
	}

	private static final GallerySelectOptionTemplate ITEM_TEMPLATE = GWT.create(GallerySelectOptionTemplate.class);

	private static final Logger logger = Logger.getLogger("AdminView");

	public AdminView(AdminActivity activity) {
		this.presenter = activity;

		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	protected void onLoad() {

		super.onLoad();

		presenter.getGalleriesAndUsers();

		$("#createUserButton").click(new Function() {
			@Override
			public boolean f(Event e) {
				presenter.createUser($("#username").val(), $("#password").val(), $("#password2").val());
				return false;
			}
		});

		$("#addUserToGalleryButton").click(new Function() {
			@Override
			public boolean f(Event e) {

				presenter.addUserToGallery($("#galleryUser").val(), $("#galleryName").val());

				return false;
			}
		});

		$("#removeUserFromGalleryButton").click(new Function() {
			@Override
			public boolean f(Event e) {

				presenter.removeUserFromGallery($("#galleryUser").val(), $("#galleryName").val());

				return false;
			}
		});

		$("#changeAdminPwdButton").click(new Function() {
			@Override
			public boolean f(Event e) {
				return false;
			}
		});

		$("#gallery").change(new Function() {
			@Override
			public boolean f(Event e, Object... arg) {

				String selection = $(e.getEventTarget()).val();

				String link = GWT.getHostPageBaseURL() + "gallery.html";

				$("#galleryLink").text(link);

				return false;
			}
		});
	}

	@Override
	protected void onUnload() {
		super.onUnload();

		$("#createUserButton").off();
		$("#addUserToGalleryButton").off();
		$("#changeAdminPwdButton").off();
		$("#gallery").off();
	}

	public void onGetGalleries(String[] galleries) {

		initSelection($("#galleryName"), galleries);

		initSelection($("#gallery"), galleries);
	}

	public void onGetUsers(String[] users) {

		initSelection($("#galleryUser"), users);

	}

	public void onAddUserToGallery() {
		logger.finest("onAddUserToGallery");

		InfoMessage.showMessage($("#addUserToGalleryButton").parent(), "Benutzer wurde hinzugef&uuml;gt.", 1000);
	}

	public void onFailure(String reason) {
		Window.alert(reason);

	}

	public void onCreateUser() {
		logger.finest("onCreateUser");

		resetUserFields();

		presenter.getGalleriesAndUsers();

		InfoMessage.showMessage($("#createUserButton").parent(), "Benutzer erfolgreich angelegt.", 1000);
	}

	public void onRemoveUserFromGallery() {
		logger.finest("onRemoveUserFromGallery");

		InfoMessage.showMessage($("#removeUserFromGalleryButton").parent(), "Benutzer wurde entfernt.", 1000);
	}

	private void resetUserFields() {
		$("#username").val("");
		$("#password").val("");
		$("#password2").val("");
	}

	public void onCreateUserFailure(String reason) {
		InfoMessage.showError($("#createUserButton").parent(), reason, 1000);

	}

	public void onRemoveUserFromGalleryFailure(String reason) {
		InfoMessage.showError($("#removeUserFromGalleryButton").parent(), reason, 1000);

	}

	public void onAddUserToGalleryFailure(String reason) {
		InfoMessage.showError($("#addUserToGalleryButton").parent(), reason, 1000);

	}

	private void initSelection(GQuery selector, String[] values) {
		selector.children().remove();
		$(ITEM_TEMPLATE.addOption("-")).appendTo(selector);
		for (String option : values) {
			$(ITEM_TEMPLATE.addOption(option)).appendTo(selector);
		}
	}
}
