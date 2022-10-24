package de.mbs.gallery.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.Function;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;
import de.mbs.gallery.client.Constants;
import de.mbs.gallery.client.activity.LoginActivity;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.gwt.query.client.GQuery.$;


public class LoginView extends Composite {

	public static final String ERROR_MSG_CONTAINER = "#errorMsgContainer";

	@UiField
	FormPanel loginViewPanel;

	LoginActivity presenter;
	
	private static final LoginViewUiBinder uiBinder = GWT
			.create(LoginViewUiBinder.class);

	interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {
	}
	
	private static final Logger logger = Logger.getLogger(LoginView.class.getSimpleName());
	
	interface ErrorMsgTemplate extends SafeHtmlTemplates {
		   @Template("<p class=\"errorMsg\">{0}</p>")
		   SafeHtml errorMsg(String msg);
		}
		
	ErrorMsgTemplate errorMsgTemplate = GWT.create(ErrorMsgTemplate.class);
	
	interface SelectOptionTemplate extends SafeHtmlTemplates {
		   @Template("<option value=\"{0}\">{0}</option>")
		   SafeHtml option(String galleryName);
		}
		
	SelectOptionTemplate selectOptionTemplate = GWT.create(SelectOptionTemplate.class);

	public LoginView(LoginActivity activity) {
		this.presenter = activity;
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	protected void onLoad() {
		
		super.onLoad();
		
		$("#loginButton").click(new Function() {
			@Override
			public boolean f(Event e) {
				
				$(ERROR_MSG_CONTAINER).children().remove();
				
				String user = $("#username").val();
				String pwd = $("#password").val();
				
				presenter.authorize(user, pwd);
				
				return false;
			}
		});

		$("#gallery").change(new Function() {
			@Override
			public boolean f(Event e) {

				String option = $(e.getEventTarget()).val();
				if(! option.equals("off")) {
					presenter.showGallery(option);
				}

				return false;
			}
		});
		
		$("#selectGalleryContainer").hide();
	}

	public void onLoginFailure(String reason) {
		
		logger.log(Level.SEVERE, () -> "onLoginFailure: " + reason);
		
		$(errorMsgTemplate.errorMsg(reason)).appendTo($(ERROR_MSG_CONTAINER));
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				$(ERROR_MSG_CONTAINER).children().remove();
				
			}
		};
		timer.schedule(Constants.SHOW_ERROR_SCHEDULE_TIME_IN_MS);
		
	}

	public void onLoginSuccess(String[] result) {
		
		for(String iter : result) {
			$("#gallery").append($(selectOptionTemplate.option(iter)));
		}
		
		$("#selectGalleryContainer").show();
	}
}
