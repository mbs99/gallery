package de.mbs.gallery.client.view;

import static com.google.gwt.query.client.GQuery.$;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.Function;
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
	
	private static AdminViewUiBinder uiBinder = GWT
			.create(AdminViewUiBinder.class);

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
				
				Window.alert($("#galleryName").val());
				
				presenter.addUserToGallery($("#galleryUser").val(),
						$("#galleryName").val());
				
				return false;
			}
		});
		
		$("#changeAdminPwdButton").click(new Function() {
			@Override
			public boolean f(Event e) {
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
	}

	public void onGetGalleries(String[] result) {
		for(String option : result) {
			$(ITEM_TEMPLATE.addOption(option)).appendTo($("#galleryName"));
		}
	}

	public void onGetUsers(String[] users) {
		for(String option : users) {
			$(ITEM_TEMPLATE.addOption(option)).appendTo($("#galleryUser"));
		}
		
	}

	public void onAddUserToGallery() {
		logger.finest("onAddUserToGallery");
	}

	public void onFailure(String reason) {
		Window.alert(reason);
		
	}

	public void onCreateUser() {
		logger.finest("onCreateUser");
		
	}
}
