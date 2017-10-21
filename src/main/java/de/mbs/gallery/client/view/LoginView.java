package de.mbs.gallery.client.view;

import static com.google.gwt.query.client.GQuery.$;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;

import de.mbs.gallery.client.activity.LoginActivity;


public class LoginView extends Composite {
	
	@UiField
	FormPanel loginViewPanel;
	
	LoginActivity presenter;
	
	private static LoginViewUiBinder uiBinder = GWT
			.create(LoginViewUiBinder.class);

	interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {
	}
	
	private static final Logger logger = Logger.getLogger("LoginView");

	public LoginView(LoginActivity activity) {
		this.presenter = activity;
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	protected void onLoad() {
		
		super.onLoad();
		
		$(loginViewPanel).submit(new Function() {
			@Override
			public boolean f(Event e) {
				
				String user = $("#username").val();
				String pwd = $("#password").val();
				
				presenter.authorize(user, pwd);
				
				return false;
			}
		});
	}
}
