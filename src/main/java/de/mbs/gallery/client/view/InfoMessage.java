package de.mbs.gallery.client.view;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Timer;

public class InfoMessage {
	
	interface InfoMsgTemplate extends SafeHtmlTemplates {
		@Template("<span class=\"infoMsg\" id=\"{1}\">{0}</span>")
		SafeHtml msg(String msg, String id);
	}
	
	private static final InfoMsgTemplate MSG_TEMPLATE = GWT.create(InfoMsgTemplate.class);
	
	interface ErrorMsgTemplate extends SafeHtmlTemplates {
		@Template("<span class=\"infoMsg errorMsg\" id=\"{1}\">{0}</span>")
		SafeHtml msg(String msg, String id);
	}
	
	private static final ErrorMsgTemplate ERROR_TEMPLATE = GWT.create(ErrorMsgTemplate.class);
	

	public static void showMessage(GQuery parentSelector, String msg, int delayInMs) {
		
		String id = "infoMsg" + msg.hashCode();
		
		parentSelector.append(MSG_TEMPLATE.msg(msg, id));

		Timer timer = new Timer() {

			@Override
			public void run() {
				$("#" + id).remove();

			}
		};

		timer.schedule(delayInMs);
	}
	
	public static void showError(GQuery parentSelector, String msg, int delayInMs) {
		
		String id = "errorMsg" + msg.hashCode();
		
		parentSelector.append(ERROR_TEMPLATE.msg(msg, id));

		Timer timer = new Timer() {

			@Override
			public void run() {
				$("#" + id).remove();

			}
		};

		timer.schedule(delayInMs);
	}
}
